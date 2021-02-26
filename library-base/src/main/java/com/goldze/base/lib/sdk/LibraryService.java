package com.goldze.base.lib.sdk;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LibraryService {
    private static HashMap<Class<?>, List<ArgsBean<?>>> servies = new HashMap<>();

    public static Constructor<?> getConstructor(Class cls, Class<?>... parameterTypes) throws ClassNotFoundException, NoSuchMethodException {
        Class<?> clasz = Class.forName(cls.getName());
        Constructor<?> cons = clasz.getConstructor(parameterTypes);
//        Constructor<?> cons = null;
//        if (parameterTypes!=null){
//            clasz.getConstructor(parameterTypes);
//        }else{
//            clasz.getConstructor();
//        }
        return cons;
    }

    public static Constructor<?> getConstructor(String packageName, Class<?>... parameterTypes) throws ClassNotFoundException, NoSuchMethodException {
        Class<?> clasz = Class.forName(packageName);
        Constructor<?> cons = clasz.getConstructor(parameterTypes);
//        Constructor<?> cons = null;
//        if (parameterTypes!=null){
//            clasz.getConstructor(parameterTypes);
//        }else{
//            clasz.getConstructor();
//        }
        return cons;
    }

    public static Object newObject(Constructor<?> constructor, Object... initargs) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return constructor.newInstance(initargs);
    }

    public static <T> void registerService(Class<T> clazz, Class<?> paramClass) {
        registerService(clazz, new ArgsBean(paramClass));
    }

    public static <T> void registerService(Class<T> clazz, ArgsBean<?> ArgsBean) {
        if (clazz != null) {
            List<ArgsBean<?>> tmpList = servies.get(clazz);
            if (tmpList == null || tmpList.isEmpty()) {
                tmpList = new ArrayList<>();
                servies.put(clazz, tmpList);
            }
            tmpList.add(ArgsBean);
        }
    }

    public static void registerService(Map<Class<?>, ArgsBean<?>> maps) {
        if (maps == null || maps.isEmpty())
            return;
        Set<Class<?>> keys = maps.keySet();
        if (keys == null)
            return;
        Iterator<Class<?>> it = keys.iterator();
        if (it == null)
            return;
        while (it.hasNext()) {
            Class<?> clazz = it.next();
            ArgsBean<?> ArgsBean = maps.get(clazz);
            if (clazz == null || ArgsBean == null)
                continue;
            registerService(clazz, ArgsBean);
        }
    }

    public static void registerService(Class<?> paramClass) {
        Class<?>[] clazzs = paramClass.getInterfaces();
        if (clazzs != null && clazzs.length > 0) {
            Class<?> clazz = clazzs[0];
            if (clazz != null) {
                List<ArgsBean<?>> tmpList = servies.get(clazz);
                if (tmpList == null || tmpList.isEmpty()) {
                    tmpList = new ArrayList<>();
                    servies.put(clazz, tmpList);
                }
                tmpList.add(new ArgsBean(paramClass));
            }
        }
    }

    public static void registerService(ArgsBean<?> paramClass) {
        if (paramClass == null)
            return;
        if (paramClass.getClazz() == null)
            return;
        Class<?>[] clazzs = paramClass.getClazz().getInterfaces();
        if (clazzs != null && clazzs.length > 0) {
            Class<?> clazz = clazzs[0];
            if (clazz != null) {
                List<ArgsBean<?>> tmpList = servies.get(clazz);
                if (tmpList == null || tmpList.isEmpty()) {
                    tmpList = new ArrayList<>();
                    servies.put(clazz, tmpList);
                }
                tmpList.add(paramClass);
            }
        }
    }

    public static void registerService(HBaseInterface hBaseInterface) {
        if (hBaseInterface == null)
            return;
        if (hBaseInterface.getClass() == null)
            return;
        Class<?>[] clazzs = hBaseInterface.getClass().getInterfaces();
        if (clazzs != null && clazzs.length > 0) {
            Class<?> clazz = clazzs[0];
            if (clazz != null) {
                List<ArgsBean<?>> tmpList = servies.get(clazz);
                if (tmpList == null || tmpList.isEmpty()) {
                    tmpList = new ArrayList<>();
                    servies.put(clazz, tmpList);
                }
                tmpList.add(new ArgsBean(hBaseInterface));
            }
        }
    }

    public static <T> T getService1(Class<T> paramClass) {
        List<ArgsBean<?>> cls = servies.get(paramClass);
        if (cls != null && !cls.isEmpty()) {
            try {
                ArgsBean<?> clazz = cls.get(0);
                return (T) newObject(getConstructor(clazz.getClazz(), clazz.getArgsTypes()), clazz.getArgs());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T> T getService(Class<T> paramClass) {
        List<ArgsBean<?>> cls = servies.get(paramClass);
        if (cls != null && !cls.isEmpty()) {
            try {
                ArgsBean<?> clazz = cls.get(0);
                if (clazz.gethBaseInterface() != null) {
                    return (T) clazz.gethBaseInterface();
                } else {
                    return (T) newObject(getConstructor(clazz.getClazz(), clazz.getArgsTypes()), clazz.getArgs());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static <T> List<T> getServices(Class<T> paramClass) {
        List<ArgsBean<?>> cls = servies.get(paramClass);
        if (cls != null && !cls.isEmpty()) {
            List<T> instances = new ArrayList<>();
            for (ArgsBean<?> clazz : cls) {
                if (clazz == null)
                    continue;
                try {
                    T instance = (T) newObject(getConstructor(clazz.getClazz(), clazz.getArgsTypes()), clazz.getArgs());
                    instances.add(instance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (instances.isEmpty())
                return null;
            return instances;
        }
        return null;
    }


    public static <T extends HBaseInterface> void setBaseInterface(T inter) {
        Class<? extends HBaseInterface> clasz = inter.getClass();
        if (clasz != null) {
            Class<?>[] inters = clasz.getInterfaces();
            if (inters != null && inters.length > 0) {
                for (Class cls : inters) {
                    if (cls == null)
                        continue;
                    if (!cls.isInterface())
                        continue;
                    boolean belong = HBaseInterface.class.isAssignableFrom(cls);
                    if (!belong)
                        continue;
                    List<ArgsBean<?>> tmpList = servies.get(cls);
                    if (tmpList == null || tmpList.isEmpty()) {
                        tmpList = new ArrayList<>();
                        servies.put(cls, tmpList);
                    } else {
                        tmpList.clear();
                    }
                    tmpList.add(new ArgsBean(inter));
                }
            }
        }
    }

    public static <T> void setInterface(T inter) {
        Class<?> clasz = inter.getClass();
        if (clasz != null) {
            Class<?>[] inters = clasz.getInterfaces();
            if (inters != null && inters.length > 0) {
                for (Class cls : inters) {
                    if (cls == null)
                        continue;
                    if (!cls.isInterface())
                        continue;
                    List<ArgsBean<?>> tmpList = servies.get(cls);
                    if (tmpList == null || tmpList.isEmpty()) {
                        tmpList = new ArrayList<>();
                        servies.put(cls, tmpList);
                    } else {
                        tmpList.clear();
                    }
                    tmpList.add(new ArgsBean(inter));
                }
            }
        }

    }

    public static <T> T getInterface(Class<T> clazz) {
        if (clazz == null)
            return null;
        List<ArgsBean<?>> inters = servies.get(clazz);
        if (inters == null)
            return null;
        if (inters.size() <= 0)
            return null;
        ArgsBean<?> inter = inters.get(0);
        if (inter == null)
            return null;
        Object object = inter.getObject();
        if (object == null)
            return null;
        return (T) object;
    }

}
