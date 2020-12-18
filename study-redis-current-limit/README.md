# study-redis-current-limit

> 此项目主要演示了 Spring Boot 项目如何通过 AOP 结合 Redis + Lua 脚本实现分布式限流，旨在保护 API 被恶意频繁访问的问题。

通过两种实现方式进行项目集成：
- 第一种通过注解方式，这种方式需要预先设置限流的各项参数，如间隔时间，可访问次数等，缺点就是不能灵活调整限流策略；
- 通过HashMap加载方式，

## 1. 主要代码

### 1.1. pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
      <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
               xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
          <modelVersion>4.0.0</modelVersion>
          <parent>
              <groupId>com.chqiuu.study</groupId>
              <artifactId>spring-boot-study</artifactId>
              <version>1.0.0</version>
          </parent>
          <artifactId>study-redis-current-limit</artifactId>
          <version>1.0.0</version>
          <name>study-redis-current-limit</name>
          <description>基于Redis网站接口限流实现</description>
      
          <dependencies>
              <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-starter-web</artifactId>
              </dependency>
              <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-starter-data-redis</artifactId>
              </dependency>
              <dependency>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-starter-aop</artifactId>
              </dependency>
              <dependency>
                  <groupId>org.apache.commons</groupId>
                  <artifactId>commons-pool2</artifactId>
                  <version>2.9.0</version>
              </dependency>
              <dependency>
                  <groupId>com.google.guava</groupId>
                  <artifactId>guava</artifactId>
                  <version>29.0-jre</version>
              </dependency>
          </dependencies>
      
          <build>
              <plugins>
                  <plugin>
                      <groupId>org.springframework.boot</groupId>
                      <artifactId>spring-boot-maven-plugin</artifactId>
                  </plugin>
              </plugins>
          </build>
      </project>
```
## 2. 测试

## 3. 参考

- [厉害了，原来 Redisson 这么好用！](https://juejin.cn/post/6844903962521387015)
- [Redisson最新版starter模式集成](https://blog.csdn.net/yaomingyang/article/details/104965570)
