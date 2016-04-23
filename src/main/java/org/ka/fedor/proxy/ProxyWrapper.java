package org.ka.fedor.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyWrapper {

    private static class Handler implements InvocationHandler {


        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            return null;
        }
    }

    private static final Handler handler = new Handler();

    public static <T> T wrap(T object) {
        Class<?> klass = object.getClass();

        return (T) Proxy.newProxyInstance(object.getClass().getClassLoader(), new Class[] {klass}, handler);
    }

    private static <T> Class<?>[] getInterfaces(T object) {
        return new Class<?>[0];
    }
}
