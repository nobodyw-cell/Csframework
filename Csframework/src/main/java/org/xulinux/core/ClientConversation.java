package org.xulinux.core;

import java.net.Socket;

/**
 * //TODO add class commment here
 *
 * @Author wfh
 * @Date 2022/1/2 下午4:17
 */
public class ClientConversation extends Communication{
    @Override
    public void dealAbnormalDrop() {

    }

    @Override
    public void dealMessage(String message) {

    }

    public ClientConversation(Socket socket) {
        super(socket);
    }
}
