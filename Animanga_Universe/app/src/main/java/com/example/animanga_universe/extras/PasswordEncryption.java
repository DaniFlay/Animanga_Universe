package com.example.animanga_universe.extras;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryption {
    public static String hashPassword(String password){
        try{
            MessageDigest md= MessageDigest.getInstance("SHA-256");
            md.update(password.getBytes());
            byte[] hashedBytes= md.digest();
            StringBuilder sb= new StringBuilder();
            for(byte b: hashedBytes){
                sb.append(String.format("%02x",b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
