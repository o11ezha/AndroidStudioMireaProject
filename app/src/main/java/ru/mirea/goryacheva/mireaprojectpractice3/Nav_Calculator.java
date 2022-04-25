package ru.mirea.goryacheva.mireaprojectpractice3;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Nav_Calculator#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Nav_Calculator extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Nav_Calculator() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Nav_Calculator.
     */
    // TODO: Rename and change types and number of parameters
    public static Nav_Calculator newInstance(String param1, String param2) {
        Nav_Calculator fragment = new Nav_Calculator();
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

    public String FirstNumber = "";
    public String Operation = "";
    public String SecondNumber = "";

    private TextView Result;
    private EditText NumberField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nav__calculator,
                container, false);

        Result = view.findViewById(R.id.resulttextView);
        NumberField = view.findViewById(R.id.editTextTextPersonName3);

        buttonmenu(view, R.id.button12);
        buttonmenu(view, R.id.button13);
        buttonmenu(view, R.id.button21);
        buttonmenu(view, R.id.button14);
        buttonmenu(view, R.id.button15);
        buttonmenu(view, R.id.button16);
        buttonmenu(view, R.id.button17);
        buttonmenu(view, R.id.button18);
        buttonmenu(view, R.id.button19);
        buttonmenu(view, R.id.button22);
        buttonmenu(view, R.id.button2);
        buttonmenu(view, R.id.button8);
        buttonmenu(view, R.id.button6);
        buttonmenu(view, R.id.button7);
        buttonmenu(view, R.id.button3);
        buttonmenu(view, R.id.button20);

        return view;
    }

    public Button buttonP;

    public void buttonmenu(View view, int butId) {
        buttonP = view.findViewById(butId);

        switch (buttonP.getText().toString()) {
            case "+":
                onOperationClick(view, butId);
                break;
            case "-":
                onOperationClick(view, butId);
                break;
            case "*":
                onOperationClick(view, butId);
                break;
            case ":":
                onOperationClick(view, butId);
                break;
            case "=":
                onResultClick(view, butId);
                break;
            default:
                onNumberClick(view, butId);
                break;
        }
    }

    public void onNumberClick(View view, int butId) {
        Button button = (Button) view.findViewById(butId);

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (Operation.equals("")) {
                    FirstNumber += button.getText().toString();
                    NumberField.setText(FirstNumber);
                } else {
                    NumberField.setText("");
                    SecondNumber += button.getText().toString();
                    NumberField.setText(SecondNumber);
                    Result.setText(FirstNumber.concat(" ").concat(Operation).concat(" ").concat(SecondNumber));
                }
            }
        });
    }

    public void onOperationClick(View view, int butId) {
        Button button = (Button) view.findViewById(butId);

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (!FirstNumber.equals("")) {
                    Operation = button.getText().toString();
                    Result.setText(FirstNumber.concat(" ").concat(Operation));
                }
            }
        });
    }

    public void onResultClick(View view, int butId) {
        Button button = (Button) view.findViewById(butId);

        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                if (!FirstNumber.equals("") && !Operation.equals("") && !SecondNumber.equals("")) {

                    try {
                        double One = Double.parseDouble(FirstNumber);
                        double Two = Double.parseDouble(SecondNumber);

                        double Result;

                        String StringResult = "null";

                        switch (Operation) {
                            case "+":
                                Result = One + Two;
                                StringResult = String.valueOf(Result);
                                break;
                            case "-":
                                Result = One - Two;
                                StringResult = String.valueOf(Result);
                                break;
                            case "*":
                                Result = One * Two;
                                StringResult = String.valueOf(Result);
                                break;
                            case ":":
                                if (Two != 0) {
                                    Result = (double) One / Two;
                                    StringResult = String.valueOf(Result);
                                } else {
                                    Nav_Calculator.this.Result.setText("You can't devide by zero");
                                }
                                break;
                        }

                        Nav_Calculator.this.Result.setText(StringResult);
                    } catch (NumberFormatException e) {
                        Result.setText("Wrong Number Format - Try Again");
                    }

                    Nav_Calculator.this.NumberField.setText("");

                    FirstNumber = "";
                    Operation = "";
                    SecondNumber = "";
                }
            }
        });
    }
}