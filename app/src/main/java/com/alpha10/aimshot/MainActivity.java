// File: MainActivity.java
package com.alpha10.aimshot;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etLicenseKey;
    private Button btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI কম্পোনেন্টগুলো ইনিশিয়ালাইজ করা
        etLicenseKey = findViewById(R.id.etLicenseKey);
        btnVerify = findViewById(R.id.btnVerify);

        // বাটনে ক্লিক করলে সাময়িকভাবে একটি টোস্ট শো করবে (ভেরিফিকেশন পরে যুক্ত হবে)
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String licenseKey = etLicenseKey.getText().toString().trim();

                if (licenseKey.isEmpty()) {
                    etLicenseKey.setError("Please enter license key");
                    return;
                }

                Toast.makeText(MainActivity.this, "UI Working! Key: " + licenseKey, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
