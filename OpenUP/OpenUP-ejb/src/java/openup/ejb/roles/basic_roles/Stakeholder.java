/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.ejb.roles.basic_roles;

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
@DeclareRoles("Stakeholder")
@RolesAllowed("Stakeholder")
@Path("stakeholder")
public class Stakeholder {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
