/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.schedule;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.auth.LoginConfig;

/**
 *
 * @author FOXCONN
 */
@ApplicationScoped
@ApplicationPath("/")
@LoginConfig(authMethod = "MP-JWT", realmName = "EPF")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class Service extends Application {
	
}
