<h1 align="center">sherly-springboot</h1>
<p align="center">
	<a href='https://gitee.com/guzi499/universal-practice-repository/stargazers'><img src='https://gitee.com/guzi499/universal-practice-repository/badge/star.svg?theme=dark' alt='star'></img></a>
</p>

### 项目简介

本项目是基于 SpringBoot2.x 和 Vue3 / React 的前后端分离的Java多租户权限管理系统。

| 项目名              | 说明                     | 传送门                                                                                                                                 |
|--------------------|--------------------------|------------------------------------------------------|
| `sherly-springboot`| sherly后端项目           | [sherly-springboot](https://gitee.com/guzi499/sherly-springboot) |
| `sherly-vue3`      | sherly前端项目           | [sherly-vue3](https://gitee.com/guzi499/sherly-vue3)       |

多租户实现方案为 mysql 分库，解决了多租户数据隔离的问题，不同的租户使用不同的数据库，完美做到数据隔离。

其次，多租户分为主租户和购入租户，主租户可以管理其他所有租户的服务和用户量。根据购入租户的购买规模、购买服务来提供支持。

主租户实现功能后，可以根据需求将菜单、权限分给下属购入租户，购入租户拥有这些菜单、权限后，就可以使用对应的菜单、权限功能。

当前系统的功能有
- 系统管理
  - 租户管理  【mysql多租户数据分库，信息隔离】
  - 部门管理
  - 菜单管理  【使用SpringSecurity，权限精确到按钮，安全稳定】
  - 角色管理
  - 用户管理
- 系统监控
  - 在线用户  【精准监控，可踢人下线】
  - 操作日志  【包含正常日志和异常日志，易排错】
  - 登陆日志
- 系统配置
  - 邮件管理  【支持多种筛选方式，精准邮件投递】
    - 邮箱配置
    - 邮件发送
  - 对象存储  【支持S3规范所有云存储以及本地存储】
    - 存储配置
    - 内容管理

### 项目特色
- 支持可开关的自定义响应结果，方便测试环境下及时排查bug，避免查日志的困扰。同时生产环境一键关闭错误堆栈，面向用户更加友好。
- 支持演示环境，禁用所有的修改接口。
- 支持S3规范下的所有云存储厂商，同时测试学习时可以使用本地存储，每个租户都可以配置自己的对象存储方案。
- 支持Excel导出，可参考用户的导出，轻而易举的实现各种数据导出。
- 架构可向微服务拓展，支持参数注解校验，knife4j文档。
- 支持定制化异常枚举，实现动态格式化枚举内容。
- 支持多租户在线切换，灵感来源于企业微信。
- 支持操作日志记录，及时处理和复现bug，不用再去翻服务器日志。
- 支持多租户权限、用户上限、过期时间动态配置，随时启用和禁用下属租户的功能。
- 架构支持多数据源拓展，租户分库的优势体现的淋漓尽致。
- 支持jenkins自动构建部署，只需简单配置jenkins服务即可。

### 演示图片
<img height="120" src="https://gitee.com/guzi499/sherly-springboot/raw/develop/sherly-springboot/display-image/login.png">
<img height="120" src="https://gitee.com/guzi499/sherly-springboot/raw/develop/sherly-springboot/display-image/menu.png">

### 演示地址
[传送门链接](http://101.34.169.185:90)

### 相关文档
项目启动文档：[传送门链接](https://gitee.com/guzi499/sherly-springboot/blob/develop/sherly-upr-admin/src/main/resources/markdown/%E9%A1%B9%E7%9B%AE%E5%90%AF%E5%8A%A8%E6%96%87%E6%A1%A3.md)

开发规范文档：[传送门链接](https://gitee.com/guzi499/sherly-springboot/blob/develop/sherly-upr-admin/src/main/resources/markdown/sherly%E5%90%8E%E7%AB%AF%E5%BC%80%E5%8F%91%E8%A7%84%E8%8C%83.md)

### 反馈交流
<img height="120" src="https://gitee.com/guzi499/sherly-springboot/raw/develop/sherly-springboot/wx.jpg">

添加我的企业微信，邀请您加入企业微信外部交流群

### 相关文档
项目启动文档：[传送门链接](https://gitee.com/guzi499/sherly-springboot/blob/develop/sherly-upr-admin/src/main/resources/markdown/sherly%E5%90%8E%E7%AB%AF%E5%BC%80%E5%8F%91%E8%A7%84%E8%8C%83.md)

开发规范文档:[传送门链接](https://gitee.com/guzi499/sherly-springboot/blob/develop/sherly-upr-admin/src/main/resources/markdown/sherly%E5%90%8E%E7%AB%AF%E5%BC%80%E5%8F%91%E8%A7%84%E8%8C%83.md)

### 特别鸣谢
<img width="120" src="https://resources.jetbrains.com/storage/products/company/brand/logos/jb_beam.png" alt="JetBrains Logo (Main) logo.">

感谢 [jetbrains](https://jb.gg/OpenSourceSupport/?from=sherly-springboot "jetbrains") 为团队提供的免费授权，希望大家能够支持jetbrains及其正版产品。

### 创作支持
如果觉得框架还不错，或者已经在使用了，希望你可以去 gitee 帮作者点个 ⭐ Star，这将是对作者极大的鼓励与支持！
