/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.digitalnipotpis;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marko
 */
public class DigitalniPotpis {
    
    
    
    public static void main(String[] args){
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            //sr.setSeed(System.currentTimeNillis());                         //ovim kontrolisemo i random generator
            kpg.initialize(1024, sr);                                         //ako stavimo samo 1024 tada smo prepustili kpg da koristi sopstveni random generator, ali cisto da vidimo da mozemo proslediti svoj random generator na osnovu koga ce se generisati, ili u ovom slucaju da se inicijalno stanje generatora setuje na osnovu trenutnog vremena
            
            KeyPair kp = kpg.genKeyPair();
            PublicKey publicK = kp.getPublic();
            PrivateKey privateK = kp.getPrivate();
            
            Signature rsaSign = Signature.getInstance("SHA256withRSA");      //klasa za digitalno potpisivanje
            rsaSign.initSign(privateK);                                     //pravimo potpis sa privatnim kljucem
            
            String poruka = "Sledece nedelje radimo drugi kolokvijum. Elektronski test preko mtutor-a.";
            rsaSign.update(poruka.getBytes());
            byte[] digitalSignature = rsaSign.sign();
            
            System.out.println("Digitalni potpis: " + Arrays.toString(digitalSignature));            
            
            String poruka2 = "Sledece nedelje radimo drugi kolokvijum. Elektronski testt preko mtutor-a."; //izmenimo originalnu poruke u "testt" da vidimo kako izgleda kada digitalni potpis ne prodje
            Signature rsaVSign = Signature.getInstance("SHA256withRSA");
            rsaVSign.initVerify(publicK);                                       //verify sing
            rsaVSign.update(poruka2.getBytes());                                //ovde prosledjujemo prvo poruka, tad ce biti verifikovan, a ako prosledimo poruku 2 koja predstavlja izmenjenu originalnu poruku potpis nece biti verifikovan
            boolean stat = rsaVSign.verify(digitalSignature);
            if(stat){
                System.out.println("Potpis je verifikovan.");
            }else{
                System.out.println("Potpis nije verifikovan.");
            }
                        
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DigitalniPotpis.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(DigitalniPotpis.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(DigitalniPotpis.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    //sopstvena pp za gen sertifikata para kljuceva gde cemo cuvati u fajlovima i app koju cemo koristiti da dig potpis za razmenu fajlova na racunaru
}
