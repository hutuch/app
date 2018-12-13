# datasource
  实现主从数据库的分离.
  核心是MultiDataSource继承AbstractRoutingDataSource,设置读写库,并重写determineCurrentLookupKey()方法

# gateway
  主要测试spring与线程的数据
  1.实现aop的日志打印
  2.使用spring spl注入值并测试@PostConstruct,@PreDestroy注解
  3.测试spring注入静态成员,实现自定义事件的发布
  4.基于rpc,测试自定义的rpc-starter
  5.实现spring的启动任务与定时任务
  6.配置ThreadPoolExecutor

# httpclient
  基于Apache httpclient 实现get/post/upload请求,并解决http登录

# mongo
  测试MongoRepository实现增删改查与分页
  测试MongoTemplate实现模糊查找与排序

# redis
  springboot配置单机redis,主从reids,集群redis,并使用stringRedisTemplate,RedisTemplate进行测试
  
# tkmybatis
  基于springboot整合tkmybatis,mybatis,同事测试Spring @Transaction注解失效情况

# valid
  基于javax.valid注解,使用hibernate-valid实现参数的校验

# web
  整合Springboot与thymeleaf,同事实现ControllerAdvice的统一异常管理
  
# webFlux
  使用springboot2.0的特色webFlux风格,实现nio与lambda的书写

  