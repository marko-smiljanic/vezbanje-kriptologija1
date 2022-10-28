package com.mycompany.aes;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.swing.JFileChooser;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Marko
 */
public class AES {
    
    //metod za generisanje secret key instance za AES algoritam, argument byte[16, 24, 32]
    //ovde se ne generise nista na osnovu korisnickog unosa (lozinke)
    //kljuc moze biti samo 128, 192, 256 velicine
    public static SecretKey generisiKljuc(int keySize){                         //stavimo da je static cisto da ne instanciramo objekat
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(keySize);
            return kg.generateKey();            

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;        
    }
    
    //metod za generisanje IV parametar spec za velicinu bloka [16, 24, 32]
    public static IvParameterSpec generisiIV(int ivSize){                       //secure random je pseudogenerator namenjena za generisanje kriptografskih parametara, on koristi random ali setuje inicijalno stanje sa prvih 100 bitova sha1 (generise 160 ali uzima 100) funkcije za trenutno vreme i na osnovu toga generise slucajne bajtove
        byte[] iv = new byte[ivSize];        
        new SecureRandom().nextBytes(iv);                                       //ne vraca rezultat nego filuje prosledjeni array sa slucajno generisanim bajtovima
        return new IvParameterSpec(iv);
    }
    
    //metod za sifrovanje i desifrovanje, argumenti plaintext/ciphertext, secret key instance i iv (AES,CBC,PKCS5 padding, Kljuc256, Blok128, tj. IV128)
    public static byte[] sifruj(byte[] plainText, SecretKey kljuc, IvParameterSpec iv){
        try {
            byte[] sifrat = null;
//            kljuc = generisiKljuc(256);                                       //ovo treba da radimo izvan funkcije, takva je idelja valjda?
//            iv = generisiIV(128);                        
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, kljuc, iv);
            sifrat = c.doFinal(plainText);
            
            //unistavamo osetljive resurse u RAM-u
            Random r = new Random();
            iv = null;
            r.nextBytes(plainText);
            kljuc = null;

            return sifrat;
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        }    
        return null;
    }
    
    public static byte[] desifruj(byte[] sifrat, SecretKey kljuc, IvParameterSpec iv){
        try {
            byte[] poruka = null;
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, kljuc, iv);
            poruka = c.doFinal(sifrat);
            
            //unistavamo osetljive resurse
            Random r = new Random();
            iv = null;
            r.nextBytes(sifrat);
            kljuc = null;
            
            return poruka;
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;        
    }
    
    public void sacuvajFajl(byte[] podaci, String poruka){
        try {
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle(poruka);
            jfc.showSaveDialog(jfc);
            String adr = jfc.getSelectedFile().getAbsolutePath();
            Files.write(Paths.get(adr), podaci);
        } catch (IOException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public byte[] ucitajFajl(String poruka){
        try {
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle(poruka);
            jfc.showOpenDialog(jfc);
            String adr = jfc.getSelectedFile().getAbsolutePath();
            return Files.readAllBytes(Paths.get(adr));
        } catch (IOException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    //za domaci + GUI
    //glavni metod za testiranje napisanih metoda
    public static void main(String[] args){
        SecretKey k1 = generisiKljuc(128);                                      //ako stavimo neke velicine koje nisu ni jedne od ove 3 koje smo stavili izbacice exeption
        SecretKey k2 = generisiKljuc(192);
        SecretKey k3 = generisiKljuc(256);                                      
        System.out.println("Kljuc 1: " + Arrays.toString(k1.getEncoded()));      
        System.out.println("Kljuc 2: " + Arrays.toString(k2.getEncoded()));
        System.out.println("Kljuc 3: " + Arrays.toString(k3.getEncoded()));
        
        IvParameterSpec iv1 = generisiIV(16);   //128 bitova
        IvParameterSpec iv2 = generisiIV(24);   //192 bita
        IvParameterSpec iv3 = generisiIV(32);   //256 bitova
        System.out.println("IV 1: " + Arrays.toString(iv1.getIV()));            //metod get iv vraca niz bajtova
        System.out.println("IV 2: " + Arrays.toString(iv2.getIV())); 
        System.out.println("IV 3: " + Arrays.toString(iv3.getIV())); 
        ///////////////////
        
        
        String poruka = "Neka poruka koju je potrebno sifrovati, a potom desifrovati.";        
        AES aes = new AES();
        
        byte[] sifrat = aes.sifruj(poruka.getBytes(), k3, iv1);
        System.out.println("Sifrat: " + new String(sifrat));
        
        byte[] desifrovanaPoruka = aes.desifruj(sifrat, k3, iv1);
        System.out.println("Desifrovana poruka: " + new String(desifrovanaPoruka));
        
        
        
        
        
        
    }
}
