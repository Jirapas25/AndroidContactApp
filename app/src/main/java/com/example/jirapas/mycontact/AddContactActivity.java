package com.example.jirapas.mycontact;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.jirapas.mycontact.model.Contact;
import com.example.jirapas.mycontact.utils.DBConnect;

public class AddContactActivity extends AppCompatActivity {
  private EditText mFirstName;
  private EditText mLastName;
  private EditText mTel;
  private EditText mEmail;
  private EditText mDescription;
  private Button mButtonOK;
  private DBConnect mHelper;
  public Bundle bundle;
  private int ID = -1;
  public Intent detail;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_contact);

    mFirstName = (EditText) findViewById(R.id.add_first_name);
    mLastName = (EditText) findViewById(R.id.add_last_name);
    mTel = (EditText) findViewById(R.id.add_tel);
    mEmail = (EditText) findViewById(R.id.add_email);
    mDescription = (EditText) findViewById(R.id.add_description);
    mButtonOK = (Button) findViewById(R.id.button_submit);
    mHelper = new DBConnect(this);

    bundle = getIntent().getExtras();
    detail = new Intent(this,MainActivity.class);
    if (bundle != null) {
      ID = bundle.getInt(Contact.Column.ID);
      String firstName = bundle.getString(Contact.Column.FIRST_NAME);
      String lastName = bundle.getString(Contact.Column.LAST_NAME);
      String tel = bundle.getString(Contact.Column.TEL);
      String email = bundle.getString(Contact.Column.EMAIL);
      String description = bundle.getString(Contact.Column.DESCRIPTION);

      mFirstName.setText(firstName);
      mLastName.setText(lastName);
      mTel.setText(tel);
      mEmail.setText(email);
      mDescription.setText(description);
    }

    mButtonOK.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        AlertDialog.Builder builder =
          new AlertDialog.Builder(AddContactActivity.this);
        if(bundle != null) {
          builder.setTitle(getString(R.string.update_data_title));
          builder.setMessage(getString(R.string.update_data_message));
        }else {
          builder.setTitle(getString(R.string.add_data_title));
          builder.setMessage(getString(R.string.add_data_message));
        }


        builder.setPositiveButton(getString(android.R.string.ok),
          new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              Contact contact = new Contact();
              contact.setFirstName(mFirstName.getText().toString());
              contact.setLastName(mLastName.getText().toString());
              contact.setTel(mTel.getText().toString());
              contact.setEmail(mEmail.getText().toString());
              contact.setDescription(mDescription.getText().toString());

              if (ID == -1) {
                mHelper.addContact(contact);
              } else {
                contact.setId(ID);
                mHelper.updateContact(contact);
              }
              //finish();
              startActivity(detail);
            }
          });

        builder.setNegativeButton(getString(android.R.string.cancel),
          new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              dialog.cancel();
            }
          });


        builder.show();
      }
    });


  }



}
