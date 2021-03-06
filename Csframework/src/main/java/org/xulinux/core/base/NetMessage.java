package org.xulinux.core.base;

/**
 * //TODO add class commment here
 *
 * @Author wfh
 * @Date 2022/1/2 下午7:39
 */
public class NetMessage {
    private ENetCommand command;
    private int sourth;
    private String target;
    private String message;
    private String action;

    public ENetCommand getCommand() {
        return command;
    }

    public NetMessage setCommand(ENetCommand command) {
        this.command = command;
        return this;
    }

    public NetMessage setAction(String action) {
        this.action = action;
        return this;
    }

    public String getAction() {
        return action;
    }

    public int getSourth() {
        return sourth;
    }

    public NetMessage setSourth(int sourth) {
        this.sourth = sourth;
        return this;
    }

    public String getTarget() {
        return target;
    }

    public NetMessage setTarget(String target) {
        this.target = target;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public NetMessage setMessage(String message) {
        this.message = message;
        return this;
    }
}
