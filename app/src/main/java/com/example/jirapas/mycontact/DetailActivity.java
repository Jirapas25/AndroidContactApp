package com.example.jirapas.mycontact;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jirapas.mycontact.model.Contact;
import com.example.jirapas.mycontact.utils.DBConnect;

public class DetailActivity extends AppCompatActivity {
  DBConnect mHelper;

  private TextView mFirstName;
  private TextView mLastName;
  private TextView mTel;
  private TextView mEmail;
  private TextView mDescription;
  private String id = "";
  private Button mButtonDelete;
  private Button mButtonEdit;
  public Intent detail;
  private Contact mContact;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);

    mHelper = new DBConnect(this);

    Bundle bundle  = getIntent().getExtras();
    detail = new Intent(this,MainActivity.class);
    if (bundle != null) {
      id = bundle.getString(Contact.Column.ID);
    }

    mFirstName = (TextView) findViewById(R.id.detail_first_name);
    mLastName = (TextView) findViewById(R.id.detail_last_name);
    mTel = (TextView) findViewById(R.id.detail_tel);
    mEmail = (TextView) findViewById(R.id.detail_email);
    mDescription = (TextView) findViewById(R.id.detail_description);
    mButtonDelete = (Button) findViewById(R.id.button_delete);
    mButtonEdit = (Button) findViewById(R.id.button_edit);

    mContact = mHelper.getContact(id);

    mFirstName.setText(mContact.getFirstName());
    mLastName.setText(mContact.getLastName());
    mTel.setText(mContact.getTel());
    mEmail.setText(mContact.getEmail());
    mDescription.setText(mContact.getDescription());

    mButtonDelete.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        AlertDialog.Builder builder =
          new AlertDialog.Builder(DetailActivity.this);
        builder.setTitle(getString(R.string.delete_title));
        builder.setMessage(getString(R.string.delete_message));

        builder.setPositiveButton(getString(android.R.string.ok),
          new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which) {
              mHelper.deleteContact(id);

              Toast.makeText(getApplication(),
                "Deleted", Toast.LENGTH_LONG).show();
              //finish();
              startActivity(detail);
            }
          });

        builder.setNegativeButton(getString(android.R.string.cancel), null);

        builder.show();

      }
    });


    mButtonEdit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent updateIntent = new Intent(DetailActivity.this,
          AddContactActivity.class);

        updateIntent.putExtra(Contact.Column.ID, mContact.getId());
        updateIntent.putExtra(Contact.Column.FIRST_NAME, mContact.getFirstName());
        updateIntent.putExtra(Contact.Column.LAST_NAME, mContact.getLastName());
        updateIntent.putExtra(Contact.Column.TEL, mContact.getTel());
        updateIntent.putExtra(Contact.Column.EMAIL, mContact.getEmail());
        updateIntent.putExtra(Contact.Column.DESCRIPTION, mContact.getDescription());

        startActivity(updateIntent);
        overridePendingTransition(android.R.anim.fade_in,
          android.R.anim.fade_out);
      }
    });
  }
}
