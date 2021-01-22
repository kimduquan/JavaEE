/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.batch;

import epf.schema.openup.Role;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.Path;

/**
 *
 * @author FOXCONN
 */
@Path("batch")
@RolesAllowed(Role.ANY_ROLE)
@RequestScoped
public class Batch implements openup.client.batch.api.Batch {
    
    
}
