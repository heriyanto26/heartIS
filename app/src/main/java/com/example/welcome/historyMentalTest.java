package com.example.welcome;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.lights.LightsManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import static com.example.welcome.Login.userID;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class historyMentalTest extends AppCompatActivity {

    private ArrayList<HashMap<String, String>> listHistory;
    ListView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_mental_test);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        ActionBar bar;
        bar = getSupportActionBar();
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#6487DF"));
        assert bar != null;
        bar.setTitle("");
        bar.setBackgroundDrawable(cd);
        bar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final String[] from = {"Date", "Result"};
        final int[] to = {R.id.txtDate, R.id.txtHistory};

        listHistory = new ArrayList<>();
        list = historyMentalTest.this.findViewById(R.id.isiTabelHistory);

        RequestQueue queue = Volley.newRequestQueue(historyMentalTest.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.urlGetListHistory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("data");
                    int sukses = object.getInt("code");

                    if(sukses == 200){
                        for (int i = 0; i < array.length(); i++){
                            JSONObject list = array.getJSONObject(i);
                            String date = list.getString("test_date");
                            String result = list.getString("test_result");


                            HashMap<String, String> map = new HashMap<>();
                            map.put("Date", date);
                            map.put("Result", result);

                            listHistory.add(map);
                        }
                        try{
                            ListAdapter adapter = new SimpleAdapter(getApplicationContext(), listHistory, R.layout.list_history, from, to);
                            list.setAdapter(adapter);

                        }catch (Exception ex){
                            Log.e("error", ex.getMessage());
                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent back = new Intent(getApplicationContext(), Home.class);
        startActivity(back);
        return true;
    }
}