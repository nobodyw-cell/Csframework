package org.xulinux.core.server;

import org.xulinux.action.ActionRequest;
import org.xulinux.action.ActionResponse;
import org.xulinux.action.DefaultActionDispatcher;
import org.xulinux.action.IActionDispatcher;
import org.xulinux.core.base.Communication;
import org.xulinux.core.base.ENetCommand;
import org.xulinux.core.base.NetMessage;
import org.xulinux.core.server.Server;
import org.xulinux.util.Util;

import java.net.Socket;

/**
 * //TODO add class commment here
 *
 * @Author wfh
 * @Date 2022/1/2 下午4:16
 */
public class ServerConversation extends Communication {
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
                break;
            case TO_OTHER:
                this.server.toOther(netMessage);
                break;
            case ACTION:
                String action = netMessage.getAction();
                ActionResponse ap = this.server.getDispatcher()
                                .dispatch(Util.gson
                                .fromJson(netMessage.getMessage(), ActionRequest.class),action);

                this.response(action,ap);
                break;
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

    public void response(String action,ActionResponse ap) {
        send(new NetMessage().setAction(action)
                .setMessage(Util.gson.toJson(ap))
                .setCommand(ENetCommand.ACTION));
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
