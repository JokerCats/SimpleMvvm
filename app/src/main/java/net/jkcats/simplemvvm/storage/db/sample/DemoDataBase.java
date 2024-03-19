package net.jkcats.simplemvvm.storage.db.sample;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import net.jkcats.simplemvvm.storage.db.Converters;
import net.jkcats.simplemvvm.storage.db.sample.dao.UserDao;
import net.jkcats.simplemvvm.storage.db.sample.entity.User;


@Database(
        entities = {User.class},
        version = 1,
        exportSchema = false
)
@TypeConverters({Converters.class})
public abstract class DemoDataBase extends RoomDatabase {

    private static final String DatabaseFileName = "Demo.db";
    private static DemoDataBase instance;

    public static synchronized DemoDataBase get(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, DemoDataBase.class, DatabaseFileName)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;

    }
   public abstract UserDao userDao();
}
