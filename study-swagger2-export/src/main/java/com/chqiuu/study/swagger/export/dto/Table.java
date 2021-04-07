package com.chqiuu.study.swagger.export.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Table implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 大标题
     */
    private String title;
    /**
     * 小标题
     */
    private String tag;
    /**
     * url
     */
    private String url;
    /**
     * 描述
     */
    private String description;

    /**
     * 请求参数格式
     */
    private String requestForm;

    /**
     * 响应参数格式
     */
    private String responseForm;

    /**
     * 请求方式
     */
    private String requestType;
    /**
     * 请求体
     */
    private List<Request> requestList;
    /**
     * 返回体
     */
    private List<Response> responseList;
    /**
     * 请求参数
     */
    private String requestParam;
    /**
     * 返回参数
     */
    private String responseParam;
}
