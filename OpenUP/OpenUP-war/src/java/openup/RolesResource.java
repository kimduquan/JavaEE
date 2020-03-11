/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import openup.ejb.roles.basic_roles.AnyRole;

/**
 * REST Web Service
 *
 * @author FOXCONN
 */
@Path("roles")
@ServletSecurity(
        @HttpConstraint(
                rolesAllowed = "AnyRole"
        )
)
@RolesAllowed("AnyRole")
@DeclareRoles("AnyRole")
public class RolesResource {

    @Context
    private UriInfo context;
    
    @EJB
    AnyRole role;

    /**
     * Creates a new instance of RolesResource
     */
    public RolesResource() {
    }

    /**
     * Retrieves representation of an instance of openup.RolesResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        //TODO return proper representation object
        role.test();
        return role.toString();
    }

    /**
     * PUT method for updating or creating an instance of RolesResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
