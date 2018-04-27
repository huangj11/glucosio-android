/*
Name: Julie Huang
Course: CSC 415
Semester: Spring 2018
Instructor: Dr. Pulimood
Project name: Assignment 4
Description: This project utilizes the Glucosio open source code, and assists diabetic users in tracking their glucose levels. This projects seeks to implement a mode in which the user can enter in their glucose level and get information on their glucose level in relation to physical activity.

Filename: WorkoutHistory.java
Description: This file displays the workout history (glucose levels throughout workout) of the user. This requires future work to fix functionality.
*/

package org.glucosio.android.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.glucosio.android.R;

import java.util.ArrayList;
import java.util.List;

public class WorkoutHistory extends AppCompatActivity {
    private ListView glucoseHistLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history);

        //Toolbar action setup to go to MainActivity
        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(2);
            getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_view_history));
        }
        //Toolbar listener to go back to DisplayRangeInfo class
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),DisplayRangeInfo.class));
//                finish();
//            }
//        });
        //new list view
        glucoseHistLV = (ListView) findViewById(R.id.workoutHistList);

        //Retrieves glucose value
        //FIXME
//        Intent intent = getIntent();
//        Double glucoseVal = intent.getDoubleExtra("glucoseToHist", 0);

//        //Populate arraylist with values and convert to list view
//        //FIXME
//        //String glucoseVal = "0.0";
//        List<String> workoutHistoryList = new ArrayList<String>();
//        //workoutHistoryList.add("0.0");
//        workoutHistoryList.add(Double.toString(glucoseVal));

        //listview adapter
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( this,
//                android.R.layout.simple_list_item_1,
//                workoutHistoryList );
//        glucoseHistLV.setAdapter(arrayAdapter);
    }


    //-----------------------------------------------------------------------------------------
//
//  Function: onOptionsItemSelected ()
//
//    Parameters: input boolean; menu item selected
//
//    Pre-condition: input boolean is true
//    Post-condition: User is taken back a page
//-----------------------------------------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
