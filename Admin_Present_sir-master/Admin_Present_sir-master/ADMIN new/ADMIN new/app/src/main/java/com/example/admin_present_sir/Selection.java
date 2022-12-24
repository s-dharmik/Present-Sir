package com.example.admin_present_sir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Selection extends AppCompatActivity {

    Button btn_s,btn_f;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        sessionManager = new SessionManager(this);

        btn_s=findViewById(R.id.btn_Student);
        btn_f=findViewById(R.id.btn_Faculty);

        btn_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Selection.this,Student.class);
                startActivity(intent);

            }
        });

        btn_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Selection.this,Faculty.class);
                startActivity(intent);
                
            }
        });
    }
    public void btn_logout_admin(View view) {
        sessionManager.logout();
    }
}