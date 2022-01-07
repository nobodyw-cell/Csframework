# 万字长文给女朋友讲懂基于java BIO的CS框架

## 简介
大家好,今天是2022年1月2日,因疫情原因在西安上学的我已经被隔离了快11天了,刚好闲下来开篇博客给和大家聊聊以前学过的一个基于java BIO的CS框架,

1. 首先什么是CS呢
   CS就是Client Server,我们的CS框架指的就是==客户机-服务器模型框架==,与之相似的还有BS(Browser Server浏览器-服务器模型).
   > CS架构   &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;  &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;  BS架构
   <img src="./pic/2022-01-02-15-27-33.png" width=200 height=200>
   <img src="./pic/2022-01-02-15-31-34.png" width=200 height=200>
2. 这个框架能做什么
   此框架可以协助我们更为方便的开发出CS架构的软件,在现实生活中有很多大型软件都是基于CS架构,像最常见的QQ,微信这些聊天工具,还有王者荣耀,吃鸡这些小游戏都是基于此架构.结束此篇后我还会带着大家,用此框架快速开发一个聊天室软件,
   学过web开发的都知道tomcat服务器,那么基于此框架,再结合一个http服务器,我们就可以做一个简单的tomcat服务器,当然如果我有时间的话,会带大家基于此框架,手写tomcat(大概率是没有时间了).

此项目源码我放在了github上大家可以clone下来分析 https://github.com/nobodyw-cell/Csframework

## 1. 项目分析

### 1.1 功能需求分析

在CS架构的程序设计中这些需求往往是必须的
1. 客户端向服务器发送消息
   1. 消息类型
      1. 请求上线
      2. 请求资源
      3. 请求转发消息
         1. 单发
         2. 群发
2. 客户端接受服务器消息
   1. 消息类型
      1. 上线许可
      2. 资源
      3. 单发消息
      4. 群发消息
3. 服务器向客户端发送消息
   1. 消息类型
      1. 返回资源
      2. 转发消息
         1. 单发
         2. 群发
4. 服务器接受消息
   1. 消息类型
      1. 请求上线
      2. 请求资源
      3. 请求转发消息
         1. 单发
         2. 群发

其形式类似这样
![](./pic/2022-01-04-16-18-47.png)

### 1.2 类的设计与抽象
> 人们所能够解决的问题的复杂性直接取决于抽象的类型与质量 --Thinking in java


掌握解决问题的方法比解决问题更要,以前听一位P8大佬说学习一个技能的最好方式是为这个技能写一个学习教程,现在感觉确实十分贴切.
以下是分析这个项目的方法
1. 我们先从面向对象的程序设计出发,对现有问题进行抽象.从而得到各个类的设计
   "我们将问题空间中的元素及其在解空间中的表示称为对象"--这个表述十分贴切,我们先将我们问题空间中的元素列出来
   |element|description|
   |---|----|
   |Client|客户端 提供给用户的对象|
   |Server|服务器 提供给用户的对象|
   |ClientConversation| 客户端的会话层 负责处理客户端与服务器之间的会话 对外封闭|
   |ServerConversation| 服务器的会话层 负责处理客户端与服务器之间的会话 对外封闭|
   |Communication|通信层,是个抽象类是两个conversation的共同基类,只负责底层信息的转发与接受,再一步处理交给会话层处理 对外封闭| 
   |NetMessage|对消息的封装 其内置通信四元素 信源 信标 命令 信息本身 对外封闭|
   |ENetCommand|消息命令 对外封闭|
   |IClientAction|是个接口具体实现交由用户实现,相当于我们给用户接口的一个集合|
   > 还有部分类未设计之后再给出
2. 各个对象应该具有的服务能力
   1. 我们从Communication开始看
      1. 他应该具有send(message)能力,向对方发送消息
      2. 且具有dealMessage(message)能力,要注意的是这里的dealMessage(message),应该是一个抽象方法,Communication应当只专注于消息的收发
      3. 而且他还要有,监视对方是否在线的能力并对异常掉线做出反应,同样这个能力也应该是个抽象方法,我叫他dealAbnormalDrop();
   2. ServerConversation和ClientConversation尽管不尽相同但是我们可以放在一块儿进行分析
      1. 作为会话层他首先要具备 发送消息能力
      2. 接受消息,并对消息进行分类处理或者交给Client或者Server处理
      3. 我们发现上边两个能力其实和Communication十分相似,没错,我们的设计是ServerConversation和ClientConversation继承Communication.
   3. Server
      1. 他应该在初始化ininServer()后可以被启动startUp()
      2. 他还可以被关闭terminal()
      3. 他通信的能力可以依靠ServerConversation也就是说,他要内置ServerConversation,由于他向很多个端进行交流会话,所以他其实要内置很多的ServerConversation
      4. 他还要监控系统运行,所以他还有一种得到运行信息的能力
      5. 他可以指定别人下线
   4. Client
      1. 首先他应该具有向一个客户端发送消息的能力 to_one(target,message)
      2. 向很多客户端发送消息的能力 toOther(targets,message)
      3. 请求资源的能力
      4. 有很多东西我们在框架这一端是不能替用户做的我们把他抽象出来统一叫做IClientAction
## 2. 代码设计

### 2.1 我们首先设计Communication类
通信是客户端服务器模型的基础,因此设计好这个类就显得十分重要.
