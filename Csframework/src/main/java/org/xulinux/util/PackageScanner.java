package org.xulinux.util;

import org.xulinux.action.annotation.Action;
import org.xulinux.action.annotation.ActionBean;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 负责包扫描的工具类.
 *
 * @Author wfh
 * @Date 2022/1/5 下午7:19
 */
public abstract class PackageScanner {

    public PackageScanner() {
    }

    /**
     * 扫描包
     * 指定包,扫描其中所有class文件,和子包中的class文件
     *
     * @author wfh
     * @date 下午7:31 2022/1/5
     * @param packagePath
     **/
    public  void scanPackage(String packagePath,String packageName) {
        parseFile(new File(packagePath),packageName);
    }

    public abstract void dealAction(Method method);



    private  void parseFile(File file,String packageName) {
        if (file.isFile()) {
            if (file.getName().endsWith(".class")) {
                try {
                    Class<?> klass = Class.forName(packageName + "." + file.getName());
                    if (klass.isAnnotationPresent(ActionBean.class)) {
                        Method[] methods = klass.getMethods();

                        for (Method method : methods) {
                            if (method.isAnnotationPresent(Action.class)) {
                                dealAction(method);
                            }
                        }
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

        } else {
            for (File f : file.listFiles()) {
                parseFile(f,packageName);
            }
        }

    }
}
