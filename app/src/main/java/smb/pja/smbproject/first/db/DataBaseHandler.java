package smb.pja.smbproject.first.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import smb.pja.smbproject.first.list.Item;

public final class DataBaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "CW_1";
    private SQLiteDatabase db;

    public DataBaseHandler(Context context) {
        super(context, DB_NAME, null, VERSION);
        db = getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String elementsTable = "" +
                "CREATE TABLE elementsList (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT, " +
                "price NUMERIC, " +
                "amount INTEGER, " +
                "bought INTEGER" +
                ")";
        db.execSQL(elementsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addRow(Item item) {
        ContentValues values = new ContentValues();
        values.put("name", item.getProductName());
        values.put("price", item.getPrice());
        values.put("amount", item.getAmount());
        values.put("bought", item.isBought());
        db.insert("elementsList", null, values);

    }

    public Optional<List<Item>> getAllItems() {
        String query = "SELECT * FROM elementsList";

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            List<Item> results = new ArrayList<>();
             do {
                Integer id = cursor.getInt(0);
                String name = cursor.getString(1);
                Float price = cursor.getFloat(2);
                Integer amount = cursor.getInt(3);
                Boolean bought = cursor.getInt(4) == 1;

                results.add(new Item(id, name, price, amount, bought));
            } while (cursor.moveToNext());
            return Optional.of(results);
        }

        return Optional.empty();
    }

    public void update(Item currentItem) {
        ContentValues cv = new ContentValues();
        cv.put("name", currentItem.getProductName());
        cv.put("price", currentItem.getPrice());
        cv.put("amount", currentItem.getAmount());
        cv.put("bought", currentItem.isBought() ? 1 : 0);
        db.update("elementsList", cv, "id = " + currentItem.getId(), null);
    }

    public void remove(Integer id) {
        db.delete("elementsList", "id = " + id, null);
    }

    public void updateCheckbox(Integer idItem, boolean isChecked) {
        ContentValues cv = new ContentValues();
        cv.put("bought", isChecked ? 1 : 0);
        db.update("elementsList", cv, "id = " + idItem, null);
    }
}
