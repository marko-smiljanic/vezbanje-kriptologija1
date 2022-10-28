
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import javax.crypto.spec.SecretKeySpec;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Marko
 */
public class DES {
    
    public byte[] encryptDES(byte[] poruka, byte[] kljuc){                       
        try {
            Random r = new Random();                                                //sluzi za unistavanje varijabli
            //na osnovu niza byte[] kljuc cemo generisati SecretKey
            byte[] sifrat = null;
            MessageDigest md = MessageDigest.getInstance("SHA-256");                //handlujemo prosledjenu vrednost za kljuc, 
            //Key k = new SecretKeySpec(kljuc, "DES");        
            Key k = new SecretKeySpec(Arrays.copyOf(md.digest(kljuc), 8), "DES");   //sad korisnik bukvalno moze da kaze da je njegova lozinka jedan karakter, tj. jedan bajt, sto je naravno suludo, md nam za bilo sta nam vraca 32 bajta, a onda mi uzimamo prvih 8 bajtova
            md.reset();                                                             //preventivno resetujemo, da ne bi zadrzala neka prethodna stanja jer je pozivamo i drugi put
            //iv - inicijalni vektor
            //MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] iv = md.digest(k.getEncoded());                                  //vratice 32 bajta, tj. 256 bitova
            IvParameterSpec IV = new IvParameterSpec(Arrays.copyOf(iv, 8));         //kada pravimo vektor uzimamo prvih 8 bajtova
            Cipher c = Cipher.getInstance("DES/CBC/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, k, IV);
            sifrat = c.doFinal(poruka);
            
            //unistavamo sve vrednosti (promenljive) koje smo imali, tj. da to bude resetovano
            //neko cak ako pronadje ove parametre u memoriji (sto ni malo nije lako) nece moci tek tako da ih upotrebi
            //inicijalni vektor bi mogao daosta pametnije da se napise ali bi bilo nepregledno
            r.nextBytes(iv);
            r.nextBytes(poruka);
            r.nextBytes(kljuc);
            k = null;
            
            return sifrat;            

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public byte[] decryptDES(byte[] sifrat, byte[] kljuc){                       
        try {
            Random r = new Random(); 
            byte[] poruka = null;
            MessageDigest md = MessageDigest.getInstance("SHA-256");                 
            Key k = new SecretKeySpec(Arrays.copyOf(md.digest(kljuc), 8), "DES");  
            md.reset();                                                             

            byte[] iv = md.digest(k.getEncoded());                                  
            IvParameterSpec IV = new IvParameterSpec(Arrays.copyOf(iv, 8));         
            Cipher c = Cipher.getInstance("DES/CBC/PKCS5Padding");

            c.init(Cipher.DECRYPT_MODE, k, IV);
            poruka = c.doFinal(sifrat);
            
            r.nextBytes(iv);
            r.nextBytes(sifrat);
            r.nextBytes(kljuc);
            k = null;
            
            return poruka;            

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    
    public static void main(String[] args){
        
//        try {
//            KeyGenerator kg = KeyGenerator.getInstance("DES");
//            SecretKey kljuc = kg.generateKey();
//            System.out.println("Kljuc: " + Arrays.toString(kljuc.getEncoded()));
//            System.out.println(kljuc.getAlgorithm());
//            
//            //Sifrovanje, rezim CBC, padding PKCS35            
//            String poruka = "DES moderni blokovski algoritam za sifrovanje";
//            Cipher c = Cipher.getInstance("DES/CBC/PKCS5Padding");              //ako se ne navede cbc, onda se koristi ecb
//            c.init(Cipher.ENCRYPT_MODE, kljuc);
//            byte[] sifrat = c.doFinal(poruka.getBytes());
//            System.out.println("Sifrovana poruka: " + new String(sifrat));
//            System.out.println("Inicijalni vektor (IV): " + c.getIV());         //ovo je inicijalni vektor koji je automatski generisan kada smo naveli cbc rezim, iv vrednos obavezno mora da se sacuva
//            
//            //desifrovanje
//            c.init(Cipher.DECRYPT_MODE, kljuc, c.getParameters());              //c.getParameters() moramo da pisemo kao treci argument funkcije jer je to inivijalni vektor desifrovanje ce uspeti na ovaj nacin jer je objekat c i dalje ziv, da nemamo c onda nikad ne bi mogli da desifrujemo jer smo izgubili inicijalni vektor
//            byte[] desifrovana_poruka = c.doFinal(sifrat);
//            System.out.println("Desifrovana poruka: " + new String(desifrovana_poruka));
//
//        } catch (NoSuchAlgorithmException ex) {
//            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NoSuchPaddingException ex) {
//            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InvalidKeyException ex) {
//            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IllegalBlockSizeException ex) {
//            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (BadPaddingException ex) {
//            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InvalidAlgorithmParameterException ex) {
//            Logger.getLogger(DES.class.getName()).log(Level.SEVERE, null, ex);
//        }


    }
}
