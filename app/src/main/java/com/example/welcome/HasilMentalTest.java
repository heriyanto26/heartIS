package com.example.welcome;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class HasilMentalTest extends AppCompatActivity {

    TextView judul,subjudul,isi;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil_mental_test);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Objects.requireNonNull(getSupportActionBar()).hide();

        ActionBar bar;
        bar = getSupportActionBar();
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#6487DF"));
        assert bar != null;
        bar.setTitle("");
        bar.setBackgroundDrawable(cd);
        bar.setDisplayHomeAsUpEnabled(true);

        Intent HasilMentalTest = getIntent();
        int score = HasilMentalTest.getIntExtra("score", 0);
        
        judul = findViewById(R.id.judul);
        subjudul = findViewById(R.id.subJudul);
        isi = findViewById(R.id.isi);

        if(score >= 26) {
            //pop_up_hasil_tes_positif
        }else {
            //pop_up_hasil_tes_negatif
        }


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent back = new Intent(getApplicationContext(), Home.class);
        startActivity(back);
        return true;
    }
}