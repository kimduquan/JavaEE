/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.delivery_processes;

import epf.schema.EPF;
import openup.schema.OpenUP;
import openup.schema.DeliveryProcess;
import openup.schema.Iteration;
import openup.schema.Phase;
import openup.schema.Role;
import java.security.Principal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import openup.client.delivery_processes.DeliveryProcesses;
import openup.persistence.Request;

/**
 *
 * @author FOXCONN
 */
@Path("delivery-processes")
@RequestScoped
@RolesAllowed(Role.ANY_ROLE)
public class DeliveryProcessesService implements DeliveryProcesses {
    
    @Inject
    private Request cache;
    
    @Inject
    private Principal principal;

    @Override
    public DeliveryProcess newDeliveryProcess(String delivery_process, String name, String summary) throws Exception {
        epf.schema.delivery_processes.DeliveryProcess epfDP = cache.find(OpenUP.Schema, principal, EPF.DeliveryProcess, epf.schema.delivery_processes.DeliveryProcess.class, delivery_process);
        if(epfDP != null){
            List<Exception> errors = new CopyOnWriteArrayList<>();
            DeliveryProcess dp = new DeliveryProcess();
            dp.setName(name);
            dp.setSummary(summary);
            dp.setDeliveryProcess(epfDP);
            cache.persist(OpenUP.Schema, principal, OpenUP.DeliveryProcess, dp.getName(), dp);
            cache.createNamedQuery(OpenUP.Schema, principal, epf.schema.delivery_processes.Phase.PHASES, epf.schema.delivery_processes.Phase.class).setParameter("name", epfDP.getName())
                    .getResultStream()
                    .forEach(epfPhase -> {
                        Phase phase = new Phase();
                        phase.setName(epfPhase.getName());
                        phase.setPhase(epfPhase);
                        phase.setParentActivities(dp);
                        try {
                            cache.persist(OpenUP.Schema, principal, OpenUP.Phase, phase.getName(), phase);
                            cache.createNamedQuery(OpenUP.Schema, principal, epf.schema.delivery_processes.Iteration.ITERATIONS, epf.schema.delivery_processes.Iteration.class)
                                    .setParameter("name", epfPhase.getName())
                                    .getResultStream()
                                    .forEach(epfIt -> {
                                        Iteration it = new Iteration();
                                        it.setIteration(epfIt);
                                        it.setName(epfIt.getName());
                                        it.setParentActivities(phase);
                                        it.setSummary(epfIt.getName());
                                        try {
                                            cache.persist(OpenUP.Schema, principal, OpenUP.Iteration, "", it);
                                        } 
                                        catch (Exception ex) {
                                            errors.add(ex);
                                        }
                                    });
                        } 
                        catch (Exception ex) {
                            errors.add(ex);
                        }
                    });
            if(!errors.isEmpty()){
                throw new InternalServerErrorException(Response.serverError().entity(errors).build());
            }
            return dp;
        }
        throw new NotFoundException();
    }
    
}
