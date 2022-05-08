package ru.mirea.goryacheva.mireaprojectpractice3.ui.Slimes;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.goryacheva.mireaprojectpractice3.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavSlimes#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavSlimes extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NavSlimes() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NavSlimes.
     */
    // TODO: Rename and change types and number of parameters
    public static NavSlimes newInstance(String param1, String param2) {
        NavSlimes fragment = new NavSlimes();
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

    private final List<Slime> slimes = new ArrayList<>();

    private SlimeViewModel slimesViewModel;
    private final SlimeAdapter slimeAdapter = new SlimeAdapter(slimes);

    private ActivityResultLauncher<Intent> launcher;

    public static final int ADD_RESULT_CODE=1;

    public static final String NAME_LABEL="name";
    public static final String TYPE_LABEL="type";
    public static final String DIET_LABEL="diet";
    public static final String FAVMEAL_LABEL="favmeal";
    public static final String FAVTOY_LABEL="favtoy";
    public static final String PLORT_LABEL="plort";

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_slimes, container, false);

        if (getActivity() != null) {
            slimesViewModel = new ViewModelProvider(getActivity()).get(SlimeViewModel.class);
            slimesViewModel.getAllSlimesLiveData().observe(getActivity(), this::onChanged);
        }

        view.findViewById(R.id.buttonCreateSlime).setOnClickListener(this::onNewSlimeClicked);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.slimesRecyclerView);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(slimeAdapter);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                (result) -> {
                    if (result.getResultCode() == ADD_RESULT_CODE && result.getData() != null) {
                        Slime slime = new Slime();
                        slime.name = result.getData().getStringExtra(NAME_LABEL);
                        slime.type = result.getData().getStringExtra(TYPE_LABEL);
                        slime.diet = result.getData().getStringExtra(DIET_LABEL);
                        slime.favmeal = result.getData().getStringExtra(FAVMEAL_LABEL);
                        slime.favtoy = result.getData().getStringExtra(FAVTOY_LABEL);
                        slime.plort = result.getData().getStringExtra(PLORT_LABEL);
                        slimesViewModel.addSlime(slime);
                        slimeAdapter.notifyDataSetChanged();
                    }
                });
        return view;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void onChanged(List<Slime> slimefromDB) {
        if (!slimes.isEmpty()){ slimes.clear(); }
        slimes.addAll(slimefromDB);
        slimeAdapter.notifyDataSetChanged();
    }

    private void onNewSlimeClicked(View view) {
        Intent intent = new Intent(getActivity(), SlimeView.class);
        launcher.launch(intent);
    }
}