package org.xulinux.core;

import org.xulinux.util.Listenner;
import org.xulinux.util.PropertiesPaser;
import org.xulinux.util.Speaker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * //TODO add class commment here
 *
 * @Author wfh
 * @Date 2022/1/2 下午5:47
 */
public class Server implements Runnable, Speaker {
    private int port;
    private int maxClientCount;
    private ServerSocket severSocket;
    private volatile boolean goon;
    private ClientPool clientPool;
    private List<Listenner> listenners;

    public Server() {
        this.clientPool = new ClientPool();
        this.listenners = new ArrayList<>();
    }

    public void offline(ServerConversation conversation) {
        speak("客户端" + conversation.getIp() + "下线");
        this.clientPool.removeClient(conversation.hashCode());
    }

    public void ininServer(String configPath) {
        PropertiesPaser paser = new PropertiesPaser(configPath);
        this.port = Integer.valueOf(paser.get("port"));
        this.maxClientCount = Integer.valueOf(paser.get("maxClientCount"));
    }

    public void startUp() {
        if (this.goon == true) {
            speak("服务器已启动无需再次启动!");
            return;
        }

        speak("服务器正在启动....");
        try {
            this.severSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        speak("服务器启动成功,正在监听客户端连接请求...");
        this.goon = true;
        new Thread(this).start();
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        while(this.goon == true) {
            try {
                Socket socket = this.severSocket.accept();
                ServerConversation serverConversation = new ServerConversation(socket,this);
                if (this.clientPool.size() > this.maxClientCount) {
                    serverConversation.overLoad();
                    speak("服务器已经达到最大负载(" + this.maxClientCount +  ")并拒绝了一名客户端的连接请求");
                } else {
                    //告知对方对方的id并维护这个会话
                    int id = this.clientPool.addClient(serverConversation);
                    serverConversation.online();
                }

                speak("客户端" + socket.getInetAddress() + "连接至服务器");
            } catch (IOException e) {
                if (this.goon == true) {
                    speak("服务器异常宕机");
                }
            }
        }
    }

    @Override
    public void speak(String message) {
        for (Listenner l : this.listenners) {
            l.messageFromSpeaker(message);
        }
    }
    
    /**
     * 转发消息给指定的一个客户端.
     * 得到指定的客户端
     * 将消息转发
     *
     * @author wfh 
     * @date 下午9:53 2022/1/4
     * @param netMessage 含有消息体 信源信标 命令
     **/
    public void toOne(NetMessage netMessage) {
       ServerConversation conversation = this.clientPool.getClient(netMessage.getTarget());

       conversation.send(netMessage);
    }

    @Override
    public void addListenner(Listenner listenner) {
        this.listenners.add(listenner);
    }

    @Override
    public void removeListenner(Listenner listenner) {
        this.listenners.remove(listenner);
    }
}