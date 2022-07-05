/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.Random;
import java.util.UUID;

/**
 *
 * @author quang
 */
public class GenerateOTP {

    public String generateOTP() {
        Random random = new Random();
        String otp = String.valueOf(100000 + random.nextInt(999999));
        return otp;
    }

    public String generateUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
    
    public static void main(String[] args) {
        GenerateOTP gene = new GenerateOTP();
        System.out.println(gene.generateUUID());
        System.out.println(gene.generateUUID().length());
    }

}
