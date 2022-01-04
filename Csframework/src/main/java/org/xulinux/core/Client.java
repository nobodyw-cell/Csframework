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
    public void toOne(String message,int target) {
        this.conversation.send(
                new NetMessage()
                        .setCommand(ENetCommand.TO_ONE)
                        .setMessage(message)
                        .setTarget(target)
                        .setSourth(this.id)
        );
    }

    public void toOther(String message,String target) {

    }

    public IClientAction getClientAction() {
        return this.clientAction;
    }

}