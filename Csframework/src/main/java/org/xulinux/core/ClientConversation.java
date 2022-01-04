package org.xulinux.core;

import java.net.Socket;

/**
 * //TODO add class commment here
 *
 * @Author wfh
 * @Date 2022/1/2 下午4:17
 */
public class ClientConversation extends Communication{
    private Client client;

    public ClientConversation(Socket socket,Client client) {
        super(socket);
        this.client = client;
    }

    @Override
    public void dealAbnormalDrop() {

    }

    /**
     * 处理,分发Client接受到的一切网络消息
     * 处理上线 消息设置id后,调用用户自定义的上线后的操作.
     *
     * @author wfh
     * @date 下午9:05 2022/1/4
     * @param netMessage socket接收到的网络消息
     **/
    @Override
    public void dealMessage(NetMessage netMessage) {
        switch (netMessage.getCommand()) {
            case ON_LINE :
                this.client.setId(Integer.valueOf(netMessage.getMessage()));
                this.client.getClientAction().afterConnect();
                break;
            case TO_ONE:
                this.client.getClientAction()
                        .dealPrivateMassage(netMessage.getMessage(), netMessage.getSourth());
                break;
        }
    }
}