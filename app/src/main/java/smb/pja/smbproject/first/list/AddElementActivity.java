package smb.pja.smbproject.first.list;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Optional;

import smb.pja.smbproject.R;
import smb.pja.smbproject.first.db.DataBaseHandler;

public class AddElementActivity extends AppCompatActivity {

    private DataBaseHandler db;

    private EditText name;
    private EditText price;
    private EditText amount;
    private Button removeButton;

    private Item currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DataBaseHandler(this);
        setContentView(R.layout.activity_add_element);
        name = findViewById(R.id.add_element_name_edit_text);
        price = findViewById(R.id.add_element_price_edit_text);
        amount = findViewById(R.id.add_element_amount_edit_text);
        removeButton = findViewById(R.id.add_element_remove_button);
        removeButton.setEnabled(false);

        Optional.ofNullable(getIntent())
                .map(b -> b.getSerializableExtra("old"))
                .ifPresent(this::fillItemFields);
    }

    private void fillItemFields(Object o) {
        currentItem = (Item) o;
        name.setText(currentItem.getProductName());
        price.setText(currentItem.getPrice().toString());
        amount.setText(currentItem.getAmount().toString());

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
        db.update(currentItem);
    }

    private void updateCurrentItem() {
        currentItem.setProductName(name.getText().toString());
        currentItem.setPrice(Float.valueOf(price.getText().toString()));
        currentItem.setAmount(Integer.valueOf(amount.getText().toString()));
    }

    private void saveNewItem() {
        String productName = name.getText().toString();
        Float price = Float.valueOf(this.price.getText().toString());
        Integer amount = Integer.valueOf(this.amount.getText().toString());
        db.addRow(new Item(productName, price, amount, false));
    }

    public void removeThis(View view) {
        db.remove(currentItem.getId());
        super.finish();
    }
}
