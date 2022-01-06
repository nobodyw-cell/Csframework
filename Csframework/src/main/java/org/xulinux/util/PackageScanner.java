package org.xulinux.util;

import org.junit.Test;
import org.xulinux.action.annotation.Action;
import org.xulinux.action.annotation.ActionBean;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 负责包扫描的工具类.
 *
 * @Author wfh
 * @Date 2022/1/5 下午7:19
 */
public abstract class PackageScanner {

    public PackageScanner() {
    }

    abstract void dealClass(Class<?> klass);

    /**
     * 扫描包
     * 对其中所有class文件做dealClass()处理
     *
     * @author wfh
     * @date 下午7:31 2022/1/5
     * @param packageName 指定的包名
     **/
    public void scanPackage(String packageName) {
        String path = packageName.replace(".","/");

        URL url = Thread.currentThread().getContextClassLoader().getResource(path);

        try {
            File file = new File(url.toURI());
            parseFile(file,packageName.substring(0,packageName.lastIndexOf(".")));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对指定file下的class做dealClass() 处理
     *
     * @author wfh
     * @date 下午1:06 2022/1/6
     * @param file 下午1:06
     **/
    public void parseFile(File file,String packageName) {
        if (file.isFile()) {
            String fileName = file.getName();
            if (fileName.endsWith(".class")) {
                String className = (packageName + "."  + fileName).replace(".class","");
                try {
                    dealClass(Class.forName(className));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } else {
            packageName = packageName + "." + file.getName();
            for (File f : file.listFiles()) {
                parseFile(f,packageName);
            }
        }
    }

}
