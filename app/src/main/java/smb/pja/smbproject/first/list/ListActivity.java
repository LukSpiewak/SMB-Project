package smb.pja.smbproject.first.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Arrays;
import java.util.List;

import smb.pja.smbproject.R;

public class ListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView =  findViewById(R.id.list_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyListAdapter(getItems()));


    }

    private List<Item> getItems() {
        return Arrays.asList(
            new Item("name", 12.0F, 1, true),
            new Item("name2", 13.0F, 3, false)
        );
    }

    public void addNewElement(View view) {
    }
}
