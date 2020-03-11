/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup;

import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * REST Web Service
 *
 * @author FOXCONN
 */
@ApplicationPath("openup")
@ServletSecurity(
        @HttpConstraint(
                rolesAllowed = "AnyRole"
        )
)
public class OpenUP extends Application {
}
