package com.mis.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class BuyedTicketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_tickets);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}