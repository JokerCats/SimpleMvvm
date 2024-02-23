package net.jkcats.simplemvvm.network.proxy;

import android.util.Log;

import androidx.annotation.Nullable;

import net.jkcats.simplemvvm.network.callback.ICallback;
import net.jkcats.simplemvvm.network.process.IClientProcess;

import java.util.Map;

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
}
