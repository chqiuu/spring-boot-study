package com.chqiuu.study.swagger.export.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 参数名
     */
    private String name;

    /**
     * 数据类型
     */
    private String type;

    /**
     * 参数类型
     */
    private String paramType;

    /**
     * 是否必填
     */
    private Boolean require;

    /**
     * 说明
     */
    private String remark;
}
