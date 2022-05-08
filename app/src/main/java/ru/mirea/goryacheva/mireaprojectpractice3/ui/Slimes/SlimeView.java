package ru.mirea.goryacheva.mireaprojectpractice3.ui.Slimes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.goryacheva.mireaprojectpractice3.MainActivity;
import ru.mirea.goryacheva.mireaprojectpractice3.R;
import ru.mirea.goryacheva.mireaprojectpractice3.ui.stories.App;
import ru.mirea.goryacheva.mireaprojectpractice3.ui.stories.AppDatabase;

public class SlimeView extends AppCompatActivity {
    public EditText addName;
    public EditText addType;
    public EditText addDiet;
    public EditText addFavmeal;
    public EditText addFavtoy;
    public EditText addPlort;

    public Button buttonSave;

    private AppDatabase db;
    private SlimeDao slimeDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.slime_view);

        db = App.getInstance().getDatabase();
        slimeDao = db.slimeDao();

        addName = findViewById(R.id.NameEdit);
        addType = findViewById(R.id.TypeEdit);
        addDiet = findViewById(R.id.DietEdit);
        addFavmeal = findViewById(R.id.FavmealEdit);
        addFavtoy = findViewById(R.id.FavtoyEdit);
        addPlort = findViewById(R.id.PlortEdit);

        buttonSave = findViewById(R.id.buttonSaveSlime);
        buttonSave.setOnClickListener(this::saveNewSlime);
    }

    public void saveNewSlime(View view) {

        Slime slime = new Slime();
        slime.name = addName.getText().toString();
        slime.type = addType.getText().toString();
        slime.diet = addDiet.getText().toString();
        slime.favmeal = addFavmeal.getText().toString();
        slime.favtoy = addFavtoy.getText().toString();
        slime.plort = addPlort.getText().toString();

        slimeDao.insert(slime);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}