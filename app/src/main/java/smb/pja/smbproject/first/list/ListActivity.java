package smb.pja.smbproject.first.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import smb.pja.smbproject.R;
import smb.pja.smbproject.first.db.DataBaseHandler;

public class ListActivity extends AppCompatActivity {

    private DataBaseHandler db;
    private RecyclerView recyclerView;
    private MyListAdapter listAdapter;

    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        db = new DataBaseHandler(this);
        recyclerView =  findViewById(R.id.list_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemList = getItems();
        listAdapter = new MyListAdapter(itemList);
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemList.clear();
        itemList.addAll(getItems());
        listAdapter.notifyDataSetChanged();
    }

    private List<Item> getItems() {
        return db.getAllItems()
                .orElse(Collections.emptyList());
    }

    public void addNewElement(View view) {
        Intent intent = new Intent(this, AddElementActivity.class);
        startActivity(intent);
    }
}
