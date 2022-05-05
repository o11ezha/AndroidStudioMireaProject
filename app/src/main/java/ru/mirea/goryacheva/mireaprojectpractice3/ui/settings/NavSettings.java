package ru.mirea.goryacheva.mireaprojectpractice3.ui.settings;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import ru.mirea.goryacheva.mireaprojectpractice3.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavSettings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavSettings extends Fragment {

    public AudioManager audioManager;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NavSettings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NavSettings.
     */
    // TODO: Rename and change types and number of parameters
    public static NavSettings newInstance(String param1, String param2) {
        NavSettings fragment = new NavSettings();
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

    private SeekBar seekBar;
    private Button upButton;
    private Button downButton;
    private Button levelBar;
    private Button savebutton;
    private EditText levelText;

    final String SAVED_NAME = "saved_name";

    private SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_settings, container, false);
        preferences = this.getActivity().getPreferences(MODE_PRIVATE);
        audioManager = (AudioManager) this.getActivity().getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

        upButton = view.findViewById(R.id.buttonVolumeUp);
        downButton = view.findViewById(R.id.buttonVolumeDown);
        levelBar = view.findViewById(R.id.buttonBar);
        savebutton = view.findViewById(R.id.buttonSave);
        seekBar = view.findViewById(R.id.seekBar2);
        levelText = view.findViewById(R.id.levelText);

        UpButton(view);
        DownButton(view);
        setLevelBar(view);
        save(view);

        return view;
    }

    public void UpButton(View view) {

        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
            }
        });
    }

    public void DownButton(View view) {
        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
            }
        });
    }

    public void save(View view) {
        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Integer.parseInt(levelText.getText().toString()) >= 0 && Integer.parseInt(levelText.getText().toString()) < 16) {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(SAVED_NAME, levelText.getText().toString());
                    editor.apply();
                }
            }
        });
    }

    public void setLevelBar(View view) {

        levelBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = preferences.getString(SAVED_NAME, "");
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, Integer.parseInt(name), AudioManager.FLAG_PLAY_SOUND);
                seekBar.setProgress(0);
                int persent = (int) (Integer.parseInt(name)*6.67);
                seekBar.setProgress(persent);
                System.out.println(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
            }
        });
    }
}