package org.xulinux.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 管理当前Server中的所有会话(ServerConversation).
 * 提供了添加,删除,查找ServerConversation的功能.
 *
 * @Author wfh
 * @Date 2022/1/2 下午7:02
 */
public class ClientPool {
    private Map<Integer,ServerConversation> pool;

    public ClientPool() {
        this.pool = new HashMap<>();
    }

    /**
     * 通过id得到会话
     *
     * @author wfh
     * @date 下午8:46 2022/1/4
     * @param id 通过此参数指出想要得到的会话
     * @return org.xulinux.core.ServerConversation 得到的会话
     **/
    public ServerConversation getClient(Integer id) {
        return this.pool.get(id);
    }

    /**
     * 添加会话
     * 添加后,会返回此会话的id(会话的hashcode)
     *
     * @author wfh
     * @date 下午8:38 2022/1/4
     * @param serverConversation 添加此会话入池
     * @return int 返回给Server,Server将其作为id使用
     **/
    public int addClient(ServerConversation serverConversation)  {
        this.pool.put(serverConversation.hashCode(),serverConversation);
        return serverConversation.hashCode();
    }


    /**
     * 移除并关闭所有客户端的连接.
     * 如果没有客户端,则直接返回
     *
     * @author wfh
     * @date 下午5:20 2022/1/5
     **/
    public void removeAll() {
        if (this.pool.size() <= 0) {
            return;
        }

        Set<Integer> set = new HashSet<>(this.pool.keySet()); //java自身设计问题,内部用的东西不要暴露给外边

        for (Integer id : set) {
            removeClient(id);
        }
    }
    //很疑惑


    /**
     * 移除并关闭会话.
     * 将会话从池中移出.
     * 关闭掉会话.
     *
     * @author wfh
     * @date 下午8:43 2022/1/4
     * @param id 通过此参数指出想要移除的会话
     **/
    public void removeClient(int id) {
        pool.remove(id).close();
    }

    /**
     * 返回当前与Servre进行会话的客户端数量.
     *
     * @author wfh
     * @date 下午8:45 2022/1/4
     * @return int 当前与Servre进行会话的客户端数量
     **/
    public int size() {
        return pool.size();
    }

}
