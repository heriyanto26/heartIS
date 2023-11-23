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
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import static com.example.welcome.Login.userID;
import static com.example.welcome.Login.Email;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePassword extends AppCompatActivity {

    TextView txtEmail;
    EditText edtNewPass, edtConfirmPass;
    Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        txtEmail = findViewById(R.id.txtEmail);
        edtNewPass = findViewById(R.id.edtNewPass);
        edtConfirmPass = findViewById(R.id.edtConfirmPass);
        btnConfirm = findViewById(R.id.btnConfirm);

        txtEmail.setText(Email);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtNewPass.getText().toString().equals(edtConfirmPass.getText().toString())){
                    changePass(userID, edtConfirmPass.getText().toString());
                    Intent back = new Intent(getApplicationContext(), Home.class);
                    startActivity(back);
                }
            }
        });
    }

    public void changePass(final String userID, final String password){
        RequestQueue queue = Volley.newRequestQueue(ChangePassword.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.urlEditPass, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object = new JSONObject(response);
                    int sukses = object.getInt("code");

                    if(sukses == 200){
                        openPopUpEditSuccess();
                    }else{
                        openPopUpEditFailed();
                    }

                } catch (JSONException e) {
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
                params.put("id", userID);
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
        final Dialog Connect = new Dialog(ChangePassword.this);

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openPopUpEditSuccess(){
        final Dialog Failed = new Dialog(ChangePassword.this);

        LayoutInflater layoutInflater = (LayoutInflater)getLayoutInflater();
        View successDialog = layoutInflater.inflate(R.layout.pop_up_edit_sukses, null);

        Button ok = successDialog.findViewById(R.id.btnOk);
        Failed.setContentView(successDialog);
        Failed.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_popup));
        Failed.getWindow().getAttributes().windowAnimations = R.style.animation;
        Failed.setCancelable(true);
        Failed.show();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent(getApplicationContext(),Home.class);
                startActivity(edit);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openPopUpEditFailed(){
        final Dialog Failed = new Dialog(ChangePassword.this);

        LayoutInflater layoutInflater = (LayoutInflater)getLayoutInflater();
        View successDialog = layoutInflater.inflate(R.layout.pop_up_edit_gagal, null);

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
}