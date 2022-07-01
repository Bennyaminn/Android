package com.example.lab3;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PhoneDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insert(Phone phone);

    @Query("DELETE FROM phone_table")
    void deleteAll();

    @Update()
    void update(Phone phone);

    @Query("SELECT * FROM phone_table ORDER BY producent desc")
    LiveData<List<Phone>> getAlphabetizedPhones();

    @Delete
    void deletePhone(Phone phone);

    @Query("DELETE FROM phone_table WHERE id = :id")
    void deletePhoneById(long id);
}
