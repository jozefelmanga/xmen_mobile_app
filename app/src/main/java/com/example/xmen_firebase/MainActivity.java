    package com.example.xmen_firebase;

    import android.annotation.SuppressLint;
    import android.app.AlertDialog;
    import android.content.Intent;
    import android.os.Bundle;
    import android.view.Menu;
    import android.view.MenuInflater;
    import android.view.MenuItem;
    import android.view.View;


    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.recyclerview.widget.LinearLayoutManager;

    import com.example.xmen_firebase.databinding.ActivityMainBinding;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;

    import java.util.ArrayList;

    public class MainActivity extends AppCompatActivity {
        private ActivityMainBinding ui;
        private XMenAdapter adapter;
        private XMenApplication application;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            ui = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(ui.getRoot());

            application = (XMenApplication) getApplication();

            // Initialize the list with a callback
            application.initListe(new XMenApplication.DataReadyCallback() {
                @Override
                public void onDataReady(ArrayList<XMen> data) {
                    // The data is ready, you can now proceed with the UI setup

                    // Create the adapter with the updated data
                    adapter = new XMenAdapter(application.getListe());
                    ui.recycler.setAdapter(adapter);

                    // Update the adapter's filteredData to reflect the changes
                    adapter.filter("", "");

                    // Set up the RecyclerView
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
                    ui.recycler.setLayoutManager(linearLayoutManager);

                    // Set up the search button click listener
                    ui.btnSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            performSearch();
                        }
                    });
                }
            });
        }

        // Method to perform search
        private void performSearch() {
            String query = ui.editTextSearch.getText().toString();
            String selectedFilter = ui.spinnerFilter.getSelectedItem().toString();
            adapter.filter(query,selectedFilter);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_menu, menu);
            return super.onCreateOptionsMenu(menu);
        }

        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item)
        {
            int itemId = item.getItemId();
            if (itemId == R.id.reinit) {
                application.initListe(null);
                recreate();
                ui.editTextSearch.setText("");
                return true;
            } else if (itemId == R.id.create) {
                onEdit(-1);
                return true;
            }
            return super.onOptionsItemSelected(item);
        }


        private void onEdit(int filteredPosition) {
            // Map the filtered position to the original position
            int originalPosition = adapter.getOriginalPosition(filteredPosition);
            // Create an Intent to launch EditActivity
            Intent intent = new Intent(this, EditActivity.class);

            // Add an extra "position" to the intent
            intent.putExtra(EditActivity.EXTRA_POSITION, originalPosition);

            // Start the activity with result expectation
            startActivityForResult(intent, 0);
        }


        private void onDelete(int filteredPosition) {
            // Map the filtered position to the original position
            int originalPosition = adapter.getOriginalPosition(filteredPosition);

            XMen xmen = application.getListe().get(originalPosition);

            new AlertDialog.Builder(this)
                    .setTitle(xmen.getNom())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage("Vous confirmez la suppression ?")
                    .setPositiveButton(android.R.string.ok, (dialog, idbtn) -> onReallyDelete(originalPosition))
                    .setNegativeButton(android.R.string.cancel, null)
                    .show();
        }


        private void onReallyDelete(int originalPosition) {
            // Remove the item from the original list
            XMen deletedXMen = application.getListe().remove(originalPosition);

            // Update filteredData to reflect the changes
            adapter.filter("", ""); // Pass empty query and filter to reset the filteredData

            // Notify the adapter that an item has been removed at the specified position
            adapter.notifyItemRemoved(originalPosition);

            // Delete the corresponding item from Firebase using the known key
            if (deletedXMen != null && deletedXMen.getKey() != null) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("xmen");;
                DatabaseReference xmenRef = databaseReference.child(deletedXMen.getKey());
                xmenRef.removeValue();
            }
        }


        /**
         * Cette méthode est appelée quand on revient dans cette activité après avoir
         * appelé une autre par startActivityForResult(intent, requestCode).
         * @param requestCode : celui qui avait été passé à startActivityForResult
         * @param resultCode : RESULT_OK (l'uti a validé l'autre activité) ou
         * RESULT_CANCELED (l'uti a fait back ou annuler), voir aussi setResult(resultCode)
         * @param data : un intent qui serait fourni par l'activité appelée
         */
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_CANCELED) return;
            // Update the adapter's filteredData to reflect the changes
            adapter.filter("", ""); // Pass empty query and filter to reset the filteredData
            adapter.notifyDataSetChanged();
        }


        @Override
        public boolean onContextItemSelected(MenuItem item)
        {
        // récupérer la position de l'élément concerné par le menu
            int position = item.getOrder();
        // selon le bouton de menu
            switch (item.getItemId()) {
                case XMenViewHolder.MENU_EDIT:
                    onEdit(position);
                    return true;
                case XMenViewHolder.MENU_DELETE:
                    onDelete(position);
                    return true;
            }
            return super.onContextItemSelected(item);
        }


    }