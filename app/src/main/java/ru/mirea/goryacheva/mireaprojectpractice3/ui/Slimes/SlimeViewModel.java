package ru.mirea.goryacheva.mireaprojectpractice3.ui.Slimes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import ru.mirea.goryacheva.mireaprojectpractice3.ui.stories.App;
import ru.mirea.goryacheva.mireaprojectpractice3.ui.stories.AppDatabase;

public class SlimeViewModel extends ViewModel {

    private final LiveData<List<Slime>> slimes;

    private final AppDatabase appDatabase = App.instance.getDatabase();
    private final SlimeDao slimeDao = appDatabase.slimeDao();

    public SlimeViewModel() { slimes = slimeDao.getAllSlime(); }

    public List<Slime> getSlimes() { return slimes.getValue(); }

    public void addSlime(Slime slime) { slimeDao.insert(slime); }

    public LiveData<List<Slime>> getAllSlimesLiveData() { return slimes; }

}