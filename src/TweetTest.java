import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


public class TweetTest
{

	@Test
	public void testTweet()
	{
		boolean checker = true;
		
		try
		{
			ArrayList<String> tags1 = new ArrayList<String>();
			ArrayList<String> tags2 = new ArrayList<String>();
			ArrayList<String> tags3 = new ArrayList<String>();
			
			tags2.add("#soccer");
			tags2.add("#test");
			tags2.add("#thisisgonnawork");
			tags2.add("#hashtag");
			tags3.add("#_");
			
			Tweet tweet1 = new Tweet(-201920, "", tags1);
			Tweet tweet2 = new Tweet(0, "EA Sports, it's in the game #soccer #test #thisisgonnawork #hashtag #", tags2);
			Tweet tweet3 = new Tweet(4, "1", tags3);
		}
		catch(Exception error)
		{
			checker = false;
		}
		
		assertTrue(checker);
	}

	@Test
	public void testGetSentiment()
	{
		ArrayList<String> tags = new ArrayList<String>();
		
		Tweet tweet1 = new Tweet(-201920, "", tags);
		Tweet tweet2 = new Tweet(0, "", tags);
		Tweet tweet3 = new Tweet(4, "", tags);
		
		assertTrue(tweet1.getSentiment() == -201920);
		assertTrue(tweet2.getSentiment() == 0);
		assertTrue(tweet3.getSentiment() == 4);
	}

	@Test
	public void testGetText()
	{
		ArrayList<String> tags = new ArrayList<String>();
		
		Tweet tweet1 = new Tweet(0, "Hello #yo", tags);
		Tweet tweet2 = new Tweet(0, "", tags);
		Tweet tweet3 = new Tweet(0, " ", tags);
		
		assertTrue(tweet1.getText().equals("Hello #yo"));
		assertTrue(tweet2.getText().equals(""));
		assertTrue(tweet3.getText().equals(" "));
	}

	@Test
	public void testGetTags()
	{
		ArrayList<String> tags1 = new ArrayList<String>();
		ArrayList<String> tags2 = new ArrayList<String>();
		ArrayList<String> tags3 = new ArrayList<String>();
		
		tags1.add("#soccer");
		tags1.add("#test");
		tags2.add("#thisisgonnawork");
		tags2.add("#hashtag");
		tags3.add("#_");
		tags3.add("#");
		
		Tweet tweet1 = new Tweet(0, "", tags1);
		Tweet tweet2 = new Tweet(0, "", tags2);
		Tweet tweet3 = new Tweet(0, "", tags3);
		
		assertTrue(tweet1.getTags().contains("#soccer") && tweet1.getTags().contains("#test"));
		assertTrue(tweet2.getTags().contains("#thisisgonnawork") && tweet2.getTags().contains("#hashtag"));
		assertTrue(tweet3.getTags().contains("#_") && tweet3.getTags().contains("#"));
	}

	@Test
	public void testToString()
	{
		ArrayList<String> tags = new ArrayList<String>();
		
		Tweet tweet1 = new Tweet(0, "Hello #yo", tags);
		Tweet tweet2 = new Tweet(0, "", tags);
		Tweet tweet3 = new Tweet(0, " ", tags);
		
		assertTrue(tweet1.toString().equals(tweet1.getText()));
		assertTrue(tweet2.toString().equals(tweet2.getText()));
		assertTrue(tweet3.toString().equals(tweet3.getText()));
	}

	@Test
	public void testCompareTo()
	{
		ArrayList<String> userHashtags = new ArrayList<String>();
		
		ArrayList<String> tags1 = new ArrayList<String>();
		ArrayList<String> tags2 = new ArrayList<String>();
		
		userHashtags.add("#soccer");
		userHashtags.add("#test");
		userHashtags.add("#thisisgonnawork");
		userHashtags.add("#hashtag");
		userHashtags.add("#_");
		userHashtags.add("#");
		
		tags1.add("#soccer");
		tags1.add("#soccer");
		tags1.add("#soccer");
		tags2.add("#soccer");
		tags2.add("#test");
		
		Tweet tweet1 = new Tweet(0, "", tags1);
		Tweet tweet2 = new Tweet(4, "", tags2);
		
		assertTrue(tweet1.compareTo(userHashtags, tweet2) == -1);
	}

}
