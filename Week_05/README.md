### Week05作业

---

1. 项目 `homework`包含了第一周和第二周的作业内容
   1. 第一道必做题`实现SpringBean装配`,有三种方式：自动装配，基于注解，xml文件; `entity`包下的是实体类的定义，`entity`下的`School` 使用自动装配实现，config 包的`TeacherConfig和XmlConfiguration`分别是基于注解和xml实现bean装配， bean xml 配置文件在`resources`目录下
   2. 周六的第二道必做题，`config`包下的 `JDBCUtil`使用`java jdbc api`实现了基础的CURD操作；加入了 PrepareStatement 和 batch；`JDBCProxy`是`JDBCUtil`的静态代理类，本来是想通过持有一个共享的`Connection`对象，使用自定义事务注解控制事务开启，实现错了，单例对象只持有一个`connection`，即便开启了事务，其他地方如果也调用使用了这个connection 的方法，是可以获得同样的connection 的；这个思路不对。
   3. 第二道必做题的第三步未完成
2. 周六的第一道必做题未完成