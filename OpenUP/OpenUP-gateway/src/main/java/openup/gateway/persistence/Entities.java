/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.gateway.persistence;

import java.io.InputStream;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Response;

/**
 *
 * @author FOXCONN
 */
@RequestScoped
public class Entities implements openup.api.persistence.Entities {

    @Override
    public Response persist(String name, String id, InputStream body) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response find(String name, String id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Response remove(String name, String id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
