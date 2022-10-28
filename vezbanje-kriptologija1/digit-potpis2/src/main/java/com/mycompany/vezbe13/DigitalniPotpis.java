/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.vezbe13;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;

/**
 *
 * @author Marko
 */
public class DigitalniPotpis {
    
    
    
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
        KeyPairGenerator kljucevi = KeyPairGenerator.getInstance("RSA");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        kljucevi.initialize(1024, sr);
        
        KeyPair javniPrivatniKljuc = kljucevi.generateKeyPair();
        PrivateKey privatni = javniPrivatniKljuc.getPrivate();
        PublicKey javni = javniPrivatniKljuc.getPublic();
        
        //generisanje digit potpisa
        Signature rsa = Signature.getInstance("SHA1withsRSA");         //ili sha256
        rsa.initSign(privatni);
      
        String poruka = "Neka poruka. Univerzitet Singidunum 2022";
        String poruka2 = "Neka poruka. Univerzitet Singidunum 2022";
        rsa.update(poruka.getBytes());                                  //ucitavamo poruku za koju je kreiran dig potpis
        byte[] digitalniPotpis = rsa.sign();                            //sign vraca niz bajtova i to je digitalni potpis
        System.out.println("Digitalni potpis: " + Hex.toHexString(digitalniPotpis));
        
        //verifikovanje digit potpisa
        rsa.initVerify(javni);

        boolean verifikovan = rsa.verify(digitalniPotpis);
        if(verifikovan){
           System.out.println("Potpis je verifikovan");
        }else{
           System.out.println("Potpis nije verifikovan"); 
        }
        
    }
    //napisati metod za generisanje potpisa, verifikaciju potpisa, generisanje para kljuceva       
    //preuzeti metod iz klase GenParaKljuceva
    //napraviti aplikaciju sa kojom mozemo da digit potpissemo fajlove na racunaru i skladistimo dig potpis
    //funkcije ucitaj i zapisi u fajl mopgu da se kopiraju
    //takodje mogu da se iskoriste funkcije iz rsa za generisanje kljuceva
    
    
    public byte[] generisiDigitPotpis(String algoritam, PrivateKey pk, byte[] poruka) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
        Signature rsa = Signature.getInstance(algoritam);
        rsa.initSign(pk);
        rsa.update(poruka);
        return rsa.sign();
    }
    
    public boolean verifikujDigitPotpis(String algoritam, PublicKey pk, byte[] potpis, byte[] poruka) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
        Signature rsa = Signature.getInstance(algoritam);
        rsa.initVerify(pk);
        rsa.update(poruka);
        return rsa.verify(potpis);
    }
    
    public void generisiKljuceve(int velicinaKljuca) throws NoSuchAlgorithmException{
        KeyPairGenerator kljucevi = KeyPairGenerator.getInstance("RSA");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        kljucevi.initialize(1024, sr);
        
        KeyPair javniPrivatniKljuc = kljucevi.generateKeyPair();
        PrivateKey privatni = javniPrivatniKljuc.getPrivate();
        PublicKey javni = javniPrivatniKljuc.getPublic();
    }            
    
    
    
    
    
    
}
