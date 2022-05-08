package ru.mirea.goryacheva.mireaprojectpractice3.ui.Slimes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Slime {

    @PrimaryKey(autoGenerate = true)

    public long id;
    public String name;
    public String type;
    public String diet;
    public String favmeal;
    public String favtoy;
    public String plort;
}
