package smb.pja.smbproject.first.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import smb.pja.smbproject.first.db.table.ElementList;
import smb.pja.smbproject.first.list.Item;

public final class DataBaseHandler extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "CW_1";

    public DataBaseHandler(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String elementsTable = "" +
                "CREATE TABLE " + ElementList.TABLE_NAME + " (" +
                ElementList.ID_COLUMN + " INTEGER PRIMARY KEY, " +
                ElementList.NAME_COLUMN + " TEXT, " +
                ElementList.PRICE_COLUMN + " NUMERIC, " +
                ElementList.AMOUNT_COLUMN + " INTEGER, " +
                ElementList.BOUGHT_COLUMN + " INTEGER" +
                ")";
        db.execSQL(elementsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ElementList.TABLE_NAME);
        onCreate(db);
    }

    public long addRow(Item item) {
        ContentValues values = new ContentValues();
        values.put(ElementList.NAME_COLUMN, item.getProductName());
        values.put(ElementList.PRICE_COLUMN, item.getPrice());
        values.put(ElementList.AMOUNT_COLUMN, item.getAmount());
        values.put(ElementList.BOUGHT_COLUMN, item.isBought());

        SQLiteDatabase db = getWritableDatabase();
        return db.insert(ElementList.TABLE_NAME, null, values);
    }

    public Optional<List<Item>> getAllItems() {
        String query = "SELECT * FROM " + ElementList.TABLE_NAME;

        SQLiteDatabase db = getReadableDatabase();
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

    public Item findItemById(Integer i) {
        String query = "SELECT * FROM " + ElementList.TABLE_NAME +
                " WHERE " + ElementList.ID_COLUMN + " = " + i;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            Integer id = cursor.getInt(0);
            String name = cursor.getString(1);
            Float price = cursor.getFloat(2);
            Integer amount = cursor.getInt(3);
            Boolean bought = cursor.getInt(4) == 1;

            return new Item(id, name, price, amount, bought);
        }
        return null;
    }

    public void update(Item currentItem) {
        ContentValues cv = new ContentValues();
        cv.put(ElementList.NAME_COLUMN, currentItem.getProductName());
        cv.put(ElementList.PRICE_COLUMN, currentItem.getPrice());
        cv.put(ElementList.AMOUNT_COLUMN, currentItem.getAmount());
        cv.put(ElementList.BOUGHT_COLUMN, currentItem.isBought() ? 1 : 0);

        SQLiteDatabase db = getWritableDatabase();
        db.update(ElementList.TABLE_NAME, cv, ElementList.ID_COLUMN + " = " + currentItem.getId(), null);
    }

    public void remove(Integer id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(ElementList.TABLE_NAME, ElementList.ID_COLUMN + " = " + id, null);
    }

    public void updateCheckbox(Integer idItem, boolean isChecked) {
        ContentValues cv = new ContentValues();
        cv.put(ElementList.BOUGHT_COLUMN, isChecked ? 1 : 0);

        SQLiteDatabase db = getWritableDatabase();
        db.update(ElementList.TABLE_NAME, cv, ElementList.ID_COLUMN + " = " + idItem, null);
    }
}
