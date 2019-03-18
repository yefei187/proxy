package com.gupao.lyf.proxy;

import com.gupao.lyf.vo.Person;

import java.lang.reflect.Method;

/**
 * Created with IntelliJ IDEA.
 * User: luo
 * Date: 19-3-17
 * Time: 下午5:16
 * To change this template use File | Settings | File Templates.
 */
public class PersonProxy implements GPInvocationHandler{

    private Person target;
    public  Object newInstance(Person target){
        this.target = target;
        return GPProxy.newInstance(new GPClassLoader(),target.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("dfdfdfdfdkfjdkf");
        method.invoke(this.target,null);
        return null;
    }
}
