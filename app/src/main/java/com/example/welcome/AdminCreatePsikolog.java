package com.example.welcome;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
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

public class AdminCreatePsikolog extends AppCompatActivity {

    Button btnCreate;
    EditText edtNamaPsikolog, edtPhone, edtgender, edtPengalaman, edtbidang;
    String name, phone, gender, pengalaman, bidang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_psikolog);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        btnCreate = findViewById(R.id.btnCreate);
        edtNamaPsikolog = findViewById(R.id.edtNamaPsikolog);
        edtPhone = findViewById(R.id.edtPhone);
        edtgender = findViewById(R.id.edtgender);
        edtPengalaman = findViewById(R.id.edtPengalaman);
        edtbidang = findViewById(R.id.edtbidang);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = edtNamaPsikolog.getText().toString();
                phone = edtPhone.getText().toString();
                gender = edtgender.getText().toString();
                pengalaman = edtPengalaman.getText().toString();
                bidang = edtbidang.getText().toString();

                signUp(name, phone, gender, pengalaman, bidang);

            }
        });

    }
    private void signUp(final String name, final String phone, final String gender, final String pengalaman, final String bidang){
        RequestQueue queue = Volley.newRequestQueue(AdminCreatePsikolog.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.urlAddPsikolog, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object = new JSONObject(response);
                    int sukses = object.getInt("code");
                    if(sukses == 200){
                        openPopUpSignUpSuccess();
                    }else {
                        openPopUpLoginFailed();

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onErrorResponse(VolleyError error) {
                openCheckConnection();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("gender", gender);
                params.put("phone", phone);
                params.put("pengalaman", pengalaman);
                params.put("bidang", bidang);
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openPopUpSignUpSuccess(){
        final Dialog Success = new Dialog(AdminCreatePsikolog.this);

        LayoutInflater layoutInflater = (LayoutInflater)getLayoutInflater();
        View successDialog = layoutInflater.inflate(R.layout.pop_up_berhasil_create_psikolog, null);

        Button ok = successDialog.findViewById(R.id.btnOk);
        Success.setContentView(successDialog);
        Success.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_popup));
        Success.getWindow().getAttributes().windowAnimations = R.style.animation;
        Success.setCancelable(false);
        Success.show();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(AdminCreatePsikolog.this, home_admin.class);
                startActivity(signup);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openPopUpLoginFailed(){
        final Dialog Failed = new Dialog(AdminCreatePsikolog.this);

        LayoutInflater layoutInflater = (LayoutInflater)getLayoutInflater();
        View successDialog = layoutInflater.inflate(R.layout.pop_up_create_psikolog_gagal, null);

        Button ok = successDialog.findViewById(R.id.btnOk);
        Failed.setContentView(successDialog);
        Failed.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_popup));
        Failed.getWindow().getAttributes().windowAnimations = R.style.animation;
        Failed.setCancelable(true);
        Failed.show();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Failed.dismiss();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openCheckConnection(){
        final Dialog Connect = new Dialog(AdminCreatePsikolog.this);

        LayoutInflater layoutInflater = (LayoutInflater)getLayoutInflater();
        View successDialog = layoutInflater.inflate(R.layout.pop_up_check_connection, null);

        Button ok = successDialog.findViewById(R.id.btnOk);
        Connect.setContentView(successDialog);
        Connect.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_popup));
        Connect.getWindow().getAttributes().windowAnimations = R.style.animation;
        Connect.setCancelable(true);
        Connect.show();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connect.dismiss();
            }
        });
    }
}