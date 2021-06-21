@[TOC](目录)

<hr style=" border:solid; width:100px; height:1px;" color=#000000 size=1">

# 前言
在实际项目开发过程中，我们有时候需要让项目在启动时执行特定方法。如要实现这些功能：
1. 提前加载相应的数据到缓存中；
2. 检查当前项目运行环境；
3. 检查程序授权信息，若未授权则不能使用后续功能；
4. 执行某个特定方法；


<hr style=" border:solid; width:100px; height:1px;" color=#000000 size=1">

# 实现方式
那么实现提前加载的方式有哪些呢？接下来我为大家介绍七种实现方式，按照执行顺序进行介绍。
## 1.实现ServletContextListener接口contextInitialized方法
<font color=#999AAA >代码如下（示例）：
```java
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

@Slf4j
@Component
public class ServletContextListenerImpl implements ServletContextListener {
	/**
     * 静态代码块会在依赖注入后自动执行,并优先执行
     */
    static{
        log.info("启动时自动执行 静态代码块");
    }
    /**
     * 在初始化Web应用程序中的任何过滤器或Servlet之前，将通知所有ServletContextListener上下文初始化。
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("启动时自动执行 ServletContextListener 的 contextInitialized 方法");
    }
}
```
**注意：该方法会在填充完普通Bean的属性，但是还没有进行Bean的初始化之前执行**

## 2.静态代码块方式
将要执行的方法所在的类交个Spring容器扫描(@Component)，在类中添加静态代码块，这样在Spring在扫描这类时候就会自动执行静态代码，达到代码自动运行的效果。示例见👆。
## 3.@PostConstruct注解方式
将要执行的方法所在的类交个Spring容器扫描(@Component),并且在要执行的方法上添加@PostConstruct注解执行
<font color=#999AAA >代码如下（示例）：
```java
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class PostConstructTest {
    @PostConstruct
    public void postConstruct() {
        log.info("启动时自动执行  @PostConstruct 注解方法");
    }
}
```

## 4. 实现ServletContextAware接口setServletContext 方法
<font color=#999AAA >代码如下（示例）：
```java
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

@Slf4j
@Component
public class ServletContextAwareImpl implements ServletContextAware {
    /**
     * 在填充普通bean属性之后但在初始化之前调用
     * 类似于InitializingBean's 的 afterPropertiesSet 或自定义init方法的回调
     */
    @Override
    public void setServletContext(ServletContext servletContext) {
        log.info("启动时自动执行 ServletContextAware 的 setServletContext 方法");
    }
}
```

## 5. @EventListener方式
将要执行的方法所在的类交个Spring容器扫描(@Component),并且在要执行的方法上添加@EventListener注解执行

<font color=#999AAA >代码如下（示例）：
```java
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventListenerTest {
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("启动时自动执行  @EventListener 注解方法");
    }
}

```

## 6. 实现ApplicationRunner接口run 方法
<font color=#999AAA >代码如下（示例）：
```java
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Slf4j
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    /**
     * 用于指示bean包含在SpringApplication中时应运行的接口。可以定义多个ApplicationRunner bean
     * 在同一应用程序上下文中，可以使用有序接口或@order注释对其进行排序。
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("启动时自动执行 ApplicationRunner 的 run 方法");

        Set<String> optionNames = args.getOptionNames();
        for (String optionName : optionNames) {
            log.info("这是传过来的参数[{}]", optionName);
        }
        String[] sourceArgs = args.getSourceArgs();
        for (String sourceArg : sourceArgs) {
            log.info("这是传过来sourceArgs[{}]", sourceArg);
        }
    }
}
```


## 7.实现CommandLineRunner接口run 方法
<font color=#999AAA >代码如下（示例）：
```java
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("启动时自动执行 CommandLineRunner 的 run 方法");
    }
}
```
<hr style=" border:solid; width:100px; height:1px;" color=#000000 size=1">

# 以上几种方式的执行顺序
以上几种方式的执行顺序如下图所示：
![方式加载的先后顺序](https://img-blog.csdnimg.cn/20210621154122444.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L1FJVTE3NjE2MTY1MA==,size_16,color_FFFFFF,t_70)
# 总结
以上介绍了SpringBoot项目中，启动项目时候自动执行的方法汇总，你还知道其他的方式吗？欢迎补充哟！！！请求大家一键三连，谢谢各位看官。