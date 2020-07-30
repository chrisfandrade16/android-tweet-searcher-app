import java.util.ArrayList;
/**
 * Represents a single tweet from the dataset and its hashtags and sentiment values
 * @author Christopher Andrade, Matthew Paulin
 */
public class Tweet
{
	/**
	 * An integer value representing whether or not the tweet had a positive reception when posted. 
	 * The sentiment can be either 0 for negative or 4 for positive.
	 */
	private int sentiment;
	/**
	 * The actual text of each tweet.
	 */
	private String text;
	/**
	 * The hashtags contained within a tweet.
	 */
    private ArrayList<String> tags;
    /**
	 * Creates a Tweet object containing a tweet's text, its sentiment, and its hashtags
	 * @param sentiment
	 * @param text
	 * @param tags
	 */
	public Tweet(int sentiment, String text, ArrayList<String> tags)
	{
		this.sentiment = sentiment;
		this.text = text;
		this.tags = tags;
    }
	/**
	 * Getter for the sentiment value of the tweet.
	 * @return An integer sentiment value for the tweet. 
	 */
	public int getSentiment()
	{
		return sentiment;
	}
	/**
	 * Getter for the textual representation of a tweet.
	 * @return A string tweet.
	 */
	public String getText()
	{
		return text;
	}
	/**
	 * Getter for the hashtags contained in a tweet.
	 * @return An ArrayList of hashtags.
	 */
	public ArrayList<String> getTags()
	{
		return tags;
	}
	/**
	 * Method used to get the string portion of the tweet (used for printing).
	 * @return A string representation of the tweet.
	 */
	public String toString()
	{
		return text;
	}
	/**
	 * Method to test if one tweet has more common hashtags to the user's inputted text than another tweet.
	 * @param userHashtags An ArrayList of the user's inputted hashtags.
	 * @param tweet A Tweet object that the current Tweet object is compared to.
	 * @return An integer representing which tweet shares more hashtags with the inputted hashtags 
	 * 			(0 if the they share the same amount, 1 if the current tweet shares more, and -1
	 * 			if the other tweet shares more).
	 */
	public int compareTo(ArrayList<String> userHashtags, Tweet tweet)
	{
		Integer a = 0, b = 0;
		
		for(String hashtag : userHashtags)
		{
			if(tags.contains(hashtag))
			{
				a++;
			}
			if(tweet.getTags().contains(hashtag))
			{
				b++;
			}
		}
		
		return a.compareTo(b);
	}
}
