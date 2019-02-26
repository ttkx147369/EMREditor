package com.emreditor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MethodProxy<T> implements InvocationHandler {
    private Class<T> methodInterface;

    public MethodProxy(Class<T> methodInterface) {
        this.methodInterface = methodInterface;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        System.out.println("=========================");
        System.out.println("方法名:" + method.getName());
        //针对不同的方法进行不同的操作
        return null;
    }

    public static void main(String[] args){
        //MethodInterface method = MethodProxyFactory.newInstance(MethodInterface.class);
        //method.helloWorld();
        MethodInterface method = new MethodInterface(){

            @Override
            public String helloWorld() {
                return null;
            }
        };
    }
}

class MethodProxyFactory {
    public static <T> T newInstance(Class<T> methodInterface) {
        final MethodProxy<T> methodProxy = new MethodProxy<T>(methodInterface);
        return (T) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{methodInterface},
                methodProxy);
    }
}

interface MethodInterface { String helloWorld(); }