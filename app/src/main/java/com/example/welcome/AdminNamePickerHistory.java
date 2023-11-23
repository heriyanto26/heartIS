package com.example.welcome;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AdminNamePickerHistory extends AppCompatActivity {
    Spinner spnName;
    Button btnConfirm;
    String Name;

    private List<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_name_picker_history);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        spnName = findViewById(R.id.spin);
        btnConfirm = findViewById(R.id.btnConfirm);
        dataList = new ArrayList<>();
        loadJsonData();

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = spnName.getSelectedItem().toString();
                Intent i = new Intent(getApplicationContext(), AdminHistoryDetail.class);
                i.putExtra("Name", Name);
                startActivity(i);

            }
        });
    }

    private void loadJsonData() {
       RequestQueue queue = Volley.newRequestQueue(AdminNamePickerHistory.this);
       StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.urlGetUser, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
               try {
                   JSONObject object = new JSONObject(response);
                   JSONArray array = object.getJSONArray("data");
                   parseJsonResponse(array);
               } catch (JSONException e) {
                   e.printStackTrace();
               }


           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {

           }
       }){
           @Override
           public Map<String, String> getHeaders() throws AuthFailureError {
               return super.getHeaders();
           }
       };
       queue.getCache().clear();
       queue.add(stringRequest);

    }

    private void parseJsonResponse(JSONArray jsonArray) {
        try {

            // Extract names from the JSON data and populate the spinner
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("fullname");
                dataList.add(name);
            }

            // Populate the spinner with the data list
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spnName.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}