package com.example.welcome;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AdminSchedulePage extends AppCompatActivity {

    private ArrayList<HashMap<String, String>> listKonseling;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_schedule_page);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        ActionBar bar;
        bar = getSupportActionBar();
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#6487DF"));
        assert bar != null;
        bar.setTitle("");
        bar.setBackgroundDrawable(cd);
        bar.setDisplayHomeAsUpEnabled(true);

        list = findViewById(R.id.AdminScheduleListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> clickedItem = listKonseling.get(position);
//                String psikologId = clickedItem.get("PsikologID");
//                Intent home = new Intent(getApplicationContext(),Schedule.class);
//                home.putExtra("psikologid",psikologId);
//                startActivity(home);
//                kirim_data(userID, psikologId);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openCheckConnection(){
        final Dialog Connect = new Dialog(AdminSchedulePage.this);

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

    @Override
    protected void onStart() {
        super.onStart();
        final String[] from ={"fullname","name", "place", "DATE_TEST"};
        final int [] to ={R.id.txtNamaMahasiswa,R.id.txtNamaPsikolog, R.id.TxtMedia,  R.id.TxtDate};

        listKonseling = new ArrayList<>();
        list = AdminSchedulePage.this.findViewById(R.id.AdminScheduleListView);

        RequestQueue queue = Volley.newRequestQueue(AdminSchedulePage.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.urlGetSchedule, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("data");
                    int sukses = object.getInt("code");

                    if(sukses == 200){
                        for(int i = 0; i < array.length(); i++){

                            JSONObject list = array.getJSONObject(i);
                            String fullname = list.getString("fullname");
                            String psikolog = list.getString("name");
                            String place = list.getString("place");
                            String date = list.getString("DATE_TEST");


                            HashMap<String, String> map = new HashMap<>();

                            map.put("fullname", fullname);
                            map.put("name", psikolog);
                            map.put("place", place);
                            map.put("DATE_TEST", date);

                            listKonseling.add(map);
                        }
                        try{
                            ListAdapter adapter = new SimpleAdapter(getApplicationContext(), listKonseling, R.layout.list_item, from, to);
                            list.setAdapter(adapter);
                        }catch (Exception ex){
                            Log.e("error", ex.getMessage());
                        }
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
        };
        queue.getCache().clear();
        queue.add(stringRequest);

    }


}
