package ru.mirea.goryacheva.mireaprojectpractice3.ui.stories;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

import ru.mirea.goryacheva.mireaprojectpractice3.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavStories#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavStories extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NavStories() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NavStories.
     */
    // TODO: Rename and change types and number of parameters
    public static NavStories newInstance(String param1, String param2) {
        NavStories fragment = new NavStories();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private List<Story> stories;
    public RecyclerView recyclerView;
    public Button createNewStory;
    private StoryDao storyDao;
    private AppDatabase db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_stories, container, false);

        db = App.getInstance().getDatabase();

        storyDao = db.storyDao();
        stories = storyDao.getAll();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        createNewStory = view.findViewById(R.id.buttonCreateStory);
        createStories(view);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        StoryAdapter adapter = new StoryAdapter(stories);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void createStories(View view) {

        createNewStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), StoryView.class);
                startActivity(intent);
            }
        });
    }
}