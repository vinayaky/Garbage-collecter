package com.ewaste.garbagecollacter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class profileFragment extends Fragment {
    List<DataClass> DataList;
    DatabaseReference databaseReference;
    ValueEventListener EventListener;
    profileAdapter Adapter;
    FloatingActionButton uploadFab;

    String key="";
    RecyclerView recyclerView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);



        return view;
    }

    @Override


    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uploadFab = view.findViewById(R.id.fab);
        uploadFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), uploadActivity.class);
                startActivity(intent);

            }
        });
        //mainRecyclerView
        recyclerView = view.findViewById(R.id.Profile);

           LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
           layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
           recyclerView.setLayoutManager(layoutManager);
           android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getContext());
           builder.setCancelable(false);
           builder.setView(R.layout.progress_layout);
           android.app.AlertDialog callDialog = builder.create();
           callDialog.show();

           DataList = new ArrayList<>();
           Adapter = new profileAdapter(getContext(), DataList);
           recyclerView.setAdapter(Adapter);

           databaseReference = FirebaseDatabase.getInstance().getReference("Garbage").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
           callDialog.show();
           EventListener = databaseReference.addValueEventListener(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot snapshot) {
                   DataList.clear();
                   for (DataSnapshot UidSnapshot : snapshot.getChildren()) {
                       DataClass dataClass = UidSnapshot.getValue(DataClass.class);
                       dataClass.setKey(UidSnapshot.getKey());
                       DataList.add(dataClass);

                   }
                   Adapter.notifyDataSetChanged();
                   callDialog.dismiss();
               }

               @Override
               public void onCancelled(@NonNull DatabaseError error) {
                   callDialog.dismiss();
               }
           });


    }

}