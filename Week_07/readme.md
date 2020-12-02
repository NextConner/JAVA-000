### 第七周必做作业

---

### 作业说明:

- **周四**：100万数据插入 : 
  - 这个没做好，只是用 JPA 的批量 save 插入了100万数据，简单记录了一下时间
- **周六**
  - **读写分离** ： 主要是配置多个数据源，通过自定义注解和切面拦截注解方法，获取注解值，更换默认数据源使用，达到动态切换数据源的方式
    - annotation包下为自定义注解 `Source ` ，值为枚举类型的 `DataSourceType`，对应数据源的key
    - aop 下是注解的切面类`DataSourceAspect` ，拦截被注解方法，并根据注解值重新设置数据源
    - config 下的 `DataSourceConfiguration` 是多数据源配置类，配置了优先的动态数据源
    - common 下为实现 `AbstractRoutingDataSource` 的`DynamicDataSource`数据源类，通过覆盖`determineCurrentLookupKey`方法，指定当前应该获取的数据源对象信息。`DataSourceHolder`类为通过ThreadLocal 保存当前默认数据源信息的工具类，提供获取，修改默认数据源信息的静态方法
    - dao 下为`UserOrder，OrderDetail `对象的JPA 接口
    - service 下声明了`OrderService` 类只使用了JdbcTemplate 来确认多数据源是否生效；`OrderJPAService`类声明了使用`@Source`注解的方法，用来测试动态数据源切换是否生效；`ShardingOrderService`使用 `sharding-jdbc`的数据源配置，测试sharding-jdbc的读写分离
    - 数据源的测试在 `test`包的`DatasourceApplicationTests`类中，在没有配置主从同步情况下，测试了数据源的动态切换和`sharding-jdbc`的读写分离配置