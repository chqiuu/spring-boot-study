# swagger2-export

介绍
-----------------------------------

用于将Swagger2在线API文档导出为离线文档 Word/PDF/HTML


技术架构：
-----------------------------------

#### 后端
- 基础框架：Spring Boot 2.1.3.RELEASE
- 日志打印：logback
- 其他：fastjson，lombok（简化代码）等。

#### 前端
 
- bootstrap
- thymeleaf

#### 开发环境

- 语言：Java 8

- IDE(JAVA)： Eclipse安装lombok插件 或者 IDEA

- IDE(前端)： WebStorm 或者 IDEA

- 依赖管理：Maven

使用说明
-----------------------------------

1. 运行项目
2. 访问：http://localhost/
3. 输入框中输入Swagger Api JSON的url 如：https://petstore.swagger.io/v2/swagger.json
4. 点击“提交”按钮，在新页面中及生成格式化的文档，Ctrl+A、Ctrl+C拷贝到指定Word文件中即可；
