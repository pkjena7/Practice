package com.example.practice.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.practice.model.Student;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "mydb.db";
    public static final String DBLOCATION = "/data/data/com.example.practice/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    public List<Student> getListProduct() {
        Student student = null;
        List<Student> productList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM PRODUCT", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            student = new Student(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3));
            productList.add(student);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return productList;
    }

    public Student getProductById(int id) {
        Student student = null;
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM PRODUCT WHERE ID = ?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        student = new Student(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3));
        //Only 1 resul
        cursor.close();
        closeDatabase();
        return student;
    }
    public long updateProduct(Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", student.getName());
        contentValues.put("AGE", student.getAge());
        contentValues.put("ADDRESS", student.getAddress());
        String[] whereArgs = {Integer.toString(student.getId())};
        openDatabase();
        long returnValue = mDatabase.update("Student",contentValues, "ID=?",whereArgs);
        closeDatabase();
        return returnValue;
    }

    public long addProduct(Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", student.getId());
        contentValues.put("NAME", student.getName());
        contentValues.put("AGE", student.getAge());
        contentValues.put("ADDRESS", student.getAddress());
        openDatabase();
        long returnValue = mDatabase.insert("Student", null, contentValues);
        closeDatabase();
        return returnValue;
    }
    public boolean deleteProductById(int id) {
        openDatabase();
        int result = mDatabase.delete("Student",  "ID =?", new String[]{String.valueOf(id)});
        closeDatabase();
        return result !=0;
    }
}
