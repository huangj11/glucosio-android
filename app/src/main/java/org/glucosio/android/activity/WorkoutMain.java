/*
Name: Julie Huang
Course: CSC 415
Semester: Spring 2018
Instructor: Dr. Pulimood
Project name: Assignment 4
Description: This program utilizes the Glucosio open source code, and assists diabetic users in tracking their glucose levels. This projects seeks to implement a mode in which the user can enter in their glucose level and get information on their glucose level in relation to physical activity.

Filename: WorkoutMain.java
Description: This file is the main Java activity page that welcomes user into "Workout Mode". It contains the buttons to input glucose information and view workout history throughout the workout. The "View workout history" button is currently a placeholder and will be implemented in the next iteration.

Last modified 4/17/2018
*/

package org.glucosio.android.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.glucosio.android.R;
import android.widget.TextView;
import android.widget.Button;
import android.content.DialogInterface;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import org.glucosio.android.db.Reminder;
import org.glucosio.android.presenter.RemindersPresenter;
import org.glucosio.android.tools.AnimationTools;


public class WorkoutMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_main);

        //Set toolbar action setup to take user back to MainActivity
        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(2);
            getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_workout_mode));
        }

        //navigation buttons
        Button testGlucoseButton = findViewById(R.id.test_glucose);
        //Button workoutHistoryButton = findViewById(R.id.workout_history);

        //set action go to AddGlucoseReading page
        testGlucoseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddReading();
            }

        });

        //set action go to WorkoutHistory page
        //future implementation
//        workoutHistoryButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                goToWorkoutHistory();
//            }
//
//        });
    }


    //methods to go to pages
    private void goToAddReading() {
        Intent intent = new Intent(this, AddGlucoseActivityWorkout.class);
        startActivity(intent);
    }
//    private void goToWorkoutHistory() {
//        Intent intent = new Intent(this, WorkoutHistory.class);
//        startActivity(intent);
//    }


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


