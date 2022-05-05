package ru.mirea.goryacheva.mireaprojectpractice3.ui.stories;

import android.app.Application;
import androidx.room.Room;

public class App extends Application {
    public static App instance;
    private AppDatabase db;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        db = Room.databaseBuilder(this, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        db.clearAllTables();
    }

    public static App getInstance() { return instance; }

    public AppDatabase getDatabase() { return db; }
}