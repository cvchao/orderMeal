# 多商户SaaS版扫码点餐系统（后端）

### 介绍
（后端）多商户SaaS版扫码点餐系统，支持后台点餐、多人同时在线点餐、购物车共享、餐桌状态实时监控，菜品管理、餐桌管理等众多功能。

| 项目  | 仓库地址 |
|-----|------------------------------------------------|
| 后端  | https://gitee.com/ah-f/Afly-OrderMeals-backend |
| 前端  | https://gitee.com/ah-f/Afly-OrderMeals-front   |
| H5端 | https://gitee.com/ah-f/Afly-OrderMeals-h5      |

系统运行大致流程
![输入图片说明](doc/%E5%9B%BE%E7%89%87.png)

### 技术架构
#### 前端
1. 商户管理端使用VUE3版本，顾客点餐端使用VUE2版本。
2. Vue Router：是 Vue框架的路由模块。
3. ElementUI Plus：一款基于vue3的组件库，开箱即用。搭配VUE来实现商户管理端的页面设计。
4. Pinia：全局状态管理器，它允许您跨组件或者页面共享状态。
5. Tailwind CSS：一款响应式设计的前端css框架。
6. cube-ui：基于Vue框架实现的移动端组件库。使用起来简单、便捷，主要用来实现顾客点餐端的界面。
7. ECharts：数据图表组件库，开箱即用，方便快捷。用在商户管理端通过图表查看店铺数据。
8. TypeScript：在JavaScript的基础上做出了升级，新增了面向对象语法。
9. Axios：基于Promise的网络请求库，异步请求结合vue实现页面局部刷新。
#### 后端
1. Spring Boot：当前非常流行的后端开源框架，在spring核心之上只对配置做了升级，不改变核心，开发者开箱即用。
2. MyBatis：数据持久化框架，对数据库连接、配置等操作进行了自动化装配，只需要进行简单的配置就可以实现自动注册驱动、建立连接、释放连接等操作。
3. MyBatis-Plus：MyBatis 的增强工具，只做增强不做改变，只为简化开发、提高效率[9]。
4. MyBatis-Plus-Join：MyBatis-Plus的增强工具，支持了多表联查。
5. Sa-Token：轻量级 Java 权限认证框架，解决登录认证、权限认证、单点登录等一系列权限相关问题。
6. Redis：基于内存的Key-Value型数据库，性能强悍。

### 系统功能
