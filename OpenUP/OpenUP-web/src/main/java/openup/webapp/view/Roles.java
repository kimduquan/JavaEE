/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.view;

import epf.client.config.ConfigNames;
import epf.client.config.ConfigSource;
import epf.client.persistence.Queries;
import epf.schema.EPF;
import epf.schema.roles.Role;
import epf.schema.roles.RoleSet;
import epf.util.client.Client;
import openup.schema.OpenUP;
import openup.webapp.Session;
import java.io.Serializable;
import java.net.URI;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;

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
    
    @Inject
    private ConfigSource config;
    
    @Inject
    private Session session;
	
	private List<RoleSet> roleSets;
    private List<Role> roles;

    public List<RoleSet> getRoleSets() throws Exception {
        if(roleSets == null){
            String url = config.getConfig(ConfigNames.PERSISTENCE_URL, "");
            try(Client client = session.newClient(new URI(url))){
            	roleSets = Queries.getCriteriaQueryResult(
            			client, 
            			new GenericType<List<RoleSet>> () {}, 
            			OpenUP.Schema,
            			target -> target.path(EPF.RoleSet), 
            			0, 
            			100);
            }
        }
        return roleSets;
    }

    public List<Role> getRoles() throws Exception {
        if(roles == null){
            String url = config.getConfig(ConfigNames.PERSISTENCE_URL, "");
            try(Client client = session.newClient(new URI(url))){
            	roles = Queries.getCriteriaQueryResult(
            			client, 
            			new GenericType<List<Role>> () {}, 
            			OpenUP.Schema, 
            			target -> target.path(EPF.Role), 
            			0, 
            			100);
            }
        }
        return roles;
    }
}
