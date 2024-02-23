package net.jkcats.simplemvvm.network.process;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.jkcats.simplemvvm.network.api.RequestConfig;
import net.jkcats.simplemvvm.network.callback.ICallback;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

public class Okhttp3Process implements IClientProcess {

    private final OkHttpClient mOkHttpClient;

    public Okhttp3Process() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    @Override
    public void sendGet(String path, ICallback callback) {
        sendGet(path, null, callback);
    }

    @Override
    public void sendGet(String path, Map<String, Object> params, ICallback callback) {
        Request request = new Request.Builder()
                .url(RequestConfig.BASE_URL + path)
                .get()
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                final String errorMsg = e.getMessage();
                callback.onFailure(errorMsg);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final ResponseBody body = response.body();
                    if (body != null) {
                        callback.onSuccess(body.string());
                    } else {
                        callback.onFailure("body is null.");
                    }
                } else {
                    // todo analyse response code.
                    callback.onFailure("response is failure.");
                }
            }
        });
    }

    @Override
    public void sendPost(String path, @Nullable Map<String, Object> params, ICallback callback) {

    }
}
