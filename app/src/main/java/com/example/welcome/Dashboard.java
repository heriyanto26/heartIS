package com.example.welcome;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import static com.example.welcome.MainDashboard.Bulan;
import static com.example.welcome.MainDashboard.Tahun;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Dashboard extends AppCompatActivity {

    TextView txtBulanTahun, txtJumlahAkun, txtJumlahPsikolog, txtJumlahTest, txtJumlahKonsul;
    int jumlahAkun, jumlahPsikolog, jumlahTest, jumlahKonsul;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();
        txtBulanTahun = findViewById(R.id.txtBulanTahum);
        txtJumlahAkun = findViewById(R.id.txtJumlahAkun);
        txtJumlahPsikolog = findViewById(R.id.txtJumlahPsikolog);
        txtJumlahTest = findViewById(R.id.txtJumlahTest);
        txtJumlahKonsul = findViewById(R.id.txtJumlahKonsul);
        btnBack = findViewById(R.id.btnBack);
        txtBulanTahun.setText("Report pada Bulan "+Bulan+" dan Tahun "+Tahun);

        getDashBoard(Bulan,Tahun);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(getApplicationContext(), MainDashboard.class);
                startActivity(back);
            }
        });

    }

    private void getDashBoard(final String Bulan, final String Tahun){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.urlGetDashboard, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    int sukses = object.getInt("code");

                    if(sukses == 200){
                        JSONArray array = object.getJSONArray("data");

                        for(int i = 0; i < array.length(); i++){
                            JSONObject item = array.getJSONObject(i);

                            String tableName = item.getString("tabel");

                            switch (tableName) {
                                case "tabelUser":
                                    jumlahAkun = item.getInt("jumlahUser");
                                    txtJumlahAkun.setText(String.valueOf(jumlahAkun));
                                    break;
                                case "tabelPsikolog":
                                    jumlahPsikolog = item.getInt("jumlahPsikolog");
                                    txtJumlahPsikolog.setText(String.valueOf(jumlahPsikolog));
                                    break;
                                case "tabelTest":
                                    jumlahTest = item.getInt("jumlahTest");
                                    txtJumlahTest.setText(String.valueOf(jumlahTest));
                                    break;
                                case "tabelKonsul":
                                    jumlahKonsul = item.getInt("jumlahKonsul");
                                    txtJumlahKonsul.setText(String.valueOf(jumlahKonsul));
                                    break;
                            }
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

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("bulan", Bulan);
                params.put("tahun", Tahun);

                return params;
            }
        };
        queue.getCache().clear();
        queue.add(stringRequest);
    }
}