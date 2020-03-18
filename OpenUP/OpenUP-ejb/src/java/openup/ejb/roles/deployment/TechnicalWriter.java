/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.ejb.roles.deployment;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.Path;

/**
 *
 * @author FOXCONN
 */
@Stateless
@LocalBean
@DeclareRoles("TechnicalWriter")
@RolesAllowed("TechnicalWriter")
@Path("technical-writer")
public class TechnicalWriter {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
