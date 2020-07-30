package com.example.tagwell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class forms the graph using a HashMap with keys represented by hashtag strings and values by ArrayLists of 
 * outgoing edges pointing to other hashtags. In this configuration, the hashtags are the nodes and the tweets are the edges
 * of the graph. The edges connect the nodes based on if a tweet contains the hashtags.
 * @author Christopher Andrade, Matthew Paulin
 */
public class Graph
{
	private HashMap<String, ArrayList<Edge>> graph;

	/**
	 * This constructor forms a Graph object by accepting a lists of possible tweets,
	 * forming appropriate nodes with the hashtags contained within these tweets, and forming
	 * edges based on the criterion discussed above.
	 * @param tweets An ArrayList of all possible Tweet objects.
	 */
	public Graph(ArrayList<Tweet> tweets)
	{
        this.graph = new HashMap<String, ArrayList<Edge>>();
        
		for(Tweet tweet: tweets)
		{	
			for(String tag1 : tweet.getTags())
			{
				if(graph.get(tag1) == null)
				{
					graph.put(tag1, new ArrayList<Edge>());
				}
				
				for(String tag2 : tweet.getTags())
				{
					graph.get(tag1).add(new Edge(tweet, tag2));
				}
			}
		}
	}
	
	/**
	 * This method performs a modified BFS by doing BFS on all the hashtags (nodes) that are contained
	 * in the user's input tweet. Tweets (edges) are then added to a HashSet if they share one or more
	 * hashtags with the user's input. All other aspects of this BFS implementation are similar
	 * to the implementation discussed in lab.
	 * @param userHashtags The user's inputed hashtags.
	 * @param allHashtags All the possible hashtags from the data file.
	 * @return Returns a HashSet of all the tweets that contain at least one of the user hashtags.
	 */
	public HashSet<Tweet> BFS(ArrayList<String> userHashtags, HashSet<String> allHashtags)
	{
		HashSet<Tweet> result = new HashSet<Tweet>();
		
		for(int i = 0; i < userHashtags.size(); i++)
		{
			userHashtags.set(i, userHashtags.get(i).toLowerCase());
		}
		
		for(String tag : userHashtags)
		{
			HashMap<String, Boolean> visitedMap = new HashMap<String, Boolean>();
			
			for(String hashtag : allHashtags)
			{
			    visitedMap.put(hashtag, false);
			}
			
			Queue<String> queue = new LinkedList<String>();
			queue.add(tag);
			visitedMap.put(tag, true);
			
			while(queue.size() != 0)
			{
				String currentHashtag = queue.poll();
				
				for(Edge edge : graph.get(currentHashtag))
				{
					if(!(visitedMap.get(edge.getHashtag())))
					{
						visitedMap.put(edge.getHashtag(), true);
						queue.add(edge.getHashtag());
					}
					
					if(userHashtags.contains(edge.getHashtag()))
					{
						result.add(edge.getTweet());
					}
				}
			}
		}
		
		return result;
	}
	/**
	 * This method sorts the tweets outputted by BFS first by how positive the tweet's sentiment, then by how many hashtags 
	 * they share with the user's input.
	 * The first sort is done in a manner similar to QuickSort's partitioning stage. Then MergeSort is completed to achieve the second sort.
	 * @param tweets A HashSet of tweets that were outputted by BFS.
	 * @param userHashtags An ArrayList of hashtags that were in the user's tweet.
	 * @return A sorted array of Tweet objects to be displayed by the Android application.
	 */
	public Tweet[] sort(HashSet<Tweet> tweets, ArrayList<String> userHashtags)
	{
		Tweet[] array = new Tweet[tweets.size()];
		int i = 0;
		
		for(Tweet tweet: tweets)
		{
			array[i++] = tweet; 
		}
		
		if(array.length == 0)
		{
			return array;
		}
		
		int left = 0, right = array.length - 1;
		/**
		 * Sort by tweet sentiment by swapping tweets with opposite sentiments that are located in
		 * incorrect positions in the array. 
		 */
		while(left <= right)
		{
			while(left < array.length && array[left].getSentiment() != 4)
			{
				left++;
			}
			
			while(right >= 0 && array[right].getSentiment() != 0)
			{
				right--;
			}
			
			if(left >= array.length || right < 0)
			{
				break;
			}
			
			Tweet temp = array[left];
			array[left] = array[right];
			array[right] = temp;
		}
		
		Merge(array, 0, array.length - 1, userHashtags);//call merge sort 
		
		return array;
	}
	/**
	 * This is a helper method to perform recursive MergeSort on a collection of Tweets to sort
	 * by how many hashtags they share with the user's inputted tweet.
	 * @param arr The array of Tweet objects to be sorted.
	 * @param l An integer value representing low index.
	 * @param r An integer value representing high index.
	 * @param userHashtags An Arraylist of the user inputted hashtags.
	 */
	private static void Merge(Tweet arr[], int l, int r, ArrayList<String> userHashtags) 
    { 
        if (l < r) 
        { 
            int m = (l + r) / 2; 

            Merge(arr, l, m, userHashtags); 
            Merge(arr , m + 1, r, userHashtags); 

            merge(arr, l, m, r, userHashtags); 
        } 
    } 
	/**
	 * This is a helper method for Merge that combines two sorted subarrays while remaining sorted.
	 * @param arr The array of Tweet objects to be sorted.
	 * @param l An integer value representing low index.
	 * @param m An integer value representing middle index.
	 * @param r An integer value representing high index.
	 * @param userHashtags An Arraylist of the user inputted hashtags.
	 */
    private static void merge(Tweet arr[], int l, int m, int r, ArrayList<String> userHashtags)
    { 
        int nl = m - l + 1; 
        int nr = r - m; 

        Tweet L[] = new Tweet [nl]; 
        Tweet R[] = new Tweet [nr]; 
  
        for(int i = 0; i < nl; i++) 
        {
            L[i] = arr[l + i];
        }

        for(int j = 0; j < nr; j++)
        {
            R[j] = arr[m + 1 + j];
        }
  
        int i = 0, j = 0, k = l;
  
        while(i < nl && j < nr) 
        { 
            if(L[i].compareTo(userHashtags, R[j]) <= 0) 
            { 
                arr[k++] = L[i++]; 
            } 
            else
            { 
                arr[k++] = R[j++]; 
            } 
        } 
  
        while(i < nl) 
        { 
            arr[k++] = L[i++]; 
        } 
  
        while(j < nr) 
        { 
            arr[k++] = R[j++]; 
        } 
    }
}