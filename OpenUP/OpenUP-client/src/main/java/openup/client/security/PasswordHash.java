/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.security;

import org.h2.security.SHA256;
import org.h2.util.StringUtils;
/**
 *
 * @author FOXCONN
 */
public class PasswordHash {
    
    private PasswordHash(){
        
    }
    
    public static String hash(String username, char[] password){
        StringBuilder builder = new StringBuilder();
        StringUtils.convertBytesToHex(builder, SHA256.getKeyPasswordHash(username.toUpperCase(), password));
        return builder.toString();
    }
}
