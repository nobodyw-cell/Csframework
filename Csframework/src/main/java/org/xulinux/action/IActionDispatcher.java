package org.xulinux.action;

import java.lang.reflect.Parameter;

/**
 * //TODO add interface commment here
 *
 * @Author wfh
 * @Date 2022/1/6 下午2:47
 */
public interface IActionDispatcher {
    ActionResponse dispatch(ActionRequest actionRequest, String action);
}
