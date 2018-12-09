package smb.pja.smbproject.first.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Optional;

import smb.pja.smbproject.R;
import smb.pja.smbproject.first.db.DataBaseHandler;
import smb.pja.smbproject.first.firebase.FirebaseDatabaseUtils;

public class AddElementActivity extends AppCompatActivity {

    private EditText name;
    private EditText price;
    private EditText amount;
    private Button removeButton;

    private Item currentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_element);
        name = findViewById(R.id.add_element_name_edit_text);
        price = findViewById(R.id.add_element_price_edit_text);
        amount = findViewById(R.id.add_element_amount_edit_text);
        removeButton = findViewById(R.id.add_element_remove_button);
        removeButton.setEnabled(false);

        Optional.ofNullable(getIntent())
                .map(b -> b.getSerializableExtra("old"))
                .map(i -> currentItem = (Item) i)
                .ifPresent(this::updateCurrentItem);

//        Optional.ofNullable(getIntent())
//                .map(Intent::getExtras)
//                .map(b -> b.get("id"))
//                .map(id -> (Integer) id)
//                .map(db::findItemById)
//                .ifPresent(this::updateCurrentItem);
    }

    private void updateCurrentItem(Item i) {
        name.setText(i.getProductName());
        price.setText(i.getPrice().toString());
        amount.setText(i.getAmount().toString());

        removeButton.setEnabled(true);
    }

    public void saveOrUpdate(View v) {
        if (currentItem != null && currentItem.getId() != null) {
            updateItem();
        } else {
            saveNewItem();
            sendBroadcastOnNewProduct();
        }
        super.finish();
    }

    private void sendBroadcastOnNewProduct() {
        Intent intent = new Intent();
        intent.setAction("smb.pja.ADDING_NEW_PRODUCT");

        String productName = name.getText().toString();
        Float price = Float.valueOf(this.price.getText().toString());
        Integer amount = Integer.valueOf(this.amount.getText().toString());
        intent.putExtra("productName", productName);
        intent.putExtra("price", price);
        intent.putExtra("amount", amount);
        intent.putExtra("id", currentItem.getId());

        sendBroadcast(intent, "smb.pja.notificationapp.permissions.NOTIFICATION_PERMISSION");
    }

    private void updateItem() {
        updateCurrentItem();
        FirebaseDatabaseUtils.createOrUpdateItem(currentItem);
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
        currentItem = new Item(productName, price, amount, false);
        FirebaseDatabaseUtils.createOrUpdateItem(currentItem);
    }

    public void removeThis(View view) {
        FirebaseDatabaseUtils.removeItem(currentItem.getId());
        super.finish();
    }
}
