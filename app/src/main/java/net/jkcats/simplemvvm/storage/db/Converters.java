package net.jkcats.simplemvvm.storage.db;

import androidx.room.TypeConverter;

import com.google.gson.Gson;

import net.jkcats.simplemvvm.storage.db.sample.entity.User;


public class Converters {

    @TypeConverter
    public static String beanToText(User data) {
        return new Gson().toJson(data);
    }

    @TypeConverter
    public static User textToBean(String value) {
        return new Gson().fromJson(value, User.class);
    }
}
