package com.example.petcareapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class DoctorDetailsActivity extends AppCompatActivity {

        private String[][] doctor_details1 =
                {
                        {"Doctor Name : Dr. Aisha Malik", "Hospital Address : Aga Khan University Hospital, Karachi", "Exp : 10yrs", "Mobile No:03001234567", "1200"},
                        {"Doctor Name : Dr. Bilal Khan", "Hospital Address : Shifa International Hospital, Islamabad", "Exp : 7yrs", "Mobile No:03332345678", "900"},
                        {"Doctor Name : Dr. Fatima Ahmed", "Hospital Address : Mayo Hospital, Lahore", "Exp : 15yrs", "Mobile No:03213456789", "1500"},
                        {"Doctor Name : Dr. Muhammad Rizwan", "Hospital Address : Doctors Hospital & Medical Center, Faisalabad", "Exp : 8yrs", "Mobile No:03454567890", "1000"},
                        {"Doctor Name : Dr. Zara Hassan", "Hospital Address : Lady Reading Hospital, Peshawar", "Exp : 5yrs", "Mobile No:03125678901", "700"}
                };

        private String[][] doctor_details2 =
                {
                        {"Doctor Name : Dr. Omar Farooq", "Hospital Address : National Hospital & Medical Centre, Lahore", "Exp : 12yrs", "Mobile No:03011122334", "1300"},
                        {"Doctor Name : Dr. Saima Ali", "Hospital Address : Islamabad International Hospital, Islamabad", "Exp : 6yrs", "Mobile No:03344455667", "850"},
                        {"Doctor Name : Dr. Kamran Hussain", "Hospital Address : Jinnah Hospital, Lahore", "Exp : 20yrs", "Mobile No:03225566778", "1800"},
                        {"Doctor Name : Dr. Hina Abbas", "Hospital Address : Chughtai Lab, Karachi", "Exp : 9yrs", "Mobile No:03466677889", "1100"},
                        {"Doctor Name : Dr. Usman Ghani", "Hospital Address : Quetta Institute of Medical Sciences, Quetta", "Exp : 4yrs", "Mobile No:03137788990", "650"}
                };

        private String[][] doctor_details3 =
                {
                        {"Doctor Name : Dr. Naveed Sultan", "Hospital Address : Allied Hospital, Faisalabad", "Exp : 11yrs", "Mobile No:03028899001", "1250"},
                        {"Doctor Name : Dr. Shazia Khan", "Hospital Address : Combined Military Hospital, Rawalpindi", "Exp : 8yrs", "Mobile No:03359900112", "950"},
                        {"Doctor Name : Dr. Faizan Raza", "Hospital Address : Services Hospital, Lahore", "Exp : 14yrs", "Mobile No:03230011223", "1400"},
                        {"Doctor Name : Dr. Rabia Anwar", "Hospital Address : Ziauddin Hospital, Karachi", "Exp : 7yrs", "Mobile No:03471122334", "1050"},
                        {"Doctor Name : Dr. Taha Mahmood", "Hospital Address : Khyber Teaching Hospital, Peshawar", "Exp : 6yrs", "Mobile No:03142233445", "750"}
                };

        private String[][] doctor_details4 =
                {
                        {"Doctor Name : Dr. Asadullah Tariq", "Hospital Address : Benazir Bhutto Hospital, Rawalpindi", "Exp : 9yrs", "Mobile No:03033344556", "1150"},
                        {"Doctor Name : Dr. Sana Batool", "Hospital Address : Bahria International Hospital, Lahore", "Exp : 5yrs", "Mobile No:03364455667", "800"},
                        {"Doctor Name : Dr. Junaid Hassan", "Hospital Address : Civil Hospital, Karachi", "Exp : 18yrs", "Mobile No:03245566778", "1600"},
                        {"Doctor Name : Dr. Mehreen Shafiq", "Hospital Address : District Headquarter Hospital, Sialkot", "Exp : 10yrs", "Mobile No:03487788990", "1200"},
                        {"Doctor Name : Dr. Hamza Iqbal", "Hospital Address : Shaikh Zayed Hospital, Lahore", "Exp : 7yrs", "Mobile No:03158899001", "900"}
                };

        private String[][] doctor_details5 =
                {
                        {"Doctor Name : Dr. Sobia Nadeem", "Hospital Address : Liaquat National Hospital, Karachi", "Exp : 13yrs", "Mobile No:03049900112", "1450"},
                        {"Doctor Name : Dr. Danish Pervez", "Hospital Address : Capital Hospital, Islamabad", "Exp : 6yrs", "Mobile No:03370011223", "880"},
                        {"Doctor Name : Dr. Ghazala Khan", "Hospital Address : Ittefaq Hospital, Lahore", "Exp : 16yrs", "Mobile No:03251122334", "1550"},
                        {"Doctor Name : Dr. Noman Butt", "Hospital Address : DHQ Hospital, Multan", "Exp : 8yrs", "Mobile No:03492233445", "1080"},
                        {"Doctor Name : Dr. Amna Siddique", "Hospital Address : Lady Wellington Hospital, Lahore", "Exp : 4yrs", "Mobile No:03163344556", "680"}
                };

    TextView tv;
    Button btn;
    String[][] doctor_details={};
    HashMap<String,String> item;
    ArrayList list;
    SimpleAdapter sa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        tv = findViewById(R.id.textViewCartTitle);
        btn = findViewById(R.id.buttonLTBack);

        Intent it = getIntent();
        String title = it.getStringExtra("title"); // Note: This string literal looks like a typo and might cause issues.
        tv.setText(title);
        if (title.compareTo("Family Physicians") == 0) {
            doctor_details = doctor_details1;
        } else if (title.compareTo("Dietician") == 0) {
            doctor_details = doctor_details2;
        } else if (title.compareTo("Dentist") == 0) {
            doctor_details = doctor_details3;
        } else if (title.compareTo("Surgeon") == 0) {
            doctor_details = doctor_details4;
        } else {
            doctor_details = doctor_details5;
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Note: 'packageContext' is not defined here.
                // It should likely be 'DoctorDetailsActivity.this' or 'getApplicationContext()'.
                startActivity(new Intent(DoctorDetailsActivity.this, FindDoctorActivity.class));
            }
        });
        list = new ArrayList();
        for (int i = 0; i < doctor_details.length; i++) {
            item = new HashMap<String, String>();
            item.put("Line1", doctor_details[i][0]);
            item.put("Line2", doctor_details[i][1]);
            item.put("Line3", doctor_details[i][2]);
            item.put("Line4", doctor_details[i][3]);
            item.put("Line5", "Cons Fees:" + doctor_details[i][4] + "/-");
            list.add(item);
        }
        sa = new SimpleAdapter(this,list,
                R.layout.multi_lines,
                new String[]{"Line1", "Line2", "Line3", "Line4", "Line5"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e}
        );
        ListView lst = findViewById(R.id.listViewLT);
        lst.setAdapter(sa);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(DoctorDetailsActivity.this, BookAppointmentActivity.class); // Note: 'packageContext' is typically 'DoctorDetailsActivity.this'
                it.putExtra("text1", title); // Note: This line likely has a syntax error
                it.putExtra("text2", doctor_details[i][0]); // Note: This line likely has a syntax error
                it.putExtra("text3", doctor_details[i][1]); // Note: This line likely has a syntax error
                it.putExtra("text4", doctor_details[i][3]); // Note: This line likely has a syntax error
                it.putExtra("text5", doctor_details[i][4]); // Note: This line likely has a syntax error
                startActivity(it);
            }
        });

    }
    }
