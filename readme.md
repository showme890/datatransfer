数据转发服务系统说明

 一、缘由：由于IPv4地址越来越紧张，众多终端设备只能使用内部地址进行通信。对于很多开发者来讲，如果想把终端连接起来就必须使用公网IP地址，但是公网IP地址并不是那么容易获得。比如，有四台设备，其中有服务器和终端都需要在互联网上可以访问。传统的反向代理模式虽然可以解决一下问题，但无法解决内部地址自由映射的问题。

二、主要组成部分

 主要由三部分组成，1.转发服务器程序，2.目标系统客户端 3.客户端

 1、转发服务器程序：主要负责将配对通道内的数据进行双向转发，且所有通讯都使用加密通讯
 2、目标系统客户端：目标系统安装客户端的目的是将在内网主机的数据进行转换和提升，这也是区别于传统代理的一个重要方面，也就是它不仅要连接服务端服务，还会主动连接本机服务
 3、客户端：既在本地制定端口建立监听并与目标主机进行配对

整体的设计思路为内网主机的反链操作。



所有通讯都为加密通讯，使用主机标识进行管理，具有用户接入验证，转发服务器需安装到公网地址。

数据转发服务器链路设计要点：
一、数据转发服务模型

             --------
            |Server1 |
             --------
           1 /      \ 1
            /        \
           /          \
        -------    -----------
       |Client |  |LocalServer|
       --------    -----------
        | | | n      | | | n
        ^ ^ ^        ^ ^ ^
        CONNECT     LISTEN 

多->1->1->多 
    
其中Server1只负责接收连接，对连接进行管理，主要实现对配对连接进行管理和维护


Server      端维护Map表：<SocketUUId,<SocketFrom,SocketTo>>
Client       维护Map表：<SocketUUId,SocketRaw>
LocalServer 端维护Map表：<SocketUUId,SocketRaw>

其中所有连接到Server端的程序只会产生一个连接，安装在目标主机上的程序对本地可能有很多连接，所有连接的数据发送与接收复用往服务端的一个连接

其中Client端的数据编码方向为：与本地程序通讯使用纯Socket Raw数据包，不做包解析，向Server端发送或接收数据，使用内部统一格式报文

LocalServer端的数据编码方向为：与本地程序通讯采用纯Socket Raw数据包，不做包解析，向Server端发送或接收数据，使用内部统一格式报文

Server端的数据编码方向为：进出都使用内部统一格式报文

配对连接管理部分
 用户认证部分
 使用userID和appID相结合的方式，其实就是用户和密码，对于认证不通过的返回错误码，决绝提供转发服务。
 限速部分
 对普通用户可以设置限速等措施
 远程管理部分（需要审核用户级别）
 可以实现不登录服务器就可以对服务端进行设置
 
 
     |       |
     |       |
     |       |
   -----     |
  | IN  |    |
   -----     |
     |       |
 --------------
  |  HANDLER    |
 --------------

Server端：由于都使用编解码器，而且

报文：
 1。验证报文
 
 2。请求打开端口报文
 3。数据报文
 4。返回结果报文
 
 5。消息报文



在实际通讯中发现SocketUUId在Server端进行ChannelPare的识别，是一个全局的标志，如果改变数据将不知道向那个通道转发，而底层的Raw Socket也需要进行标识，但涉及到底层的是在有实际数据
交换的情况下，所以想在DataMessage中加入一个lSocketId（Local Socket ID）来识别数据的本地通道地址，这样在不影响其他报文改变的情况下做到兼容。




          
  