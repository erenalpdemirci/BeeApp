package com.example.beeapp.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.beeapp.data.model.Bee;

import java.util.List;

@Dao
public interface BeeDao {
    @Query("SELECT * FROM bees")
    LiveData<List<Bee>> getAllBees();

    @Query("SELECT * FROM bees WHERE id = :id")
    LiveData<Bee> getBeeById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Bee> bees);

    @Query("DELETE FROM bees")
    void deleteAll();
}