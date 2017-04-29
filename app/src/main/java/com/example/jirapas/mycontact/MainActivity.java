package com.example.jirapas.mycontact;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;


import com.example.jirapas.mycontact.model.Contact;
import com.example.jirapas.mycontact.utils.DBConnect;

import java.util.List;

public class MainActivity extends AppCompatActivity {
  DBConnect mHelper;
  List<String> contacts;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
         // .setAction("Action", null).show();
          addNewContact();
      }
    });


    mHelper = new DBConnect(this);
    contacts = mHelper.getContactList();
    ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contacts);
    ListView listView = (ListView) findViewById(R.id.view);
    listView.setAdapter(adapter);
    listView.setOnItemClickListener(new OnItemClickListener(){
      public void onItemClick(AdapterView<?> arg0
        , View arg1, int arg2, long arg3) {
        showDetail(arg2);
      }

    });


  }

  public void showDetail(int data) {
    Intent detail = new Intent(this,DetailActivity.class);
    String listName = contacts.get(data);
    int index = listName.indexOf(" ");
    String columnId = listName.substring(0, index);
    detail.putExtra(Contact.Column.ID, columnId);
    startActivity(detail);
  }

  public void addNewContact() {
    Intent intent = new Intent(this, AddContactActivity.class);
    startActivity(intent);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
