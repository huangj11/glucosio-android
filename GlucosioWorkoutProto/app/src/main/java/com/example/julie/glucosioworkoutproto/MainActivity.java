/*
Name: Julie Huang
Course: CSC 415
Semester: Spring 2018
Instructor: Dr. Pulimood 
Project name: Assignment 4 
Description: This program utilizes the Glucosio open source code, and assists diabetic users in tracking their glucose levels. This projects seeks to implement a mode in which the user can enter in their glucose level and get information on their glucose level in relation to physical activity.

Filename: MainActivity.java 
Description: This file is the main Java activity page that welcomes user into "Workout Mode". It contains the buttons to input glucose information and view glucose history throughout the workout. It is currently a prototype.
*/

package com.example.julie.glucosioworkoutproto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        Button button2 = findViewById(R.id.button2);

		 //set action go to AddReading page
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddReading();
            }

        });

		 //set action go to goToViewHistory page
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToViewHistory();
            }

        });

    }

	//methods to go to pages
    private void goToAddReading() {
        Intent intent = new Intent(this, AddReading.class);
        startActivity(intent);
    }
    private void goToViewHistory() {
        Intent intent = new Intent(this, ViewHistory.class);
        startActivity(intent);
    }
}
