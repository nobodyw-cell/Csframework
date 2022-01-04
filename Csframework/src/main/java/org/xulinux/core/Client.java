package org.xulinux.core;

import org.xulinux.util.PropertiesPaser;
import org.xulinux.util.Util;

import java.io.IOException;
import java.net.Socket;

/**
 * //TODO add class commment here
 *
 * @Author wfh
 * @Date 2022/1/2 下午4:29
 */
public class Client {
    public int port;
    public String ip;
    private ClientConversation conversation;
    private IClientAction clientAction;
    private int id;

    public Client() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClientAction(IClientAction clientAction) {
        this.clientAction = clientAction;
    }

    public void connectToServer(String path) {
        PropertiesPaser paser = new PropertiesPaser(path);

        this.ip = paser.get("ip");
        this.port = Integer.valueOf(paser.get("port"));

        try {
            Socket socket = new Socket(this.ip,this.port);
            this.conversation = new ClientConversation(socket,this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向指定客户端单发消息
     *
     * @author wfh
     * @date 下午9:40 2022/1/4
     * @param message 发送的消息内容
     * @param target 指定客户端的id
     **/
    public void toOne(String message,String target) {
        this.conversation.send(
                new NetMessage()
                        .setCommand(ENetCommand.TO_ONE)
                        .setMessage(message)
                        .setTarget(String.valueOf(target))
                        .setSourth(this.id)
        );
    }

    /**
     * 客户端下线操作.
     * 这个操作中,下线的确认,下线准备,下线善后由客户端定义
     *
     * @author wfh
     * @date 下午11:16 2022/1/4
     **/
    public void offline() {
        if (this.clientAction.besureOffline()) {
            this.clientAction.besureOffline();
            this.conversation.send(new NetMessage().setCommand(ENetCommand.OFFLINE));
            this.clientAction.afterOffline();
        }
    }

    public void toOther(String message,String targets) {
        this.conversation.toOther(
                new NetMessage()
                        .setCommand(ENetCommand.TO_OTHER)
                        .setTarget(targets)
                        .setSourth(this.id)
                        .setMessage(message)
        );
    }

    public IClientAction getClientAction() {
        return this.clientAction;
    }

}