package org.xulinux.action;

import javax.servlet.annotation.WebServlet;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.http.HttpRequest;

/**
 * //TODO add class commment here
 *
 * @Author wfh
 * @Date 2022/1/6 下午3:20
 */
public class ActionDefination {
    private Object object;
    private Method method;

    /**
     * 根据传进来的ActionRequest生成,方法的参数供调用
     *
     * @author wfh
     * @date 下午7:13 2022/1/6
     * @param ar
     * @return java.lang.Object[] Method.invoke中的参数集合
     **/
    public Object[] createParameters(ActionRequest ar) {
        Parameter[] ps =  method.getParameters();
        Object[] os = new Object[ps.length];

        for (int i = 0; i < ps.length; i++) {
            os[i] = ar.getParameter(ps[i].getName());
        }

        return os;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
