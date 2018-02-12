package com.example.victoriafisher.helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user taps the Send button */
    public void showScripture(View view) {

        // Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText chapter = (EditText) findViewById(R.id.chapter);
        EditText book = (EditText) findViewById(R.id.book);
        EditText verse = (EditText) findViewById(R.id.verse);
        String message = book.getText().toString() + " " + chapter.getText().toString()  + ":" + verse.getText().toString();
        Log.d("debug", "About to create intent with" +  message);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
