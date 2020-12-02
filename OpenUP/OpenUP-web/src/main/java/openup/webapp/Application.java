/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Named;
import javax.security.enterprise.authentication.mechanism.http.AutoApplySession;
import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.RememberMe;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
@RememberMe
@AutoApplySession
@BasicAuthenticationMechanismDefinition(realmName = "OpenUP")
@FacesConfig
@Named("webapp_application")
public class Application {
}
