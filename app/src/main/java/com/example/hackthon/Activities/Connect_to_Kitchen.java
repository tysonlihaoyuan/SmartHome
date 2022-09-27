package com.example.hackthon.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hackthon.R;

public class Connect_to_Kitchen extends AppCompatActivity {
    private  Button btn_Later;
    private  Button btn_Buy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_to_kitchen);

        btn_Buy = (Button) findViewById(R.id.button_buy);
        btn_Buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finshPurchase();
            }
        });

        btn_Later = (Button) findViewById(R.id.button_later);
        btn_Later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });

    }





    public void finshPurchase() {
        Intent intent = new Intent(this,FinishPurchase.class);
        startActivity(intent);
    }
    public void backToMain() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
    }

