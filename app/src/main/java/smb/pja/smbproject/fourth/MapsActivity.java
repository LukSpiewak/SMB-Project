package smb.pja.smbproject.fourth;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import smb.pja.smbproject.R;
import smb.pja.smbproject.fourth.list.AddShopActivity;
import smb.pja.smbproject.fourth.list.ShopListActivity;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference database;

    private GeofencingClient geofencingClient;
    private List<Geofence> geofenceList = new ArrayList<>();
    private PendingIntent geofencePendingIntent;


    List<Shop> shopList = new ArrayList<>();
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        createNotificationChannel();

        database = FirebaseDatabase.getInstance().getReference("shop");
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                MapsActivity.this.location = location;
            }
        });

        geofencingClient = LocationServices.getGeofencingClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    private PendingIntent getGeofencePendingIntent() {
        if (geofencePendingIntent != null) {
            return geofencePendingIntent;
        }
        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        geofencePendingIntent = PendingIntent.getService(this, 0, intent, PendingIntent.
                FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }

    private GeofencingRequest getGeofencingRequest() {
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofenceList);
        return builder.build();
    }

    private void fillGeofencingList() {
        shopList.forEach(shop -> {
            Geofence geofence = new Geofence.Builder()
                    .setRequestId(shop.getId().toString())
                    .setCircularRegion(shop.getLat(), shop.getLon(), shop.getRadius())
                    .setExpirationDuration(3600000L)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_EXIT)
                    .build();
            geofenceList.add(geofence);
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shopList.clear();

                dataSnapshot.getChildren().forEach(shop_ -> {
                    Shop shop = shop_.getValue(Shop.class);

                    shopList.add(shop);
                });

                fillMapMarkers();
                fillGeofencingList();

                if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                if (location != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13));
                }
                mMap.setMyLocationEnabled(true);
                geofencingClient.addGeofences(getGeofencingRequest(), getGeofencePendingIntent());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void fillMapMarkers() {
        shopList.forEach(shop -> {
            LatLng position = new LatLng(shop.getLat(), shop.getLon());
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(position)
                    .title(shop.getName());

            CircleOptions circleOptions = new CircleOptions()
                    .center(position)
                    .radius(Double.valueOf(shop.getRadius()))
                    .fillColor(Color.GRAY);
            mMap.addMarker(markerOptions);
            mMap.addCircle(circleOptions);
        });
    }

    public void redirectToShopList(View view) {
        Intent intent = new Intent(this, ShopListActivity.class);
        startActivity(intent);
    }

    public void redirectToAddShop(View view) {
        Intent intent = new Intent(this, AddShopActivity.class);
        startActivity(intent);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "channel";
            String description = "description";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("channel1", name, importance);
            channel.setDescription(description);

            NotificationManager nm = getSystemService(NotificationManager.class);
            nm.createNotificationChannel(channel);
        }
    }
}
