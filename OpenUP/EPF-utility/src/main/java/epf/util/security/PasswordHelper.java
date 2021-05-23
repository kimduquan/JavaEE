/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.util.security;

import java.util.Locale;
import java.util.Objects;
import org.eclipse.persistence.internal.security.JCEEncryptor;
import org.h2.security.SHA256;
import org.h2.util.StringUtils;
/**
 *
 * @author FOXCONN
 */
public final class PasswordHelper {
    
    /**
     * 
     */
    private static JCEEncryptor encryptor;
    
    private PasswordHelper(){
        
    }
    
    /**
     * @param username
     * @param password
     * @return
     * @throws Exception
     */
    public static String hash(final String username, final char... password) throws Exception {
    	Objects.requireNonNull(username);
    	Objects.requireNonNull(password);
        if(encryptor == null){
            encryptor = new JCEEncryptor();
        }
        final StringBuilder builder = new StringBuilder();
        StringUtils.convertBytesToHex(builder, SHA256.getKeyPasswordHash(username.toUpperCase(Locale.getDefault()), password));
        return encryptor.encryptPassword(builder.toString());
    }
}
