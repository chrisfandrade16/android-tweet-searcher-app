package com.example.tagwell;

/**
 * Represents an edge in the graph of tweets and hashtags. 
 * An edge consists of a tweet containing hashtags and
 * a node that represents on of the tweet's hashtags.
 * @author Christopher Andrade, Matthew Paulin
 */
public class Edge
{
	private Tweet tweet;
	private String hashtag;
	/**
	 * Creates a new Edge object with the given tweet and hashtag.
	 * @param tweet A Tweet object representing a tweet connecting one or more hashtags (nodes) together.
	 * @param hashtag A string representing a node in the graph and one of the hashtags contained within the tweet.
	 */
	public Edge(Tweet tweet, String hashtag)
	{
		this.tweet = tweet;
		this.hashtag = hashtag;
	}
	/**
	 * Getter for the tweet contained within the edge. 
	 * @return A Tweet object.
	 */
	public Tweet getTweet()
	{
		return tweet;
	}
	/**
	 * Getter for the hashtag contained within the edge.
	 * @return A string representation of a hashtag.
	 */
	public String getHashtag()
	{
		return hashtag;
	}
}
