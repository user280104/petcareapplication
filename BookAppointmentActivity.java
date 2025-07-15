
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class BookAppointmentActivity extends AppCompatActivity {
    EditText ed1, ed2, ed3, ed4;
    TextView tv;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button timeButton, dateButton, btnBook, btnBack;

    // Declare these variables at the class level if they are used within inner classes
    // and initialized from the Intent.
    private String title;
    private String fullname;
    private String address;
    private String contact;
    private String fees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_book_appointment);

        tv = findViewById(R.id.textViewAppTitle);
        ed1 = findViewById(R.id.editTextAppFullName);
        ed2 = findViewById(R.id.editTextAppAddress);
        ed3 = findViewById(R.id.editTextAppContactNumber);
        ed4 = findViewById(R.id.editTextAppFees);
        dateButton = findViewById(R.id.buttonAppDate);
        timeButton = findViewById(R.id.buttonAppTime);
        btnBook = findViewById(R.id.buttonBookAppointment);
        btnBack = findViewById(R.id.buttonAppBack);


        // Making EditTexts non-editable via setKeyListener(null) is correct for display purposes
        ed1.setKeyListener(null);
        ed2.setKeyListener(null);
        ed3.setKeyListener(null);
        ed4.setKeyListener(null);

        Intent it = getIntent();

        // Initialize class-level variables from Intent
        title = it.getStringExtra("text1");
        fullname = it.getStringExtra("text2");
        address = it.getStringExtra("text3");
        contact = it.getStringExtra("text4");
        fees = it.getStringExtra("text5");

        tv.setText(title);
        ed1.setText(fullname);
        ed2.setText(address);
        ed3.setText(contact);
        ed4.setText("Cons Fees:" + fees + "/-");

        //datepicker
        initDatePicker();

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
        //timepicker - Add this call
        initTimePicker();
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BookAppointmentActivity.this, FindDoctorActivity.class));
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // The variables below should be taken from the correct EditTexts (ed1, ed2, ed3, etc.)
                // and not from undeclared variables like edFullName, edAddress, etc.
                String enteredFullName = ed1.getText().toString();
                String enteredAddress = ed2.getText().toString();
                String enteredContact = ed3.getText().toString();
                // If you have a pincode EditText, get it here, otherwise, it's missing in your current layout.
                // For now, assuming pincode is not collected or you have a default value or it's not needed by addOrder.
                // If addOrder absolutely needs pincode, you'll need to add an EditText for it in your layout.
                String pincode = "0"; // Placeholder, replace with actual pincode if collected

                Database db = new Database(getApplicationContext(), "healthcare", null, 1);
                SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedpreferences.getString("username", "").toString();

                if (db.checkAppointmentExists(username, title + "=>" + enteredFullName, enteredAddress, enteredContact, dateButton.getText().toString(), timeButton.getText().toString()) == 1) {
                    Toast.makeText(getApplicationContext(), "Appointment already booked", Toast.LENGTH_LONG).show();
                } else {
                    // Make sure the parameters match your Database class's addOrder method
                    // Assuming addOrder expects (username, fullname, address, contact, pincode(int), date, time, fees(float), otype)
                    db.addOrder(username, enteredFullName, enteredAddress, enteredContact, Integer.parseInt(pincode), dateButton.getText().toString(), timeButton.getText().toString(), Float.parseFloat(fees), "appointment");
                    Toast.makeText(getApplicationContext(), "Your appointment is done successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(BookAppointmentActivity.this, HomeActivity.class));
                }
            }
        });
    }


    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1 + 1; // Month is 0-indexed, so add 1 for display
                dateButton.setText(i2 + "/" + i1 + "/" + i); // Format as Day/Month/Year
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK; // Using a deprecated theme, consider Theme_DeviceDefault_Dialog_Alert or Theme_Material_Dialog_Alert
        // Assign to the class-level datePickerDialog
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        // Set minimum date to tomorrow (current time in millis + 24 hours in milliseconds)
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis() + 86400000);
    }

    private void initTimePicker() {

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                // i is hour (0-23 for 24-hour format), i1 is minute
                // Format the time with leading zeros for single-digit minutes
                String formattedMinutes = (i1 < 10) ? "0" + i1 : String.valueOf(i1);
                timeButton.setText(i + ":" + formattedMinutes);
            }
        };

        Calendar cal = Calendar.getInstance();
        int hrs = cal.get(Calendar.HOUR_OF_DAY); // Use HOUR_OF_DAY for 24-hour format (0-23)
        int mins = cal.get(Calendar.MINUTE);

        int style = AlertDialog.THEME_HOLO_DARK; // Using a deprecated theme, consider Theme_DeviceDefault_Dialog_Alert or Theme_Material_Dialog_Alert
        // Assign to the class-level timePickerDialog
        timePickerDialog = new TimePickerDialog(this, style, timeSetListener, hrs, mins, true); // true for 24-hour format
    }

}
