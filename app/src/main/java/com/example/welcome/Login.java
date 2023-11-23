package com.example.welcome;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

public class Login extends AppCompatActivity {

    public static String userID;
    public static String Email;
    public static String Password;

    Button signUp, login;
    TextView forgotPass;
    EditText edtEmail, edtPassword;
    String email, password;

    private JSONObject data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        signUp = findViewById(R.id.txtSignUp);
        login = findViewById(R.id.btnLogin);
        edtEmail = findViewById(R.id.loginEmail);
        edtPassword = findViewById(R.id.loginPassword);
        forgotPass = findViewById(R.id.TxtForgotPassword);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(Login.this, SignUp.class);
                startActivity(signUp);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtEmail.getText().toString();
                password = edtPassword.getText().toString();
                Password = password;
               SignIn(email,password);
            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgot = new Intent(getApplicationContext(), ForgotPassword.class);
                startActivity(forgot);
            }
        });



    }

    public void SignIn (final String email, final String password){
        RequestQueue queue = Volley.newRequestQueue(Login.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.urlLogin, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("error", "masuk try");
                    JSONObject object = new JSONObject(response);
                    int sukses = object.getInt("code");

                    if (sukses == 200) {
                        data = object.getJSONObject("data");
                        String role = data.getString("role");
                        Email = data.getString("email");
                        userID = data.getString("id");


                        if(role.equals("user")){
                            openPopUpLoginSuccess();
                        }
                        if(role.equals("admin")){
                            Intent admin = new Intent(getApplicationContext(), home_admin.class);
                            startActivity(admin);

                       }
                    }else {
                        openPopUpLoginFailed();
                    }

                } catch (JSONException e) {
                    Log.e("error", e.toString());
                    Log.e("error","masuk catch");
                }
            }
        }, new Response.ErrorListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
                // dialog check connection
                openCheckConnection();
            }

        }){
            @Override
            protected Map<String, String> getParams(){
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
    private void openPopUpLoginSuccess(){
        final Dialog Success = new Dialog(Login.this);

        LayoutInflater layoutInflater = (LayoutInflater)getLayoutInflater();
        View successDialog = layoutInflater.inflate(R.layout.pop_up_login_sukses, null);

        Button ok = successDialog.findViewById(R.id.btnOk);
        Success.setContentView(successDialog);
        Success.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_popup));
        Success.getWindow().getAttributes().windowAnimations = R.style.animation;
        Success.setCancelable(false);
        Success.show();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Signin = new Intent(getApplicationContext(),Home.class);
                startActivity(Signin);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openPopUpLoginFailed(){
        final Dialog Failed = new Dialog(Login.this);

        LayoutInflater layoutInflater = (LayoutInflater)getLayoutInflater();
        View successDialog = layoutInflater.inflate(R.layout.pop_up_login_gagal, null);

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
        final Dialog Connect = new Dialog(Login.this);

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