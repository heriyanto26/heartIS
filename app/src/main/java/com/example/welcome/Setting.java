package com.example.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Setting extends Fragment {

    TextView txtDeleteAccount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Button logout;

        View v = inflater.inflate(R.layout.fragment_setting, null);
        logout = v.findViewById(R.id.btnLogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logout = new Intent(getContext(), MainActivity.class);
                startActivity(logout);
            }
        });

        return v;
    }
}