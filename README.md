# Community
nowcoderProject

1.项目中所用到的大部分技术栈
        整个技术是构建在SpringBoot上的，其他技术是依托于SpringBoot之上的。SpringBoot只是起到辅助的作用，降低其他技术的使用难度。整个技术的核心是Spring框架，在Spring之上使用了SpringMvc（解决了前后端请求处理交互的问题）、
        Spring Mybatis（可以访问数据库）、Spring Security（用于管理项目中的登录权限等）。SpringMvc、Spring Mybatis、Spring Security构成了项目的基石，项目中几乎所有请求是由他们完成的。 
        ![image](https://github.com/kurskal0/Community/assets/57366502/cf8a5565-253d-400e-bfe0-7862fed91ad7)


2.权限模块
        应用了Spring Email和SpringMvc中的Interceptor（拦截器），其中拦截器能拦截所有请求，能解决通用的问题，涉及的面比较广、影响的请求比较多要重点关注。权限模块主要开发了注册、登录、退出、状态（在每个页面上怎么去显示登录用户的头像、用户名等）、设置（用户头像、修改密码等）、授权（不同类型的用户访问不同的功能，使用Security实现的）、会话管理（重点需要了解Cookie、session、项目中为什么不用session(主要是考虑分布式部署Session的问题)、不用session是如何解决的问题（把数据存在Redis中，使用了ThreadLocal））等功能。

3.核心功能
        基于SpringMvc实现的首页、帖子、评论、私信的功能，异常和日志使用到了通用的技术。重点关注敏感词是怎么实现的（前缀树算法），事务也需要重点关注（什么是事务、事物的隔离级别，怎么去管理事务的）。整个模块还用到了Advice（控制器的通知，统一处理了异常）、AOP（统一记录了日志，事实上其他的很多功能（比如事务）都应用到了AOP）、Transaction（重点）。

4.性能模块
        一些高频访问的功能（点赞、关注、统计、缓存）需要redis，redis不止能应用于这些功能还应用于统计网站的UV，活跃用户等使用了redis的两种特殊的数据类型，还使用redis用作缓存提高了性能。

5.通知模块
        应用了消息队列的Kakfa，框架的使用非常简单，重点去了解Kafka的生产消费模式。重点回顾生产消费模型，了解能够解决哪些问题。

6.搜索功能
        全文搜索：针对帖子能够进行全文搜索，使用了Elasticsearch，使用起来也是非常简单，重点了解其数据结构，其存数据的方式与数据库不同，关注其索引的结构（找一找相关的文章）

7.其他功能
        排行榜、上传、服务器缓存、线程池、缓存等。使用Quartz定时任务，重点关注Caffeine怎么提高了应用的性能，还要了解其局限性。还要重点关注线程池、缓存这两个话题。
