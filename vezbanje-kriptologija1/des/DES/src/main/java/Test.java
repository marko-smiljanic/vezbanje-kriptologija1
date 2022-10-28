/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Marko
 */
public class Test {
    
    public static void main(String[] args){
        String poruka = "DES moderni blokovski algoritam za sifrovanje";        //ako ostavimo da je poruka samo npr. jedno slovo, padding funkcionalnost ce dopuniti do jednog bloka
        DES des = new DES();
        byte[] sifrat = des.encryptDES(poruka.getBytes(), "12345678".getBytes());  //uvek cemo dobiti identican sifrat za 12345678
        System.out.println("Sifrat: " + new String(sifrat));
        
        DES des2 = new DES();
        byte[] desifrovana_poruka = des2.decryptDES(sifrat, "12345678".getBytes());
        System.out.println("Desifrovana poruka: " + new String(desifrovana_poruka));
        
        
    }
    
}
