package com.example.chris.nicolepetrol.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.chris.nicolepetrol.R;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Listener attached to the buttons on the GUI layout
     */
    public void onClick(View view) {
        Log.d(TAG, "onclick received");
        Intent newActivity = null;
        switch (view.getId()) { //depending on the button pressed the correct activity is launched
            case R.id.recordFillUpButton:
                newActivity = new Intent(getApplicationContext(), RecordFillUpActivity.class);
                break;
            default:
                Log.d(TAG, "ignored click event on" + view.getId());
        }
        if (newActivity != null) startActivity(newActivity);
    }
}

