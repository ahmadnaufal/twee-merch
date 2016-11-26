/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twitter;

import java.util.List;

/**
 *
 * @author Husni Munaya
 */
public class TweetTest {
    public static void main(String[] args) {
        TweetGet tweet = new TweetGet();
        tweet.getTweetFromUser("tweemerch");
        List<MyStatus> stats = tweet.getTweet();
        for (MyStatus status : stats) {
            System.out.println(status.getTweet());
        }
    }
}
