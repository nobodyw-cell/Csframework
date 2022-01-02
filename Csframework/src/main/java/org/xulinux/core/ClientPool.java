package org.xulinux.core;

import java.util.HashMap;
import java.util.Map;

/**
 * //TODO add class commment here
 *
 * @Author wfh
 * @Date 2022/1/2 下午7:02
 */
public class ClientPool {
    private Map<String,ServerConversation> pool;

    public ClientPool() {
        this.pool = new HashMap<>();
    }

    public ServerConversation getClient(String id) {
        return this.pool.get(id);
    }

    public String addClient(ServerConversation serverConversation)  {
        String id = idcreator(serverConversation);
        this.pool.put(id,serverConversation);
        return id;
    }

    public void removeClient(String id) {
        pool.remove(id);
    }

    private String idcreator(ServerConversation serverConversation) {
        return String.valueOf(serverConversation.hashCode());
    }

}
