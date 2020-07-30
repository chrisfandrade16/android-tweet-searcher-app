import static org.junit.Assert.*;

import org.junit.Test;


public class ParserTest
{	
	{
		Parser.readData();
	}
	
	@Test
	public void testReadData()
	{
		assertTrue(Parser.getTweets() != null);
		assertTrue(Parser.getHashtags() != null);
	}

	@Test
	public void testGetTweets()
	{
		assertTrue(Parser.getTweets().size() == 28864);
	}

	@Test
	public void testGetHashtags()
	{
		assertTrue(Parser.getHashtags().size() == 10173);
	}
}
