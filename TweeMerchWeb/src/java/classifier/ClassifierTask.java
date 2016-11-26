/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classifier;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter.MyStatus;
import twitter.TweetGet;

/**
 *
 * @author Ahmad
 */
public class ClassifierTask {
        
    public static void main(String[] args) {
        SellerClassifier sc = new SellerClassifier();
        
        try {
            sc.buildModel("data/training.arff");
//            sc.loadModel("model/seller_classify_model.model");
        } catch (Exception ex) {
            Logger.getLogger(ClassifierTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        TweetGet tg = new TweetGet();
        tg.query("jual");
        List<MyStatus> tweets = tg.getTweet();
        
        for (int i = 0; i < tweets.size(); ++i) {
            System.out.println(sc.classifyTweet(tweets.get(i)));
        }
    }
        
    public void runClassifier() {
    }

    public void classifyTweets(MyStatus[] tweets) {
    }
}
