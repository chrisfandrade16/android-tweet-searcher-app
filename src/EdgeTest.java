import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class EdgeTest
{
	@Test
	public void testEdge()
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
			
			Edge edge1 = new Edge(tweet1, "");
			Edge edge2 = new Edge(tweet2, "#test");
			Edge edge3 = new Edge(tweet3, "#_");
		}
		catch(Exception error)
		{
			checker = false;
		}
		
		assertTrue(checker);
	}

	@Test
	public void testGetTweet()
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
		
		Edge edge1 = new Edge(tweet1, "");
		Edge edge2 = new Edge(tweet2, "#test");
		Edge edge3 = new Edge(tweet3, "#_");
		
		assertTrue(edge1.getTweet().equals(tweet1));
		assertTrue(edge2.getTweet().equals(tweet2));
		assertTrue(edge3.getTweet().equals(tweet3));
	}

	@Test
	public void testGetHashtag()
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
		
		Edge edge1 = new Edge(tweet1, "");
		Edge edge2 = new Edge(tweet2, "#test");
		Edge edge3 = new Edge(tweet3, "#_");
		
		assertTrue(edge1.getHashtag().equals(""));
		assertTrue(edge2.getHashtag().equals("#test"));
		assertTrue(edge3.getHashtag().equals("#_"));
	}

}
