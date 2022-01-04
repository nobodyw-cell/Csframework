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
    private int id;
    private String ip;
    private Server server;

    public ServerConversation(Socket socket,Server server) {
        super(socket);
        this.ip = socket.getInetAddress().getHostAddress();
        this.id = this.hashCode();
        this.server = server;
    }

    public String getIp() {
        return ip;
    }

    @Override
    public void dealAbnormalDrop() {
        this.server.speak("客户端" + this.ip + "异常掉线");
    }

    @Override
    public void dealMessage(NetMessage netMessage) {

        switch(netMessage.getCommand()) {
            case TO_ONE :
                this.server.toOne(netMessage);
                break;
            case OFFLINE:
                this.server.offline(this);
        }
    }

    /**
     * 告知客户端上线成功.
     * 告知客户端上线成功,并将其id告知.
     *
     * @author wfh
     * @date 下午8:52 2022/1/4
     **/
    public void online() {
        send(new NetMessage()
                .setCommand(ENetCommand.ON_LINE)
                .setMessage(String.valueOf(this.id))
        );
    }

    /**
     * 告知对端服务器超载.
     * 发送超载消息给对端.
     * 关闭conversation.
     *
     * @author wfh
     * @date 下午8:20 2022/1/4
     **/
    public void overLoad() {
        send(
                new NetMessage().setCommand(ENetCommand.OVER_LOAD)
        );
        close();
    }

}
