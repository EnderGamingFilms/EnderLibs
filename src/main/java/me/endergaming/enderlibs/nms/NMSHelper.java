package me.endergaming.enderlibs.nms;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class NMSHelper {
    static ConcurrentHashMap<String, Class<?>> classMap = new ConcurrentHashMap<>();

    public static Class<?> getMinecraftClass(String packageName) {
        Class<?> clazz = classMap.get(packageName);

        if (clazz != null) {
            return clazz;
        }

        try {
            clazz = Class.forName(packageName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return clazz;
    }

    public static Class<?> getMinecraftClass(MCP mcp) {
        return getMinecraftClass(mcp.get());
    }

    public static Constructor<?> getConstructor(Class<?> clazz, @NotNull Class<?>... parameterTypes) throws NoSuchMethodException {
        return clazz.getConstructor(parameterTypes);
    }

    public static Constructor<?> getConstructor(MCP mcp, @NotNull Class<?>... parameterTypes) throws NoSuchMethodException {
        return getConstructor(getMinecraftClass(mcp), parameterTypes);
    }

    public static Object getEnum(MCP mcp, String method, Object value) {
        return getEnum(getMinecraftClass(mcp), method, value);
    }

    public static Object getEnum(Class<?> clazz, String method, Object value) {
        for (Object obj : clazz.getEnumConstants()) {
            try {
                Method m = clazz.getMethod(method);
                System.out.println(m.invoke(obj));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
                System.out.println("could not find enum");
            }
        }

        try {
            Method m = clazz.getMethod(method);
            return m.invoke(value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            System.out.println("could not find enum");
        }

        return null;
    }
}

