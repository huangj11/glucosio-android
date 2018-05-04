/*
Name: Julie Huang
Course: CSC 415
Semester: Spring 2018
Instructor: Dr. Pulimood 
Project name: Assignment 4 
Description: This program utilizes the Glucosio open source code, and assists diabetic users in tracking their glucose levels. This projects seeks to implement a mode in which the user can enter in their glucose level and get information on their glucose level in relation to physical activity.

Filename: ViewHistory.java 
Description: This file is the Java activity page that will display the glucose information inputted throughout the workout. It is currently a placeholder and will be implemented in further development.
*/
package com.example.julie.glucosioworkoutproto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ViewHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);
    }
}
