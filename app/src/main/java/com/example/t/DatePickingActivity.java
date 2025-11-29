package com.example.t;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DatePickingActivity extends AppCompatActivity {

    private EditText fromDateEditText;
    private EditText toDateEditText;
    private Calendar fromDateCalendar = Calendar.getInstance();
    private Calendar toDateCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Make window transparent and apply blur
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setupWindowBlur();

        setContentView(R.layout.date_picking);

        // Find views
        fromDateEditText = findViewById(R.id.fromDateEditText);
        toDateEditText = findViewById(R.id.toDateEditText);
        Button saveButton = findViewById(R.id.button);

        // --- Date Picker for "FROM" date ---
        DatePickerDialog.OnDateSetListener fromDateListener = (view, year, month, dayOfMonth) -> {
            fromDateCalendar.set(Calendar.YEAR, year);
            fromDateCalendar.set(Calendar.MONTH, month);
            fromDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(fromDateEditText, fromDateCalendar);
        };

        fromDateEditText.setOnClickListener(v -> new DatePickerDialog(DatePickingActivity.this,
                fromDateListener,
                fromDateCalendar.get(Calendar.YEAR),
                fromDateCalendar.get(Calendar.MONTH),
                fromDateCalendar.get(Calendar.DAY_OF_MONTH)).show());

        // --- Date Picker for "TO" date ---
        DatePickerDialog.OnDateSetListener toDateListener = (view, year, month, dayOfMonth) -> {
            toDateCalendar.set(Calendar.YEAR, year);
            toDateCalendar.set(Calendar.MONTH, month);
            toDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(toDateEditText, toDateCalendar);
        };

        toDateEditText.setOnClickListener(v -> new DatePickerDialog(DatePickingActivity.this,
                toDateListener,
                toDateCalendar.get(Calendar.YEAR),
                toDateCalendar.get(Calendar.MONTH),
                toDateCalendar.get(Calendar.DAY_OF_MONTH)).show());

        // --- Save Button ---
        saveButton.setOnClickListener(v -> {
            String fromDate = fromDateEditText.getText().toString();
            String toDate = toDateEditText.getText().toString();

            if (fromDate.isEmpty() || toDate.isEmpty()) {
                Toast.makeText(DatePickingActivity.this, "Please select both dates", Toast.LENGTH_SHORT).show();
            } else if (fromDateCalendar.after(toDateCalendar)) {
                Toast.makeText(DatePickingActivity.this, "Invalid interval", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DatePickingActivity.this, "Dates saved", Toast.LENGTH_SHORT).show();
                //go to searchPlacesActivity
                Intent intent = new Intent(this, searchPlacesActivity.class);
                startActivity(intent);

            }
        });
    }

    //blurry window
    private void setupWindowBlur() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        }
    }

    private void updateLabel(EditText editText, Calendar calendar) {
        String myFormat = "MM/dd/yyyy"; // You can change this format
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(calendar.getTime()));
    }
}
