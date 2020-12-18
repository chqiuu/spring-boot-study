# study-redisson

> 此项目主要演示了 Spring Boot 项目如何集成 Redisson 

Redis 是最流行的 NoSQL 数据库解决方案之一，而 Java 是世界上最流行（注意，我没有说“最好”）的编程语言之一。虽然两者看起来很自然地在一起“工作”，但是要知道，Redis 其实并没有对 Java 提供原生支持。
相反，作为 Java 开发人员，我们若想在程序中集成 Redis，必须使用 Redis 的第三方库。而 Redisson 就是用于在 Java 程序中操作 Redis 的库，它使得我们可以在程序中轻松地使用 Redis。Redisson 在 java.util 中常用接口的基础上，为我们提供了一系列具有分布式特性的工具类。
在这篇文章里，我会给你们介绍 Redisson 的一些常见用例，请跟随我一起来看看吧。

## 1. 主要代码

### 1.1. pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.4.1</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.chqiuu.study</groupId>
    <artifactId>study-redisson</artifactId>
    <version>1.0.0</version>
    <name>study-redisson</name>
    <description>Spring Boot 集成 Redisson</description>

    <dependencies>
        <!-- Redis-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <dependency>
            <groupId>org.redisson</groupId>
            <artifactId>redisson-spring-boot-starter</artifactId>
            <version>3.14.0</version>
            <exclusions>
                <exclusion>
                    <artifactId>javassist</artifactId>
                    <groupId>org.javassist</groupId>
                </exclusion>
            </exclusions>
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
