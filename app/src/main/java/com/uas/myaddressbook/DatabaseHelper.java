package com.uas.myaddressbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.uas.myaddressbook.models.AddressBookEmployee;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "employee", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE employee(employeeId INTEGER PRIMARY KEY, name TEXT, city TEXT, phone TEXT, email TEXT, picture TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS employee");
    }

    public boolean insertEmployee(AddressBookEmployee employee){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = getEmployee(employee.getEmployeeId());
        if(cursor.moveToFirst() && cursor.getCount() > 0){
            return false;
        }
        else{
            ContentValues contentValues = new ContentValues();
            contentValues.put("employeeId", employee.getEmployeeId());
            contentValues.put("name", employee.getName());
            contentValues.put("city", employee.getCity());
            contentValues.put("phone", employee.getPhone());
            contentValues.put("email", employee.getEmail());
            contentValues.put("picture", employee.getPicture());
            long result = db.insert("employee", null, contentValues);
            if(result == -1){
                return false;
            }
            else{
                return true;
            }
        }
    }

    public Cursor getAllEmployees(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM employee", null);
        return cursor;
    }

    public Cursor getEmployee(Integer employeeId){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM employee WHERE employeeId=?", new String[] {String.valueOf(employeeId)});
        return cursor;
    }
}
