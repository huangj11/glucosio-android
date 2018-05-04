/*
Name: Julie Huang
Course: CSC 415
Semester: Spring 2018
Instructor: Dr. Pulimood
Project name: Assignment 4
Description: This program utilizes the Glucosio open source code, and assists diabetic users in tracking their glucose levels. This projects seeks to implement a mode in which the user can enter in their glucose level and get information on their glucose level in relation to physical activity.

Filename: AddGlucoseActivityWorkout.java
Description: This file borrows from the AddGlucoseActivity class from the original OSS. The changes made include redirecting the back button, changing the presenter, and overriding the functionality of the button to submit the reading. This is the Java activity that implements the algorithm that takes the user inputted glucose information, compares it to the ranges, and sets the displayed text to the resulting information.
Last modified on: 4/17/2018
*/

package org.glucosio.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import org.glucosio.android.Constants;
import org.glucosio.android.GlucosioApplication;
import org.glucosio.android.R;
import org.glucosio.android.analytics.Analytics;
import org.glucosio.android.db.GlucoseReading;
import org.glucosio.android.presenter.AddGlucosePresenter;
import org.glucosio.android.presenter.AddGlucosePresenterWorkout;
import org.glucosio.android.tools.FormatDateTime;
import org.glucosio.android.tools.GlucosioConverter;
import org.glucosio.android.tools.LabelledSpinner;
import org.glucosio.android.tools.NumberFormatUtils;
import org.glucosio.android.tools.ReadingTools;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Calendar;


public class AddGlucoseActivityWorkout extends AddReadingActivity {

    private static final int CUSTOM_TYPE_SPINNER_VALUE = 11;

    private TextView readingTextView;
    private EditText typeCustomEditText;
    private EditText notesEditText;
    private LabelledSpinner readingTypeSpinner;
    private boolean isCustomType = false;

    private final NumberFormat numberFormat = NumberFormatUtils.createDefaultNumberFormat();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_glucose);
        Toolbar toolbar = findViewById(R.id.activity_main_toolbar);

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setElevation(2);
        }

        //Toolbar listener to go back to DisplayRangeInfo class
        //Altered implementation
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                finish();
//            }
//        });

        this.retrieveExtra();

        //Sets presenter
        AddGlucosePresenterWorkout presenter = new AddGlucosePresenterWorkout(this);
        setPresenter(presenter);
        presenter.setReadingTimeNow();

        readingTypeSpinner = findViewById(R.id.glucose_add_reading_type);
        readingTypeSpinner.setItemsArray(R.array.dialog_add_measured_list);
        readingTextView = findViewById(R.id.glucose_add_concentration);
        typeCustomEditText = findViewById(R.id.glucose_type_custom);
        TextInputLayout readingInputLayout = findViewById(R.id.glucose_add_concentration_layout);
        AppCompatButton addFreeStyleButton = findViewById(R.id.glucose_add_freestyle_button);
        notesEditText = findViewById(R.id.glucose_add_notes);

        this.createDateTimeViewAndListener();
        this.createFANViewAndListener();

        readingTypeSpinner.setOnItemChosenListener(new LabelledSpinner.OnItemChosenListener() {
            @Override
            public void onItemChosen(View labelledSpinner, AdapterView<?> adapterView, View itemView, int position, long id) {
                // If other is selected
                if (position == CUSTOM_TYPE_SPINNER_VALUE) {
                    typeCustomEditText.setVisibility(View.VISIBLE);
                    isCustomType = true;
                } else {
                    if (typeCustomEditText.getVisibility() == View.VISIBLE) {
                        typeCustomEditText.setVisibility(View.GONE);
                        isCustomType = false;
                    }
                }
            }

            @Override
            public void onNothingChosen(View labelledSpinner, AdapterView<?> adapterView) {

            }
        });

        TextView unitM = findViewById(R.id.glucose_add_unit_measurement);

        if (Constants.Units.MG_DL.equals(presenter.getUnitMeasurement())) {
            unitM.setText(getString(R.string.mg_dL));
        } else {
            unitM.setText(getString(R.string.mmol_L));
        }

        // If an id is passed, open the activity in edit mode
        Calendar cal = Calendar.getInstance();
        FormatDateTime dateTime = new FormatDateTime(getApplicationContext());
        if (this.isEditing()) {
            setTitle(R.string.title_activity_add_glucose_edit);
            GlucoseReading readingToEdit = presenter.getGlucoseReadingById(this.getEditId());

            String readingString;
            if (presenter.getUnitMeasurement().equals(Constants.Units.MG_DL)) {
                readingString = String.valueOf(readingToEdit.getReading());
            } else {
                readingString = String.valueOf(GlucosioConverter.glucoseToMmolL(readingToEdit.getReading()));
            }

            readingTextView.setText(readingString);
            notesEditText.setText(readingToEdit.getNotes());
            cal.setTime(readingToEdit.getCreated());
            this.getAddDateTextView().setText(dateTime.getDate(cal));
            this.getAddTimeTextView().setText(dateTime.getTime(cal));
            presenter.updateReadingSplitDateTime(readingToEdit.getCreated());
            // retrieve spinner reading to set the registered one
            String measuredTypeText = readingToEdit.getReading_type();
            Integer measuredId = presenter.retrieveSpinnerID(measuredTypeText, Arrays.asList(getResources().getStringArray(R.array.dialog_add_measured_list)));
            if (measuredId == null) { // if nothing, it a custom type
                this.isCustomType = true;
                readingTypeSpinner.setSelection(CUSTOM_TYPE_SPINNER_VALUE);
            } else {
                readingTypeSpinner.setSelection(measuredId);
            }
            if (this.isCustomType) {
                typeCustomEditText.setText(measuredTypeText);
            }
        } else {
            this.getAddDateTextView().setText(dateTime.getDate(cal));
            this.getAddTimeTextView().setText(dateTime.getTime(cal));
            presenter.updateSpinnerTypeTime();
        }


        // Check if activity was started from a NFC sensor
        if (getIntent().getExtras() != null) {
            Bundle p;
            String reading;

            p = getIntent().getExtras();
            reading = p.getString("reading");
            if (reading != null) {
                double readingDouble = ReadingTools.safeParseDouble(reading);
                readingTextView.setText(numberFormat.format(readingDouble));
                readingInputLayout.setErrorEnabled(true);
                readingInputLayout.setError(getResources().getString(R.string.dialog_add_glucose_freestylelibre_added));
                addFreeStyleButton.setVisibility(View.GONE);

                addAnalyticsEvent();
            }
        }

        // Check if FreeStyle support is enabled in Preferences
        if (presenter.isFreeStyleLibreEnabled()) {
            addFreeStyleButton.setVisibility(View.VISIBLE);
            addFreeStyleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startLibreActivity();
                }
            });
        }
    }

    private void addAnalyticsEvent() {
        Analytics analytics = ((GlucosioApplication) getApplication()).getAnalytics();
        analytics.reportAction("FreeStyle Libre", "New reading added");
    }

    //Determines glucose range for exercise depending on user input
    //Added implementation
    //-----------------------------------------------------------------------------------------
