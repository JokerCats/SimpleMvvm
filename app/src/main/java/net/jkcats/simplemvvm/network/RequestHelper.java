package net.jkcats.simplemvvm.network;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestHelper {

    private static class CreateHolder {
        public static final RequestHelper INSTANCE = new RequestHelper();
    }

    private OkHttpClient mOkHttpClient;

    private RequestHelper() {
        if (mOkHttpClient == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            mOkHttpClient = new OkHttpClient.Builder().addInterceptor(logging).build();
        }
    }

    public static RequestHelper getInstance() {
        return CreateHolder.INSTANCE;
    }

    public RequestManifest getService() {
        return getService(RequestConfig.BASE_URL);
    }

    /**
     * 获取网络请求服务
     *
     * @param domain First-level domain name.
     */
    public RequestManifest getService(String domain) {
        return new Retrofit.Builder()
//                .client(mOkHttpClient)
                .baseUrl(domain)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RequestManifest.class);
    }
}
