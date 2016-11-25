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
    public final static String REGEX_PHONE = "(08|\\+?628|02\\d)(\\d{9,11})";
    public final static String REGEX_MAIL = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
    public final static String REGEX_LINK = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)";
    
    private InformationSell infoSell;
    private String tweet;
    
    public InformationExtraction(String tweet){
        this.infoSell = new InformationSell();
        this.tweet = tweet;
    }
    
    public InformationSell getInformationSell(){
        return infoSell;
    }
    
    public void ieAll(){
        iePrice();
        iePhone();
        ieMail();
        ieLink();
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
    
    public void iePhone(){
        // Goal: Extract nomor ponsel pada tweet -> infoSell
        String extractPhone;
        Pattern pattern = Pattern.compile(REGEX_PHONE);
        Matcher matcher = pattern.matcher(tweet);
        
        if(matcher.find()){
            extractPhone = matcher.group(0);
        }else{
            extractPhone = "-";
        }
        
        infoSell.setPhone(extractPhone);
    }
    
    public void ieMail(){
        String extractMail;
        Pattern pattern = Pattern.compile(REGEX_MAIL);
        Matcher matcher = pattern.matcher(tweet);
        
        if(matcher.find()){
            extractMail = matcher.group(0);
        }else{
            extractMail = "-";
        }
        
        infoSell.setEmail(extractMail);
    }
    
    public void ieLink(){
        String extractLink;
        Pattern pattern = Pattern.compile(REGEX_LINK);
        Matcher matcher = pattern.matcher(tweet);
        
        if(matcher.find()){
            extractLink = matcher.group(0);
        }else{
            extractLink = "-";
        }
        
        infoSell.setEmail(extractLink);
    }
}
