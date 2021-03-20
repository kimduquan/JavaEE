/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.webapp.view;

import epf.client.persistence.Queries;
import epf.schema.EPF;
import epf.schema.delivery_processes.Iteration;
import epf.schema.delivery_processes.Phase;
import epf.schema.tasks.Task;
import epf.util.client.Client;
import epf.webapp.Session;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;

/**
 *
 * @author FOXCONN
 */
@ViewScoped
@Named("delivery_processes")
public class DeliveryProcesses implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
    /**
     * 
     */
    @Inject
    private transient Session session;
    
    /**
     * 
     */
    @Inject
    private transient Logger logger;
	
	/**
	 * 
	 */
	private transient List<Phase> phases;
    /**
     * 
     */
    private transient List<Iteration> iterations;
    /**
     * 
     */
    private transient List<Task> tasks;

    /**
     * @return
     * @throws Exception
     */
    public List<Phase> getPhases() {
        if(phases == null){
            try(Client client = session.newClient("persistence")){
            	phases = Queries.getCriteriaQueryResult(
            			client, 
            			new GenericType<List<Phase>>() {}, 
            			EPF.SCHEMA,
            			target -> target.path(EPF.PHASE), 
            			0, 
            			100);
            } 
            catch (Exception e) {
				logger.severe(e.getMessage());
			}
        }
        return phases;
    }

    public List<Iteration> getIterations() {
        if(iterations == null){
            try(Client client = session.newClient("persistence")){
            	iterations = Queries.getCriteriaQueryResult(
            			client, 
            			new GenericType<List<Iteration>>() {}, 
            			EPF.SCHEMA, 
            			target -> target.path(EPF.ITERATION), 
            			0, 
            			100);
            } 
            catch (Exception e) {
            	logger.severe(e.getMessage());
			}
        }
        return iterations;
    }

    public List<Task> getTasks() {
        if(tasks == null){
            try(Client client = session.newClient("persistence")){
            	tasks = Queries.getCriteriaQueryResult(
            			client, 
            			new GenericType<List<Task>> () {}, 
            			EPF.SCHEMA, 
            			target -> target.path(EPF.TASK), 
            			0, 
            			100);
            } 
            catch (Exception e) {
            	logger.severe(e.getMessage());
			}
        }
        return tasks;
    }
}
