package com.example.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class AdminMedicalNotesFragment extends Fragment {

    private ListView adminMedicalNotesListView;
    private List<String> itemList;
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_medical_notes, container, false);

        adminMedicalNotesListView = view.findViewById(R.id.adminMedicalNotesListView);

        itemList = new ArrayList<>();
        itemList.add("Item 1");
        itemList.add("Item 2");
        itemList.add("Item 3");

        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, itemList);

        adminMedicalNotesListView.setAdapter(adapter);

        adminMedicalNotesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = itemList.get(position);
                // Perform actions based on the selected item
                Toast.makeText(requireContext(), "Clicked: " + selectedItem, Toast.LENGTH_SHORT).show();

                // Example: Start a new activity based on the selected item
                Intent intent = new Intent(requireContext(), AdminMedicalNotesDetail.class);
                intent.putExtra("selectedItem", selectedItem);
                startActivity(intent);
            }
        });

        return view;
    }
}
