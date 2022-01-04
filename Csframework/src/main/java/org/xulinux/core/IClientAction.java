package org.xulinux.core;

/**
 * //TODO add class commment here
 *
 * @Author wfh
 * @Date 2022/1/2 下午7:34
 */
public abstract class IClientAction {
    abstract void afterConnect();
    abstract void dealPrivateMassage(String Message,int sourth);
    abstract void dealSomePeopleMessage(String message,String others,int sourth);
    abstract boolean besureOffline();
    abstract void beforeOffline();
    abstract void afterOffline();
}
