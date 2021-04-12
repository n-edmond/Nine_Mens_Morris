package com.example.fp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

/*
    This is the main page. Editing info here will change how the main page redirects or looks
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String tag = "Main Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creating the play button object
        Button play = findViewById(R.id.play_btn);
        play.setOnClickListener(this);

        //creating about image object
        ImageButton about = (ImageButton) findViewById(R.id.about_btn);
        about.setOnClickListener(this);

        //creating exit button object
        ImageButton exit = findViewById(R.id.exit_btn);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // handles what should be done once a click has been completed
        switch (v.getId()) {
            case R.id.play_btn:
                //intent creates the ability to go to a new page. Redirects to game
                //THIS WILL NEED TO BE EDITED TO ALLOW FOR CHOOSING PLAYER TYPE
                Intent intent = new Intent(this, Gameplay.class);
                startActivity(intent);
                break;
            case R.id.about_btn:
                //intent creates the ability to go to a new page. redirects to about
                Log.i(tag, "About button has been clicked");
                Intent intent2 = new Intent(MainActivity.this, About.class);
                startActivity(intent2);
                break;
            case R.id.exit_btn:
                //need to add functionality to exit app easily
                break;
            default:
                break;
        }
    }
}


