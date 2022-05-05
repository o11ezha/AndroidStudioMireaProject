package ru.mirea.goryacheva.mireaprojectpractice3.ui.stories;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Story {
    @PrimaryKey(autoGenerate = true)

    public long id;
    public String name;
    public String description;
}
