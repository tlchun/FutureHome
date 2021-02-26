package com.goldze.base.lib.sdk;

import android.app.Application;

public interface ILibraryLifeCycle {
    void onCreate(Application application);
    void onTerminate();
}
