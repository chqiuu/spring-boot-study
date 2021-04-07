package com.chqiuu.study.swagger.export.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chqiuu.study.swagger.export.dto.Request;
import com.chqiuu.study.swagger.export.dto.Response;
import com.chqiuu.study.swagger.export.dto.Table;
import com.chqiuu.study.swagger.export.service.ExportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @author chqiu
 */
@Slf4j
@Service
public class ExportServiceImpl implements ExportService {
    private static final String SUBSTR = "://";
    private final RestTemplate restTemplate;

    @Autowired
    public ExportServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * 根据API url获取API结构化实体类
     *
     * @param swaggerUrl API url
     * @return API结构化实体类
     */
    @Override
    public List<Table> tableList(String swaggerUrl) {
        List<Table> result = new ArrayList<>();
        try {
            String jsonStr = restTemplate.getForObject(swaggerUrl, String.class);
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);

            //得到 host 和 basePath，拼接访问路径
            // 取得最后一个指定字符串之前的字符串
            String host = StrUtil.subBefore(swaggerUrl, SUBSTR, false) + SUBSTR + jsonObject.getString("host") + jsonObject.getString("basePath");
            //解析paths
            JSONObject paths = jsonObject.getJSONObject("paths");
            if (paths != null) {
                // 1.请求路径URL
                for (String key : paths.keySet()) {
                    // 2.请求方式，类似为 get,post,delete,put 这样
                    String requestTypes = "";
                    JSONObject path = paths.getJSONObject(key);
                    for (String requestType : path.keySet()) {
                        requestTypes += requestType + ",";
                        JSONObject content = path.getJSONObject(requestType);
                        // 4. 大标题（类说明）
                        String title = String.valueOf(((List) content.get("tags")).get(0));
                        // 5.小标题 （方法说明）
                        String tag = String.valueOf(content.get("summary"));
                        // 6.接口描述
                        String description = String.valueOf(content.get("description"));
                        // 7.请求参数格式，类似于 multipart/form-data
                        StringBuilder requestForm = new StringBuilder();
                        JSONArray consumes = content.getJSONArray("consumes");
                        if (consumes != null && consumes.size() > 0) {
                            for (Object consume : consumes) {
                                requestForm.append(consume.toString()).append(",");
                            }
                        }
                        // 8.返回参数格式，类似于 application/json
                        StringBuilder responseForm = new StringBuilder();
                        JSONArray produces = content.getJSONArray("produces");
                        if (produces != null && produces.size() > 0) {
                            for (Object produce : produces) {
                                responseForm.append(produce.toString()).append(",");
                            }
                        }
                        // 9. 请求体
                        List<Request> requestList = new ArrayList<>();
                        JSONArray parameters = content.getJSONArray("parameters");
                        if (parameters != null && parameters.size() > 0) {
                            for (int i = 0; i < parameters.size(); i++) {
                                Request request = new Request();
                                JSONObject param = parameters.getJSONObject(i);
                                request.setName(param.getString("name"));
                                request.setType(param.getString("type") == null ? "Object" : param.getString("type"));
                                request.setParamType(param.getString("in"));
                                request.setRequire(param.getBoolean("required"));
                                request.setRemark(param.getString("description"));
                                requestList.add(request);
                            }
                        }
                        // 10.返回体
                        List<Response> responseList = new ArrayList<>();
                        JSONObject responses = content.getJSONObject("responses");
                        for (String responseKey : responses.keySet()) {
                            Response response = new Response();
                            // 状态码 200 201 401 403 404 这样
                            response.setName(responseKey);
                            JSONObject statusInfos = responses.getJSONObject(responseKey);
                            if (statusInfos != null) {
                                if (statusInfos.getString("description") != null) {
                                    response.setDescription(statusInfos.getString("description"));
                                }
                                if (statusInfos.getString("description") != null) {
                                    response.setRemark(statusInfos.getString("description"));
                                }
                            }
                            responseList.add(response);
                        }

                        // 模拟一次HTTP请求,封装请求体和返回体
                        // 得到请求方式
                        Map<String, Object> paramMap = buildParamMap(requestList);
                        String buildUrl = buildUrl(host + key, requestList);

                        //封装Table
                        Table table = new Table();
                        table.setTitle(title);
                        table.setUrl(key);
                        table.setTag(tag);
                        table.setDescription(description);
                        table.setRequestForm(StrUtil.removeSuffix(requestForm.toString(), ","));
                        table.setResponseForm(StrUtil.removeSuffix(responseForm.toString(), ","));
                        table.setRequestType(StrUtil.removeSuffix(requestTypes, ","));
                        table.setRequestList(requestList);
                        table.setResponseList(responseList);
                        table.setRequestParam(paramMap.toString());
                        table.setResponseParam(doRestRequest(requestType, buildUrl, paramMap));
                        result.add(table);
                    }

                }
            }
        } catch (Exception e) {
            log.error("parse error", e);
        }
        return result;
    }


    /**
     * 重新构建url
     *
     * @param url
     * @param requestList
     * @return etc:http://localhost:8080/rest/delete?uuid={uuid}
     */
    private String buildUrl(String url, List<Request> requestList) {
        String param = "";
        if (requestList != null && requestList.size() > 0) {
            for (Request request : requestList) {
                String name = request.getName();
                param += name + "={" + name + "}&";
            }
        }
        if (StrUtil.isNotEmpty(param)) {
            url += "?" + StrUtil.removeSuffix(param, "&");
        }
        return url;

    }

    /**
     * 发送一个 Restful 请求
     *
     * @param restType "get", "head", "post", "put", "delete", "options", "patch"
     * @param url      资源地址
     * @param paramMap 参数
     * @return
     */
    private String doRestRequest(String restType, String url, Map<String, Object> paramMap) {
        Object object = new Object();
        try {
            switch (restType) {
                case "get":
                    object = restTemplate.getForObject(url, Object.class, paramMap);
                    break;
                case "post":
                    object = restTemplate.postForObject(url, paramMap, Object.class);
                    break;
                case "put":
                    restTemplate.put(url, null, paramMap);
                    break;
                case "head":
                    HttpHeaders httpHeaders = restTemplate.headForHeaders(url, paramMap);
                    return JSONObject.toJSONString(httpHeaders);
                case "delete":
                    restTemplate.delete(url, paramMap);
                    break;
                case "options":
                    Set<HttpMethod> httpMethodSet = restTemplate.optionsForAllow(url, paramMap);
                    return JSONObject.toJSONString(httpMethodSet);
                case "patch":
                    object = restTemplate.execute(url, HttpMethod.PATCH, null, null, paramMap);
                    break;
                case "trace":
                    object = restTemplate.execute(url, HttpMethod.TRACE, null, null, paramMap);
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            // 无法使用 restTemplate 发送的请求，返回""
            // ex.printStackTrace();
            return "";
        }
        return JSONObject.toJSONString(object);
    }

    /**
     * 封装post请求体
     *
     * @param list
     * @return
     */
    private Map<String, Object> buildParamMap(List<Request> list) {
        Map<String, Object> map = new HashMap<>(8);
        if (list != null && list.size() > 0) {
            for (Request request : list) {
                String name = request.getName();
                String type = request.getType();
                switch (type) {
                    case "string":
                        map.put(name, "string");
                        break;
                    case "integer":
                        map.put(name, 0);
                        break;
                    case "number":
                        map.put(name, 0.0);
                        break;
                    case "boolean":
                        map.put(name, true);
                    default:
                        map.put(name, null);
                        break;
                }
            }
        }
        return map;
    }
}
