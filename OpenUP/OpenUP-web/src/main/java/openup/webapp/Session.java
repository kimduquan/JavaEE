/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp;

import java.io.Serializable;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import epf.client.config.ConfigNames;
import epf.client.config.ConfigSource;
import epf.client.security.Security;
import epf.util.client.RestClient;
import openup.schema.OpenUP;
import openup.webapp.security.TokenPrincipal;

/**
 *
 * @author FOXCONN
 */
@SessionScoped
@Named("webapp_session")
public class Session implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(Session.class.getName());

	private TokenPrincipal principal;
    
    @Inject
    private SecurityContext context;
    
    @Inject
    private ConfigSource config;
    
    @PostConstruct
    void postConstruct(){
        if(context.getCallerPrincipal() instanceof TokenPrincipal){
            principal = ((TokenPrincipal)context.getCallerPrincipal());
        }
    }
    
    @PreDestroy
    void preDestroy(){
        if(principal != null){
            try(RestClient restClient = new RestClient(new URI(config.getConfig(ConfigNames.OPENUP_GATEWAY_URL, "")), b -> b)) {
                Security service = restClient
                		.authorization(
                				principal
                				.getToken()
                				.getRawToken()
                				)
                		.build(Security.class);
                service.logOut(OpenUP.Schema);
            }
            catch (Exception ex) {
                logger.log(Level.SEVERE, ex.getMessage(), ex);
            }
        }
    }
    
    public TokenPrincipal getPrincipal() {
    	return principal;
    }
}
