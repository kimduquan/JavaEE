/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.ejb.work_products.deployment;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;

/**
 *
 * @author FOXCONN
 */
@Stateful
@LocalBean
@RolesAllowed("CourseDeveloper")
public class TrainingMaterials {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
