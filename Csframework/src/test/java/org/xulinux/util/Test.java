package org.xulinux.util;

import com.sun.source.doctree.SeeTree;
import org.xulinux.util.PropertiesPaser;

import java.lang.reflect.Method;
import java.net.URISyntaxException;
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

    @org.junit.Test
    public void test2() {
        try {
            System.out.println(Thread.currentThread().getContextClassLoader().getResource("org/xulinux/util").toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void testForSB() {
        StringBuffer sb = new StringBuffer("com.mec.ff.bb");

        sb.substring(sb.indexOf("."));

        System.out.println(sb.delete(sb.lastIndexOf("."),sb.length()));
        System.out.println(sb);
    }

    @org.junit.Test
    public void testForPackageScanner() {
        PackageScanner ps = new PackageScanner() {
            @Override
            void dealClass(Class<?> klass) {
                System.out.println(klass.getName());
            }
        };
        ps.scanPackage("org.xulinux");
    }




}
