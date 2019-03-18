package com.gupao.lyf.vo;

import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created with IntelliJ IDEA.
 * User: luo
 * Date: 19-3-17
 * Time: 下午1:12
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] args){
        Jack jack = new Jack();
        jack.drink();
        Object ob = Proxy.newProxyInstance(Jack.class.getClassLoader(),Jack.class.getInterfaces(),new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                method.invoke(proxy,args);
                return null;
            }
        });
        byte[] ss = ProxyGenerator.generateProxyClass("$Proxy0",new Class[]{Person.class});
        try {
            FileOutputStream fos = new FileOutputStream("E://$Proxy0.class");
            fos.write(ss);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class JackProxy implements InvocationHandler{

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            method.invoke(proxy,args);
            return null;
        }
    }
}
