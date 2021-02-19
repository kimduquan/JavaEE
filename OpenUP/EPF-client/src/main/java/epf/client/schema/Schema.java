/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.client.schema;

import epf.schema.delivery_processes.DeliveryProcess;
import epf.schema.roles.RoleSet;
import epf.schema.tasks.Discipline;
import epf.schema.work_products.Domain;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author FOXCONN
 */
@Path("schema")
public interface Schema {
    
    @Path("delivery-processes")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<DeliveryProcess> getDeliveryProcesses() throws Exception;
    
    @Path("roles")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<RoleSet> getRoleSets() throws Exception;
    
    @Path("tasks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Discipline> getDisciplines() throws Exception;
    
    @Path("work-products")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Domain> getDomains() throws Exception;
}
