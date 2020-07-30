package com.example.tagwell;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class is the screen the user sees on launch. It accepts a user's tweet and can send it to the backend for analysis.
 * Results from various algorithms are then displayed on screen.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button clrBtn;
    private Button submitBtn;
    private ImageButton copyBtn;
    private ImageButton tBtn;
    private EditText userInput;//The textbox for user input
    private ListView listview;//The list view of the tweets
    private Graph graph;
    ArrayList<String> toBeDisplayed;//a list of tweets to be displayed
    ArrayList<String> tags;//a list of the tags for each tweet
    ArrayList<String> sentiment;//A list of the sentiments for each tweet
    private ArrayList<Tweet> dataTweets ;//An ArrayList of all the tweets containing at least one valid hashtag.
    private HashSet<String> dataHashtags;//A set of all the unique hashtags in the dataset.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * This method runs when the app starts and initializes variables
         * and creates the Graph containing the tweets in the dataset.
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userInput = (EditText) findViewById(R.id.usrTxt);
        clrBtn = (Button) findViewById(R.id.clrBtn);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        copyBtn = (ImageButton) findViewById(R.id.copyBtn);
        tBtn = (ImageButton) findViewById(R.id.twitterBtn);
        clrBtn.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
        copyBtn.setOnClickListener(this);
        tBtn.setOnClickListener(this);
        listview=(ListView) findViewById(R.id.listView);
        try {
            readData();//parse the dataset
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Log.d("here", "finished reading data");
        graph = new Graph(dataTweets);//create the graph
        Log.d("here", "finished graphing data");

    }
    @Override
    public void onClick(View v){
        /**
         * This method is used to get instances when the different buttons are pressed
         */
        if(v.getId()==R.id.clrBtn){//clear the text box
            userInput.setText("");
        }
        else if(v.getId()==R.id.submitBtn){
            /**
             * This block highlights the user inputted hashtags and saves them to an Arraylist.
             * The searching (BFS) and sorting methods are then called to get a list of tweets that
             * should be displayed on screen. These tweets are then passed to another activity
             * if they are presesed so that more detailed info can be shown.
             */


            ArrayList<Tweet> t  = dataTweets;
            HashSet<String> h = dataHashtags;
            ArrayList<String> userHashtags = new ArrayList<String>();
            Pattern p = Pattern.compile("(?<![a-zA-Z0-9_])#(?=[0-9_]*[a-zA-Z])[a-zA-Z0-9_]+");//Regex for finding all hashtags in the user's input
            Matcher m = p.matcher(userInput.getText().toString());
            String modifiedText=userInput.getText().toString();

            while (m.find()){//highlight hashtags and add them to a list
                String toHighlight = m.group();
                if(dataHashtags.contains(toHighlight))
                    userHashtags.add(toHighlight);
                String replaceText = "<span style='background-color:#00acee'>" + "#~"+
                        toHighlight.substring(1) + "</span>";
                modifiedText = modifiedText.replaceFirst(toHighlight, replaceText);
            }
            modifiedText = modifiedText.replaceAll("#~", "#");
            userInput.setText(HtmlCompat.fromHtml(modifiedText, HtmlCompat.FROM_HTML_MODE_LEGACY));

            HashSet<Tweet> tweets = graph.BFS(userHashtags, dataHashtags);//Search for all tweets from the dataset where at least one tag matches

            Log.d("here", "finished BFS");
            Tweet[] data = graph.sort(tweets, userHashtags);//Sort them by sentiment and how many matching tags there are
            Log.d("here", "finished sorting");

            for(int i=0; i<data.length/2; i++){//reverse tweet array to display items in sorted order
                Tweet temp = data[i];
                data[i] = data[data.length -i -1];
                data[data.length -i -1] = temp;
            }

            toBeDisplayed = new ArrayList<String>();
            sentiment= new ArrayList<String>();
            tags = new ArrayList<String>();
            StringBuilder sb = new StringBuilder();
            int n = 10;
            for(int i =0 ;i < n && i < data.length; i++){//Display the first n items in a clickable list on screen
                toBeDisplayed.add(data[i].getText());
                if(data[i].getSentiment()==4){
                    sentiment.add("Positive");
                }
                else{
                    sentiment.add("Negative");
                }
                sb = new StringBuilder();
                for(String s : data[i].getTags()){
                    sb.append(s);
                    sb.append("    ");
                }
                tags.add(sb.toString());
            }
            displayTweets(toBeDisplayed);

        }
        else if(v.getId()==R.id.copyBtn){//Copy the user's input to the device's clipboard
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("usrText", userInput.getText());
            clipboard.setPrimaryClip(clip);
        }
        else if(v.getId()==R.id.twitterBtn){//Go to twitter.com (presumably to tweet)
            String website = "https://www.twitter.com";
            Uri address = Uri.parse(website);
            Intent go = new Intent(Intent.ACTION_VIEW, address);
            if(go.resolveActivity(getPackageManager())!= null){
                startActivity(go);
            }
        }
    }
    public void displayTweets(ArrayList<String> arr){
        /**
         * This method is used to display an array of tweets on screen
         */
        ArrayAdapter a = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1,arr){//change text size
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                /// Get the Item from ListView
                View view = super.getView(position, convertView, parent);

                TextView tv = (TextView) view.findViewById(android.R.id.text1);

                // Set the text size 25 dip for ListView each item
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,20);

                // Return the view
                return view;
            }
        };
        listview.setAdapter(a);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {//Get instances when list items are pressed
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//Display detailed data about a certain tweet if it is pressed
                Intent showTweetActivity = new Intent(getApplicationContext(), TweetActivity.class);
                ArrayList<String> arr = new ArrayList<String>();
                arr.add(tags.get(position));
                arr.add(toBeDisplayed.get(position));
                arr.add(sentiment.get(position));
                showTweetActivity.putStringArrayListExtra("arr", arr);
                startActivity(showTweetActivity);//Go to another activity/screen to display information
            }
        });
    }
    public void readData() throws FileNotFoundException {
        /**
         * This method parses a subset of the original dataset to save resources/time. All the tweets this method parses
         * include at least one hashtag, so are useful for the graph. From each line in a csv file, a new Tweet object is
         * created for later user with algorithms.
         */
        dataTweets = new ArrayList<Tweet>();
        dataHashtags = new HashSet<String>();
        String line="";
        InputStream inputStream = getResources().openRawResource(R.raw.output);//get input stream from csv
        BufferedReader file = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));

        try{
            line = file.readLine();
            Log.d("here", "begin parsing");
            while((line = file.readLine()) != null)//Read tweets line by line
            {
                String[] row = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);//Seperate by commas outside of quotes only
                int sentiment = Integer.parseInt(row[0].substring(1, row[0].length() - 1));
                String tweet = row[1];
                String allTags = row[2].substring(1,row[2].length()-1);
                ArrayList<String> tags = new ArrayList<String>();
                String[] tagsArr = allTags.split(",");
                for(int i = 0; i< tagsArr.length; i++) {//Add hashtags to global HashSet of all tags
                    tags.add(tagsArr[i]);
                    dataHashtags.add(tagsArr[i]);
                }
                dataTweets.add(new Tweet(sentiment, tweet.substring(1, tweet.length() - 1), tags)); //create Tweet object with appropriate fields
            }
            file.close();
            Log.d("here", "end parsing");
        }
        catch(IOException error)
        {
            Log.wtf("MainActivity","Error on line" + line, error);
            error.printStackTrace();
        }
    }
}
