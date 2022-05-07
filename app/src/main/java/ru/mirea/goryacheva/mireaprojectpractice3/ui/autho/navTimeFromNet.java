package ru.mirea.goryacheva.mireaprojectpractice3.ui.autho;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

import ru.mirea.goryacheva.mireaprojectpractice3.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link navTimeFromNet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class navTimeFromNet extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public navTimeFromNet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment navSensors.
     */
    // TODO: Rename and change types and number of parameters
    public static navTimeFromNet newInstance(String param1, String param2) {
        navTimeFromNet fragment = new navTimeFromNet();
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
    
    private TextView textView;
    private String host = "time-a.nist.gov";
    private int port = 13;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nav_time_from_net, container, false);

        textView =  view.findViewById(R.id.textview);
        view.findViewById(R.id.buttonTime).setOnClickListener(this::knowTimeOnClick);

        return view;
    }

    public void knowTimeOnClick(View view) {
        GetTimeTask timeTask = new GetTimeTask();
        timeTask.execute();
    }
    private class GetTimeTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String timeResult = "";
            try {
                Socket socket = new Socket(host, port);
                BufferedReader reader = Sockettss.getReader(socket);
                reader.readLine();
                timeResult = reader.readLine();
                socket.close();
            } catch (IOException e) { e.printStackTrace(); }
            return timeResult;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            textView.setText(result);
        }
    }
}