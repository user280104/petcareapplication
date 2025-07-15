package com.example.petcareapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.petcareapplication.BuyMedicineActivity;
import com.example.petcareapplication.Database;

public class BuyMedicineDetailsActivity extends AppCompatActivity { // Assuming this is the correct class name

    TextView tvPackageName, tvTotalCost;
    EditText edDetails;
    Button btnBack, btnAddToCart;

    // Assuming these are defined elsewhere or passed via Intent for the full context
    // String[][] packages;
    // String[] package_details;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine_details); // Assuming your layout file

        tvPackageName = findViewById(R.id.textViewBMDPackageName); // Replace with your actual ID
        tvTotalCost = findViewById(R.id.textViewBMDTotalCost); // Replace with your actual ID
        edDetails = findViewById(R.id.editTextTextBMDMultiLine); // Replace with your actual ID
        btnBack = findViewById(R.id.buttonBMDBack); // Replace with your actual ID
        btnAddToCart = findViewById(R.id.buttonBMDAddToCart); // Replace with your actual ID

        edDetails.setKeyListener(null); // Assuming this is intended

        Intent intent = getIntent();
        tvPackageName.setText(intent.getStringExtra("text1"));
        edDetails.setText(intent.getStringExtra("text2"));
        tvTotalCost.setText("Total Cost :" + intent.getStringExtra("text3") + "/-");


        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedpreferences.getString("username", "").toString();
                String product = tvPackageName.getText().toString();
                float price = Float.parseFloat(intent.getStringExtra("text3").toString()); // Assuming 'intent' is accessible here

                Database db = new Database(getApplicationContext(), "pethcare", null, 1);

                if (db.checkCart(username, product) == 1) {
                    Toast.makeText(getApplicationContext(), "Product Already Added", Toast.LENGTH_SHORT).show();
                } else {
                    db.addCart(username, product, price, "medicine");
                    Toast.makeText(getApplicationContext(), "Record Inserted to Cart", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(BuyMedicineDetailsActivity.this, BuyMedicineActivity.class));
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BuyMedicineDetailsActivity.this, BuyMedicineActivity.class));
            }
        });
    }
}