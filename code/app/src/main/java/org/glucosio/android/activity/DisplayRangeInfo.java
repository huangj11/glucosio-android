/*
Name: Julie Huang
Course: CSC 415
Semester: Spring 2018
Instructor: Dr. Pulimood
Project name: Assignment 4
Description: This project utilizes the Glucosio open source code, and assists diabetic users in tracking their glucose levels. This projects seeks to implement a mode in which the user can enter in their glucose level and get information on their glucose level in relation to physical activity.

Filename: DisplayRangeInfo.java
Description: This file displays the range message from AddGlucoseActivityWorkout.java and offers the user the option to retest.
*/

package org.glucosio.android.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.glucosio.android.R;

import java.util.ArrayList;
import java.util.List;

public class DisplayRangeInfo extends AppCompatActivity {
    TextView glucRange; //text to be changed
    TextView displayGluc; //text to display glucose level
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_range_info);

        //Toolbar action setup to go to MainActivity
        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_18dp);
            getSupportActionBar().setElevation(2);
            getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_workout_mode));
        }

        //retrieves message and glucose info
        Bundle b = getIntent().getExtras();
        String message = b.getString("range");
        String glucoseVal = b.getString("glucose", "0");


        //sets text to display glucose info
        displayGluc = (TextView)findViewById(R.id.displayGlucose);
        displayGluc.setText("Your glucose level is: ");
        displayGluc.append(glucoseVal);




        //sets message
        glucRange = (TextView) findViewById(R.id.rangeDisplay);
        glucRange.setText(message);




        //Populate arraylist with values and convert to list view
        //FIXME

//        List<String> workoutList = new ArrayList<String>();
        //workoutHistoryList.add("0.0");
//        workoutList.add(Double.toString(glucoseVal));



        //Sends glucose value to be stored in history
        //FIXME
//        Intent intent2 = new Intent(this, WorkoutHistory.class);
//        intent2.putExtra("glucoseToHist", glucoseVal);
//        startActivity(intent2);

        //Buttons to navigate
        Button retestGlucoseButton = findViewById(R.id.retest_glucose);
        //Button workoutHistoryButton = findViewById(R.id.workout_history);

        //set action go to AddReading page
        retestGlucoseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddReading();
            }

        });

        //set action go to goToViewHistory page
        //future implementation
//        workoutHistoryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goToWorkoutHistory();
//            }
//
//        });


    }


    //methods to go to page
    //-----------------------------------------------------------------------------------------
//
//  Function: goToAddReading ()
//
//    Parameters: N/A
//
//    Pre-condition: Button properly implemented
//    Post-condition: User is taken to page to add new reading
//-----------------------------------------------------------------------------------------
    private void goToAddReading() {
        Intent intent = new Intent(this, AddGlucoseActivityWorkout.class);
        startActivity(intent);
    }
    //-----------------------------------------------------------------------------------------
//
//  Function: goToWorkoutHistory ()
//
//    Parameters: N/A
//
//    Pre-condition: Button properly implemented
//    Post-condition: User is taken to page to view history
//-----------------------------------------------------------------------------------------
//    private void goToWorkoutHistory() {
//        Intent intent = new Intent(this, WorkoutHistory.class);
//        startActivity(intent);
//    }

    //Set toolbar action to take user back to MainActivity
    //-----------------------------------------------------------------------------------------
//
//  Function: onOptionsItemSelected ()
//
//    Parameters: input boolean; menu item selected
//
//    Pre-condition: input boolean is true
//    Post-condition: User is returned to main activity page
//-----------------------------------------------------------------------------------------
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
            Intent openMainActivity= new Intent(DisplayRangeInfo.this, MainActivity.class);
            openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityIfNeeded(openMainActivity, 0);

        }
        return super.onOptionsItemSelected(menuItem);
    }
}
