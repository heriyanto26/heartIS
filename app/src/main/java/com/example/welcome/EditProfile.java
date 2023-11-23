package com.example.welcome;

import static com.example.welcome.Login.Email;
import static com.example.welcome.Login.userID;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
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

public class EditProfile extends AppCompatActivity {

    String[] optGender = {"Laki-laki", "Perempuan"};

    AutoCompleteTextView edtGender;
    TextView txtemail;
    Button btnConfirm;
    String gender, phone;
    EditText txtPhone;

    ArrayAdapter<String> adapterGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();


        edtGender = findViewById(R.id.edtGender);
        txtemail = findViewById(R.id.txtemail);
        txtPhone = findViewById(R.id.edtPhone);
        btnConfirm = findViewById(R.id.btnConfirm);

        txtemail.setText(Email);

        adapterGender = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, optGender);

        edtGender.setAdapter(adapterGender);
        edtGender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String gender = parent.getItemAtPosition(position).toString();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gender = edtGender.getText().toString();
                phone = txtPhone.getText().toString();
                updateUser(userID,phone, gender);

            }
        });




    }
    private void updateUser(final String userID, final String phone, final String gender){
        RequestQueue queue = Volley.newRequestQueue(EditProfile.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.urlEditProfile, new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    int sukses = object.getInt("code");
                    if(sukses == 200){
                        openPopUpEditSuccess();
                    }else{
                        openPopUpEditFailed();
                        Log.e("error", "API ERROR");

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
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", userID);
                params.put("phone", phone);
                params.put("gender", gender);
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
    private void openPopUpEditSuccess(){
        final Dialog Failed = new Dialog(EditProfile.this);

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
        final Dialog Failed = new Dialog(EditProfile.this);

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openCheckConnection(){
        final Dialog Connect = new Dialog(EditProfile.this);

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