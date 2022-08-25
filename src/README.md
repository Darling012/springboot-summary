

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

#### 统一参数合法性校验

spring 和 jsr提供的注解异同及校验时机？

1. Validator  解决参数校验
2. Spring Assert  解决业务校验
3. optional 辅助解决

[参数业务逻辑校验](#数据业务逻辑校验)

[NPE](#NPE)

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
存在在异步线程中获取不到request情况，因为通过@async注解的方法，会被springboot丢到线程池中去执行，就等于开启了新的线程； 但是RequestContextHolder使用ThreadLocal保存request实例的，那么如果在新的线程中肯定会获取不到request的。建议在调用异步方法的时候，将request作为参数传递到异步方法中
1. https://mp.weixin.qq.com/s/eu6cpP7rpjZvf4-nl3K7uw
2. https://blog.51cto.com/u_15127686/2832738
3. https://blog.csdn.net/jiao_zg/article/details/121030592
4. https://blog.csdn.net/wzy_168/article/details/109182301
5. 
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

#### excel导入数据
1. 解析Excel，数据正确性校验
   1. 能正确解析的数据parseSuccessList
   2. 解析失败的数据failList,失败原因
2. parseSuccessList进行业务性校验
   1. 符合业务规则可入库的数据readyList
   2. 不符合业务规则的数据放入failList,失败原因
3. readyList进行入库
   1. 成功
   2. 失败数据放入failList,失败原因

[mybatisPlus批量操作]()

[多线程事务]()

PS：
[数据校验问题](#数据业务逻辑校验)，无业务的必填长短等校验通过Java Bean Validation，从各层中抽取出来在bean上做。
有业务逻辑的校验如数据唯一性，目前看要提取出校验接口然后调用比较合理。像新增学生只涉及一个表操作的，无论数据来源web还是excel导入还是微信同步，都能在新增学生接口控制字段唯一，没有提前业务验证到三个入口的必要。业务验证前置的是因为涉及多个表多个模块避免做补偿。

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


### 数据业务逻辑校验
只涉及一张表的，下沉到底层业务接口就可以，也符合高内聚，下边主要讨论一个请求多个模块多张表，以期用业务验证前置避免进行数据补偿来快速失败。业务验证通过，后续代码问题数据库问题网络问题等通过分布式事务解决。
1. 数据业务正确性判断，一种情况是业务复杂，牵扯多张表，这种情况必须在代码里组织业务判断。还有一种是较为简单的，比如字段唯一性判断，这种情况，**依赖数据库唯一索引判断还是写代码判断？**以及并发下，代码唯一字段为真，但新增多条数据情况（幂等性）
   1. https://www.zhihu.com/question/39967106 数据库规范，写的很全

   2. https://webcache.googleusercontent.com/search?q=cache:f_s8dqYHS9wJ:https://www.v2ex.com/t/619930+&cd=1&hl=zh-CN&ct=clnk&gl=us

      1. > 1.  并发。要自己实现，要控制并发和加锁。
         > 2.  索引。唯一性的校验取出的数据没有索引，没有数据库效率高。
         > 3.  性能。每次插入更新之前都要查一次数据库，好像也没有减少数据库的性能消耗。还不如让数据库系统自己校验。

      2. > 数据库唯一约束,分表无法使用

      3. > 代码判断要做，有问题直接报错返回
         > 数据库约束也要加，遇到并发 /业务漏洞可以兜底

   3. https://www.cnblogs.com/buguge/p/15113553.html 唯一索引是兜底，也要在代码里加分布式锁解决

   4. 阿里索引规约 提到做了代码校验也要用唯一索引

   5. mybatisplus 唯一索引与逻辑删除 逻辑删除时delflag值为id，以解决若delflag删除为1，不能重复删除情况

   6. https://blog.csdn.net/codingtu/article/details/86683000 联合唯一索引，若某个字段为null，可重复插入

   7. https://developer.51cto.com/article/704511.html 并发场景下唯一性字段判断失效，如何幂等

2. 以及在以往代码里入库操作往往统一切面处理异常，是否有必要针对jdbc异常或数据库异常进行异常处理？感觉对于前端没有必要，后台日志有必要细分这些数据库异常。
   
1. 如果是java程序需要捕获：DuplicateKeyException异常，如果使用了spring框架还需要捕获：MySQLIntegrityConstraintViolationException异常。
   
3. 这里还有一个点是当前业务流程为A-》B-》C,那么BC的业务性校验是否有必要提前到入口A处？假如提到A处就会出现耦合，也存在别的地方调BC接口情况。假如不提前，若A处有数据入库就要做补偿。把BC业务验证再提供一个接口，在A中先验证数据，再执行业务。另一个思路是DDD,pojo中包含自验证方法。



第一个问题假若只依赖唯一索引，若多模块则有可能出现需要补偿的情况，AB正常存入C失败。同样为了避免补偿，第三个问题也应该是在入口处校验，是通过接口还是Pojo自包含验证方法，还需再对比一下。

pojo自包含验证方法：

1. Pojo为提供给其他模块的API，只有API接口和Pojo，但是验证逻辑需要数据库交互。
2. 不可避免，还是需要提供验证API接口
3. DDD的服务编排层不清楚
还有就是《凤凰架构》中提到的自定义Bean Validation ，这跟DDD的Domain primitive差不多，但同样解决不了验证前置这种情况。业务验证必然与数据库产生交互，也就是怎么将B模块数据库与A不产生强耦合，目前看只能是提供接口解耦。

提取验证方法为接口：
1. 在A处，调用BC的验证接口，为了保证A接口并发，以及多服务实例A接口并发，要加分布式锁。但也会存在D模块调用BC接口情况，这种情况依靠简单的AD入口的分布式锁控制不住。
2. 不仅仅在AD处验证，在BC入口同样再次验证
   1. 当锁下沉到最基础的接口，锁粒度越细，并发控制越好
   2. 但同样，对于入口接口来说不好控制，可能要做补偿，涉及分布式事务。

针对于上述多个入口调用同一底层接口来说，应该考虑接口设计是否合理。为什么会出现多个入口情况，以及真有这种场景时，如日志，那么是否有必要进行业务校验。


新建考试过程，一场考试创建成功需满足实验室可用，老师学生可参加，都需要校验时间是否冲突

1. 在提供可选取实验室，老师学生数据的时候，就进行一次数据筛选，这样保证了在当前操作下数据是可用的
2. 在点击最后保存进入后台接口进行业务数据校验
3. 并发情况下，在validate校验后，获取分布式锁，进行业务逻辑校验。
4. 分布式事务

分布式锁和分布式事务分别解决了什么问题？
1. https://zhuanlan.zhihu.com/p/268454670
2. https://blog.csdn.net/xingsfdz/article/details/81105683
3. https://blog.csdn.net/Shockang/article/details/115610063

### 缓存

#### mybatis

1. 一级缓存默认开启，事务开启后，同一个sqlSession下的get请求
2. 二级缓存可依赖

refrence

1. https://zhuanlan.zhihu.com/p/142794376
2. https://tech.meituan.com/2018/01/19/mybatis-cache.html

#### spring

#### 本地缓存

#### 集中式缓存

#### 双写一致性

### NPE

发生NPE的情况：

1. **在空对象上调用实例方法**。对空对象调用静态方法或类方法时，不会报 NPE，因为静态方法不需要实例来调用任何方法；
2. **访问或更改空对象上的任何变量或字段时**；
3. **抛出异常时抛出 null**；
4. **数组为 null 时，访问数组长度**；
5. **数组为 null 时，访问或更改数组的插槽**；
6. **对空对象进行同步或在同步块内使用 null**。
7. 参数是Integer等包装类，自动拆箱时
8. 字符串比较
9. 如ConcurrentHashMap这种不支持K.V为null的容器
10. A对象包含B对象，通过A对象的字段获得B对象后，没有判空B就调用B的方法
11. 方法或其它服务返回的List不是空而是null，没有进行判空就直接调用List的方法
12. 

##### 1. 查询

敏感信息处理，字典转换

###### 1. 分页查询

1. pojo中属性，哪个有值查哪个。注意时间段查询边界问题。

###### 2. 单个查询

##### 2.删除

级联删除业务问题

###### 1. 批量删除

###### 2. 单个删除

##### 3.新增

新增只需要保证不可重复的属性数据库中未存在即可。

1. 业务判断
   1. 不能跟表中数据重复

```Bash
carService.lambdaQuery()
        .eq(Car::getCarNum, carVO.getCarNum())
        .select(Car::getId)
        .oneOpt()
        .ifPresent(cars->{throw new BizException("车牌号已存在,请重新绑定");});
1. param中属性需要关联已有数据
BizUserBase bizUserBase = bizUserBaseService.lambdaQuery()
        .eq(BizUserBase::getId, bizUser.getBaseUserId())
        .select(BizUserBase::getPhone, BizUserBase::getSex,BizUserBase::getIdCard).oneOpt()
        .orElseThrow(()->  new BizException("不存在相关用户基础信息"));
```

1. copyProperties
2. save

##### 4. 修改

修改需要保证业务上具有唯一性的字段数据库中唯一，这就把前台修改分为两种：

1. 一种是非唯一字段修改
2. 一种唯一字段修改

我们不知道前台是哪种修改，但我们要保证数据库中唯一性字段的不重复。具体讨论为：

1. 用唯一字段去数据库查，没有查到数据。说明当前pojo修改了唯一字段的数据，且没有重复，允许修改。（唯一性字段相等没有数据）
2. 用唯一字段去数据库查，查到数据了，此时就要判断ID是否为它本身：
   1. 第一种是，ID相等，查出的来是它本身，情况为当前pojo没有修改唯一字段，修改了其他字段，允许修改；（唯一性字段相等，且ID相等）
   2. 第二种是，ID不相等，查出来的不是它本身，则当前pojo的唯一性字段为修改后的值且与数据库重复。不允许修改。（唯一性字段相等，且ID不相等）

**总结为**：若存在唯一性字段相等且ID不相等的数据，则重复。

1. 业务判断，某业务上具有唯一性属性修改后的数据不能跟已有数据重复

```Bash
carService.lambdaQuery().eq(Car::getCarNum, carVO.getCarNum()).select(Car::getId).oneOpt().ifPresent(cars->{
    if (!Objects.equals(cars.getId(), carVO.getId())){
       throw new BizException("车牌号已存在,本次绑定无效");
    }
});
```

1. copyProperties
2. save

refrence

1. https://blessing.blog.csdn.net/article/details/109631779
2. https://blog.csdn.net/qq_45893748/article/details/118668620
4. https://blog.csdn.net/Samurai_L/article/details/102859598
5. https://www.jianshu.com/p/a187bffcfe1c

mybatisplus 的ne  简化

### 接口对接

##### 问题

1. 如何保证数据在传输过程中的安全性
2. 数据已经到达服务器端，服务器端如何识别数据，如何不被攻击

##### 问题1解决

1. https
2. 非对称加密

### mybatisPlus批量操作

1. 单条sql，批量提交
   1. 需要开启数据库批量提交rewriteBatchedStatements=true
   2. mybatisplus的saveOrUpdateBatch会生成一条查询sql
   3. https://www.jianshu.com/p/7eb8eec78b9a
   4. https://blog.csdn.net/qq271859852/article/details/79562262
   5. https://www.jianshu.com/p/04d3d235cb9f
   6. https://github.com/baomidou/mybatis-plus/issues/2456
   7. https://github.com/baomidou/mybatis-plus/issues/2786
2. 大sql更新
   1. insert into user(id, name, age) values (1, "a", 17), (2,"b", 18)
      1. https://cloud.tencent.com/developer/article/1886324
   2. 数据量过大或字段过多造成拼接sql过长问题
      1. 改参数

##### mySql执行以上两种sql效率分析

1. https://segmentfault.com/a/1190000008890065

#### 逻辑删除与唯一索引冲突

https://baobao555.tech/archives/39

https://chsm1998.github.io/2020/08/29/logical-deletion-and-unique-index/

