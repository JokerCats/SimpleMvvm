package net.jkcats.simplemvvm.network.callback;

import android.util.Log;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class ServerCallback<R> implements ICallback {

    private static final String TAG = ServerCallback.class.getSimpleName();

    @Override
    public final void onSuccess(String response) {
        Class<R> aClass = analyseClass(this);
        if (aClass != null) {
            Gson gson = new Gson();
            R result = gson.fromJson(response, aClass);
            onCompleted(result);
        }
    }

    @Override
    public void onFailure(String msg) {
        Log.e(TAG, msg);
        onError(msg);
    }

    private Class<R> analyseClass(Object obj) {
        Type type = obj.getClass().getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] arguments = ((ParameterizedType) type).getActualTypeArguments();
            return (Class<R>) arguments[0];
        }
        return null;
    }

    public abstract void onCompleted(R result);

    public abstract void onError(String errorMsg);
}
