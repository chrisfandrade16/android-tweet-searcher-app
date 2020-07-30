import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Test;


public class GraphTest
{
	{
		Parser.readData();
	}
	
	Graph graph = new Graph(Parser.getTweets());
	
	@Test
	public void testGraph()
	{
		boolean checker = true;
		
		try
		{
			Graph tester = new Graph(Parser.getTweets());
		}
		catch(Exception error)
		{
			checker = false;
		}
		
		assertTrue(checker);
	}

	@Test
	public void testBFS()
	{
		boolean checker1 = false;
		boolean checker2 = false;
		
		ArrayList<String> userHashtags1 = new ArrayList<String>();
		ArrayList<String> userHashtags2 = new ArrayList<String>();
		ArrayList<String> userHashtags3 = new ArrayList<String>();
		
		userHashtags1.add("#Geek");
		userHashtags1.add("#WebTech");
		userHashtags1.add("#News");
		
		userHashtags2.add("#goodtimes");
		userHashtags2.add("#softwaretesting");
		
		HashSet<Tweet> tweets1 = graph.BFS(userHashtags1, Parser.getHashtags());
		HashSet<Tweet> tweets2 = graph.BFS(userHashtags2, Parser.getHashtags());
		HashSet<Tweet> tweets3 = graph.BFS(userHashtags3, Parser.getHashtags());
		
		System.out.println(tweets3);
		
		for(Tweet tweet: tweets1)
		{
			if(tweet.getText().equals(" RIM aims to cross categories with BlackBerry Tour (Reuters) #Geek #WebTech #News http://tinyurl.com/mjpjtx"))
			{
				checker1 = true;
			}
		}
		
		for(Tweet tweet: tweets2)
		{
			if(tweet.getText().equals("Top 10 software testing myths I came across http://tinyurl.com/m5nyal #testing #softwaretesting #qa .Thanks for your feedback in advance "))
			{
				checker2 = true;
			}
		}
		
		assertTrue(checker1);
		assertTrue(checker2);
		assertTrue(tweets3.size() == 0);
	}

	@Test
	public void testSort()
	{	
		boolean checker1 = false;
		boolean checker2 = false;
		
		ArrayList<String> userHashtags1 = new ArrayList<String>();
		ArrayList<String> userHashtags2 = new ArrayList<String>();
		
		userHashtags1.add("#Geek");
		userHashtags1.add("#WebTech");
		userHashtags1.add("#News");
		
		userHashtags2.add("#goodtimes");
		userHashtags2.add("#softwaretesting");
		
		HashSet<Tweet> tweets1 = graph.BFS(userHashtags1, Parser.getHashtags());
		HashSet<Tweet> tweets2 = graph.BFS(userHashtags2, Parser.getHashtags());
		
		Tweet[] data1 = graph.sort(tweets1, userHashtags1);
		Tweet[] data2 = graph.sort(tweets2, userHashtags2);
		
		for(String tag : data1[data1.length - 1].getTags())
		{			
			if(tag.equals("#WebTech".toLowerCase()))
			{
				checker1 = true;
			}
		}
				
		for(String tag : data2[0].getTags())
		{
			if(tag.equals("#goodtimes") || tag.equals("#softwaretesting"))
			{
				checker2 = true;
			}
		}
		
		assertTrue(checker1);
		assertTrue(checker2);
	}
}
