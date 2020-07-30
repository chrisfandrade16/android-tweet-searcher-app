import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * A class used to parse a dataset containing tweets and their corresponding information to be used
 * in conjunction with the android app.
 * @author Christopher Andrade, Matthew Paulin
 */
public class Parser
{
	/**
	 * An ArrayList of all the tweets containing at least one valid hashtag.
	 */
	private static ArrayList<Tweet> tweets ;
	/**
	 * A set of all the unique hashtags in the dataset.
	 */
	private static HashSet<String> hashtags;
	/**
	 * Method to read the data CSV file and create an ArrayList of all the possible Tweets and a HashSet of all the unique hashtags
	 */
	public static void readData()
	{
		tweets = new ArrayList<Tweet>();
		hashtags = new HashSet<String>();
		
		try
		{
			BufferedReader file = new BufferedReader(new FileReader("data/data.csv"));
			String line = file.readLine();
			
			while((line = file.readLine()) != null)
			{
				String[] row = line.split(",");
				int sentiment = Integer.parseInt(row[0].substring(1, row[0].length() - 1));
				String tweet = row[5];
				
				ArrayList<String> tags = new ArrayList<String>();
				
				Pattern p = Pattern.compile("(?<![a-zA-Z0-9])#(?=[0-9]*[a-zA-Z])[a-zA-Z0-9_]+");//regex used to find all valid hashtags in a tweet
				Matcher m = p.matcher(tweet);
				
	            while(m.find())//Finds valid hashtags in a tweet
	            {
	            	tags.add(m.group().toLowerCase());
	                hashtags.add(m.group().toLowerCase());
	            }
	            
	            if(tags.size() == 0)
	            {
	            	continue;
	            }
	            
	            tweets.add(new Tweet(sentiment, tweet.substring(1, tweet.length() - 1), tags)); //create Tweet object with appropriate fields
			}
			
			file.close();
		} 
		catch(FileNotFoundException error)
		{
			error.printStackTrace();
		}
		catch(IOException error)
		{
			error.printStackTrace();
		}
	}
	
	/**
	 * This method returns the tweets field.
	 * @return Returns ArrayList of tweets field.
	 */
	public static ArrayList<Tweet> getTweets()
	{
		return tweets;
	}
	
	/**
	 * This method returns the hashtags field.
	 * @return Returns HashSet of hashtags field.
	 */
	public static HashSet<String> getHashtags()
	{
		return hashtags;
	}
}
