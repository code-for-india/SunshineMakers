package com.hack.swachhshauchalaya;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Button user = (Button)findViewById(R.id.user_button);
        Button cleanner = (Button)findViewById(R.id.cleaner_button);
        Button payment = (Button)findViewById(R.id.payment_button);
        final Intent intent = new Intent(this,MainActivity.class);
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("login","User");
                startActivity(intent);
                finish();
            }
        });
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("login","Payment");
                startActivity(intent);
                finish();
            }
        });
        cleanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("login","Cleaner");
                startActivity(intent);
                finish();
            }
        });
    }

}
