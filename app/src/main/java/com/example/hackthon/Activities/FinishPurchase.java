package com.example.hackthon.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.hackthon.R;

public class FinishPurchase extends AppCompatActivity {
    private ImageView imageDone;
    private TextView textView_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_purchase);




        imageDone = (ImageView) findViewById(R.id.imageView_fridge);
        imageDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });

        textView_message = (TextView) findViewById(R.id.new_meassage);
        textView_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });



    }


    public void backToMain() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }


}