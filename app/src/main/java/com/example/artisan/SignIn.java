package com.example.artisan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    TextView already;
    EditText email_et, password_et;
    Button sign_in;

    FirebaseAuth mAuth;
    ProgressBar progressBar;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        already = (TextView) findViewById(R.id.already);
        already.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        sign_in = (Button) findViewById(R.id.sign_in);
        sign_in.setOnClickListener(this);

        email_et = (EditText) findViewById(R.id.email_et);
        password_et = (EditText) findViewById(R.id.password_et);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.already:
                startActivity(new Intent(this, SignUp.class));
                break;

            case R.id.sign_in:
            userSignin();
            break;

        }
    }

    private void userSignin() {
        String email = email_et.getText().toString().trim();
        String password = password_et.getText().toString().trim();

        if(email.isEmpty()){
            email_et.setError("This field is required");
            email_et.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_et.setError("Please provide a valid email");
            email_et.requestFocus();
            return;
        }
        if(password.isEmpty()){
            password_et.setError("This field is required");
            password_et.requestFocus();
            return;
    }
        if(password.length() < 6){
            password_et.setError("Min password length should be 6 characters");
            password_et.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            progressBar.setVisibility(View.GONE);

                            if (user.isEmailVerified()) {
                                Toast.makeText(SignIn.this, "Update your profile for better experience", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignIn.this, StartActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }else {
                                        Toast.makeText(SignIn.this, "Failed to sign in! Please check your credentials", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                    }
