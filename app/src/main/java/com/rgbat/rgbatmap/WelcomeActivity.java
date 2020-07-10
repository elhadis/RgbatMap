package com.rgbat.rgbatmap;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {
    private Button buttonDriver,buttonCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        buttonDriver = findViewById(R.id.driver);
        buttonCustomer = findViewById(R.id.customer);
        buttonDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),RegisterActivity.class);
                startActivity(intent);
            }
        });
        buttonCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),CustomerRegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
