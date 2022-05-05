package ru.mirea.goryacheva.mireaprojectpractice3.ui.stories;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Story.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    public abstract StoryDao storyDao();
}