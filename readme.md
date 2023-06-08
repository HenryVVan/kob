**项目设计**

- 名称：`King Of Bots`，简称`kob`
- 图标：

![kob.png](https://cdn.acwing.com/media/article/image/2022/07/07/1_d7f3b93efd-kob.png)

- 项目包含的模块
  - PK模块：匹配界面（微服务）、实况直播界面（WebSocket协议）
  - 对局列表模块：对局列表界面、对局录像界面
  - 排行榜模块：Bot排行榜界面
  - 用户中心模块：注册界面、登录界面、我的Bot界面、每个Bot的详情界面
- 前后端分离模式
  - `SpringBoot`实现后端
  - Vue3实现Web端和App端