package org.xulinux.action;

import org.xulinux.action.annotation.Action;
import org.xulinux.action.annotation.ActionBean;
import org.xulinux.util.PackageScanner;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Action的池子.
 * 加载程序上下文并存储
 * 其实就是通过包扫描得到所有的Action供以后dispatcher调用
 *
 * @Author wfh
 * @Date 2022/1/6 下午2:49
 */
public class ActionBeanFactory {
    static {
        actionMap = new HashMap<>();
    }

    private static Map<String,ActionDefination> actionMap;

    public static ActionDefination getActionBeanDefination(String action) {
        return actionMap.get(action);
    }

    public static void loadApplicationContext(String packageName) {
        PackageScanner ps = new PackageScanner() {
            @Override
            public void dealClass(Class<?> klass) {
                if (klass.isAnnotationPresent(ActionBean.class)) {
                    Method[] methods = klass.getMethods();

                    for (int i = 0; i < methods.length; i++) {
                        if (methods[i].isAnnotationPresent(Action.class)) {
                            ActionDefination actionDefination = new ActionDefination();
                            Action action = methods[i].getAnnotation(Action.class);

                            actionDefination.setMethod(methods[i]);
                            try {
                                actionDefination.setObject(klass.getConstructor().newInstance());
                            } catch (InstantiationException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            } catch (NoSuchMethodException e) {
                                e.printStackTrace();
                            }

                            actionMap.put(action.value(), actionDefination);
                        }
                    }
                }
            }
        };

    }
}