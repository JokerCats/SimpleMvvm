package net.jkcats.simplemvvm.basics;

import android.app.Application;

import net.jkcats.simplemvvm.network.process.rxjava.RxJavaProcess;
import net.jkcats.simplemvvm.network.proxy.ClientProxy;
import net.jkcats.simplemvvm.storage.db.sample.DemoDataBase;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class BaseApplication extends Application {

    private DemoDataBase database = null;

    public DemoDataBase getDatabase() {
        return database;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 使用代理方法
//        ClientProxy.init(new Okhttp3Process());
        ClientProxy.init(new RxJavaProcess(), this);
        // 初始化数据库
        database = DemoDataBase.get(this);
    }
}
