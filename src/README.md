

#### 一个脚手架应该具备哪些部分？

##### 大致两类：

1. crud业务代码相关如异常处理、日志、统一出入参处理等
2. 集成其他组件如flyway、swagger等

#### 细分：

##### 单体内部：

1. [统一参数校验](#统一参数校验)
2. [统一状态码设计](#统一状态码设计)
3. [统一返回体处理](#统一返回体处理)
4. [统一异常处理](#统一异常处理)
5. [统一日志处理](#统一日志处理)
6. [统一pojo属性设计](#统一pojo属性设计)
7. [统一pojo转换](#统一pojo转换)
8. [提供一个线程池：异步执行、定时任务、事件监听](#提供一个线程池：异步执行、定时任务、事件监听)
9. [提供切面处理](#提供切面处理)


##### 集成组件：

1. 统一API文档
2. 数据库更新迁移
3. 缓存
4. 定时任务，批处理

#### 统一参数校验

spring 和 jsr提供的注解异同及校验时机？

1. Validator  解决参数校验
2. Spring Assert  解决业务校验
3. optional 辅助解决

#### 统一异常处理

##### 自定义异常处理器

1. HandlerExceptionResolver#resolveException
2. 注解
3. 当两种方式都实现时，HandlerExceptionResolver要先于ControllerAdvice执行
4. 404等，继承AbstractErrorController、ResponseEntityExceptionHandler

##### 异常处理原则：

##### http:

##### 业务代码：

##### 线程池：

##### 流程

1. ExceptionHandlerMethodResolver： 此缓存Map存放了@ControllerAdvice中所有注解了@ExceptionHandler的方法，其中@ExceptionHandler的value也就是Exception做为Key，值为当前Method
2. exceptionHandlerAdviceCache： key为标注了@ControllerAdvice的类，value为ExceptionHandlerMethodResolver
3. SpringMVC通过HandlerExceptionResolver的resolveException调用实现类的实际实现方法doResolveException
4. doResolveException方法实际调用ExceptionHandlerExceptionResolver的doResolveHandlerMethodException方法。
5. 根据类型进行匹配，匹配不到getCause（）再匹配，匹配多个排序后取第一个

#### 统一日志处理

问题：

1. 跨线程　　TaskDecorator
2. 跨jvm 　　http调用拦截器

##### 日志分类：

1.



#### 提供一个线程池：异步执行、定时任务、事件监听

Spring提供了多种线程池：

- `SimpleAsyncTaskExecutor`：不是真的线程池，这个类不重用线程，每次调用都会创建一个新的线程。
- `SyncTaskExecutor`：这个类没有实现异步调用，只是一个同步操作。只适用于不需要多线程的地
- `ConcurrentTaskExecutor`：Executor的适配类，不推荐使用。如果ThreadPoolTaskExecutor不满足要求时，才用考虑使用这个类
- `ThreadPoolTaskScheduler`：可以使用cron表达式
- `ThreadPoolTaskExecutor` ：最常使用，推荐。其实质是对java.util.concurrent.ThreadPoolExecutor的包装

阿里规范不允许直接建线程，要自建线程池，项目中额外线程需求并不高，所以提供一个线程池满足异步需求。spring 提供的ThreadPoolTaskExecutor，可直接注入使用， 默认线程数量为cpu核心数。

##### 定时任务

springtask默认单线程串行执行，可通过配置SchedulingConfigurer实现并行任务（ThreadPoolTaskScheduler，可注入的线程池为XXXScheduler），即不同任务方法用不同线程，解决不能同时执行不同任务。加上@Async实现并行异步任务（另起线程执行）解决单个任务需要间隔三秒，但执行了五秒，上个任务影响下个任务问题。只加@Async也能实现并行。

即ThreadPoolTaskScheduler解决了调用定时任务时，当存在多个任务，提供了不同线程。

@Async是异步执行，无论各个任务，还是单个任务不同循环，都是另起线程，所以实现了并行。要注意极端情况线程池没线程可用。

##### 事件监听

spring事件监听默认同步阻塞，实现异步借助@Async。

##### 异步执行

使用@Async，默认实现为SimpleAsyncTaskExecutor，每次都会开启一个子线程消耗大。若自定义线程池类型为ThreadPoolTaskExecutor则会替换默认，若不是需指定否则还会用默认。



#### 提供切面处理

拦截器、过滤器、监听器

##### 过滤器

​	servlet规范，函数回调实现，只能在servlet容器中使用。进入servlet前预处理和返回前处理，判断URL、读取session、权限判断等。

##### 监听器

​    servlet规范，用于监听servletContext、HttpSession和servletRequest等域对象的创建和销毁事件。统计在线人数、访问路径等。

##### 拦截器

​	spring提供，AOP，基于反射。

##### 切面Aspect

​	最灵活

##### advice



filter ->servlet-> interceptor -> controllerAdvice -> aspect  -> controller







#### rpc

```java
        GenericResponse response = null;
        try {
            response = HttpClint.invoke();
        } catch (Exception exception) {
            // httpClient调用产生的异常
            log.error();
            handlerErrorAndThrowExcetption(exception);
        }
        // 调用成功返回结果
        if (response.code != 200) {
            handlerErrorAndThrowExcetption();
        }
        Pojo pojo = response.getData();
```

**能否用布尔值代替void作为返回值？**

https://www.zhihu.com/question/321451061

不行

1. 语义混淆，假如单指程序是否成功运行，那false的时候，就会包含多种情况，不明确。
2. 对于确实不需要返回的函数它是过度设计了，对于 false 不能明确失败原因的场景，还是要引入异常或其他机制，而代码逻辑因此要分散到返回值和异常两个不同的执行路径，无谓增加圈复杂度。那还不如统一用异常好了，或者类似 Go 的做法，返回 （result, error)。
3. 就是函数的调用方只知道是否成功，但是并不知道失败的原因是什么。所以 Go 和 Lua 的惯用手法除了布尔值表示成功失败以外，还会有个错误原因的额外返回值。WIN32 API则要么提供`GetLastError` ，要么用的`HRESULT` 代替布尔值来提供异常原因。
4. 测试单元的方法 明确要求是void的
5. 如果确定函数内不会有问题或意外发生的，那返回true或false没意义，如果会有意外发生的，那true或false不足够携带所有信息



##### 讨论Rpc情况

既然不能用布尔值代替，那么void函数执行失败应该抛出异常。在多模块情况下，就是当前模块抛出异常，统一捕获封装。调用模块判断结果，若失败则抛出异常。即上边模板所示流程。

先讨论一下第一步httpClent的调用产生的异常，若此处不捕获，那么向外抛出，advice统一异常处理是可以捕获到的。在配合ribbion、熔断后，以及fegin自定义配置，猜测可捕获掉调用异常，如重试，但最后猜测都应该将异常传递到request线程，抛出由统一异常捕获返回前台。k12中，在统一异常处理中，捕获了RetryableException，k12用的是fegin自定义重试。且利用fegin的ErrorDecoder对异常进行了封装，并再次抛出异常，以在调用模块统一异常处理中捕获（用请求头判断了是否为fegin请求，但未看明白使用）。

第二步判断调用结果，k12中在fegin统一处理了失败请求，再次抛出异常，但这样统一处理，没法细分业务，根据返回信息特定处理业务。

