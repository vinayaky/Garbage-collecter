package com.ewaste.garbagecollacter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class GarbageList extends Fragment {

    List<DataClass> DataList;
    DatabaseReference databaseReference;
    ValueEventListener EventListener;
    MainAdapter Adapter;
    RecyclerView HomeRecyclerView;


    SearchView search;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_garbage_list, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HomeRecyclerView = view.findViewById(R.id.HomeRecycler);
        search = view.findViewById(R.id.search);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        HomeRecyclerView.setLayoutManager(layoutManager);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        android.app.AlertDialog callDialog = builder.create();
        callDialog.show();

        DataList = new ArrayList<>();
        Adapter = new MainAdapter(getContext(), DataList);
        HomeRecyclerView.setAdapter(Adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Garbage");
        callDialog.show();
        EventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataList.clear();
                for (DataSnapshot UidSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot uidSnap : UidSnapshot.getChildren()) {
                        DataClass dataClass = uidSnap.getValue(DataClass.class);
                        DataList.add(dataClass);
                    }

                }
                Adapter.notifyDataSetChanged();
                callDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callDialog.dismiss();
            }
        });


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                hiringSearchList(newText);
                return true;
            }
        });
    }

    public void hiringSearchList(String text){
        ArrayList<DataClass> mainSearchList = new ArrayList<>();
        for(DataClass dataClass:DataList) {
            try {
                if (dataClass.getDataClint().toLowerCase().contains(text.toLowerCase()) || dataClass.getDataAdd().toLowerCase().contains(text.toLowerCase()) || dataClass.getDataName().toLowerCase().contains(text.toLowerCase())) {
                    mainSearchList.add(dataClass);
                }
            } catch (NullPointerException e) {
                Toast.makeText(getContext(), "no garbage", Toast.LENGTH_SHORT).show();
            }


        }Adapter.searchDataList(mainSearchList);
    }
}