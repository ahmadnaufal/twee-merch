/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package information_extraction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fauzanrifqy
 */
public class HttpReq {
    
    public final static String USER_AGENT = "Mozilla/5.0";
    
    private StringBuffer response;
    private boolean ready;
    
    public HttpReq(){ ready = false; }
    
    public boolean isReady(){
        return ready;
    }
    
    public String getResponse(){
        if(response!=null){
            return response.toString();
        }else{
            return "";
        }
    }
    
    public void getReq(String url){
        ready = false;
        URL obj;
        try {
            obj = new URL(url);
            
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

        } catch (MalformedURLException ex) {
            Logger.getLogger(HttpReq.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HttpReq.class.getName()).log(Level.SEVERE, null, ex);
        }
        ready = true;
    }
}
