/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.gateway.config;

import java.util.Map;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author FOXCONN
 */
@RequestScoped
public class Config implements openup.api.config.Config {

    @Override
    public Map<String, Object> getConfig() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
