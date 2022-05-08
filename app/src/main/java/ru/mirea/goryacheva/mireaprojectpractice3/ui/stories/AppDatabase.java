package ru.mirea.goryacheva.mireaprojectpractice3.ui.stories;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import ru.mirea.goryacheva.mireaprojectpractice3.ui.Slimes.Slime;
import ru.mirea.goryacheva.mireaprojectpractice3.ui.Slimes.SlimeDao;

@Database(entities = {Story.class, Slime.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract StoryDao storyDao();
    public abstract SlimeDao slimeDao();
}