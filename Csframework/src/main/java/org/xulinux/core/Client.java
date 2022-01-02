package org.xulinux.core;

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

    public Client() {
    }

    public void connectToServer() {
        try {
            Socket socket = new Socket(ip,port);
            this.conversation = new ClientConversation(socket);
            this.clientAction.afterConnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void toOne(String message,String target) {
        NetMessage netMessage = new NetMessage();
        netMessage.setMessage(message);
        netMessage.setTarget(target);

        this.conversation.send(Util.gson.toJson(netMessage));
    }

    public void toOther(String message,String target) {

    }

}