package com.example.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class home_admin extends AppCompatActivity {

    Button btnCreate, btnSchedule, btnHistory, btnMedicalNotes, btnLogOut, btnDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btnCreate = findViewById(R.id.btnCreate);
        btnSchedule = findViewById(R.id.btnSchedule);
        btnHistory = findViewById(R.id.btnHistory);
        btnMedicalNotes = findViewById(R.id.btnMedicalNotes);
        btnLogOut = findViewById(R.id.btnLogOut);
        btnDashboard = findViewById(R.id.btnDashboard);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(logout);
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminCreatePsikolog.class);
                startActivity(intent);
            }
        });

        btnSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminSchedulePage.class);
                startActivity(intent);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminNamePickerHistory.class);
                startActivity(intent);
            }
        });


        //adminmedicalnotesdetail
        btnMedicalNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminNamePickerNotes.class);
                startActivity(intent);
            }
        });

        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainDashboard.class);
                startActivity(intent);
            }
        });

    }
}