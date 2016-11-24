
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
        
        InformationExtraction ie = new InformationExtraction("Di Jual jersey seharga 125rb sudah ongkir via JNE, kalau mau COD di Lbk.Bulus , Ciputat, Depok (Nama dan Nomor punggung cp : 081314406127)");
        ie.iePrice();
        ie.iePhone();
        
        InformationSell infoSell = ie.getInformationSell();
        System.out.print(infoSell.getPrice().toString()+"\n");
        System.out.print(infoSell.getPhone()+"\n");
    }
}
