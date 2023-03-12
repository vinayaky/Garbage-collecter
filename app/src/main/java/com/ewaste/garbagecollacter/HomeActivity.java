package com.ewaste.garbagecollacter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
   private String keyvalue;
    boolean DoublePressToExit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.bottomNavigationView);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
             keyvalue=bundle.getString("key");

        }


        loadFragment(new MapsFragment());
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case (R.id.profile):

                        loadFragment(new AwarenessFragment());

                        break;
                    case (R.id.Home):
                        loadFragment(new HomeFragment());

                        break;
                    case (R.id.map):
                        loadFragment(new MapsFragment());

                        break; }

                return true;
            }
        });

    }
    public void onBackPressed() {

        if (DoublePressToExit) {
            finishAffinity();
        } else {
            DoublePressToExit = true;
            Toast.makeText(HomeActivity.this, "Press again back to exit", Toast.LENGTH_SHORT).show();
            Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    DoublePressToExit = false;
                }
            }, 1500);
        }
    }
    public void loadFragment(Fragment fragment){
        Bundle bundleK = new Bundle();
        bundleK.putString("key1",keyvalue);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
        fragment.setArguments(bundleK);
        fragmentTransaction.replace(R.id.detailsContent,fragment);
        fragmentTransaction.commit();
    }
}