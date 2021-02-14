/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.client.security;

import java.io.IOException;
import javax.enterprise.context.Dependent;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;

/**
 *
 * @author FOXCONN
 */
@Dependent
public class Header implements ClientRequestFilter {
    
    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public void filter(ClientRequestContext crc) throws IOException {
        if(token != null){
        	StringBuilder builder = new StringBuilder();
        	builder.append("Bearer ");
        	builder.append(token);
            crc.getHeaders().putSingle(
                    HttpHeaders.AUTHORIZATION, 
                    builder.toString()
            );
        }
    }
}
