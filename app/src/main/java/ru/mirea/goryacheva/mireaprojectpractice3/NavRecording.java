package ru.mirea.goryacheva.mireaprojectpractice3;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.File;
import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavRecording#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavRecording extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NavRecording() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NavRecording.
     */
    // TODO: Rename and change types and number of parameters
    public static NavRecording newInstance(String param1, String param2) {
        NavRecording fragment = new NavRecording();
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

    private PlayRecord PlayRecordThread;
    private Button playButton;
    private Button startButton;
    private Button stopButton;

    private MediaRecorder mediaRecorder;
    private File audioFile;

    ActivityResultLauncher<String[]> permissionsRequest;

    private final String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };

    private boolean isWork;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nav_recording,
                container, false);

        playButton = (Button) view.findViewById(R.id.buttonListenRecord);
        startButton = (Button) view.findViewById(R.id.buttonStartRecord);
        stopButton = (Button) view.findViewById(R.id.buttonStopRecord);

        onPlayButtonClick(view);
        onRecordStartClick(view);
        onRecordStopClick(view);

        mediaRecorder = new MediaRecorder();
        permissionsRequest = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(), isGranted -> {
                    if (isGranted.containsValue(false)){
                        permissionsRequest.launch(PERMISSIONS);
                    } else {
                        isWork = true;
                    }
                });

        isWork = hasPermissions(getContext(), PERMISSIONS);

        if (!isWork) { permissionsRequest.launch(PERMISSIONS); }

        return view;
    }

    private void onPlayButtonClick(View view) {

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PlayRecordThread == null && audioFile != null) {
                    PlayRecordThread = new PlayRecord(getContext(),
                            audioFile.getAbsolutePath());
                    PlayRecordThread.start();
                } else {
                    if (PlayRecordThread != null) {
                        PlayRecordThread.interrupt();
                    }
                    PlayRecordThread = null;
                }
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (PlayRecordThread != null){
            PlayRecordThread.interrupt();
        }
    }

    private void onRecordStartClick(View view) {

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PlayRecordThread != null && PlayRecordThread.isAlive()){
                    PlayRecordThread.interrupt();
                }

                playButton.setEnabled(false);
                startButton.setEnabled(false);
                stopButton.setEnabled(true);
                stopButton.requestFocus();

                try { startRecording(); } catch (IOException e) { }
            }
        });
    }

    private void startRecording() throws IOException {
        if (isWork){
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            if (audioFile == null && getActivity() != null){
                audioFile = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_MUSIC),
                        "mirea.3gp");
            }

            mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
            mediaRecorder.prepare();
            mediaRecorder.start();
        }
    }

    private void onRecordStopClick(View view) {

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startButton.setEnabled(true);
                stopButton.setEnabled(false);
                startButton.requestFocus();

                stopRecording();
                processAudioFile();

                playButton.setEnabled(true);
            }
        });
    }

    private void stopRecording() {
        if(mediaRecorder != null){
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
        }
    }

    private void processAudioFile() {
        ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();

        values.put(MediaStore.Audio.Media.TITLE, "audio" + audioFile.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
        values.put(MediaStore.Audio.Media.DATA, audioFile.getAbsolutePath());

        ContentResolver contentResolver = getActivity().getContentResolver();

        Uri baseUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri newUri = contentResolver.insert(baseUri, values);

        getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_DENIED) {
                    return false;
                }
            }
        }
        return true;
    }
}