package com.example.xmen_firebase;

import android.app.Application;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class XMenApplication extends Application {
    private ArrayList<XMen> liste;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        initListe(null);
    }

    public ArrayList<XMen> getListe() {
        return liste;
    }

    public interface DataReadyCallback {
        void onDataReady(ArrayList<XMen> data);
    }


    public void initListe(DataReadyCallback callback) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("xmen");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                liste = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    XMen xmen = snapshot.getValue(XMen.class);

                    // Set the key for the XMen object
                    if (xmen != null) {
                        xmen.setKey(snapshot.getKey());
                        liste.add(xmen);
                    }
                }

                // Notify listeners or update UI here if needed

                // Invoke the callback when data is ready
                if (callback != null) {
                    callback.onDataReady(liste);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
}
