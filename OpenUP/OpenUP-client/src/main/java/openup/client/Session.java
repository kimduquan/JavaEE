/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client;

import java.io.Serializable;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import openup.client.config.Config;
import openup.client.config.ConfigNames;
import openup.client.config.ConfigSource;
import openup.client.persistence.Entities;
import openup.client.persistence.Queries;
import openup.client.security.Header;
import openup.client.security.Security;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

/**
 *
 * @author FOXCONN
 */
@SessionScoped
public class Session implements Serializable {
    
    private String base;
    private Header securityHeader;
    private Security security;
    private Config config;
    private Entities entities;
    private Queries queries;
    
    @Inject
    private ClientQueue clients;
    
    @Inject
    private ConfigSource configs;
    
    @PostConstruct
    void postConstruct(){
        try {
            base = configs.getConfig(ConfigNames.OPENUP_GATEWAY_URL, "");
            securityHeader = new Header();
            config = builder().build(Config.class);
        } 
        catch (Exception ex) {
            Logger.getLogger(Session.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Produces
    public Config config() throws Exception{
        return config;
    }
    
    @Produces
    public Security security() throws Exception{
        if(security == null){
            security = builder().build(Security.class);
        }
        return security;
    }
    
    @Produces
    public Entities entities() throws Exception{
        if(entities == null){
            entities = builder().build(Entities.class);
        }
        return entities;
    }
    
    @Produces
    public Queries queries() throws Exception{
        if(queries == null){
            queries = builder().build(Queries.class);
        }
        return queries;
    }
    
    @Produces
    public RestClientBuilder builder() throws Exception{
        URL baseUrl = new URL(base);
        return RestClientBuilder
                .newBuilder()
                .baseUrl(baseUrl)
                .register(securityHeader);
    }
    
    ClientQueue clients(){
        return clients;
    }
    
    public Header header(){
        return securityHeader;
    }
}
