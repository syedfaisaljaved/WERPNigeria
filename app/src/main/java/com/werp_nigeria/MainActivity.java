package com.werp_nigeria;

import android.app.ProgressDialog;
import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText mEmail, mPassword;
    private Button mLogin, mSignup;
    private TextView mForgotPassword;
    private ProgressDialog mProgressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mProgressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();

        mEmail = (EditText) findViewById(R.id.email_edit_text_view);
        mPassword = (EditText) findViewById(R.id.password_edit_text_view);

        mLogin = (Button) findViewById(R.id.login_button);
        mLogin.setOnClickListener(mlistener);

        mSignup = (Button) findViewById(R.id.signup_button);
        mSignup.setOnClickListener(mlistener);

        mForgotPassword = (TextView) findViewById(R.id.forgot_password_text_view);
        mForgotPassword.setOnClickListener(mlistener);
    }

    View.OnClickListener mlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.login_button:
                    String enteredEmail = mEmail.getText().toString();
                    String enteredPassword = mPassword.getText().toString();

                    if (TextUtils.isEmpty(enteredEmail)) {
                        Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (TextUtils.isEmpty(enteredPassword)) {
                        Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    mProgressDialog.setMessage("Logging in...");
                    mProgressDialog.show();

                    firebaseAuth.signInWithEmailAndPassword(enteredEmail,enteredPassword).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mProgressDialog.hide();
                            if (task.isSuccessful()){
                                if (firebaseAuth.getCurrentUser().isEmailVerified()){
                                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                                }else {
                                    Toast.makeText(MainActivity.this,"Please verify your email address",Toast.LENGTH_LONG).show();
                                }
                            }else{
                                Toast.makeText(MainActivity.this,"This email is not registered.",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    break;
                case R.id.signup_button:
                    Intent intent = new Intent(MainActivity.this, SignUp.class);
                    startActivity(intent);
                    break;
                case R.id.forgot_password_text_view:
                    startActivity(new Intent(MainActivity.this, ForgotPassword.class));
                    break;
            }

        }
    };


}
