package com.chqiuu.study.swagger.export.service;

import com.chqiuu.study.swagger.export.dto.Table;

import java.util.List;

/**
 * @author chqiu
 */
public interface ExportService {
    /**
     * 根据API url获取API结构化实体类
     *
     * @param swaggerUrl API url
     * @return API结构化实体类
     */
    List<Table> tableList(String swaggerUrl);
}
