package com.example.chris.nicolepetrol.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chris.nicolepetrol.R;
import com.example.chris.nicolepetrol.model.PetrolTracker;
import com.example.chris.nicolepetrol.service.UTCTime;

/**
 * Created by chris on 25/11/2015.
 */
public class RecordFillUpActivity extends AppCompatActivity {
    private final static String TAG = "RecordFillUpActivity";
    /** time formatter */
    private UTCTime uTCTimeFormatter = new UTCTime();
    /** the UTC date and time of the fill up */
    private long uTCDateTime;
    /** the date picker */
    private DatePicker dateTimeTextView;
    /** the view holding the odometer reading */
    private TextView odometerTextView;
    /** the view holding the price reading */
    private TextView priceTextView;
    /** the view holding the volume reading */
    private TextView volumeTextView;
    /** Radio button for the partial fillup */
    private CheckBox partialCheckBox;
    /** the odometer value to save */
    private int odometer;
    /** the price to save */
    private int price;
    /** the volume to save */
    private float volume;
    /** the partial indication of the fill up */
    private boolean partial;
    /** the fill up object created */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordfillup);
        this.uTCDateTime = System.currentTimeMillis();
        this.odometerTextView = (TextView) findViewById(R.id.odometerField);
        this.volumeTextView = (TextView) findViewById(R.id.volumeField);
        this.priceTextView = (TextView) findViewById(R.id.priceField);
        this.partialCheckBox = (CheckBox) findViewById(R.id.partialFillUpCheckBox);
    }

    /**
     * handler for all the views on the screen
     * @param view the sender
     */
    public void onClick(View view){
        Log.d(TAG, "onClick received");
        switch (view.getId()) {
            case R.id.submitButton:
                handleSubmitButtonOnClick();
                break;
            case R.id.cancelButton:
                this.finish();
                break;
            case R.id.cameraButton:
                Toast.makeText(RecordFillUpActivity.this, "Camera function not yet implemented", Toast.LENGTH_LONG).show();
                break;
            default:
                Log.d(TAG, "ignored click event on:" + view.getId());
        }
    }

    /**
     * handle a fill-up submit: check that all the fields are in the correct format and send to the controller
     */
    private void handleSubmitButtonOnClick() {
        PetrolTracker petrolTracker = PetrolTracker.getInstance();
        if (standardiseFields()) {
            boolean success = petrolTracker.reportFillUp(this, uTCDateTime, this.odometer, this.price, this.volume, partial);
            if (success) {
                Toast.makeText(RecordFillUpActivity.this, "Fill Up successfully reported. Thank You.", Toast.LENGTH_LONG).show();
                this.finish();
            } else {
                Log.d(TAG, "unsuccessful API call to report a fill up");
                Toast.makeText(RecordFillUpActivity.this, "A problem occurred. Please try again.", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * checks if the fields entered by the user are valid (i.e. present, in the right format and range)
     * and transform them in default format
     * @return whether he operation was successful or not
     */
    private boolean standardiseFields() {
        final String odometerString = this.odometerTextView.getText().toString();
        final String priceString = this.priceTextView.getText().toString();
        final String volumeString = this.volumeTextView.getText().toString();
        try {
            this.odometer = Integer.parseInt(odometerString);
        } catch (NumberFormatException e) {
            Toast.makeText(RecordFillUpActivity.this, "The odometer field must be valid number", Toast.LENGTH_LONG).show();
            return false;
        }
        try {
            this.price = Integer.parseInt(priceString);
        } catch (NumberFormatException e) {
            Toast.makeText(RecordFillUpActivity.this, "The price field must be valid number, no dot", Toast.LENGTH_LONG).show();
            return false;
        }
        try {
            this.volume = Float.valueOf(volumeString);
        } catch (NumberFormatException e) {
            Toast.makeText(RecordFillUpActivity.this, "The volume field must be valid number", Toast.LENGTH_LONG).show();
            return false;
        }
        this.partial = partialCheckBox.isChecked();
        return true;
    }
}