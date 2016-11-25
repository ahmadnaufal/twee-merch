
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
        
        String tweet = "Di jual parfum oriflame giordani man notte ..... Harga 385000 area tembilahan https://t.co/2k4GwNywhu";
        InformationExtraction ie = new InformationExtraction(tweet.toLowerCase());
        ie.ieAll();
        
        InformationSell infoSell = ie.getInformationSell();
        System.out.print(infoSell.getItemName()+"\n");
        System.out.print(infoSell.getPrice().toString()+"\n");
        System.out.print(infoSell.getPhone()+"\n");
        System.out.print(infoSell.getEmail()+"\n");
        
        System.out.println(InformationExtraction.makeUniform(InformationExtraction.UNIFORM_ALL, tweet.toLowerCase()));
    }
}
