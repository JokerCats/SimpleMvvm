package net.jkcats.simplemvvm.network.process.rxjava.convert;

import android.text.TextUtils;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * 字符串转换器，用于处理Gson中的字符串序列化和反序列化。
 */
public class StringConverter implements JsonSerializer<String>, JsonDeserializer<String> {

    /**
     * 将字符串序列化为Json元素。
     * @param src                  要序列化的字符串
     * @param typeOfSrc            字符串的类型
     * @param context              序列化上下文
     * @return                     序列化后的Json元素
     */
    public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
        if (TextUtils.isEmpty(src)) {
            return new JsonPrimitive("");
        } else {
            return new JsonPrimitive(src);
        }
    }

    /**
     * 将Json元素反序列化为字符串。
     * @param json                  要反序列化的Json元素
     * @param typeOfT               字符串的类型
     * @param context               反序列化上下文
     * @return                      反序列化后的字符串
     * @throws JsonParseException   如果反序列化过程中发生错误
     */
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return json.getAsJsonPrimitive().getAsString();
    }
}
