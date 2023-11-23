package com.example.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Objects;

public class MainDashboard extends AppCompatActivity {
    String[] optBulan = {"Januari", "Februari", "Maret", "April", "Mei", "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
    ArrayAdapter<String> adapterGender;
    Spinner edtBulan;
    EditText edtTahun;
    Button btnNext;

    public static String Bulan, Tahun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        adapterGender = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optBulan);

        edtBulan = findViewById(R.id.edtBulan);
        edtTahun = findViewById(R.id.edtTahun);
        btnNext = findViewById(R.id.btnNext);

        edtBulan.setAdapter(adapterGender);
        edtBulan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Bulan = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tahun = edtTahun.getText().toString();
                Intent next = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(next);
            }
        });


    }
}