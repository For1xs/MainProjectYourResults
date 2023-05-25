package com.example.mainprojectyourresults;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText email;
    private TextInputEditText password;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private TextView registerNow;
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();
        init();
        setOnClickListenerForLoginNowText();
        setOnClickListenerForLoginButton();
    }
    private void init(){
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        registerNow = findViewById(R.id.registerNow);

    }
    private void setOnClickListenerForLoginNowText(){
        registerNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setOnClickListenerForLoginButton(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE );
                String valEmail;
                String valPassword;
                valEmail = String.valueOf(email.getText());
                valPassword = String.valueOf(password.getText());
                if (TextUtils.isEmpty(valEmail)){
                    Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_LONG).show();
                    return;

                }
                if (TextUtils.isEmpty(valPassword)){
                    Toast.makeText(LoginActivity.this, "Enter password", Toast.LENGTH_LONG).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(valEmail, valPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(LoginActivity.this, "Login successful",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });



            }
        });
    }

}