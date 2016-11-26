
import information_extraction.InformationExtraction;
import model.InformationSell;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author fauzanrifqy
 */
public class Main {
    
    public static void main(String[] args) {
        
        String tweet = "Jual Iphone 5 16gb ios 10 https://t.co/LAaV1UuZpM";
        InformationExtraction ie = new InformationExtraction(tweet);
        ie.ieAll();
        ie.crawlLink();
        
        InformationSell infoSell = ie.getInformationSell();
        System.out.print(infoSell.getItemName()+"\n");
        System.out.print(infoSell.getPrice().toString()+"\n");
        System.out.print(infoSell.getPhone()+"\n");
        System.out.print(infoSell.getEmail()+"\n");
        System.out.print(infoSell.getImageLink()+"\n");
        
        System.out.println(InformationExtraction.makeUniform(InformationExtraction.UNIFORM_ALL, tweet.toLowerCase()));
    }
}
