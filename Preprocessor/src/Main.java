
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
        
        String tweet = "Di jual macbook pro retina harga 125rb sudah ongkir via JNE, fauzan@gmail.com kalau mau COD di Lbk.Bulus , Ciputat, Depok (Nama dan Nomor punggung cp : 081314406127)";
        InformationExtraction ie = new InformationExtraction(tweet);
        ie.ieAll();
        
        InformationSell infoSell = ie.getInformationSell();
        System.out.print(infoSell.getItemName()+"\n");
        System.out.print(infoSell.getPrice().toString()+"\n");
        System.out.print(infoSell.getPhone()+"\n");
        System.out.print(infoSell.getEmail()+"\n");
        
        System.out.println(InformationExtraction.makeUniform(InformationExtraction.UNIFORM_ALL, tweet));
    }
}
