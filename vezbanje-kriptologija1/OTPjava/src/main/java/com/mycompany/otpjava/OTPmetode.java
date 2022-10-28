/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.otpjava;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author Marko
 */
public class OTPmetode {
    
        public byte[] generisiKljuc(int duzinaKljuca){
        Random sb = new Random();                                               //posle cemo koristiti secure random koja je bas napravljena za ove stvari
        sb.setSeed(System.currentTimeMillis());                                 //ovo radi da bi niz generisan bio isti na svakom racunaru za dati seed, ovaj random generator ima periodu 2 ^ 80, tj. toliko brojeva ce generisati pre ponavljanja redosleda generisanja
        byte[] kljuc = new byte[duzinaKljuca];
        sb.nextBytes(kljuc);                                                    //generise slucajne vrednosti i filuje niz sa slucajnim vrednostima
        return kljuc;
    }
    
    public byte[] generisiKljuc(int duzinaKljuca, long lozinka){
        Random sb = new Random();
        sb.setSeed(lozinka);          
        byte[] kljuc = new byte[duzinaKljuca];
        sb.nextBytes(kljuc);                                
        return kljuc;
    }
    
    public byte[] sifrujDesifruj(byte[] podatak, byte[] kljuc){                 //xor za svaki bajt podatka sa svakim bajtom kljuca
        byte[] rezultat = new byte[podatak.length];                             //ubaciti mozda proveru da li je duzina niza podatak isti kao duzina niza kljuc
        for(int i = 0; i < rezultat.length; i++){
            rezultat[i] = (byte) (podatak[i] ^ kljuc[i]);                       //kljuc i podatak moraju biti iste duzine, kast u bajt se radi jer imamo binarnu operaciju ^
        }
        return rezultat;
    }
    
    public void sacuvajFajl(byte[] podaci, String poruka){
        try {
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle(poruka);
            jfc.showSaveDialog(jfc);
            String adr = jfc.getSelectedFile().getAbsolutePath();
            Files.write(Paths.get(adr), podaci);
        } catch (IOException ex) {
            Logger.getLogger(OTPmetode.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(OTPmetode.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    

    
    
    
    
    
    
    
}
