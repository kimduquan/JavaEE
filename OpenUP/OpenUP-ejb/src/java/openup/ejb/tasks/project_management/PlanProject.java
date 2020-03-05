/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.ejb.tasks.project_management;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;

/**
 *
 * @author FOXCONN
 */
@Stateful
@LocalBean
@RolesAllowed({"ProjectManager", "Analyst", "Architect", "Developer", "Stakeholder", "Tester"})
public class PlanProject {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
