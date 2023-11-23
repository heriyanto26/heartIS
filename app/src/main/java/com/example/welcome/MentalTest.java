package com.example.welcome;

import static com.example.welcome.Login.userID;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
public class MentalTest extends AppCompatActivity implements View.OnClickListener {

    TextView idPertanyaan;
    Button btnPilihan1, btnPilihan2, btnPilihan3, btnPilihan4;

    int score=0;
    int totalQuestion = Pertanyaan.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mental_test);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();

        idPertanyaan = findViewById(R.id.idPertanyaan);

        btnPilihan1 = findViewById(R.id.btnPilihan1);
        btnPilihan2 = findViewById(R.id.btnPilihan2);
        btnPilihan3 = findViewById(R.id.btnPilihan3);
        btnPilihan4 = findViewById(R.id.btnPilihan4);

        btnPilihan1.setOnClickListener(this);
        btnPilihan2.setOnClickListener(this);
        btnPilihan3.setOnClickListener(this);
        btnPilihan4.setOnClickListener(this);

//        loadNewQuestion(0);
        loadNewQuestion();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {

        btnPilihan1.setBackgroundColor(Color.WHITE);
        btnPilihan2.setBackgroundColor(Color.WHITE);
        btnPilihan3.setBackgroundColor(Color.WHITE);
        btnPilihan4.setBackgroundColor(Color.WHITE);

            if(btnPilihan1.isPressed()){
                score = score + 4;
                currentQuestionIndex++;
//                loadNewQuestion(score);
                loadNewQuestion();
            }
            else if (btnPilihan2.isPressed()) {
                score = score + 3;
                currentQuestionIndex++;
//                loadNewQuestion(score);
                loadNewQuestion();
            }
            else if (btnPilihan3.isPressed()) {
                score = score + 2;
                currentQuestionIndex++;
//                loadNewQuestion(score);
                loadNewQuestion();
            }
            else if(btnPilihan4.isPressed()) {
                score = score + 1;
                currentQuestionIndex++;
//                loadNewQuestion(score);
                loadNewQuestion();
            }


    }

//    void loadNewQuestion(int score){
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
void loadNewQuestion(){
        if(currentQuestionIndex == totalQuestion ){
            insertTest(userID, score);
//            insertTestResult(1, score);
            finishQuiz();
            return;
        }
        idPertanyaan.setText(Pertanyaan.question[currentQuestionIndex]);
        btnPilihan1.setText(Pertanyaan.choices[currentQuestionIndex][0]);
        btnPilihan2.setText(Pertanyaan.choices[currentQuestionIndex][1]);
        btnPilihan3.setText(Pertanyaan.choices[currentQuestionIndex][2]);
        btnPilihan4.setText(Pertanyaan.choices[currentQuestionIndex][3]);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    void finishQuiz(){
        if(score >= 26) {
            Log.e("error","masuk positif");
            openDialogHasilPositif();
        }else {
            Log.e("error","masuk Negatif");
            openDialogHasilNegatif();
        }


    }

    private void insertTest(final String userID, final int Score){
        RequestQueue queue = Volley.newRequestQueue(MentalTest.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, DbContract.urlInsertTestResult, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject object = new JSONObject(response);
                    int sukses = object.getInt("code");

                    if (sukses == 200){
                        Log.e("error", "data masuk");
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
                params.put("id",userID);
                params.put("score", String.valueOf(Score));

                return params;
            }
        };
        queue.getCache().clear();
        queue.add(stringRequest);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void openDialogHasilPositif(){
        final Dialog Success = new Dialog(MentalTest.this);

        LayoutInflater layoutInflater = (LayoutInflater)getLayoutInflater();
        View successDialog = layoutInflater.inflate(R.layout.pop_up_tes_positif, null);

        Button btnYes = successDialog.findViewById(R.id.btnYes);
        Button btnNo = successDialog.findViewById(R.id.btnNo);
        Success.setContentView(successDialog);
        Success.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_popup));
        Success.getWindow().getAttributes().windowAnimations = R.style.animation;
        Success.setCancelable(false);
        Success.show();

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yes = new Intent(getApplicationContext(),Psikolog.class);
                startActivity(yes);
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent no = new Intent(getApplicationContext(),Home.class);
                startActivity(no);
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void openDialogHasilNegatif(){
        final Dialog Success = new Dialog(MentalTest.this);

        LayoutInflater layoutInflater = (LayoutInflater)getLayoutInflater();
        View successDialog = layoutInflater.inflate(R.layout.pop_up_tes_negatif, null);

        Button btnYes = successDialog.findViewById(R.id.btnYes);
        Button btnNo = successDialog.findViewById(R.id.btnNo);
        Success.setContentView(successDialog);
        Success.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_popup));
        Success.getWindow().getAttributes().windowAnimations = R.style.animation;
        Success.setCancelable(false);
        Success.show();

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent yes = new Intent(getApplicationContext(),Psikolog.class);
                startActivity(yes);
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent no = new Intent(getApplicationContext(),Home.class);
                startActivity(no);
            }
        });

    }

}
