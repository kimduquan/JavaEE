/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.persistence;

import epf.DeliveryProcesses;
import epf.schema.roles.RoleSet;
import epf.schema.tasks.Discipline;
import epf.schema.work_products.Domain;
import java.util.stream.Stream;

/**
 *
 * @author FOXCONN
 */
public interface Persistence {
    
    Stream<DeliveryProcesses> getDeliveryProcesses() throws Exception;
    Stream<RoleSet> getRoleSets() throws Exception;
    Stream<Domain> getDomains() throws Exception;
    Stream<Discipline> getDisciplines() throws Exception;
}
