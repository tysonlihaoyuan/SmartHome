package com.example.hackthon.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hackthon.R;

public class MainActivity extends AppCompatActivity {
    private TextView textView_entertainment;
    private TextView textView_kitchen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        textView_entertainment = (TextView) findViewById(R.id.menu_entertainment);
        textView_entertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConnectToDraw();
            }
        });

        textView_kitchen = (TextView) findViewById(R.id.menu_kitchen);
        textView_kitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openConnectToKitchen();
            }
        });

    }





    public void openConnectToDraw() {
        Intent intent = new Intent(this,ConnectToDraw.class);
        startActivity(intent);
    }
    public void openConnectToKitchen() {
        Intent intent = new Intent(this,Connect_to_Kitchen.class);
        startActivity(intent);
    }





    }
