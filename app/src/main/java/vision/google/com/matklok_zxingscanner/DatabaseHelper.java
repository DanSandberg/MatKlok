package vision.google.com.matklok_zxingscanner;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Adapter;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";


    public static final String DATABASE_NAME = "mylist.db";
    public static final String TABLE_NAME = "mylist_data";
    public static final String ROW_ID = "_id";
    public static final String COL2 = "ITEM1";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        String createTable = "CREATE TABLE " + TABLE_NAME + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "ITEM1 TEXT)";

        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP IF TABLE EXISTS " + TABLE_NAME);
        onCreate(db);
    }


    public boolean addData(String ITEM1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put(COL2, ITEM1);


        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }


    //public Cursor getListContents() {
    //    SQLiteDatabase db = this.getReadableDatabase();
    //    Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    //    return data;
    //}

    public Cursor getItemID(String ITEM1) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + ROW_ID + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + ITEM1 + "'";

        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleterow(String ITEM1) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, COL2 + "='" + ITEM1 + "'", null);
    }

    public void deleterowid(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, ROW_ID + "='" + id + "'", null);
    }


    public void deleteItem(int id, String Item1) {


        SQLiteDatabase db = this.getWritableDatabase();


        //db.delete(TABLE_NAME, ROW_ID + " = '" + id + "';" + COL2 + " = '" + NAME + "';",null);


        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + ROW_ID + " = '" + id + "'" +
                " AND " + COL2 + " = '" + Item1 + "'";

        //Log.d(TAG, "deleteName: query: " + query);
        //Log.d(TAG, "deleteName: Deleting " + name + " from database.");


        db.execSQL(query);


        //   db.close();


        //public void deleteItem(String _id){
        //   SQLiteDatabase db = this.getWritableDatabase();
        //   db.delete(TABLE_NAME, "_id=?", new String[]{""+_id});

        //   db.close();


    }
}


