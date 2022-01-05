package org.xulinux.core.client;

/**
 * //TODO add class commment here
 *
 * @Author wfh
 * @Date 2022/1/2 下午7:34
 */
public interface IClientAction {
    void afterConnect();
    void dealPrivateMassage(String Message,int sourth);
    void dealSomePeopleMessage(String message,String others,int sourth);
    boolean besureOffline();
    void beforeOffline();
    void afterOffline();

    void dealAbnormalDrop();
}
