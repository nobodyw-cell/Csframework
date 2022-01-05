package org.xulinux.util;

import com.sun.source.doctree.SeeTree;
import org.xulinux.util.PropertiesPaser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * //TODO add class commment here
 *
 * @Author wfh
 * @Date 2022/1/2 下午4:53
 */
public class Test {

    @org.junit.Test
    public void test() {
        Map<String,String> map = new HashMap<>();
        map.put("fds","fdsfg");
        map.put("gf","fds");
        map.put("fdsfd","fdsf");
        map.put("fdsgsfsds","fds");

        Set<String> set = new HashSet<>(map.keySet());

        for (String s : set) {
            System.out.println(s);
            map.remove(s);
        }
    }
}
