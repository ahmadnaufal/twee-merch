/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preprocessor;

/**
 *
 * @author husni
 */
public class MyInstance {
    private String tweet;
    private int classNum;
    
    public MyInstance(String tweet, int classNum) {
        this.tweet = tweet;
        this.classNum = classNum;
    }
    
    public String getTweet() {
        return tweet;
    }
    
    public void setTweet(String tweet) {
        this.tweet = tweet;
    }
    
    public int getClassNum() {
        return classNum;
    }
}
