
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
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
public class RC4 {
    
    public SecretKey generisiKljuc(){
        try { 
            KeyGenerator kg = KeyGenerator.getInstance("ARCFOUR");
            SecretKey kljuc = kg.generateKey();
            return kljuc;
                       
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RC4.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void izveziKljuc(SecretKey kljuc){
        try {
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("Eksportovanje kljuca");            
            jfc.showSaveDialog(jfc);
            Files.write(Paths.get(jfc.getSelectedFile().getAbsolutePath()), kljuc.getEncoded());
                        
        } catch (IOException ex) {
            Logger.getLogger(RC4.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public SecretKey uveziKljuc(){
        try {
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("Importovanje kljuca");
            jfc.showOpenDialog(jfc);
            byte[] kljuc = Files.readAllBytes(Paths.get(jfc.getSelectedFile().getAbsolutePath()));
            SecretKeySpec sks = new SecretKeySpec(kljuc, "ARCFOUR");
            return sks;
                        
        } catch (IOException ex) {
            Logger.getLogger(RC4.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;               
    }
    
    public byte[] sifruj(byte[] poruka, SecretKey kljuc){
        try {
            Cipher c = Cipher.getInstance("ARCFOUR");
            c.init(Cipher.ENCRYPT_MODE, kljuc);
            return c.doFinal(poruka);
                        
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RC4.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(RC4.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(RC4.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(RC4.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(RC4.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;        
    }
    
    public byte[] desifruj(byte[] sifrat, SecretKey kljuc){
        try {
            Cipher c = Cipher.getInstance("ARCFOUR");
            c.init(Cipher.DECRYPT_MODE, kljuc);
            return c.doFinal(sifrat);
              
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RC4.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(RC4.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(RC4.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(RC4.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(RC4.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;        
    }
    
    //fumkcija racuna entropiju ako prosecnu kolicinu inf koja je sadrzana u jednom simbolu na osnovu ascii2 tabele
    //vraca vrednosti od min 0 do max 8 u zavisnosti od entropije
    public float entropija(byte[] podaci){
        int brojac[] = new int[256];
        float entropija = 0;
        float suma = podaci.length;
        for(byte b : podaci){
            brojac [b + 128]++;            
        }
        for(int c: brojac){
            if(c == 0){          //zanemarujemo simbole cija je verovatnoca == 0
                continue;
            }
            float p = c / suma;
            entropija -= p * Math.log(p) / Math.log(2);
        }
        return entropija;
    }
    
    
    ////////////////////
    public static void main(String[] args){        
//        try {
//            KeyGenerator kg = KeyGenerator.getInstance("ARCFOUR");                //ovim govorimo koji je algoritam u pitanju
//            SecretKey kljuc = kg.generateKey();
//            System.out.println(kljuc.getEncoded().length);                        //getencoded konvertuje u niz bajtova
//            System.out.println(Arrays.toString(kljuc.getEncoded()));
//                        
//        } catch (NoSuchAlgorithmException ex) {
//            Logger.getLogger(RC4.class.getName()).log(Level.SEVERE, null, ex);
//        }   

        //otp je perfektna sifra, rc4 je pseudoperfektna sifra

        String poruka1 = "Neka poruka koju je potrebno sifrovati. Kriptologija 1, RC4 sifra.";
        //String poruka2 = "aaaa aaaaaa aaaa je potrebno sifrovati. Kriptologija z, aaa aaaaa.";   //bitno je samo da ima isti broj bajtova 
        String poruka2 = "Neka poruka koju je potrebno sifrovati. Kriptologija 1, RC4 sifra.";  //ovo je potrebno zbog entropije, da ne bi prijavljivalo gresku ako poruke nisu iste, za prosli primer su mi trebale razlicite poruke da bi smo prikazali kako se druga poruka moze dobiti na osnovu prve poruke, sifrata1, sifata2
        RC4 rc4 = new RC4(); 
        SecretKey kljuc = rc4.generisiKljuc();                                   //unutrasnji kljuc, seed
        System.out.println("Kljuc je: " + Arrays.toString(kljuc.getEncoded()));
        
        //Sifrovanje    
        byte[] sifrat1 = rc4.sifruj(poruka1.getBytes(), kljuc);        //c1
        byte[] sifrat2 = rc4.sifruj(poruka2.getBytes(), kljuc);        //c2
        System.out.println("Sifrat je: " + new String(sifrat1));                  //prikazace samo one bajtove koje moze da prikaze preko ascii tabele
        
        
        //Desifrovanje
        byte[] desifrovanaPoruka = rc4.desifruj(sifrat1, kljuc);
        System.out.println("Desifrovana poruka je: " + new String(desifrovanaPoruka)); 
        
        
        //prikazati bajtove radnog kljuca za sifrovanu poruku
        String enkodiraniKljuc = Base64.getEncoder().encodeToString(kljuc.getEncoded());  //moze da se sabere svaki bajt sifrata i poruke mozemo dobiti kljuc
        System.out.println("Radni kljuc je: " + enkodiraniKljuc);
        
        //poruka p1 + poruka p2 == sifrat c1 + sifrat c2   // + je u stvari ^!!!
        //dokazati da je p2 = c1 + c2 + p1        
        byte[] c1c2 = new byte[sifrat1.length];
        for(int i = 0; i < c1c2.length; i++){
            c1c2[i] = (byte) (sifrat1[i] ^ sifrat2[i]);
        }
        
        byte[] c1c2p1 = new byte[sifrat1.length];
        for(int i = 0; i < c1c2.length; i++){
            c1c2p1[i] = (byte) (c1c2[i] ^ poruka1.getBytes()[i]);
        }        
        System.out.println("Izracunata poruka 2 kada je koristen isti kljuc za sifrovanje obe poruke: " + new String(c1c2p1));        
        //zbog ovoga se ne treba koristiti samo jedan kljuc nego umesto dofinal da se pozove update
        //kada imamo jedan kljuc koji smo setovali mozemo da na dnevnom nivou npr. u 8 sati ujutru i u toku sledecih 24h pozivamo metod update
        
        //RC4 algoritam je slican kao OTP, ali kod otp kljuc koji generisemo ne dobijamo preko generatora kom smo
        //RC4 je generator kome prosledjumemo unutrasnji kljuc i dobijamo radni kljuc
        //kompresija podize nivo bezbednosti, i primenom komprersije takodje mozemo ustedeti na duzini kljuca
        
        //entropija je mera neuredjenosti nekog sistema
        //da bi ovo bilo dosta realnije treba proslediti neki veci tekst
        System.out.println("Entropija (H) poruke1: " + rc4.entropija(poruka1.getBytes()));
        System.out.println("Entropija (H) kljuca: " + rc4.entropija(kljuc.getEncoded()));
        System.out.println("Entropija (H) sifrata: " + rc4.entropija(sifrat1));
        
        byte[] test = new byte[1000];
        new Random().nextBytes(test);
        System.out.println("Entropija (H) slucajno ozabranih 1000 bajtova: " + rc4.entropija(test));     //ovaj generator ima dobra statisticka (prosao bi sve statisticke testove) svojstva ali ne i kriptoloska   
        byte[] c3 = rc4.sifruj(test, kljuc);
        System.out.println("Entropija (H) sifrovanog test niza: " + rc4.entropija(c3));
        
        
        
        
        
    }
    
    

    
}