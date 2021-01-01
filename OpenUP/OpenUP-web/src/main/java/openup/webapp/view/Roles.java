/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.view;

import epf.schema.EPF;
import epf.schema.OpenUP;
import epf.schema.roles.Role;
import epf.schema.roles.RoleSet;
import java.io.Serializable;
import java.net.URI;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import openup.client.Client;
import openup.client.config.ConfigNames;
import openup.client.config.ConfigSource;
import openup.webapp.Session;

/**
 *
 * @author FOXCONN
 */
@ViewScoped
@Named("roles")
public class Roles implements Serializable {
    
    private List<RoleSet> roleSets;
    private List<Role> roles;
    
    @Inject
    private Session session;
    
    @Inject
    private ConfigSource config;

    public List<RoleSet> getRoleSets() throws Exception {
        if(roleSets == null){
            String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
            try(Client client = new Client(session.getClient(), new URI(url))){
                Response response = client
                        .target()
                        .path(OpenUP.Schema)
                        .path(EPF.RoleSet)
                        .request(MediaType.APPLICATION_JSON)
                        .get();
                roleSets = response.readEntity(new GenericType<List<RoleSet>> () {});
            }
        }
        return roleSets;
    }

    public List<Role> getRoles() throws Exception {
        if(roles == null){
            String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
            try(Client client = new Client(session.getClient(), new URI(url))){
                Response response = client
                        .target()
                        .path(OpenUP.Schema)
                        .path(EPF.Role)
                        .request(MediaType.APPLICATION_JSON)
                        .get();
                roles = response.readEntity(new GenericType<List<Role>> () {});
            }
        }
        return roles;
    }
}
