/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.tool;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author FOXCONN
 */
public class OpenUPTool {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            generateKeyPair("RSA", 2048);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(OpenUPTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void generateKeyPair(String alogrithm, int keySize) throws NoSuchAlgorithmException{
        KeyPairGenerator generator = KeyPairGenerator.getInstance(alogrithm);
        generator.initialize(keySize);
        KeyPair keyPair = generator.generateKeyPair();
        
        PublicKey publicKey = keyPair.getPublic();
        PKCS8EncodedKeySpec encodedPublicKey = new PKCS8EncodedKeySpec(publicKey.getEncoded());
        byte[] publicKeyBytes = encodedPublicKey.getEncoded();
        
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] privateKeyBytes = privateKey.getEncoded();
        
        Encoder encoder = Base64.getUrlEncoder();
        
        String publicKeyText = encoder.encodeToString(publicKeyBytes);
        System.out.println("public key:");
        System.out.print(publicKeyText);
        
        String privateKeyText = encoder.encodeToString(privateKeyBytes);
        System.out.println("");
        System.out.println("private key:");
        System.out.print(privateKeyText);
        
    }
}
