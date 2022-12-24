package com.example.admin_present_sir;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import java.util.HashMap;



public class HomeActivity extends AppCompatActivity {

    SessionManager sessionManager;
    String getRole;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);


        HashMap<String, String> user = sessionManager.getUserDetail();

        getRole = user.get(sessionManager.ID);

        if (getRole == null)
        {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            finish();
        }
        else if (getRole.equals("12"))
        {
            startActivity(new Intent(getApplicationContext(), Selection.class));
            finish();
        }
        else
        {
            startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            finish();
        }

    }
}


