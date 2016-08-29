package tech28.alarme;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;

public class commander_side extends AppCompatActivity {

    public final int PICK_CONTACT = 2015;
    private ArrayList<String> lstNumbers;
    ContactsAdapter cAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commander_side);

        lstNumbers = new ArrayList<>();

        final Button btnAccept = (Button)findViewById(R.id.btnAcceptCommander);
        CheckBox chkAllowFees = (CheckBox)findViewById(R.id.chkKnowAboutFees);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recviewContacts);

        cAdapter = new ContactsAdapter(this, this.lstNumbers);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(cAdapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button btnAddContact = (Button)findViewById(R.id.btnAddContact);

        chkAllowFees.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                btnAccept.setClickable(isChecked);
            }
        });

        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(i, PICK_CONTACT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        if (requestCode == PICK_CONTACT && resultCode == RESULT_OK) {
//            Uri contactUri = data.getData();
//            Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
//            cursor.moveToFirst();
//            int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
//            //Toast.makeText(this, cursor.getString(column), Toast.LENGTH_LONG).show();
//            //Log.d("phone number", cursor.getString(column));
//        }

        // Check which request it is that we're responding to
        if (requestCode == 2015) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};

                Cursor cursor = getContentResolver()
                        .query(contactUri, projection, null, null, null);
                cursor.moveToFirst();

                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);

                // Do something with the phone number...
                this.lstNumbers.add(number);
                cAdapter.notifyDataSetChanged();
            }
        }
    }
}
