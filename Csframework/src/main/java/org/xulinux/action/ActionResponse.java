package org.xulinux.action;

import java.util.HashMap;
import java.util.Map;

/**
 * //TODO add class commment here
 *
 * @Author wfh
 * @Date 2022/1/6 下午5:51
 */
public class ActionResponse {
    private Map<String,Object> parakv;

    public ActionResponse() {
        this.parakv = new HashMap<>();
    }

    public Object getParameter(String name) {
        return parakv.get(name);
    }

    public ActionResponse setParameter(String name,Object parameter) {
        this.parakv.put(name,parameter);
        return this;
    }
}
