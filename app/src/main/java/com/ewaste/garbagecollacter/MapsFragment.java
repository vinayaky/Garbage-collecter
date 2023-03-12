package com.ewaste.garbagecollacter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class MapsFragment extends Fragment {
    private GoogleMap map;
    String value;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_maps, container, false);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
                  Bundle bun=this.getArguments();
        if (bun!=null){
            value =bun.getString("key1");
        }


        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

    }
    private OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            map=googleMap;
            LatLng Bengaluru = new LatLng(13.034506, 77.619497);
            map.moveCamera(CameraUpdateFactory.newLatLng(Bengaluru));
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(Bengaluru,12f));
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            if (Objects.equals(value, "value")){
                map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        Geocoder geocoder= new Geocoder(getContext());
                        try {
                            map.addMarker(new MarkerOptions().position(latLng).title("clicked here"));
                            ArrayList<Address> address =(ArrayList<Address>) geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
                            Intent intent = new Intent(getContext(),uploadActivity.class);
                            intent.putExtra("add",address.get(0).getAddressLine(0));

                            startActivity(intent);
                            onStop();
                        } catch (NullPointerException | IOException e) {
                            Toast.makeText(getContext(), "no address", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
            FirebaseDatabase.getInstance().getReference("Garbage").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot UidSnapshot : snapshot.getChildren()) {
                        for (DataSnapshot uidSnap:UidSnapshot.getChildren()){
                            DataClass dataClass = uidSnap.getValue(DataClass.class);
                           String address= dataClass.getDataAdd();
                           String nam=dataClass.getDataName();
                            Geocoder geocoder= new Geocoder(getContext());

                            try {
                                ArrayList<Address>    locate = (ArrayList<Address>) geocoder.getFromLocationName(address,1);
                                LatLng latLng=new LatLng(locate.get(0).getLatitude(),locate.get(0).getLongitude());
                                map.addMarker(new MarkerOptions().position(latLng).title(nam));
                            } catch (IOException e) {
                                         continue;

                            }



                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    };


}