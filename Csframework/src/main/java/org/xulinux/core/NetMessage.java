package org.xulinux.core;

/**
 * //TODO add class commment here
 *
 * @Author wfh
 * @Date 2022/1/2 下午7:39
 */
public class NetMessage {
    private ENetCommand command;
    private String sourth;
    private String target;
    private String message;

    public ENetCommand getCommand() {
        return command;
    }

    public void setCommand(ENetCommand command) {
        this.command = command;
    }

    public String getSourth() {
        return sourth;
    }

    public void setSourth(String sourth) {
        this.sourth = sourth;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
