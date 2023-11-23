package com.example.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AdminMedicalNotesDetail extends AppCompatActivity {

    TextView txtNamaMahasiswa, txtNamaPsikolog, txtMedia, txtDateTime, txtMedicalNotes;
    Button btnSubmit;
    String namaMahasiswa, namaPsikolog, media, date, time, medicalNotes, dateTime, notes;

    private JSONObject data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_medical_notes_detail);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        Intent intent = getIntent();
        String Name = intent.getExtras().getString("Name");

        getMedicalNotes(Name);

        Toast.makeText(getApplicationContext(), Name, Toast.LENGTH_LONG).show();

        txtNamaMahasiswa = findViewById(R.id.txtNamaMahasiswa);
        txtNamaPsikolog = findViewById(R.id.txtNamaPsikolog);
        txtMedia = findViewById(R.id.txtMedia);
        txtDateTime = findViewById(R.id.txtDateTime);
        txtMedicalNotes = findViewById(R.id.EdtMedicalNotes);
        btnSubmit = findViewById(R.id.btnSubmitMedicalNotes);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes = txtMedicalNotes.getText().toString();
                updateNotes(Name, notes);

            }
        });
    }

    private void updateNotes(String Name, String Notes){
        RequestQueue queue = Volley.newRequestQueue(AdminMedicalNotesDetail.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.urlUpdateNotes, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object = new JSONObject(response);
                    int sukses = object.getInt("code");

                    if (sukses == 200){

                        Intent back = new Intent(getApplicationContext(), home_admin.class);
                        startActivity(back);
                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", Name);
                params.put("notes", Notes);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.getCache().clear();
        queue.add(stringRequest);

    }
    private void getMedicalNotes(String Name){
        RequestQueue queue = Volley.newRequestQueue(AdminMedicalNotesDetail.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.urlGetMedicalNotes, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object = new JSONObject(response);
                    int sukses = object.getInt("code");

                    if (sukses == 200){
                        data = object.getJSONObject("data");
                        namaMahasiswa = data.getString("fullname");
                        namaPsikolog = data.getString("name");
                        media = data.getString("place");
                        date = data.getString("DATE_TEST");
                        time = data.getString("TIME_TEST");
                        medicalNotes = data.getString("notes");
                        dateTime = date+" / "+time;

                        txtNamaMahasiswa.setText(namaMahasiswa);
                        txtNamaPsikolog.setText(namaPsikolog);
                        txtMedia.setText(media);
                        txtDateTime.setText(dateTime);
                        if (medicalNotes != null) {
                            txtMedicalNotes.setText(medicalNotes);
                        }


                    }
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", Name);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        queue.getCache().clear();
        queue.add(stringRequest);
    }
}