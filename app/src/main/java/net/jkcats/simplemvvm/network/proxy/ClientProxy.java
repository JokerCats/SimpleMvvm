package net.jkcats.simplemvvm.network.proxy;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import net.jkcats.simplemvvm.network.api.RequestService;
import net.jkcats.simplemvvm.network.api.ResponseDataRx;
import net.jkcats.simplemvvm.network.callback.ICallback;
import net.jkcats.simplemvvm.network.process.IClientProcess;
import net.jkcats.simplemvvm.network.process.rxjava.BaseObserver;
import net.jkcats.simplemvvm.network.process.rxjava.RxJavaProcess;
import net.jkcats.simplemvvm.network.process.rxjava.manager.RetrofitManager;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;

public class ClientProxy implements IClientProcess {

    private static final String TAG = ClientProxy.class.getSimpleName();

    private static IClientProcess mClientProcess = null;

    private static class Holder {

        private static final ClientProxy INSTANCE = new ClientProxy();
    }

    private ClientProxy() {

    }

    public static ClientProxy obtain() {
        return Holder.INSTANCE;
    }

    @Override
    public void sendGet(String path, ICallback callback) {
        if (isClientPrepared()) {
            mClientProcess.sendGet(path, callback);
        }
    }

    @Override
    public void sendGet(String path, @Nullable Map<String, Object> params, ICallback callback) {
        if (isClientPrepared()) {
            mClientProcess.sendGet(path, params, callback);
        }
    }

    @Override
    public void sendPost(String path, @Nullable Map<String, Object> params, ICallback callback) {
        if (isClientPrepared()) {
            mClientProcess.sendPost(path, params, callback);
        }
    }

    private boolean isClientPrepared() {
        if (mClientProcess == null) {
            Log.e(TAG, "The client is not initialized.");
            return false;
        }
        return true;
    }

    public static void init(IClientProcess process) {
        mClientProcess = process;
    }

    // ############# Support Rxjava + Retrofit ########### start ########

    public static RequestService mRetrofit;

    public static void init(IClientProcess process, Context context) {
        init(process);
        // 使用 Rxjava + Retrofit 作为网络框架时，初始化 Retrofit 配置
        if (process instanceof RxJavaProcess) {
            RetrofitManager.getManager().initRetrofit(context);
            RetrofitManager mManager = RetrofitManager.getManager();
            mRetrofit = mManager.getRetrofit().create(RequestService.class);
        }
    }

    /**
     * 使用 Rxjava + Retrofit 执行网络请求并处理返回结果。
     *
     * @param <T>        响应数据的类型
     * @param observable 用于发送网络请求的 Observable
     * @param observer   用于接收并处理响应结果的 Observer
     */
    public <T> void sendRequest(Observable<ResponseDataRx<T>> observable, BaseObserver<T> observer) {
        if (!isClientPrepared()) {
            return;
        }
        if (mClientProcess instanceof RxJavaProcess) {
             ((RxJavaProcess) mClientProcess).requestApi(observable , observer);
        }
    }
    // ############# Support Rxjava + Retrofit ########### end ########
}
