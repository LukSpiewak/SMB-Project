package smb.pja.smbproject.fourth.list;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Optional;

import smb.pja.smbproject.R;
import smb.pja.smbproject.first.firebase.FirebaseDatabaseUtils;
import smb.pja.smbproject.fourth.Shop;

public class AddShopActivity extends AppCompatActivity {

    private EditText name;
    private EditText description;
    private EditText radius;

    private Button removeButton;

    private Shop currentItem;
    private LocationManager locationManager;
    private Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new MyLocationListener(location));

        name = findViewById(R.id.add_shop_name_edit_text);
        description = findViewById(R.id.add_shop_description_edit_text);
        radius = findViewById(R.id.add_shop_radius_edit_text);
        removeButton = findViewById(R.id.add_shop_remove_button);
        removeButton.setEnabled(false);

        Optional.ofNullable(getIntent())
                .map(b -> b.getSerializableExtra("old"))
                .map(i -> currentItem = (Shop) i)
                .ifPresent(this::updateCurrentShop);
    }

    private void updateCurrentShop(Shop i) {
        name.setText(i.getName());
        description.setText(i.getDescription());
        radius.setText(i.getRadius().toString());

        removeButton.setEnabled(true);
    }

    public void saveOrUpdate(View v) {
        if (currentItem != null && currentItem.getId() != null) {
            updateItem();
        } else {
            saveNewItem();
        }
        super.finish();
    }

    private void updateItem() {
        updateCurrentItem();
        FirebaseDatabaseUtils.createOrUpdateShop(currentItem);
    }

    private void updateCurrentItem() {
        currentItem.setName(name.getText().toString());
        currentItem.setDescription(description.getText().toString());
        currentItem.setRadius(Integer.valueOf(radius.getText().toString()));
    }

    private void saveNewItem() {
        String name_ = name.getText().toString();
        String description_ = description.getText().toString();
        Integer radius_ = Integer.valueOf(radius.getText().toString());
        currentItem = new Shop(name_, description_, location.getLatitude(), location.getLongitude(), radius_);
        FirebaseDatabaseUtils.createOrUpdateShop(currentItem);
    }

    public void removeThis(View view) {
        FirebaseDatabaseUtils.removeItem(currentItem.getId());
        super.finish();
    }
}
