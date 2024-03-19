package net.jkcats.simplemvvm.storage.db.sample.entity;

import android.app.Application;
import android.text.TextUtils;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import net.jkcats.simplemvvm.basics.BaseApplication;


@Entity(tableName = "table_user")
public class User {


    private static User instance;

    public static synchronized User getInstance(BaseApplication app) {
        if (instance == null) {
            instance = app.getDatabase().userDao().findUser();
            if (instance == null) {
                instance = new User();
            }
        }

        return User.instance;
    }

    public static boolean isLogon() {
        // todo 替换成项目的业务逻辑
        return !TextUtils.isEmpty("");
    }

    public static String getToken() {
        // todo 替换成项目的业务逻辑
        return "";
    }

    public static void update() {
        instance = null;
    }

    public static void clearAllUser(BaseApplication app ) {
        app.getDatabase().userDao().clear();
        instance = null;
    }

    @PrimaryKey
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "name")
    public String name;
}
