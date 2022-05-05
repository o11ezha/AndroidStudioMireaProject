package ru.mirea.goryacheva.mireaprojectpractice3.ui.stories;

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
import ru.mirea.goryacheva.mireaprojectpractice3.ui.stories.Story;
import ru.mirea.goryacheva.mireaprojectpractice3.ui.stories.StoryDao;

public class StoryView extends AppCompatActivity {
    public EditText addName;
    public EditText addDesc;
    public Button buttonSave;
    private AppDatabase db;
    private StoryDao storyDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.story_view);

        db = App.getInstance().getDatabase();
        storyDao = db.storyDao();

        addName = findViewById(R.id.NameEdit);
        addDesc = findViewById(R.id.AgeEdit);

        buttonSave = findViewById(R.id.buttonSaveStory);
        buttonSave.setOnClickListener(this::saveNewStory);
    }

    public void saveNewStory(View view) {

        Story story = new Story();
        story.name = addName.getText().toString();
        story.description = addDesc.getText().toString();

        storyDao.insert(story);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}