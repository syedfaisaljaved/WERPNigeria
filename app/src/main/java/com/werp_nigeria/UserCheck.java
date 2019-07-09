package com.werp_nigeria;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/** this class check if the user is still logged in.
 * if true, directly opens the Profile**/

public class UserCheck extends Application {

    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    public void onCreate() {
        super.onCreate();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null && firebaseUser.isEmailVerified()){
            startActivity(new Intent(UserCheck.this,ProfileActivity.class));
        }

    }
}
