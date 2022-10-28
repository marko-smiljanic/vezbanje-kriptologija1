/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vezbe13;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

        
/**
 *
 * @author Marko
 */
public class HMAC_1 {
    
    
    public static void main (String[] args) throws NoSuchAlgorithmException, InvalidKeyException{
        KeyGenerator kg = KeyGenerator.getInstance("HmacSHA1");
        SecretKey kljuc = kg.generateKey();
        
        Mac hmac = Mac.getInstance("HmacSHA1");
        hmac.init(kljuc);
        String poruka = "Neka poruka koja ce nam biti plain text.";
        byte[] hmacVrednost = hmac.doFinal(poruka.getBytes());
        System.out.println("Hmac: " + Hex.toHexString(hmacVrednost));    //Hex klasa ne postoji, sta da stavim umesto ovoga nemam pojma
    
    }
    
    public SecretKey generisiKljucHmac(String algoritam) throws NoSuchAlgorithmException{
        KeyGenerator kg = KeyGenerator.getInstance(algoritam);
        return kg.generateKey();
             
    }
    
    public SecretKey generisiKljucHmac2(String algoritam) throws NoSuchAlgorithmException{  //kljuceve za gui deo treba generisati ovako
        SecureRandom sr = SecureRandom.getInstance("SH1PRNG");
        byte[] slucajneVr = new byte[16];
        sr.nextBytes(slucajneVr);
        SecretKeySpec sks = new SecretKeySpec(slucajneVr, algoritam);
        return sks;
    }
    
    public String hexHmac (byte[] otvorenaPoruka, String algoritam, SecretKey kljuc) throws NoSuchAlgorithmException, InvalidKeyException{
        Mac hmac = Mac.getInstance(algoritam);
        hmac.init(kljuc);
        return Hex.toHexString(hmac.doFinal(otvorenaPoruka));
    }
    

    
    
    
    
    
}
