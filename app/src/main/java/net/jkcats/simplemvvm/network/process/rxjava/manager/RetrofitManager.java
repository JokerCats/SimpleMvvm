package net.jkcats.simplemvvm.network.process.rxjava.manager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import net.jkcats.simplemvvm.network.api.RequestConfig;
import net.jkcats.simplemvvm.network.process.rxjava.convert.NullOrEmptyConvertFactory;
import net.jkcats.simplemvvm.network.process.rxjava.convert.StringConverter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit管理器，用于管理Retrofit实例的创建和初始化。
 */
public class RetrofitManager {

    private Retrofit retrofit;
    private static RetrofitManager manager;
    private final int TIME_OUT_SECONDS = 60;

    /**
     * 初始化Retrofit实例
     *
     * @param context context
     */
    public void initRetrofit(Context context) {
        if (retrofit == null) {
            create(context);
        }
    }

    /**
     * 获取RetrofitManager单例对象
     *
     * @return RetrofitManager实例
     */
    public static synchronized RetrofitManager getManager() {
        if (manager == null) {
            manager = new RetrofitManager();
        }
        return manager;
    }

    /**
     * 获取当前的Retrofit实例
     *
     * @return 当前的Retrofit实例
     */
    public Retrofit getRetrofit() {
        return retrofit;
    }

    /**
     * 创建Retrofit实例
     *
     * @param context context
     */
    private void create(Context context) {
        OkHttpClient.Builder okClient = new OkHttpClient.Builder()
                .connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .callTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        // 可在此处追加请求API接口的公共参数
                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Content-Type", "application/x-www-form-urlencoded")
                                .header("Accept", "application/json")
                                .header("device", "android");
                        Request build = requestBuilder.build();
                        return chain.proceed(build);
                    }
                });

        // 添加日志拦截器，仅在调试模式下使用
//      if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor log = new HttpLoggingInterceptor(message ->
                    Log.d("请求日志", message)
            );
            log.setLevel(HttpLoggingInterceptor.Level.BODY);
            okClient.addInterceptor(log);
//      }

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(RequestConfig.BASE_URL)
                .client(okClient.build())
                .addConverterFactory(new NullOrEmptyConvertFactory())
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create());
        retrofit = builder.build();
    }

    /**
     * 创建Gson实例
     *
     * @return Gson实例
     */
    public Gson buildGson() {
        return new GsonBuilder()
                .registerTypeAdapter(JsonElement.class, new StringConverter())
                .create();
    }
}
