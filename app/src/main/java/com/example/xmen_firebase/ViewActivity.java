package com.example.xmen_firebase;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.xmen_firebase.databinding.ViewActivityBinding;
import com.squareup.picasso.Picasso;

public class ViewActivity extends AppCompatActivity {
    private ViewActivityBinding ui;
    private int position;
    private XMen currentXMen;
    private XMenAdapter adapter;
    XMenApplication application;
    public static final String EXTRA_POSITION = "extra_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui = ViewActivityBinding.inflate(getLayoutInflater());
        setContentView(ui.getRoot());
        position = getIntent().getIntExtra(EXTRA_POSITION, -1);

        // If position is non-negative, it's for viewing an existing X-Men
        // Retrieve the X-Men at the specified position
        application = (XMenApplication) getApplication();
        adapter = new XMenAdapter(application.getListe());

        // Use the original list instead of filteredData to get the item at the position
        if (position >= 0 && position < adapter.getItemCount()) {
            currentXMen = adapter.liste.get(position);
            setXMen(currentXMen);

            // Log the original data
            for (XMen xMen : adapter.liste) {
                Log.d("OriginalData", xMen.toString());
            }
        } else {
            // Handle the case where the position is invalid
            Log.e("ViewActivity", "Invalid position: " + position);
            // You might want to finish() the activity or display an error message.
            finish(); // Finish the activity if the position is invalid
        }
    }

    public void setXMen(XMen xmen) {
        Picasso.get().load(xmen.getIdImage()).into( ui.xmenImage);
        ui.xmenName.setText(xmen.getNom());
        ui.xmenAlias.setText(xmen.getAlias());
        ui.xmenDescription.setText(xmen.getDescription());
        ui.xmenPowers.setText(xmen.getPouvoirs());
    }
}
