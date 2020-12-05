/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.client.epf.schema;

import epf.schema.roles.RoleSet;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author FOXCONN
 */
@Path("schema/roles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface Roles {
    
    public static final String ANY_ROLE = "ANY_ROLE";
    public static final String ADMIN = "ADMIN";
    
    @GET
    List<RoleSet> getRoleSets() throws Exception;
}
