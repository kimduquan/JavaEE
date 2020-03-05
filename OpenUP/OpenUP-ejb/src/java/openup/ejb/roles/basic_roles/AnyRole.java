/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.ejb.roles.basic_roles;

import javax.annotation.security.DeclareRoles;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;

/**
 *
 * @author FOXCONN
 */
@Stateful
@LocalBean
@DeclareRoles("AnyRole")
public class AnyRole {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
