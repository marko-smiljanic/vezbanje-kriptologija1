/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.otpjava;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Marko
 */
public class Test {
    public static void main(String[] args) {
        Random sb = new Random();
        byte[] niz = new byte[1000];
        sb.setSeed(1234567890);                                                 //prosledjuje se long broj, generisani niz ce biti isti na svakom racunaru uz isti seed
        sb.nextBytes(niz);
        System.out.println(Arrays.toString(niz));
        
        new OTPmetode().sacuvajFajl(niz, "Unesite naziv fajla za kljuc");
        
        //test za metode
        
        OTPmetode otp = new OTPmetode();
        byte [] k = otp.generisiKljuc(12);
        otp.sacuvajFajl(k,"Unesite naziv fajla za kljuc2");
        System.out.println(Arrays.toString(k));
        
        
        OTPmetode otp2 = new OTPmetode();
        byte [] k2 = otp.generisiKljuc(12, 1234567890);
        otp2.sacuvajFajl(k2,"Unesite naziv fajla za kljuc3");
        System.out.println(Arrays.toString(k2));
        
        
        
        
    }
}
