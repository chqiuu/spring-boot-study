package com.chqiuu.study.redis;

/**
 * 全局静态常量类
 */
public class Constant {
    /**
     * 配置rateLimit的前缀
     */
    public static final String PREFIX = "current-limit";
    /**
     * 集群模式指定slot的hash tag
     */
    public static final String HASH_TAG = "{current-limit}";
    /**
     * hash tag 前缀
     */
    public static final String HASH_TAG_PRFIX = "{";
    /**
     * hash tag 后缀
     */
    public static final String HASH_TAG_SUFFIX = "}";
    /**
     * 自定义拦截方式时的key
     */
    public static final String CUSTOM = "current-limit-custom";
}
