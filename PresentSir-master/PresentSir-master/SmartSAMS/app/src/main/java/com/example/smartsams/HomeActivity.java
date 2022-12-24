package com.example.smartsams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.HashMap;

import com.example.smartsams.Director.Director_home;
import com.example.smartsams.HOD.HOD_home;
import com.example.smartsams.Student.Student_home;
import com.example.smartsams.Teacher.Teacher_home;

public class HomeActivity extends AppCompatActivity {

    SessionManager sessionManager;
    String getRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);


        HashMap<String, String> user = sessionManager.getUserDetail();

        getRole = user.get(sessionManager.ROLE);

        if (getRole == null)
        {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }
        else if (getRole.equals("01"))
        {
            startActivity(new Intent(getApplicationContext(), Student_home.class));
            finish();
        }
        else if(getRole.equals("02"))
        {
            startActivity(new Intent(getApplicationContext(), Teacher_home.class));
            finish();
        }
        else if(getRole.equals("03"))
        {
            startActivity(new Intent(getApplicationContext(), HOD_home.class));
            finish();
        }
        else if(getRole.equals("04"))
        {
            startActivity(new Intent(getApplicationContext(), Director_home.class));
            finish();
        }
        else
        {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();
        }

    }
}


