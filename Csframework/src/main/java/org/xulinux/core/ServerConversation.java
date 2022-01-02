package org.xulinux.core;

import org.xulinux.util.Util;

import java.net.Socket;

/**
 * //TODO add class commment here
 *
 * @Author wfh
 * @Date 2022/1/2 下午4:16
 */
public class ServerConversation extends Communication{

    public ServerConversation(Socket socket) {
        super(socket);
    }

    @Override
    public void dealAbnormalDrop() {

    }

    @Override
    public void dealMessage(String message) {
        NetMessage netMessage = Util.gson.fromJson(message,NetMessage.class);

        switch(netMessage.getCommand()) {
            case TO_ONE:

                break;
        }
    }

}
