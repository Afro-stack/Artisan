package com.example.artisan;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    TextView already;
    EditText name_et, email_et, password_et, username_et;
    Button sign_up;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        already = (TextView) findViewById(R.id.already);
        already.setOnClickListener(this);

        sign_up = (Button) findViewById(R.id.sign_up);
        sign_up.setOnClickListener(this);

        name_et = (EditText) findViewById(R.id.name_et);
        username_et = (EditText) findViewById(R.id.username_et);
        email_et = (EditText) findViewById(R.id.email_et);
        password_et = (EditText) findViewById(R.id.password_et);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_up:
                sign_up();
                break;
            case R.id.already:
                startActivity(new Intent(this, SignIn.class));
                break;
        }

    }

    private void sign_up() {
        String name = name_et.getText().toString().trim();
        String username = username_et.getText().toString().trim();
        String email = email_et.getText().toString().trim();
        String password = password_et.getText().toString().trim();
        String id = mAuth.getCurrentUser().getUid();
        String bio = "";
        String imageurl = "default";

        if(name.isEmpty()){
            name_et.setError("This field is required");
            name_et.requestFocus();
            return;
        }
        if(username.isEmpty()){
            username_et.setError("This field is required");
            username_et.requestFocus();
            return;
        }
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
        {
            mAuth.fetchSignInMethodsForEmail(email_et.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                        @Override
                        public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                            boolean check = !task.getResult().getSignInMethods().isEmpty();
                            if(check)
                            {
                                Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    User user = new User(name, username, email, password, id, bio, imageurl);
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {

                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                        progressBar.setVisibility(View.GONE);

                                        if (user.isEmailVerified()) {
                                            startActivity(new Intent(SignUp.this, StartActivity.class));
                                        }
                                        else {
                                            user.sendEmailVerification();
                                            Toast.makeText(SignUp.this, "Check your email to verify your account", Toast.LENGTH_LONG).show();
                                            progressBar.setVisibility(View.GONE);
                                        }
                                    if(!task.isSuccessful())
                                    {
                                        Toast.makeText(SignUp.this, "Failed to sign up", Toast.LENGTH_LONG).show();
                                        progressBar.setVisibility(View.GONE);
                                    }
                                }
                            }

                        });
                    }
                }
    });
}}
