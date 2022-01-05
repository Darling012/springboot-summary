

#### 一个脚手架应该具备哪些部分？

##### 大致两类：

1. crud业务代码相关如异常处理、日志、统一出入参处理等
2. 集成其他组件如flyway、swagger等

#### 细分：

##### 单体内部：

1. 统一参数校验
2. 统一状态码设计
3. 统一返回体处理
4. 统一异常处理
5. 统一日志处理
6. 统一pojo属性设计
7. 统一pojo转换
8. 提供一个线程池：异步执行、定时任务、事件监听
9. 提供切面处理


##### 集成组件：

1. 统一API文档
2. 数据库更新迁移
3. 缓存
4. 定时任务，批处理

#### 统一异常处理

##### 异常处理原则：

##### http:

##### 业务代码：

##### 线程池：

#### 提供一个线程池：异步执行、定时任务、事件监听

阿里规范不允许直接建线程，要自建线程池，项目中额外线程需求并不高，所以提供一个线程池满足异步需求。spring 提供的ThreadPoolTaskExecutor，可直接注入使用， 默认线程数量为cpu核心数。

##### 定时任务

springtask默认单线程串行执行，可通过配置SchedulingConfigurer实现并行任务，再加上@Async实现并行异步任务（任务里的方法设置为异步）。只加@Async也能实现并行。

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

​    

filter ->servlet-> interceptor -> controllerAdvice -> aspect  -> controller



   



