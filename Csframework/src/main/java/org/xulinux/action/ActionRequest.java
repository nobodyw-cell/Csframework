package org.xulinux.action;

import java.util.HashMap;
import java.util.Map;

/**
 * //TODO add class commment here
 *
 * @Author wfh
 * @Date 2022/1/6 下午5:51
 */
public class ActionRequest {
    private Map<String,Object> parakv;

    public ActionRequest() {
        this.parakv = new HashMap<>();
    }

    public Object getParameter(String name) {
        return parakv.get(name);
    }

    public ActionRequest setParameter(String name,Object parameter) {
        this.parakv.put(name,parameter);
        return this;
    }
}
