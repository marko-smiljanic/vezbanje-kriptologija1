/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.rsa;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Marko
 */
public class RSA {                                                              //koristi se najcesce za autentifikaciju za npr. logovanje u neku aplikaciju
    //nakon sifrovanja se treba cuvati taj par kljuceva.
    //Jedan nacin je da napravimo klasu koja ce imati metod za serijalizaciju instancu KeyPair, i onda koristiti njegova dva stanja private and public key
    //Drugi nacin je da pozovemo getEncoded i da u vidu bajtova sacuvamo u neku bazu ili fajl.
    //Procedura bi bila: X509 klasa je klasa cija je instanca nama potrebna. Prosledimo joj niz bajtova, pozoveko klasu KeyFactory gde prosledjujemo taj X509
    //KeyFactory nam mozda i ne treba, dovoljno je samo da posle toga ubacimo klasu public key i prosledimo x509 instancu.
    //Kada hocemo da regenerisemo privatni kljuc na onnovu niza bajtova tada cemo koristiti klasu koja pocinje sa PKCS8 encodedkeyspec. i koristicemo PricateKey da bi smo generisali kljuc na osnovu instance PKCS8
    public static KeyPair parkljuceva(int velicinaKljuca){                       
        try {
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(velicinaKljuca);
            KeyPair kp = kpg.genKeyPair();
            
            return kp;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;        
    }
    
    public static PublicKey uveziJavniKljuc(byte[] niz){
        try {
            X509EncodedKeySpec x509 = new X509EncodedKeySpec(niz);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey pk = kf.generatePublic(x509);
            return pk;
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;        
    }
    
    //Uvezi i izvezi nam trebaju jer trebamo sacuvati kljuceve, zbog toga kad se sacuvaju treba da se uvezu i da se kreira instanca public ili private
    public static PrivateKey uveziPrivatniKljuc(byte[] niz){
        try {
            PKCS8EncodedKeySpec pkcs8 = new PKCS8EncodedKeySpec(niz);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PrivateKey pk = kf.generatePrivate(pkcs8);
            return pk;
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;        
    }
    
    //keypair . getpublic i dobijamo instancu PublicKey
    public static byte[] sifruj(byte[] poruka, PublicKey pk){    
        try {
            Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c.init(Cipher.ENCRYPT_MODE, pk);
            return c.doFinal(poruka);
        
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static byte[] desifruj(byte[] sifrat, PrivateKey pk){    
        try {
            Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            c.init(Cipher.DECRYPT_MODE, pk);
            return c.doFinal(sifrat);
        
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    
    public static void main(String[] args){
        KeyPair asimetricniKljuc = parkljuceva(2048);    //moze i 4096                         
        System.out.println("Javni kljuc: " + asimetricniKljuc.getPublic().getFormat());
        System.out.println("Javni kljuc: " + Arrays.toString(asimetricniKljuc.getPublic().getEncoded()));
        System.out.println("Privatni kljuc: " + asimetricniKljuc.getPrivate().getFormat());
        System.out.println("Privatni kljuc: " + Arrays.toString(asimetricniKljuc.getPrivate().getEncoded()));
        
        String poruka = "Neki string koji ce se sifrovati, a potom desifrovati.";
        byte[] sifrat = RSA.sifruj(poruka.getBytes(), asimetricniKljuc.getPublic());
        System.out.println("Sifrovana poruka: " + new String(sifrat));
        
        byte[] desifrovanaPoruka = RSA.desifruj(sifrat, asimetricniKljuc.getPrivate());
        System.out.println("Poruka: " + new String(desifrovanaPoruka));
        
        
        
    }
}
