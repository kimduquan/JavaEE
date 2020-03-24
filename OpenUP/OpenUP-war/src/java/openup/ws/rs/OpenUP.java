/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.ws.rs;

import javax.annotation.security.DenyAll;
import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.identitystore.LdapIdentityStoreDefinition;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Produces;


/**
 * REST Web Service
 *
 * @author FOXCONN
 */
@ApplicationPath("openup")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@ApplicationScoped
@BasicAuthenticationMechanismDefinition( realmName = "file" )
@LdapIdentityStoreDefinition(
        url = "ldap://localhost:10389",
        bindDn = "uid=admin,ou=system",
        bindDnPassword = "admin",
        callerBaseDn = "dc=OpenUP,dc=WSO2,dc=ORG",
        callerSearchBase = "ou=Roles,dc=OpenUP,dc=WSO2,dc=ORG",
        groupSearchBase = "ou=Roles,dc=OpenUP,dc=WSO2,dc=ORG"
)
@DenyAll
public class OpenUP extends Application {
}
