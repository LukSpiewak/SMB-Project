package smb.pja.smbproject.first.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import smb.pja.smbproject.R;
import smb.pja.smbproject.first.db.DataBaseHandler;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyListAdapter listAdapter;

    private FirebaseAuth auth;
    private DatabaseReference database;

    private List<Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView =  findViewById(R.id.list_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("products").child(auth.getUid());
    }

    @Override
    protected void onStart() {
        super.onStart();

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                itemList.clear();

                dataSnapshot.getChildren().forEach(product -> {
                    Item item =  product.getValue(Item.class);

                    itemList.add(item);
                });

                listAdapter = new MyListAdapter(itemList);
                recyclerView.setAdapter(listAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void addNewElement(View view) {
        Intent intent = new Intent(this, AddElementActivity.class);
        startActivity(intent);
    }
}
