package org.xulinux.util;

import org.xulinux.util.PropertiesPaser;

/**
 * //TODO add class commment here
 *
 * @Author wfh
 * @Date 2022/1/2 下午4:53
 */
public class Test {

    public static void main(String[] args) {
        PropertiesPaser propertiesPaser = new PropertiesPaser("netconfig.properties");
        System.out.println(propertiesPaser.getKeySet());
    }

    @org.junit.Test
    public void test() {

    }
}
