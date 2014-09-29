package com.example.walter.firstlab.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.walter.firstlab.core.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by walter on 29.09.14.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 1;

    private final static String DATABASE_NAME = "students_contacts";
    private final static String TABLE_CONTACTS = "contacts";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE_NUMBER = "phone";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT," + KEY_PHONE_NUMBER + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public void addStudent(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, student.getName());
        contentValues.put(KEY_EMAIL, student.getMail());
        contentValues.put(KEY_PHONE_NUMBER, student.getPhoneNumber());
        db.insert(TABLE_CONTACTS, null, contentValues);
        db.close();
    }

    public Student getStudent(String name){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{
                KEY_ID, KEY_NAME, KEY_EMAIL, KEY_PHONE_NUMBER }, KEY_NAME + "=?",
                new String[]{name}, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
        }
        Student student = new Student(Integer.valueOf(cursor.getString(0)), cursor.getString(1), cursor.getColumnName(2), cursor.getString(3));
        return student;
    }

    public List<Student> getAllStudents(){
        List<Student> studentsList = new ArrayList<Student>();
        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do {
                Student student = new Student();
                student.setName(cursor.getString(1));
                student.setMail(cursor.getString(2));
                student.setPhoneNumber(cursor.getString(3));
                studentsList.add(student);
            } while (cursor.moveToNext());
        }
        return studentsList;
    }

    public int updateStudent(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, student.getName());
        contentValues.put(KEY_EMAIL, student.getMail());
        contentValues.put(KEY_PHONE_NUMBER, student.getPhoneNumber());

        return db.update(TABLE_CONTACTS, contentValues, KEY_ID + " =?", new String[]{String.valueOf(student.getId())});
    }

    public void deleteStudent(Student student){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_NAME + " =?", new String[]{String.valueOf(student.getName())});
        db.close();
    }

    public int getStudentsCount(){
        String countQuery = "SELECT * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }
}
