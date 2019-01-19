package smb.pja.smbproject.fourth.list;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

class MyLocationListener implements LocationListener {

    private Location current;

    public MyLocationListener(Location location) {
        current = location;
    }

    @Override
    public void onLocationChanged(Location location) {
        current = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
