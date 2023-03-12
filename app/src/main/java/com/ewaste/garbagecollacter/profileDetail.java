package com.ewaste.garbagecollacter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.util.Calendar;

public class profileDetail extends AppCompatActivity {
    TextView name , work, add ,number;
    ImageView image;
    String imageUrl="";
    String key ="";
    String garbage;
    Button garbageCollected;
    FloatingActionButton deleteFab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);
        image = findViewById(R.id.profileDetailImage);
        name = findViewById(R.id.profileDetailName);
        work = findViewById(R.id.profileDetailWork);
        add =  findViewById(R.id.profileDetailAdd);
        number=findViewById(R.id.profileDetailNumber);
        deleteFab=findViewById(R.id.DeleteFab);
        garbageCollected=findViewById(R.id.Gc);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            name.setText(bundle.getString("name"));
            work.setText(bundle.getString("work"));
            add.setText(bundle.getString("add"));
            number.setText(bundle.getString("number"));
            key=bundle.getString("key");
            imageUrl=bundle.getString("image");
            garbage= bundle.getString("garbage");
            Glide.with(this).load(bundle.getString("image")).into(image);

        }
        deleteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Garbage");
                FirebaseStorage storage=FirebaseStorage.getInstance();
                StorageReference storageReference=storage.getReferenceFromUrl(imageUrl);
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        reference.child(FirebaseAuth.getInstance().getUid()).child(key).removeValue();
                        Toast.makeText(profileDetail.this, "Deleted", Toast.LENGTH_SHORT).show();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.container,new profileFragment());
                        fragmentTransaction.commit();
                        finish();
                    }
                });
            }
        });
        garbageCollected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String nam =name.getText().toString();
                    String weight = work.getText().toString();
                    String num= number.getText().toString();
                    String address= add.getText().toString();

                    DataClass dataClass = new DataClass(nam,imageUrl,weight,address,num,garbage);
                final DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Garbage");

                    String cDate= DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                    FirebaseDatabase.getInstance().getReference("Collected").child(FirebaseAuth.getInstance().getUid()).child(cDate)

                            .setValue(dataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    reference.child(FirebaseAuth.getInstance().getUid()).child(key).removeValue();
                                    Toast.makeText(profileDetail.this, "collected", Toast.LENGTH_SHORT).show();
                                    FragmentManager fragmentManager = getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.container,new profileFragment());
                                    fragmentTransaction.commit();
                                    finish();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }


        });
    }
}
