/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author pallgree
 */
public class Hash {

    public String hashPass(String password) {
        // Hash a password
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        return hashed;
    }

    public boolean checkPass(String candidate, String hashed) {
        // Check that an unencrypted password matches or not
        if (BCrypt.checkpw(candidate, hashed)) {
            return true;
        } else {
            return false;
        }
    }
    
    public static void main(String[] args) {
        Hash hash = new Hash();
        String hashPass = hash.hashPass("123");
        System.out.println(hashPass);
        
       // System.out.println(hash.checkPass("Khjuoasdn1i9", "$2a$10$6cmcUz9nDrJefb1Vhk5Np.N62lE9htkwK2TY/KLC9.Rm.hpL/0yge"));
    }
}

