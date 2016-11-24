/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 *
 * @author fauzanrifqy
 */
public class InformationSell {
    private String sellerName;  // Important!
    private String itemName;    // Important!
    private Double price;
    private String phone;
    private String email;
    private String link;
    private Map contacts;
    
    public InformationSell(){}
    
    public InformationSell(String sellerName, String itemName){
        this.sellerName = sellerName;
        this.itemName = itemName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Map getContacts() {
        return contacts;
    }

    public void setContacts(Map contacts) {
        this.contacts = contacts;
    }

    public BufferedImage getPicture() {
        return picture;
    }

    public void setPicture(BufferedImage picture) {
        this.picture = picture;
    }
    private BufferedImage picture;
    
    
}
