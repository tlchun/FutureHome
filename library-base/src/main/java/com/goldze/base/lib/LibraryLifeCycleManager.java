package com.goldze.base.lib;

import android.app.Application;
import android.util.Log;


import com.goldze.base.lib.sdk.ArgsBean;
import com.goldze.base.lib.sdk.ILibraryLifeCycle;
import com.goldze.base.lib.sdk.LibraryService;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.HashMap;

public class LibraryLifeCycleManager {
    private static final String TAG = LibraryLifeCycleManager.class.getSimpleName();
    private static LibraryLifeCycleManager instance = null;
    private static HashMap<Class<?>, ILibraryLifeCycle> sLibrary = new HashMap<>();

    public static LibraryLifeCycleManager getInstance() {
        if (instance == null) {
            synchronized (LibraryLifeCycleManager.class) {
                if (instance == null) {
                    instance = new LibraryLifeCycleManager();
                }
            }
        }
        return instance;
    }

    public void register(String claszForName) {
        try {
            Class service = Class.forName(claszForName);
            if (service == null)
                return;
            if (sLibrary.containsKey(service))
                return;
            Constructor constructor = service.getConstructor();
            ILibraryLifeCycle registerService = (ILibraryLifeCycle) constructor.newInstance();
            if (registerService != null) {
                sLibrary.put(service, registerService);
            } else {
                Log.e(TAG, "ILibraryLifeCycle is not component's grandfather!");
            }
        } catch (Exception e) {
            Log.e(TAG, "register fail!:" + e.getMessage());
        }


    }

    public <T extends ILibraryLifeCycle> void register(Class<T> service) {
        ILibraryLifeCycle registerService = null;
        try {
            if (service == null)
                return;
            if (sLibrary.containsKey(service))
                return;
            Log.i(TAG, "##uu##clife.LibraryLifeCycleManager.register");
            Constructor constructor = service.getConstructor();
            registerService = (ILibraryLifeCycle) constructor.newInstance();
            if (registerService != null) {
                sLibrary.put(service, registerService);
            } else {
                Log.e(TAG, "ILibraryLifeCycle is not component's grandfather!");
            }
        } catch (Exception e) {
            Log.e(TAG, "register fail!:" + e.getMessage());
        }
    }

    public <T extends ILibraryLifeCycle> void register(ArgsBean<T> paramClass) {
        if (paramClass == null)
            return;
        Class<T> service = paramClass.getClazz();
        if (service == null)
            return;
        try {
            ILibraryLifeCycle registerService = (ILibraryLifeCycle) LibraryService.newObject(LibraryService.getConstructor(paramClass.getClazz(), paramClass.getArgsTypes()), paramClass.getArgs());
            if (sLibrary.containsKey(service))
                return;
            Log.i(TAG, "##uu##clife.LibraryLifeCycleManager.register");
            if (registerService != null) {
                sLibrary.put(service, registerService);
            } else {
                Log.e(TAG, "ILibraryLifeCycle is not component's grandfather!");
            }
        } catch (Exception e) {
            Log.e(TAG, e + "");
            e.printStackTrace();
        }
    }

    public <T extends ILibraryLifeCycle> void register(Class<T> service, ArgsBean<T> paramClass) {
        if (paramClass == null)
            return;
        if (service == null)
            return;
        paramClass.clasz(service);
        try {
            ILibraryLifeCycle registerService = (ILibraryLifeCycle) LibraryService.newObject(LibraryService.getConstructor(paramClass.getClazz(), paramClass.getArgsTypes()), paramClass.getArgs());
            if (sLibrary.containsKey(service))
                return;
            Log.i(TAG, "##uu##clife.LibraryLifeCycleManager.register");
            if (registerService != null) {
                sLibrary.put(service, registerService);
            } else {
                Log.e(TAG, "ILibraryLifeCycle is not component's grandfather!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void onCreate(Application application) {
        Collection<ILibraryLifeCycle> values = sLibrary.values();
        for (ILibraryLifeCycle lifeCycle : values) {
            lifeCycle.onCreate(application);
        }
    }

    public void onTerminate() {
        Collection<ILibraryLifeCycle> values = sLibrary.values();
        for (ILibraryLifeCycle lifeCycle : values) {
            lifeCycle.onTerminate();
        }
        sLibrary.clear();
    }
}
