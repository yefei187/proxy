package com.gupao.lyf.proxy;

import com.gupao.lyf.vo.Jack;
import com.gupao.lyf.vo.Person;

/**
 * Created with IntelliJ IDEA.
 * User: luo
 * Date: 19-3-17
 * Time: 下午5:12
 * To change this template use File | Settings | File Templates.
 */
public class ProxyTest {

    public static void main(String[] args) throws Exception {
        Person jack = (Person)new PersonProxy().newInstance(new Jack());
        jack.drink();
    }
}