//
//  Function: dialogOnAddButtonPressed()
//
//    Parameters: none
//
//    Pre-condition: readingTextView has been correctly retrieved from user; button listener has been correctly implemented
//    Post-condition: The message has been determined and stored into the variable to be passed to the DisplayRangeInfo class
//-----------------------------------------------------------------------------------------
    @Override
    protected void dialogOnAddButtonPressed() {
        Double glucose = Double.parseDouble(readingTextView.getText().toString());
        String message;

        if (glucose< 100) {
            message = "Your blood glucose is rather low. Consider eating some carbs and testing again before exercising.";
        }
        else if (glucose >= 100 && glucose < 150) {
            message = "Your blood glucose level is fairly safe. However, consider eating a snack just in case.";
        }
        else if (glucose >= 150 && glucose < 200) {
            message = "Your blood glucose is at a safe level. You may need a snack for a lengthier exercise.";
        }
        else if (glucose >= 200 && glucose < 300) {
            message = "Your blood glucose is at a safe level. Proceed according to your body's needs.";
        }
        else if (glucose >= 300) {
            message = "Your blood glucose level is too high. You should wait or take insulin before exercising.";
        }
        else {
            message = "Invalid input";
        }

        //intent to pass message to DisplayRangeInfo class
        Intent intent = new Intent(this, DisplayRangeInfo.class);
        Bundle b = new Bundle();
        b.putString("range", message);
        b.putString("glucose", Double.toString(glucose));
        intent.putExtras(b);
        startActivity(intent);

        //intent to pass glucose value to DisplayRangeInfo class
        //fix implementation
//        Intent intent2 = new Intent(this, DisplayRangeInfo.class);
//        intent2.putExtra("glucose", glucose);
//        startActivity(intent2);

        AddGlucosePresenterWorkout presenter = (AddGlucosePresenterWorkout) getPresenter();
        String readingType;
        if (isCustomType) {
            readingType = typeCustomEditText.getText().toString();
        } else {
            readingType = readingTypeSpinner.getSpinner().getSelectedItem().toString();
        }


        if (this.isEditing()) {
            presenter.dialogOnAddButtonPressed(this.getAddTimeTextView().getText().toString(),
                    this.getAddDateTextView().getText().toString(), readingTextView.getText().toString(),
                    readingType, notesEditText.getText().toString(), this.getEditId());
        } else {
            presenter.dialogOnAddButtonPressed(this.getAddTimeTextView().getText().toString(),
                    this.getAddDateTextView().getText().toString(), readingTextView.getText().toString(),
                    readingType, notesEditText.getText().toString());
        }
    }

    public void showErrorMessage() {
        View rootLayout = findViewById(android.R.id.content);
        Snackbar.make(rootLayout, getString(R.string.dialog_error2), Snackbar.LENGTH_SHORT).show();
    }

    public void startLibreActivity() {
        Intent intent = new Intent(this, FreestyleLibreActivity.class);
        startActivity(intent);
    }

    public void showDuplicateErrorMessage() {
        View rootLayout = findViewById(android.R.id.content);
        Snackbar.make(rootLayout, getString(R.string.dialog_error_duplicate), Snackbar.LENGTH_SHORT).show();
    }

    public void updateSpinnerTypeTime(int selection) {
        readingTypeSpinner.setSelection(selection);
    }

    private void updateSpinnerTypeHour(int hour) {
        AddGlucosePresenterWorkout presenter = (AddGlucosePresenterWorkout) getPresenter();
        readingTypeSpinner.setSelection(presenter.hourToSpinnerType(hour));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        super.onTimeSet(view, hourOfDay, minute);
        DecimalFormat df = new DecimalFormat("00");
        updateSpinnerTypeHour(Integer.parseInt(df.format(hourOfDay)));
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
