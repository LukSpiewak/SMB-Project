package smb.pja.smbproject.first.firebase;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.Random;

import smb.pja.smbproject.first.list.Item;

import static android.support.constraint.Constraints.TAG;

public class FirebaseDatabaseUtils {

    private static DatabaseReference databaseReference = com.google.firebase.database.FirebaseDatabase.getInstance().getReference("products");
    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static Random random = new Random();

    public static void createOrUpdateItem(Item item) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (item.getId() == null) {
            item.setId(random.nextInt());
        }

        databaseReference.child(currentUser.getUid())
                .child(item.getId().toString())
                .setValue(item)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "success saveNewItem: " + item.toString());
                    } else {
                        Log.d(TAG, "failed saveNewItem: " + item.toString());
                    }
                });
    }

    public static void removeItem(Integer id) {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        databaseReference.child(currentUser.getUid())
                .child(id.toString())
                .removeValue();
    }
}
