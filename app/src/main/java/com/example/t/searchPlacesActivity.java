package com.example.t;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.SearchView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class searchPlacesActivity extends AppCompatActivity {

    RecyclerView recyclerViewPlaces;
    PlacesAdapter placesAdapter;

    ArrayList<JapanPlace> placesList = new ArrayList<>();
    ArrayList<JapanPlace> filteredList = new ArrayList<>();
    SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_places);

        loadPlaces(); // Load all places initially

        recyclerViewPlaces = findViewById(R.id.recyclerViewPlaces);
        recyclerViewPlaces.setLayoutManager(new LinearLayoutManager(this));
        placesAdapter = new PlacesAdapter(filteredList, place -> {
            // Handle place click
            Intent intent = new Intent(searchPlacesActivity.this, chosenPlacesActivity.class);
            intent.putExtra("place", place);
            startActivity(intent);
        });
        recyclerViewPlaces.setAdapter(placesAdapter);

        setupSearch();
        addButton();
    }
    private void addButton() {
        FloatingActionButton fab = findViewById(R.id.addPlace);
        fab.setOnClickListener(view -> showAddPlaceDialog());
    }
    private void showAddPlaceDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_place, null);
        final EditText editTextName = dialogView.findViewById(R.id.place_name);
        final EditText editTextDescription = dialogView.findViewById(R.id.place_description);
        builder.setView(dialogView)
                .setTitle("Add New Place")
                .setPositiveButton("Add", (dialog, id) -> {
                    String name = editTextName.getText().toString().trim();
                    String description = editTextDescription.getText().toString().trim();
                    if (!name.isEmpty() && !description.isEmpty()) {
                        JapanPlace newPlace = new JapanPlace(name, description, R.drawable.jp3);
                        placesList.add(newPlace);
                        filter(searchView.getQuery().toString());
                    }
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.cancel());
    }

    private void setupSearch() {
        searchView = findViewById(R.id.searchView);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String query) {
        filteredList.clear();
        if (query.isEmpty()) {
            filteredList.addAll(placesList);
        } else {
            filteredList.addAll(placesList.stream()
                    .filter(p -> p.getName().toLowerCase().contains(query.toLowerCase()) ||
                                 p.getDescription().toLowerCase().contains(query.toLowerCase()))
                    .collect(Collectors.toCollection(ArrayList::new)));
        }
        placesAdapter.notifyDataSetChanged();
    }

    private void loadPlaces() {
        placesList.add(new JapanPlace("Mount Fuji", "Iconic snow-capped mountain", R.drawable.fuji));
        placesList.add(new JapanPlace("Tokyo Tower", "Famous landmark in Tokyo", R.drawable.tokyo_tower));
        placesList.add(new JapanPlace("Kyoto Temples", "Historic temples in Kyoto", R.drawable.kyoto_temples));
        placesList.add(new JapanPlace("Osaka Castle", "Historic castle in Osaka", R.drawable.osaka_castle));
        placesList.add(new JapanPlace("Hiroshima Peace Memorial", "Memorial for peace", R.drawable.hiroshima_peace));
        placesList.add(new JapanPlace("Nara Park", "Park with friendly deer", R.drawable.nara_park));
        placesList.add(new JapanPlace("Sapporo Snow Festival", "Annual winter festival", R.drawable.sapporo_snow));
        placesList.add(new JapanPlace("Fushimi Inari Shrine", "Famous shrine with red torii gates", R.drawable.fushimi_inari));
        placesList.add(new JapanPlace("Okinawa Beaches", "Beautiful beaches in Okinawa", R.drawable.okinawa_beaches));
        placesList.add(new JapanPlace("Nagoya Castle", "Historic castle in Nagoya", R.drawable.nagoya_castle));
        // Initially, the filtered list is the full list
        filteredList.addAll(placesList);
    }
}
