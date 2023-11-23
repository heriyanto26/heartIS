package com.example.welcome;

import static com.example.welcome.Login.userID;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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


public class HomePage extends Fragment {

    TextView btnMentalHealth, btnPentingnyaMentalHealth, txtNama, txtMentalScore;
    ImageView btnkonsul, btnMentalTest, btnHistory,btnImageProfile;
    String FirstName, testScore;

    private JSONObject data;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container, @NonNull Bundle savedInstanceState) {
        getName(userID);


        View v = inflater.inflate(R.layout.fragment_home_page, null);

        btnImageProfile = v.findViewById(R.id.btnImgProfile);
        btnMentalHealth = v.findViewById(R.id.btnMentalHealth);
        btnPentingnyaMentalHealth = v.findViewById(R.id.PentingnyaMentalHealth);
        btnkonsul = v.findViewById(R.id.btnKonsul);
        btnMentalTest = v.findViewById(R.id.btnMentaltest);
        btnHistory = v.findViewById(R.id.btnHistory);
        txtNama = v.findViewById(R.id.txtNama);


        btnImageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btnMentalTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mentalTest = new Intent(getContext(), MentalTest.class);
                startActivity(mentalTest);
            }
        });

        btnPentingnyaMentalHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mentalHealth = new Intent(getContext(), PentingnyaKesehatanMental.class);
                startActivity(mentalHealth);

            }
        });

        btnkonsul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent konsul = new Intent(getContext(), Psikolog.class);
                startActivity(konsul);
            }
        });

        btnMentalHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mentalHeath = new Intent(getContext(),DescMentalHealth.class);
                startActivity(mentalHeath);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hasil = new Intent(getContext(), historyMentalTest.class);
                startActivity(hasil);
            }
        });



        return v;
    }



    private void getName(final String userID){
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.urlGetProfile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object = new JSONObject(response);
                    int sukses = object.getInt("code");

                    if(sukses == 200){
                        data = object.getJSONObject("data");
                        FirstName = data.getString("firstname");
                        txtNama.setText(FirstName);
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