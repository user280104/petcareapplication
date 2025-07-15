package com.example.petcareapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class CartLabActivity extends AppCompatActivity {
    // These variables seem unused in the provided snippet, but kept as per your original code.
    // If you intend to use them, ensure they are properly initialized and utilized.
    HashMap<String, String> item;
    ArrayList list;
    ListView lst;
    SimpleAdapter sa;
    TextView tvTotal; // This will likely be used for textViewCartTotalCost

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton, timeButton, btnCheckout, btnBack;
    //private  Package [][] packages = {};
    private String[][] packages ={};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ensure this matches your XML layout file name (activity_cartlab.xml)
        setContentView(R.layout.activity_cart_lab);

        // --- Corrected findViewById IDs to match your XML ---
        dateButton = findViewById(R.id.buttonBMCartDate);
        timeButton = findViewById(R.id.buttonCartTime);
        btnCheckout = findViewById(R.id.buttonCartCheckout);
        btnBack = findViewById(R.id.buttonBMCartBack);
        tvTotal = findViewById(R.id.textViewBMCartTotalCost); // Initializing tvTotal with its corresponding TextView
        lst = findViewById(R.id.listViewBMCart);

        SharedPreferences sharedpreferences = getSharedPreferences("name", Context.MODE_PRIVATE);
        String username = sharedpreferences.getString("username", "").toString();

        Database db = new Database(getApplicationContext(), "healthcare", null, 1);

        float totalAmount = 0;
        ArrayList dbData = db.getCartData(username, "lab");
        Toast.makeText(getApplicationContext(), "" + dbData, Toast.LENGTH_LONG).show();

        packages = new String[dbData.size()][];
        for (int i = 0; i < packages.length; i++) {
            packages[i] = new String[5];
        }

        for (int i = 0; i < dbData.size(); i++) {
            String arrData = dbData.get(i).toString();
            String[] strData = arrData.split(java.util.regex.Pattern.quote("$"));
            packages[i][0] = strData[0];
            packages[i][4] = "Cost : " + strData[1] + "/-";
            totalAmount = totalAmount + Float.parseFloat(strData[1]);
        }

        tvTotal.setText("Total Cost : " + totalAmount);
        list = new ArrayList<>();
        for (int i = 0; i < packages.length; i++) {
            HashMap<String, String> item = new HashMap<>();
            item.put("Line1", packages[i][0]);
            item.put("Line2", packages[i][1]);
            item.put("Line3", packages[i][2]);
            item.put("Line4", packages[i][3]);
            item.put("Line5", packages[i][4]);
            list.add(item);
        }
        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"Line1", "Line2", "Line3", "Line4", "Line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        lst.setAdapter(sa);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ensure LabTestActivity is correctly defined and exists
                startActivity(new Intent(CartLabActivity.this, LabTestActivity.class));
            }
        });
        btnCheckout.setOnClickListener(v -> {
            Intent intent = new Intent(CartLabActivity.this, LabTestBookActivity.class);
            intent.putExtra("price", tvTotal.getText().toString());
            intent.putExtra("date", dateButton.getText().toString());
            intent.putExtra("time", timeButton.getText().toString());
            startActivity(intent);
        });

        // Date Picker initialization and listener
        initDatePicker();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        // Time Picker initialization and listener
        initTimePicker();
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });

        // You might want to populate your ListView here and calculate the total cost.
        // For example, if 'tvTotal' is meant to display the total cost, you'd set its text.
        // tvTotal.setText("Total Cost : CALCULATED_COST/-");
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                // Month is 0-indexed in Calendar and DatePicker, so add 1 for display
                month = month + 1;
                // Format as Day/Month/Year
                String date = dayOfMonth + "/" + month + "/" + year;
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        // Using THEME_HOLO_DARK as per your original code
        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);

        // Set minimum date to tomorrow (current time in millis + 24 hours in milliseconds)
        // This prevents selecting a date earlier than tomorrow.
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis() + 86400000);
    }

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                // Format the time to ensure two digits for hour and minute (e.g., 09:05 instead of 9:5)
                String formattedTime = String.format("%02d:%02d", hourOfDay, minute);
                timeButton.setText(formattedTime);
            }
        };

        Calendar cal = Calendar.getInstance();
        // Use HOUR_OF_DAY for 24-hour format if you want the initial time to be 24-hour based
        int hrs = cal.get(Calendar.HOUR_OF_DAY);
        int mins = cal.get(Calendar.MINUTE);

        int style = AlertDialog.THEME_HOLO_DARK;
        // The last parameter 'true' indicates 24-hour view
        timePickerDialog = new TimePickerDialog(this, style, timeSetListener, hrs, mins, true);
    }
}