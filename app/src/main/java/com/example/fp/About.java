package com.example.fp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

//This class exist purely to create the about game page. Not really required for gameplay

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_game);
    }
}
