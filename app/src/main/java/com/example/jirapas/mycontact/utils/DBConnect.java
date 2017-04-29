package com.example.jirapas.mycontact.utils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import com.example.jirapas.mycontact.model.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jirapas on 4/28/2017.
 */

public class DBConnect extends SQLiteOpenHelper {
  SQLiteDatabase sqLiteDatabase;
  public DBConnect(Context context) {
    super(context, "my_contact.db", null, 1);
  }
  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    String CREATE_CONTACT_TABLE = String.format("CREATE TABLE %s " +
        "(%s INTEGER PRIMARY KEY  AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
      Contact.TABLE,
      Contact.Column.ID,
      Contact.Column.FIRST_NAME,
      Contact.Column.LAST_NAME,
      Contact.Column.TEL,
      Contact.Column.EMAIL,
      Contact.Column.DESCRIPTION);

    sqLiteDatabase.execSQL(CREATE_CONTACT_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    String DROP_CONTACT_TABLE = "DROP TABLE IF EXISTS "+Contact.TABLE;
    sqLiteDatabase.execSQL(DROP_CONTACT_TABLE);
    onCreate(sqLiteDatabase);
  }

  public List<String> getContactList() {
    List<String> Contacts = new ArrayList<>();

    sqLiteDatabase = this.getWritableDatabase();

    Cursor cursor = sqLiteDatabase.query
      (Contact.TABLE, null, null, null, null, null, null);

    if (cursor != null) {
      cursor.moveToFirst();
    }

    while(!cursor.isAfterLast()) {

      Contacts.add(cursor.getLong(0) + " " +
        cursor.getString(1) + " " +
        cursor.getString(2));

      cursor.moveToNext();
    }

    sqLiteDatabase.close();

    return Contacts;
  }

  public void addContact(Contact contact) {
    sqLiteDatabase = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    //values.put(Friend.Column.ID, friend.getId());
    values.put(Contact.Column.FIRST_NAME, contact.getFirstName());
    values.put(Contact.Column.LAST_NAME, contact.getLastName());
    values.put(Contact.Column.TEL, contact.getTel());
    values.put(Contact.Column.EMAIL, contact.getEmail());
    values.put(Contact.Column.DESCRIPTION, contact.getDescription());

    sqLiteDatabase.insert(Contact.TABLE, null, values);

    sqLiteDatabase.close();
  }

  public void updateContact(Contact contact) {

    sqLiteDatabase  = this.getWritableDatabase();

    ContentValues values = new ContentValues();
    values.put(Contact.Column.ID, contact.getId());
    values.put(Contact.Column.FIRST_NAME, contact.getFirstName());
    values.put(Contact.Column.LAST_NAME, contact.getLastName());
    values.put(Contact.Column.TEL, contact.getTel());
    values.put(Contact.Column.EMAIL, contact.getEmail());
    values.put(Contact.Column.DESCRIPTION, contact.getDescription());

    int row = sqLiteDatabase.update(Contact.TABLE,
      values,
      Contact.Column.ID + " = ? ",
      new String[] { String.valueOf(contact.getId()) });

    sqLiteDatabase.close();
  }

  public void deleteContact(String id) {

    sqLiteDatabase = this.getWritableDatabase();

    sqLiteDatabase.delete(Contact.TABLE, Contact.Column.ID + " = " + id, null);

    sqLiteDatabase.close();
  }
  public Contact getContact(String id) {

    sqLiteDatabase = this.getReadableDatabase();

    Cursor cursor = sqLiteDatabase.query( Contact.TABLE,
      null,
      Contact.Column.ID + " = ? ",
      new String[] { id },
      null,
      null,
      null,
      null);


    if (cursor != null) {
      cursor.moveToFirst();
    }

    Contact contact = new Contact();

    contact.setId((int) cursor.getLong(0));
    contact.setFirstName(cursor.getString(1));
    contact.setLastName(cursor.getString(2));
    contact.setTel(cursor.getString(3));
    contact.setEmail(cursor.getString(4));
    contact.setDescription(cursor.getString(5));

    return contact;
  }


}
