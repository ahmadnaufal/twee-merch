/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package information_extraction;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.InformationSell;

/**
 *
 * @author fauzanrifqy
 */
public class InformationExtraction {
    
    public final static String REGEX_PRICE = "(rp|idr|harga)(\\.|,| )*(\\d)(\\d+|\\.|,)*( ?ribu| ?ratus| ?juta| ?k| ?rb| ?jt)*";
    public final static String REGEX_NOMINAL = "[\\d|\\.]+";
    
    private InformationSell infoSell;
    private String tweet;
    
    public InformationExtraction(String tweet){
        this.infoSell = new InformationSell();
        this.tweet = tweet;
    }
    
    public InformationSell getInformationSell(){
        return infoSell;
    }
    
    public void iePrice(){
        // Goal: Extract harga pada tweet -> infoSell
        String extractHarga;
        Pattern pattern = Pattern.compile(REGEX_PRICE);
        Matcher matcher = pattern.matcher(tweet);
        
        if(matcher.find()){
            extractHarga = matcher.group(0);
        }else{
            extractHarga = "0";
        }
        
        System.out.println(extractHarga);
        
        Double harga = extractPrice(extractHarga, matcher.group(5));
        infoSell.setPrice(harga);
    }
    
    protected Double extractPrice(String harga, String endPoint){
        // Goal: Extract hasil REGEX_PRICE ke dalam nilai nominal
        Double temp = 0.0;
        Pattern pattern = Pattern.compile(REGEX_NOMINAL);
        Matcher matcher = pattern.matcher(harga);
        
        if(matcher.find()){
            temp = Double.parseDouble(matcher.group(0));
        }
        
        if(endPoint.contains("ratus")){
            temp = temp*100;
        }
        if(endPoint.contains("juta")||endPoint.contains("jt")){
            temp = temp*1000000;
        }else if(endPoint.contains("ribu")||endPoint.contains("rb")||endPoint.contains("k")){
            temp = temp*1000;
        }
        
        return temp;
    }
}
