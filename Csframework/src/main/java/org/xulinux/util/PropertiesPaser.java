package org.xulinux.util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * //TODO add class commment here
 *
 * @Author wfh
 * @Date 2022/1/2 下午4:38
 */
public class PropertiesPaser {
    private Properties properties;

    public PropertiesPaser(String path) {
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("netconfig.properties");
            this.properties = new Properties();
            this.properties.load(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<Object> getKeySet() {
        return this.properties.keySet();
    }

    public String get(String key) {
        return this.properties.getProperty(key);
    }
}
