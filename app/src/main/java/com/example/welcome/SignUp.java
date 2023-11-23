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

public class SignUp extends AppCompatActivity {

    Button btnSignUp;
    EditText edtEmail, edtPass, edtRePass;
    String email, pass, rePass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        btnSignUp = findViewById(R.id.btnSignUp);
        edtEmail = findViewById(R.id.txtEmail);
        edtPass = findViewById(R.id.txtPass);
        edtRePass = findViewById(R.id.txtRePass);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtEmail.getText().toString();
                pass = edtPass.getText().toString();
                rePass = edtRePass.getText().toString();

                if (!email.equals("") && !pass.equals("") && !rePass.equals("")){
                    if (pass.equals(rePass)){
                        signUp(email, pass);
                    }else {
                        Toast.makeText(getApplicationContext(), "The Password is dont match", Toast.LENGTH_LONG).show();
                    }
                }else {
                    Toast.makeText(getApplicationContext(), "Please Check Your Input", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
    private void signUp(final String email, final String password){
        RequestQueue queue = Volley.newRequestQueue(SignUp.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.urlSignUp, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object = new JSONObject(response);
                    int sukses = object.getInt("code");
                    if(sukses == 200){
                        openPopUpSignUpSuccess();
                    }else if(sukses == 500){
                        openDialogPopUpAccountExist();

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

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
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
    private void openDialogPopUpAccountExist(){
        final Dialog Acc = new Dialog(SignUp.this);

        LayoutInflater layoutInflater = (LayoutInflater)getLayoutInflater();
        View accExist = layoutInflater.inflate(R.layout.pop_up_akun_sudah_ada, null);

        Button ok = accExist.findViewById(R.id.btnOk);
        Acc.setContentView(accExist);
        Acc.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_popup));
        Acc.getWindow().getAttributes().windowAnimations = R.style.animation;
        Acc.setCancelable(false);
        Acc.show();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Acc.dismiss();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openPopUpSignUpSuccess(){
        final Dialog Success = new Dialog(SignUp.this);

        LayoutInflater layoutInflater = (LayoutInflater)getLayoutInflater();
        View successDialog = layoutInflater.inflate(R.layout.pop_up_regis_sukses, null);

        Button ok = successDialog.findViewById(R.id.btnOk);
        Success.setContentView(successDialog);
        Success.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_popup));
        Success.getWindow().getAttributes().windowAnimations = R.style.animation;
        Success.setCancelable(false);
        Success.show();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(SignUp.this, Ready.class);
                startActivity(signup);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openPopUpLoginFailed(){
        final Dialog Failed = new Dialog(SignUp.this);

        LayoutInflater layoutInflater = (LayoutInflater)getLayoutInflater();
        View successDialog = layoutInflater.inflate(R.layout.pop_up_regis_gagal, null);

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
        final Dialog Connect = new Dialog(SignUp.this);

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