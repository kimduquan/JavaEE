/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.ejb;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.identitystore.LdapIdentityStoreDefinition;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
@BasicAuthenticationMechanismDefinition
@LdapIdentityStoreDefinition(
        url = "ldap://localhost:10389",
        bindDn = "uid=admin,ou=system",
        bindDnPassword = "admin",
        callerBaseDn = "dc=OpenUP,dc=WSO2,dc=ORG",
        callerSearchBase = "ou=Roles,dc=OpenUP,dc=WSO2,dc=ORG",
        groupSearchBase = "ou=Roles,dc=OpenUP,dc=WSO2,dc=ORG"
)
public class OpenUP {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
