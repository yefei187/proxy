package com.gupao.lyf.proxy;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: luo
 * Date: 19-3-17
 * Time: 上午10:34
 * To change this template use File | Settings | File Templates.
 */
public interface GPInvocationHandler {
    Object invoke(Object proxy,Method method,Object[] args) throws Throwable;
}
