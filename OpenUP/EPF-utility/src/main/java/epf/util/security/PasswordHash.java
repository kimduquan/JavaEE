/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.util.security;

import org.eclipse.persistence.internal.security.JCEEncryptor;
import org.h2.security.SHA256;
import org.h2.util.StringUtils;
/**
 *
 * @author FOXCONN
 */
public class PasswordHash {
    
    private static JCEEncryptor encryptor;
    
    private PasswordHash(){
        
    }
    
    public static String hash(String username, char[] password) throws Exception{
        if(encryptor == null){
            encryptor = new JCEEncryptor();
        }
        StringBuilder builder = new StringBuilder();
        StringUtils.convertBytesToHex(builder, SHA256.getKeyPasswordHash(username.toUpperCase(), password));
        return encryptor.encryptPassword(builder.toString());
    }
}
