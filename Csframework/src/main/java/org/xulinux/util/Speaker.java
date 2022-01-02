package org.xulinux.util;

import java.util.List;

/**
 * //TODO add interface commment here
 *
 * @Author wfh
 * @Date 2022/1/2 下午4:09
 */
public interface Speaker {
    void speak(String message);
    void addListenner(Listenner listenner);
}
