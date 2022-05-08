package ru.mirea.goryacheva.mireaprojectpractice3.ui.Slimes;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SlimeDao {
        @Query("SELECT * FROM SLIME")
        LiveData<List<Slime>> getAllSlime();

        @Insert
        void insert(Slime slime);

        @Update
        void update(Slime slime);
}