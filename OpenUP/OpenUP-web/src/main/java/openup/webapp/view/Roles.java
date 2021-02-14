/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.view;

import epf.client.config.ConfigNames;
import epf.client.config.ConfigSource;
import epf.schema.EPF;
import epf.schema.roles.Role;
import epf.schema.roles.RoleSet;
import epf.util.client.Client;
import epf.util.client.ClientQueue;
import java.io.Serializable;
import java.net.URI;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author FOXCONN
 */
@ViewScoped
@Named("roles")
public class Roles implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<RoleSet> roleSets;
    private List<Role> roles;
    
    @Inject
    private ClientQueue clients;
    
    @Inject
    private ConfigSource config;

    public List<RoleSet> getRoleSets() throws Exception {
        if(roleSets == null){
            String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
            try(Client client = new Client(clients, new URI(url))){
            	roleSets = client
                        .target()
                        .path(EPF.Schema)
                        .path(EPF.RoleSet)
                        .request(MediaType.APPLICATION_JSON)
                        .get(new GenericType<List<RoleSet>> () {});
            }
        }
        return roleSets;
    }

    public List<Role> getRoles() throws Exception {
        if(roles == null){
            String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
            try(Client client = new Client(clients, new URI(url))){
            	roles = client
                        .target()
                        .path(EPF.Schema)
                        .path(EPF.Role)
                        .request(MediaType.APPLICATION_JSON)
                        .get(new GenericType<List<Role>> () {});
            }
        }
        return roles;
    }
}
