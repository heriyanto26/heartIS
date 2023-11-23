package com.example.welcome;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import static com.example.welcome.Login.userID;

public class Schedule extends AppCompatActivity implements View.OnClickListener {

    Button btnDatePicker,  btnConfirm;
    EditText txtDate, txtTime;

    String psikologid, Date;



        @Override
        protected void onCreate(Bundle savedInstanceState) {

            Bundle bundle = getIntent().getExtras();
            psikologid = bundle.getString("psikologid");

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_schedule);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            Objects.requireNonNull(getSupportActionBar()).hide();

            btnDatePicker=(Button)findViewById(R.id.btn_date);
            //btnTimePicker=(Button)findViewById(R.id.btn_time);
            txtDate=(EditText)findViewById(R.id.in_date);
            txtTime=(EditText)findViewById(R.id.in_time);
            btnConfirm = findViewById(R.id.btn_ok);

            btnDatePicker.setOnClickListener(this);
            //btnTimePicker.setOnClickListener(this);

            Spinner spinnerKonseling=findViewById(R.id.spinnerKonseling);

            ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.konseling, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

            spinnerKonseling.setAdapter(adapter);
            btnConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String place = spinnerKonseling.getSelectedItem().toString();
                    String dateTime = txtDate.getText().toString();
                    Toast.makeText(getApplicationContext(), place+dateTime, Toast.LENGTH_LONG).show();

                    insertSchedule(psikologid, userID, dateTime, place);
                }
            });

        }

        public void insertSchedule(final String psikologid,final String userID, final String dateTime, final String place){
            RequestQueue queue = Volley.newRequestQueue(Schedule.this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.urlInsertConsultation, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try{
                        Log.e("Error", "masuktry");
                        JSONObject object = new JSONObject(response);
                        int sukses = object.getInt("code");
                        if(sukses == 200){
                            Intent home = new Intent(getApplicationContext(),Home.class);
                            startActivity(home);
                        }
                    }catch (JSONException e){
                        Log.e("error", e.toString());
                        Log.d("Response", response);
                        Log.e("error","masuk catch");
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
                protected Map<String, String> getParams(){
                    Map<String, String> params = new HashMap<>();
                    params.put("psikolog",psikologid);
                    params.put("id", userID);
                    params.put("test_date", dateTime);
                    params.put("place",place);
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
        final Dialog Connect = new Dialog(Schedule.this);

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
    public void onClick(View v) {

        if (v == btnDatePicker) {
            showDatePicker(psikologid);
        }

    }

    private void showDatePicker(final String psikologid){
            Calendar now = Calendar.getInstance();

            List<Calendar> disableDates = new ArrayList<>();

            RequestQueue queue = Volley.newRequestQueue(this);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.urlGetPsikologSchedule,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject object = new JSONObject(response);
                                JSONArray jsonArray = object.getJSONArray("data");
                                int sukses = object.getInt("code");

                                if(sukses == 200) {
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject list = jsonArray.getJSONObject(i);
                                        String tanggal = list.getString("test_date");
                                        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                        Date date = format.parse(tanggal);

                                        Calendar calendar = Calendar.getInstance();
                                        assert date != null;
                                        calendar.setTime(date);
                                        disableDates.add(calendar);

                                    }
                                }
                                CalendarConstraints.Builder constraintsBuilder = DatePickerUtils.disableSpecificDates(disableDates);

                                MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
                                builder.setCalendarConstraints(constraintsBuilder.build());

                                MaterialDatePicker<Long> datePicker = builder.build();
                                datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                                    @Override
                                    public void onPositiveButtonClick(Long selectedDate) {
                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTimeInMillis(selectedDate);

                                        // Process the selected date as needed
                                        int year = calendar.get(Calendar.YEAR);
                                        int month = calendar.get(Calendar.MONTH);
                                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                                        Date = day + " - "+(month + 1) + " - " +year;
                                        txtDate.setText(Date);
                                    }
                                });

                                datePicker.show(getSupportFragmentManager(), datePicker.toString());




                            } catch (JSONException | ParseException e) {
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

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("id",psikologid);

                    return params;
                }
            };
            queue.getCache().clear();
            queue.add(stringRequest);
    }

}