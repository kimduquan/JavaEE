/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.lang;

import java.net.URI;

/**
 *
 * @author FOXCONN
 */
public class DocumentUri {
    
    private URI uri;
    
    public DocumentUri(String uri) throws Exception {
        this.uri = new URI(uri);
    }
    
    @Override
    public String toString(){
        return uri.toString();
    }
}
