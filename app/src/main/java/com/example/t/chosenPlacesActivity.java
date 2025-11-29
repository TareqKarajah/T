package com.example.t;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class chosenPlacesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen_places);

        // Find the views from the layout
        ImageView placeImage = findViewById(R.id.place_image);
        TextView placeName = findViewById(R.id.place_name);
        TextView placeDescription = findViewById(R.id.place_description);

        // Get the JapanPlace object from the intent
        JapanPlace place = (JapanPlace) getIntent().getSerializableExtra("place");

        // Check if the place object is not null and populate the views
        if (place != null) {
            placeImage.setImageResource(place.getImageResourceId());
            placeName.setText(place.getName());
            placeDescription.setText(place.getDescription());
        } else {
            // Handle the case where the data is missing
            Toast.makeText(this, "Error: Place data not found.", Toast.LENGTH_SHORT).show();
            finish(); // Close the activity if there's no data
        }
    }
}
