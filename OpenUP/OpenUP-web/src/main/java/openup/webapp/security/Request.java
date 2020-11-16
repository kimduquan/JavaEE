/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.security;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.AutoApplySession;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.authentication.mechanism.http.RememberMe;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.identitystore.IdentityStore;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author FOXCONN
 */
@RequestScoped
@AutoApplySession
@RememberMe
public class Request implements HttpAuthenticationMechanism {
    
    @Inject
    private IdentityStore identityStore;

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext context) throws AuthenticationException {
        Credential credential = context.getAuthParameters().getCredential();
        if (credential != null) {
            return context.notifyContainerAboutLogin(identityStore.validate(credential));
        }
        else {
            return context.doNothing();
        }
    }
    
}
