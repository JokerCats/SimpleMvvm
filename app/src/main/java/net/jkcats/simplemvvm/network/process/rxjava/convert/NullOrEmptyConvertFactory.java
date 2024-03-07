package net.jkcats.simplemvvm.network.process.rxjava.convert;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 用于处理空或空响应。
 */
public class NullOrEmptyConvertFactory extends Converter.Factory {

    @Nullable
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(@NotNull Type type,
                                                            @NonNull @NotNull Annotation[] annotations,
                                                            Retrofit retrofit) {
        // 从 Retrofit 中获取默认的转换器
        final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(
                this,
                type,
                annotations);

        // 创建一个自定义的转换器，用于处理空或空响应
        return new Converter<ResponseBody, Object>() {

            /**
             * 将响应体转换为对象。
             *
             * @param value 响应体
             * @return 转换后的对象，如果响应体为空则返回 null
             * @throws IOException 如果无法转换响应体
             */
            @Nullable
            @Override
            public Object convert(@NotNull ResponseBody value) throws IOException {
                // 检查响应体是否为空
                if (value.contentLength() == 0) {
                    return null;
                }
                // 委托默认转换器进行转换
                return delegate.convert(value);
            }
        };
    }
}