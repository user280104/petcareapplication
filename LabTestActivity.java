package com.example.petcareapplication; // Ensure this matches your package name

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class LabTestActivity extends AppCompatActivity {
    // Corrected packages array: Each inner array has 4 elements (index 0-3)
    // where index 3 is the cost.
    private String[][] packages =
            {
                    {"Package 1 : Pets Body Checkup", "", "", "999"},
                    {"Package 2 : Blood Glucose Fasting", "", "", "299"},
                    {"Package 3 : Virus Antibody - IgG", "", "", "899"},
                    {"Package 4 : Thyroid Check", "", "", "499"},
                    {"Package 5 : Immunity Check", "", "", "699"}
            };

    private String[] package_details = {
            "Blood Glucose Fasting\\n" +
                    "Complete Hemogram\\n" +
                    "HbA1c\\n" +
                    "Iron Studies\\n" +
                    "Kidney Function Test\\n" +
                    "LDH Lactate Dehydrogenase, Serum\\n" +
                    "Lipid Profile\\n" +
                    "Liver Function Test",
            "Blood Glucose Fasting",
            "virus Antibody - IgG",
            "Thyroid Profile-Total (T3, T4 & TSH Ultra-sensitive)",
            "Complete Hemogram\\n" +
                    "CRP (C Reactive Protein) Quantitative, Serum\\n" +
                    "Iron Studies\\n" +
                    "Kidney Function Test\\n" +
                    "Vitamin D Total-25 Hydroxy\\n" +
                    "Liver Function Test\\n" +
                    "Lipid Profile"
    };

    HashMap<String, String> item;
    ArrayList list;
    SimpleAdapter sa;
    Button btnGoToCart, btnBack;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ensure this line is present if you are using EdgeToEdge for fullscreen
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lab_test);

        // Apply window insets for proper UI display with system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnGoToCart = findViewById(R.id.buttonLTGoToCart);
        btnBack = findViewById(R.id.buttonLTBack);
        listView = findViewById(R.id.listViewLT);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Corrected Intent constructor: Removed 'packageContext:'
                startActivity(new Intent(LabTestActivity.this, HomeActivity.class));
            }
        });

        list = new ArrayList();
        for (int i = 0; i < packages.length; i++) {
            item = new HashMap<String, String>();
            item.put("line1", packages[i][0]);
            item.put("line2", packages[i][1]);
            item.put("line3", packages[i][2]);
            // packages[i][3] holds the cost
            item.put("line4", packages[i][3]); // Using packages[i][3] for line4 if it's meant to display the cost directly
            item.put("line5", "Total Cost:" + packages[i][3] + "/-"); // Corrected: Used packages[i][3] for cost
            list.add(item);
        }

        // Corrected SimpleAdapter constructor: Removed 'context:'
        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"line1", "line2", "line3", "line4", "line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        listView.setAdapter(sa);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // Get logged-in user
                SharedPreferences sharedPreferences = getSharedPreferences("name", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");

                // Save selected package to cart
                Database db = new Database(getApplicationContext(), "healthcare", null, 1);

                // Optional: check if already in cart
                if (db.checkCart(username, packages[i][0]) == 0) {
                    db.addCart(username, packages[i][0], Float.parseFloat(packages[i][3]), "lab");
                    Toast.makeText(getApplicationContext(), "Added to cart", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Already in cart", Toast.LENGTH_SHORT).show();
                }

                // Optionally go to details screen

        Intent it = new Intent(LabTestActivity.this, LabTestDetailsActivity.class);
        it.putExtra("text1", packages[i][0]);
        it.putExtra("text2", package_details[i]);
        it.putExtra("text3", packages[i][3]);
        startActivity(it);

            }
        });


       /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(LabTestActivity.this, LabTestDetailsActivity.class);
                it.putExtra("text1", packages[i][0]);
                it.putExtra("text2", package_details[i]);
                it.putExtra("text3", packages[i][3]); // Corrected: Used packages[i][3] for cost
                startActivity(it);
            }
        });*/


        btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LabTestActivity.this, CartLabActivity.class));
            }
        });
    }
}