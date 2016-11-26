/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classifier;

import java.util.List;
import twitter.MyStatus;
import twitter.TweetGet;

/**
 *
 * @author Ahmad
 */
public class MainClassifier {
    public static void main(String[] args) {
        TweetGet tg = new TweetGet();
        tg.query("jual harga");
        List<MyStatus> ls = tg.getTweet();
        System.out.println(ls.toString());
    }
}
