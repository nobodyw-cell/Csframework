package org.xulinux.action;

import org.xulinux.action.IActionDispatcher;
import org.xulinux.action.annotation.Action;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * //TODO add class commment here
 *
 * @Author wfh
 * @Date 2022/1/6 下午7:46
 */
public class DefaultActionDispatcher implements IActionDispatcher {
    @Override
    public ActionResponse dispatch(ActionRequest aq, String action) {
        ActionResponse ap = new ActionResponse();

        ActionDefination ad = ActionBeanFactory.getActionBeanDefination(action);

        Method method = ad.getMethod();
        Object object = ad.getObject();
        Object[] para = ad.createParameters(aq);

        try {
            ActionResponse actionResponse = (ActionResponse) method.invoke(object,para);
            return actionResponse;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
