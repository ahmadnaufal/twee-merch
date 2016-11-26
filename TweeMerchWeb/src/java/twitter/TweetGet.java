/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twitter;

import classifier.SellerClassifier;
import java.io.File;
import java.util.ArrayList;
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
            result = twitter.search(query);
            tweets = result.getTweets();
            
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
        }
    }
    
    public void getTweetFromUser(String username) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("2MZnm7LM7Ik9W3hzcXJOBaNER")
                .setOAuthConsumerSecret("0GER2kH3o1gOAyfPEDd870Iiuiu6XbQDayAXWfUBxU5APg4Le6")
                .setOAuthAccessToken("18972247-HgBP0djVaLw4U9fSX4lUdhKpZcqsJIAgnUCW3DRS5")
                .setOAuthAccessTokenSecret("bn8EQoOyGSRVUNX6elTmX9Wt9jiFJaxDaCZDk3U3hQB0g");

        Twitter twitter = new TwitterFactory(cb.build()).getInstance();
        
        try {
            tweets = twitter.getUserTimeline(username);
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
        }
    }
    
    public List<MyStatus> getTweet() {
        List<MyStatus> stats = new ArrayList<>();
        SellerClassifier sc = new SellerClassifier();
        try {
            File file =  new File(".");
            System.out.println("PATH: " + file.getAbsolutePath());
            sc.initModel("data\\training.arff");
        } catch (Exception ex) {
            Logger.getLogger(TweetGet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (Status tweet : tweets) {
            MyStatus myStatus = new MyStatus();
            myStatus.setTweet(tweet.getText());
            myStatus.setStatusId(tweet.getId());
            myStatus.setUsername(tweet.getUser().getName());
            myStatus.setUserScreenName(tweet.getUser().getScreenName());
            myStatus.setImageUrl(tweet.getUser().getProfileImageURLHttps());
            
            int classRes = (int) sc.classifyTweet(myStatus);
            
            if (classRes == 1) {
                myStatus.setClassification(classRes);
                stats.add(myStatus);
            }
        }
        
        return stats;
    }
}
