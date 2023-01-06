package com.chakra.projects.investment.utils;

import java.util.HashMap;
import java.util.Map;

public class Context {

    private static ThreadLocal<Context> context = new ThreadLocal<>();

    private Map<String, Object> _storage = new HashMap<>();

    public static Context getContext() {
        if (context.get() == null) {
            context.set(new Context());
        }

        return context.get();
    }

    public <T> T get(String key) {
        return (T)_storage.get(key);
    }

    public void put(String key,Object obj) {
        _storage.put(key,obj);
    }

}
