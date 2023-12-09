// EditActivity.java
package com.example.xmen_firebase;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xmen_firebase.databinding.EditActivityBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditActivity extends AppCompatActivity {
    private EditActivityBinding ui;
    private int position;
    private XMen currentXMen;
    private XMenAdapter adapter;
    XMenApplication application;
    public static final String EXTRA_POSITION = "extra_position";

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = EditActivityBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());
        position = getIntent().getIntExtra(EXTRA_POSITION, -1);

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("xmen");

        // If position is negative, it's a new X-Men creation, nothing else to do
        if (position < 0) {
            return;
        }

        // If position is non-negative, it's for editing an existing X-Men
        // Retrieve the X-Men at the specified position
        application = (XMenApplication) getApplication();
        adapter = new XMenAdapter(application.getListe());
        if (position < adapter.getItemCount()) {
            currentXMen = adapter.filteredData.get(position);
            setXMen(currentXMen);
        }

        setXMen(currentXMen);
    }

    public void onAccept(MenuItem item) {
        // If position is negative, it's a new X-Men creation
        if (position < 0) {
            // Create a new instance of XMen and fill it with values from EditText fields
            XMen xmen = new XMen();
            xmen.setNom(ui.editNom.getText().toString());
            xmen.setAlias(ui.editAlias.getText().toString());
            xmen.setDescription(ui.editDescription.getText().toString());
            xmen.setPouvoirs(ui.editPouvoirs.getText().toString());

            // Push the new XMen to Firebase
            databaseReference.push().setValue(xmen.toMap());

        } else {
            // If position is non-negative, it's for editing an existing X-Men
            // Update the current X-Men with values from EditText fields
            currentXMen.setNom(ui.editNom.getText().toString());
            currentXMen.setAlias(ui.editAlias.getText().toString());
            currentXMen.setDescription(ui.editDescription.getText().toString());
            currentXMen.setPouvoirs(ui.editPouvoirs.getText().toString());

            // Update the existing XMen in Firebase
            databaseReference.child(currentXMen.getKey()).setValue(currentXMen.toMap());
        }

        // Finish the activity indicating success
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void setXMen(XMen xmen) {
        ui.editNom.setText(xmen.getNom());
        ui.editAlias.setText(xmen.getAlias());
        ui.editDescription.setText(xmen.getDescription());
        ui.editPouvoirs.setText(xmen.getPouvoirs());
    }
}