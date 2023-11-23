package com.example.welcome;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class ForgotPassword extends AppCompatActivity {

    EditText edtEmail, edtPhone, edtNewPass, edtNewPassConfirm;
    Button btnChange;

    private JSONObject data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtNewPass = findViewById(R.id.edtNewPass);
        edtNewPassConfirm = findViewById(R.id.edtConfirmNewPass);
        btnChange = findViewById(R.id.btnChangeNewPass);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getForgot(edtEmail.getText().toString());

            }
        });


    }

    private void getForgot(final String email){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.urlGetForgot, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    int sukses = object.getInt("code");

                    if(sukses == 200){
                        data = object.getJSONObject("data");
                        String Phone = data.getString("phone");
                        if (edtPhone.getText().toString().equals(Phone)){
                            if(edtNewPass.getText().toString().equals(edtNewPassConfirm.getText().toString())){
                                updatePassword(edtNewPassConfirm.getText().toString(), edtEmail.getText().toString());
                            }else{
                                Toast.makeText(getApplicationContext(), "Mohon Periksa kembali data anda", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "Mohon Periksa kembali data anda", Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "Mohon Periksa kembali data anda", Toast.LENGTH_LONG).show();

                    }
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
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                return params;
            }
        };
        queue.getCache().clear();
        queue.add(stringRequest);
    }

    private void updatePassword(final String newPass, final String email){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.urlEditPass, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    int sukses = object.getInt("code");

                    if(sukses == 200){
                        Intent login = new Intent(getApplicationContext(), Login.class);
                        startActivity(login);
                    }else{
                        Toast.makeText(getApplicationContext(), "Mohon Periksa kembali data anda", Toast.LENGTH_LONG).show();
                    }
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
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("newPass", newPass);
                return params;
            }
        };
        queue.getCache().clear();
        queue.add(stringRequest);

    }
}