package com.gupao.lyf.vo;

/**
 * Created with IntelliJ IDEA.
 * User: luo
 * Date: 19-3-17
 * Time: 上午11:23
 * To change this template use File | Settings | File Templates.
 */
public class Jack implements Person {

    @Override
    public void drink() {
        System.out.println("Jack is drinking");
    }

    @Override
    public void run() {
        System.out.println("Jack is running");
    }
}
