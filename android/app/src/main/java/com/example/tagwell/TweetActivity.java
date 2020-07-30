package com.example.tagwell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This class is used to display all the information for a single tweet
 * (likely because it gets cutoff in the list view in the other activity)
 */
public class TweetActivity extends AppCompatActivity {
    ArrayList<String> arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet);

        arr = new ArrayList<String>();
        arr = getIntent().getStringArrayListExtra("arr");//Get the data passed from the previous activity
        TextView details = (TextView) findViewById(R.id.details);//Ouyput field

        StringBuilder sb = new StringBuilder();//Build a single string containing all necessary information
        sb.append("Hashtags: "+arr.get(0) + "\n\n");
        sb.append("Tweet: "+arr.get(1) + "\n\n");
        sb.append("Sentiment: "+arr.get(2) + "\n\n");
        details.setText("");
        details.setText(sb.toString());//display information
    }
}
