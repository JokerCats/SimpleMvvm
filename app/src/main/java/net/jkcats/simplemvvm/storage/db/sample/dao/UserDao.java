package net.jkcats.simplemvvm.storage.db.sample.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import net.jkcats.simplemvvm.storage.db.sample.entity.User;


@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(User var1);

    @Query("SELECT * FROM table_user")
    User findUser();

    @Query("DELETE FROM table_user")
    void clear();

    @Update
    void updateUser(User user);

}
