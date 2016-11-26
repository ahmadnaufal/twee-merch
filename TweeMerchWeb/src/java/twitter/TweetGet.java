/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twitter;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author Husni Munaya
 */
public class TweetGet {
    private List<Status> tweets;
    
    public void query(String queryString) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("2MZnm7LM7Ik9W3hzcXJOBaNER")
                .setOAuthConsumerSecret("0GER2kH3o1gOAyfPEDd870Iiuiu6XbQDayAXWfUBxU5APg4Le6")
                .setOAuthAccessToken("18972247-HgBP0djVaLw4U9fSX4lUdhKpZcqsJIAgnUCW3DRS5")
                .setOAuthAccessTokenSecret("bn8EQoOyGSRVUNX6elTmX9Wt9jiFJaxDaCZDk3U3hQB0g");

        Twitter twitter = new TwitterFactory(cb.build()).getInstance();
        Query query = new Query(queryString);
        query.setCount(20);
        QueryResult result;
        try {
//            do {
                result = twitter.search(query);
                tweets = result.getTweets();
//            } while ((query = result.nextQuery()) != null);
            
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
        }
    }
    
    public List<Status> getTweet() {
        return tweets;
    }
}
