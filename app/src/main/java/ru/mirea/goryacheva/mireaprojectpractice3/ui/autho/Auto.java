package ru.mirea.goryacheva.mireaprojectpractice3.ui.autho;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.mirea.goryacheva.mireaprojectpractice3.MainActivity;
import ru.mirea.goryacheva.mireaprojectpractice3.R;

public class Auto extends AppCompatActivity implements View.OnClickListener {

    private EditText mEmailField;
    private EditText mPasswordField;

    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto);

        mEmailField = findViewById(R.id.textviewFieldEmail);
        mPasswordField = findViewById(R.id.textviewFieldPassword);

        findViewById(R.id.buttonSignIn).setOnClickListener(this);
        findViewById(R.id.buttonCreateAccount).setOnClickListener(this);
        findViewById(R.id.buttonSignOut).setOnClickListener(this);
        findViewById(R.id.buttonGoToContent).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {

            findViewById(R.id.buttonSignIn).setVisibility(View.GONE);
            findViewById(R.id.buttonCreateAccount).setVisibility(View.GONE);
            findViewById(R.id.buttonSignOut).setVisibility(View.VISIBLE);
            findViewById(R.id.buttonGoToContent).setVisibility(View.VISIBLE);
            findViewById(R.id.textviewFieldEmail).setVisibility(View.GONE);
            findViewById(R.id.textviewFieldPassword).setVisibility(View.GONE);

        } else {

            findViewById(R.id.buttonSignIn).setVisibility(View.VISIBLE);
            findViewById(R.id.buttonCreateAccount).setVisibility(View.VISIBLE);
            findViewById(R.id.buttonSignOut).setVisibility(View.GONE);
            findViewById(R.id.buttonGoToContent).setVisibility(View.GONE);
            findViewById(R.id.textviewFieldEmail).setVisibility(View.VISIBLE);
            findViewById(R.id.textviewFieldPassword).setVisibility(View.VISIBLE);
        }
    }

    private boolean validateForm() {
        boolean valid = true;
        String email = mEmailField.getText().toString();

        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else { mEmailField.setError(null); }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else { mPasswordField.setError(null); }
        return valid;
    }

    private void createAccount(String email, String password) {

        if (!validateForm()) { return; }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else { updateUI(null); }
                    }
                });
    }

    private void signIn(String email, String password) {

        if (!validateForm()) { return; }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else { updateUI(null); }
                    }
                });
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.buttonCreateAccount) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (i == R.id.buttonSignIn) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (i == R.id.buttonSignOut) {
            signOut();
        } else if (i == R.id.buttonGoToContent) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}