package com.example.welcome;

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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.example.welcome.Login.Email;
import static com.example.welcome.Login.Password;

public class Home extends AppCompatActivity {
    private JSONObject data;

    HomePage home = new HomePage();
    Profile profile = new Profile();
    Setting setting = new Setting();

    BottomNavigationView navBar;

    @Override
    protected void onStart() {
        super.onStart();
        checkUser(Email, Password);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_home);

        navBar = findViewById(R.id.NavBar);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, home).commit();

        navBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, home).commit();
                    return true;

                case R.id.profile:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, profile).commit();
                    return true;

                case R.id.setting:
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, setting).commit();
                    return true;
            }
            return false;
        });
    }
    public void checkUser(final String Email, final String password){
        RequestQueue queue = Volley.newRequestQueue(Home.this);
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
                        String phone = data.getString("phone");
                        String gender = data.getString("gender");
                        if(phone.equals("") && gender.equals("")){
                            Toast.makeText(getApplicationContext(),"Please Input your Phone Number and Gender", Toast.LENGTH_LONG).show();
                            Intent profile = new Intent(getApplicationContext(), EditProfile.class);
                            startActivity(profile);
                        }

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
                params.put("email", Email);
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
    private void openCheckConnection(){
        final Dialog Connect = new Dialog(Home.this);

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