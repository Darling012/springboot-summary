1. 解析Excel，数据正确性校验
   1. 能正确解析的数据parseSuccessList
   2. 解析失败的数据failList,失败原因
2. parseSuccessList进行业务性校验
   1. 符合业务规则可入库的数据readyList
   2. 不符合业务规则的数据放入failList,失败原因
3. readyList进行入库
   1. 成功
   2. 失败数据放入failList,失败原因

PS：

1. 数据业务正确性判断，一种情况是业务复杂，牵扯多张表，这种情况必须在代码里组织业务判断。还有一种是较为简单的，比如字段唯一性判断，这种情况，**依赖数据库唯一索引判断还是写代码判断？**
   1. https://www.zhihu.com/question/39967106
   2. https://webcache.googleusercontent.com/search?q=cache:f_s8dqYHS9wJ:https://www.v2ex.com/t/619930+&cd=1&hl=zh-CN&ct=clnk&gl=us
   3. https://www.cnblogs.com/buguge/p/15113553.html
   4. 阿里索引规约
   5. mybatisplus 唯一索引与逻辑删除
   6. https://blog.csdn.net/codingtu/article/details/86683000
   7. https://developer.51cto.com/article/704511.html
2. 以及在以往代码里入库操作往往统一切面处理异常，是否有必要针对jdbc异常或数据库异常进行异常处理？感觉对于前端没有必要，后台日志有必要细分这些数据库异常。
3. 这里还有一个点是当前业务流程为A-》B-》C,那么BC的业务性校验是否有必要提前到入口A处？假如提到A处就会出现耦合，假如不提前，若A处有数据入库就要做补偿。把BC业务验证再提供一个接口，在A中先验证数据，再执行业务。
