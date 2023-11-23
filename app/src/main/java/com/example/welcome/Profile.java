package com.example.welcome;

import static com.example.welcome.Login.userID;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

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


public class Profile extends Fragment {

    TextView txtFullname, txtEmail, txtTelp, txtGender;
    Button btnChange, btnChangePass;

    public static String fullname;
    public static String email;
    public static String phone;
    public static String gender;

    private JSONObject data;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, null);

        getProfile(userID);

        txtFullname = v.findViewById(R.id.txtFullname);
        txtEmail = v.findViewById(R.id.txtEmail);
        txtTelp = v.findViewById(R.id.txtTelp);
        txtGender = v.findViewById(R.id.txtGender);
        btnChange = v.findViewById(R.id.btnEditProfile);
        btnChangePass = v.findViewById(R.id.btnEditPass);

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent change = new Intent(getContext(), EditProfile.class);
                startActivity(change);
            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pass = new Intent(getContext(), ChangePassword.class);
                startActivity(pass);
            }
        });
        return v;
    }



    private void getProfile(final String userID){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.urlGetProfile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object = new JSONObject(response);
                    int sukses = object.getInt("code");

                    if (sukses == 200){
                        data = object.getJSONObject("data");
                        String FullName = data.getString("fullname");
                        String Email = data.getString("email");
                        String Phone = data.getString("phone");
                        String Gender = data.getString("gender");

                        fullname = FullName;
                        email = Email;
                        phone = Phone;
                        gender = Gender;

                        txtFullname.setText(FullName);
                        txtEmail.setText(Email);
                        txtTelp.setText(Phone);
                        txtGender.setText(Gender);
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
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", userID);
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