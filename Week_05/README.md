### Week05作业

---

1. 项目 `homework`包含了第一周和第二周的作业内容
   
   1. 执行 test.sql 文件创建指定的`student`表
   
   2. 第一道必做题`实现SpringBean装配`,有三种方式：自动装配，基于注解，xml文件; `entity`包下的是实体类的定义，`entity`下的`School` 使用自动装配实现，config 包的`TeacherConfig和XmlConfiguration`分别是基于注解和xml实现bean装配， bean xml 配置文件在`resources`目录下
   
   3. 周六的第二道必做题，`config`包下的 `JDBCUtil`使用`java jdbc api`实现了基础的CURD操作；加入了 PrepareStatement 和 batch；`JDBCProxy`是`JDBCUtil`的静态代理类，本来是想通过持有一个共享的`Connection`对象，使用自定义事务注解控制事务开启，实现错了，单例对象只持有一个`connection`，即便开启了事务，其他地方如果也调用使用了这个connection 的方法，是可以获得同样的connection 的；这个思路不对。
   
   4. `Bean`的装配验证在`HomeworkApplication`类中，启动之后通过`ConfigurableApplicationContext`去获取了对应的bean 对象并输出，确认装配成功。
   
   5. JDBC curd，batch insert ，事务操作的验证在`JDBCRunner`类中。
   
   6. 第二道必做题的第三步，在配置文件添加了连接池配置，`HikariRunner` 类中注入了`DataSource` ，在 'SimpleTransactionAspect'切面中，将执行sql 的 `JdbcProxy`的 Connection 对象更换为一个新的从连接池获取的对象，并开启事务执行，`HikariRunner`输出结果表示事务执行成功
   
      
   
2. 周六的第一道必做题 完成 实体类的自动配置和定义starter 在项目 starter 中，没有配置多个实体类，只是配置了一个`School`实体，制定了`prefix="school"`,添加了`SchoolConfig`配置类，指定了自动配置的对象以及配置的生效的`ConditionalOnClass(AllService.class)`；在 `homework` 项目的 `JDBCRunner`类中引入了starter 的 `AllService`对象，配置文件 application.properties 配置了`school`对象的两个属性，在`JDBCRunner`类中打印出来是生效的。

