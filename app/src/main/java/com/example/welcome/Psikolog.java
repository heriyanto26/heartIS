package com.example.welcome;

import static com.example.welcome.Login.userID;

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

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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



public class Psikolog extends AppCompatActivity {

    private ArrayList<HashMap<String, String>> listPsikolog;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_psikolog);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        ActionBar bar;
        bar = getSupportActionBar();
        ColorDrawable cd = new ColorDrawable(Color.parseColor("#6487DF"));
        assert bar != null;
        bar.setTitle("");
        bar.setBackgroundDrawable(cd);
        bar.setDisplayHomeAsUpEnabled(true);

        list = findViewById(R.id.psikologListView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> clickedItem = listPsikolog.get(position);
                String psikologId = clickedItem.get("PsikologID");
                Intent home = new Intent(getApplicationContext(),Schedule.class);
                home.putExtra("psikologid",psikologId);
                        startActivity(home);
//                kirim_data(userID, psikologId);
            }
        });
    }

//    private void kirim_data(final String userID, final String psikolog){
//        RequestQueue queue = Volley.newRequestQueue(Psikolog.this);
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.urlInsertRequest , new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try{
//                    Log.e("Error", "masuktry");
//                    JSONObject object = new JSONObject(response);
//                    int sukses = object.getInt("code");
//                    if(sukses == 200){
//                        Intent home = new Intent(getApplicationContext(),Schedule.class);
//                        startActivity(home);
//                    }
//                }catch (JSONException e){
//                    Log.e("error", e.toString());
//                    Log.d("Response", response);
//                    Log.e("error","masuk catch");
//                }
//            }
//        }, new Response.ErrorListener() {
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                openCheckConnection();
//            }
//        }){
//            @Nullable
//            @Override
//            protected Map<String, String> getParams(){
//                Map<String, String> params = new HashMap<>();
//                params.put("id", userID);
//                params.put("psikolog", psikolog);
//                return params;
//            }
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("Content-Type", "application/json;charset=utf-8");
//                return params;
//            }
//        };
//
//        queue.getCache().clear();
//        queue.add(stringRequest);
//    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openCheckConnection(){
        final Dialog Connect = new Dialog(Psikolog.this);

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
        final String[] from ={"PsikologID","Name", "Phone", "Gender", "Pengalaman", "Bidang"};
        final int [] to ={R.id.txtId,R.id.txtName, R.id.txtPhonePsikolog, R.id.txtGender, R.id.txtPengalaman, R.id.txtBidang};

        listPsikolog = new ArrayList<>();
        list = Psikolog.this.findViewById(R.id.psikologListView);

        RequestQueue queue = Volley.newRequestQueue(Psikolog.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.urlGetPsikolog, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("data");
                    int sukses = object.getInt("code");

                    if(sukses == 200){
                        for(int i = 0; i < array.length(); i++){

                            JSONObject list = array.getJSONObject(i);
                            String psikolog = String.valueOf(list.getInt("psikologID"));
                            String name = list.getString("name");
                            String phone = list.getString("phone");
                            String gender = list.getString("gender");
                            String pengalaman = list.getString("pengalaman");
                            String bidang = list.getString("bidang");

                            HashMap<String, String> map = new HashMap<>();

                            map.put("PsikologID", psikolog);
                            map.put("Name", name);
                            map.put("Phone", phone);
                            map.put("Gender", gender);
                            map.put("Pengalaman", pengalaman);
                            map.put("Bidang", bidang);
                            listPsikolog.add(map);
                        }
                        try{
                            ListAdapter adapter = new SimpleAdapter(getApplicationContext(), listPsikolog, R.layout.item_view, from, to);
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
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("catagory", "muda");
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
