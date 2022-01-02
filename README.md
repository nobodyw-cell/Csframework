# 万字长篇给女朋友讲懂基于java BIO的CS框架

## 简介
大家好,今天是2022年1月2日,因疫情原因在西安上学的我已经被隔离了快11天了,刚好闲下来开篇博客给和大家聊聊以前学过的一个基于java BIO的CS框架,

1. 首先什么是CS呢
   CS就是Client Server,我们的CS框架指的就是==客户机-服务器模型框架==,与之相似的还有BS(Browser Server浏览器-服务器模型).
   > CS架构
   ![CS](https://github.com/nobodyw-cell/Csframework/tree/main/pic/2022-01-02-15-27-33.png)
   
    > BS架构
    ![BS](https://github.com/nobodyw-cell/Csframework/tree/main/pic/2022-01-02-15-31-34.png)
2. 这个框架能做什么
   此框架可以协助我们更为方便的开发出CS架构的软件,在现实生活中有很多大型软件都是基于CS架构,像最常见的QQ,微信这些聊天工具,还有王者荣耀,吃鸡这些小游戏都是基于此架构.结束此篇后我还会带着大家,用此框架快速开发一个聊天室软件,
   学过web开发的都知道tomcat服务器,那么基于此框架,再结合一个http服务器,我们就可以开发一个简单的tomcat服务器,当然如果我有时间的话,会带大家基于此框架,手写tomcat(大概率是没有时间了).

此项目源码我放在了github上大家可以clone下来分析 https://github.com/nobodyw-cell/Csframework

## 1. Communication

