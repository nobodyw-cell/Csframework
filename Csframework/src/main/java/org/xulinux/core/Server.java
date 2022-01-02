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
    public int port;
    private ServerSocket severSocket;
    private volatile boolean goon;
    private ClientPool clientPool;
    private List<Listenner> listenners;

    public Server() {
        this.clientPool = new ClientPool();
        this.listenners = new ArrayList<>();
    }

    public void ininServer(String configPath) {
        PropertiesPaser paser = new PropertiesPaser(configPath);
        this.port = Integer.valueOf(paser.get("port"));
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
                ServerConversation serverConversation = new ServerConversation(socket);
                this.clientPool.addClient(serverConversation);
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

    private void toOne(NetMessage netMessage) {

    }

    public void send(NetMessage netMessage) {
        switch (netMessage.getCommand()) {
            case TO_ONE:
                toOne(netMessage);
                break;
        }
    }

    @Override
    public void addListenner(Listenner listenner) {
        this.listenners.add(listenner);
    }
}