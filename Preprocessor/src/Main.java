
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
        
        InformationExtraction ie = new InformationExtraction("Jual macbook idr 11.5jt nego");
        ie.iePrice();
        
        InformationSell infoSell = ie.getInformationSell();
        System.out.print(infoSell.getPrice().toString()+"\n");
    }
}
