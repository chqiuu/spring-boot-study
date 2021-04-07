package com.chqiuu.study.swagger.export.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * API 文档概述
 */
@Data
public class Overview implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 项目域名
     */
    private String host;

    /**
     * 项目根名称
     */
    private String basePath;

    /**
     * 项目名称
     */
    private String title;
    /**
     * 项目描述
     */
    private String description;
    /**
     * API版本号
     */
    private String version;
    /**
     * 服务协议
     */
    private String termsOfService;
    /**
     * 联系人
     */
    private String contactName;
    /**
     * 联系人URL
     */
    private String contactUrl;
    /**
     * 联系人邮箱
     */
    private String contactEmail;
    /**
     * 许可证名称
     */
    private String licenseName;
    /**
     * 许可证的URL
     */
    private String licenseUrl;
}
