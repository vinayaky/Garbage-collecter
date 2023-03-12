package com.ewaste.garbagecollacter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class garbage_collected extends Fragment {
    List<DataClass> DataList;
    DatabaseReference databaseReference;
    ValueEventListener EventListener;
    MainAdapter Adapter;
    RecyclerView RecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_garbage_collected, container, false);
        //mainRecyclerVie
        RecyclerView = view.findViewById(R.id.collected);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        RecyclerView.setLayoutManager(layoutManager);
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        android.app.AlertDialog callDialog = builder.create();
        callDialog.show();

        DataList = new ArrayList<>();
        Adapter = new MainAdapter(getContext(), DataList);
        RecyclerView.setAdapter(Adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("Collected");
        callDialog.show();
        EventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataList.clear();
                for (DataSnapshot UidSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot uidSnap:UidSnapshot.getChildren()){
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
       return view;
    }
}