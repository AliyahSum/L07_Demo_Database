package sg.edu.rp.c346.id22015529.demodatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VER = 1 ;
    private static final String DATABASE_NAME = "tasks.db" ;

    private static final String TABLE_TASK = "task" ;
    private static final String COLUMN_ID = "_id" ;
    private static final String COLUMN_DESCRIPTION = "description" ;
    private static final String COLUMN_DATE = "date" ;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER) ;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE task(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "description TEXT, "
                + "date TEXT)" ;

        sqLiteDatabase.execSQL(sql) ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS task") ;
        onCreate(sqLiteDatabase) ;
    }

    public void insertTask(String description, String date) {
        SQLiteDatabase db = this.getWritableDatabase() ;
        ContentValues values = new ContentValues() ;
        values.put(COLUMN_DESCRIPTION, description) ;
        values.put(COLUMN_DATE, date) ;
        db.insert(TABLE_TASK, null, values) ;
        db.close() ;
    }

    public ArrayList<String> getTaskContent() {
        ArrayList<String> task = new ArrayList<String>() ;
        SQLiteDatabase db = this.getReadableDatabase() ;
        String[] columns = {COLUMN_ID, COLUMN_DESCRIPTION, COLUMN_DATE} ;
        Cursor cursor = db.query(TABLE_TASK, columns, null, null, null, null, null, null) ;

        if (cursor.moveToFirst()) {
            do {
                task.add(cursor.getString(1)) ;
            }
            while (cursor.moveToNext()) ;
        }
        cursor.close() ;
        db.close() ;

        return task ;
    }

    public ArrayList<Task> getTasks() {
        ArrayList<Task> task = new ArrayList<Task>() ;
        SQLiteDatabase db = this.getReadableDatabase() ;
        String[] columns = {COLUMN_ID, COLUMN_DESCRIPTION, COLUMN_DATE} ;
        Cursor cursor = db.query(TABLE_TASK, columns, null, null, null, null, null, null) ;

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0) ;
                String description = cursor.getString(1) ;
                String date = cursor.getString(2) ;
                Task obj = new Task(id, description, date) ;
                task.add(obj) ;
                }
            while (cursor.moveToNext()) ;
        }
        cursor.close() ;
        db.close() ;
        return task ;
    }
}