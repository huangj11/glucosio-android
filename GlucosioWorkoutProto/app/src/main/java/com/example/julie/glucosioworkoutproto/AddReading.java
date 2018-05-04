/*
Name: Julie Huang
Course: CSC 415
Semester: Spring 2018
Instructor: Dr. Pulimood 
Project name: Assignment 4 
Description: This program utilizes the Glucosio open source code, and assists diabetic users in tracking their glucose levels. This projects seeks to implement a mode in which the user can enter in their glucose level and get information on their glucose level in relation to physical activity.

Filename: AddReading.java
Description: This file is the Java activity that implements the algorithm that take in the user inputted glucose information, compare it to the ranges, and set the displayed text to show the resulting information. It is currently a prototype version.
*/

package com.example.julie.glucosioworkoutproto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class AddReading extends AppCompatActivity {
    int glucose = 0;
    EditText glucoseRead;
    TextView glucRange1;
    Button rangeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reading);
        glucRange1 = (TextView) findViewById(R.id.rangeDisplay);

        glucoseRead = (EditText) findViewById(R.id.glucoseReading);
        rangeButton = (Button) findViewById(R.id.rangeButton);

		//listener for button press to display range information
        rangeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                glucose = Integer.parseInt(glucoseRead.getText().toString());
                if (glucose < 100) {
                    glucRange1.setText("Your blood glucose is rather low. Consider eating some carbs and testing again before exercising.");
                }
                else if (glucose >= 100 && glucose < 150) {
                    glucRange1.setText("Your blood glucose level is fairly safe. However, consider eating a snack just in case.");
                }
                else if (glucose >= 150 && glucose < 200) {
                    glucRange1.setText("Your blood glucose is at a safe level. You may need a snack for a lengthier exercise.");
                }
                else if (glucose >= 200 && glucose < 300) {
                    glucRange1.setText("Your blood glucose is at a safe level. Proceed according to your body's needs.");
                }
                else if (glucose >= 300) {
                    glucRange1.setText("Your blood glucose level too high. You should wait or take insulin before exercising.");
                }
                else {
                    glucRange1.setText("No");
                }

            }
        });
    }
}
