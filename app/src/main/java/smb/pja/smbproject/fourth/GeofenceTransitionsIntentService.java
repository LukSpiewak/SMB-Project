package smb.pja.smbproject.fourth;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import smb.pja.smbproject.R;

public class GeofenceTransitionsIntentService extends IntentService {
    private static final String channelID = "channel1";
    private int id = 0;

    public GeofenceTransitionsIntentService() {
        super("GeofenceTransitionsIntentService");
    }

    public GeofenceTransitionsIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        final int geofenceTransition = geofencingEvent.getGeofenceTransition();
        List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        for (Geofence triggeringGeofence : triggeringGeofences) {
            mDatabase.child("shop").child(triggeringGeofence.getRequestId()).addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String contentTitle = "";
                            Shop shop = dataSnapshot.getValue(Shop.class);
                            if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
                                contentTitle = (String) getText(R.string.hello_message);
                            } else if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
                                contentTitle = (String) getText(R.string.goodbye_message);
                            }
                            NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), channelID)
                                    .setSmallIcon(R.mipmap.ic_launcher)
                                    .setContentTitle(contentTitle + "-" + shop.getName())
                                    .setContentText(getText(R.string.promo_message))
                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                    .setAutoCancel(true);
                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                            notificationManager.notify(id++, notification.build());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w("tag", "onCancelled", databaseError.toException());
                        }
                    });
        }
    }
}
