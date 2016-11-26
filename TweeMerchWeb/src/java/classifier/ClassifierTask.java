/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classifier;

import twitter.MyStatus;

/**
 *
 * @author Ahmad
 */
public class ClassifierTask {
        
    SellerClassifier sc = new SellerClassifier();
        
    public void runClassifier() {
        sc.buildModel("data/training.arff");
    }

    public void classifyTweets(MyStatus[] tweets) {
        for (int i = 0; i < tweets.length; ++i) {
            sc.classifyTweet(tweets[i]);
        }
    }
}
