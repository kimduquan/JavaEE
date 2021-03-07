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
    
    @Inject
    private Session session;
	
	private List<Phase> phases;
    private List<Iteration> iterations;
    private List<Task> tasks;

    public List<Phase> getPhases() throws Exception {
        if(phases == null){
            try(Client client = session.newClient("persistence")){
            	phases = Queries.getCriteriaQueryResult(
            			client, 
            			new GenericType<List<Phase>>() {}, 
            			EPF.Schema,
            			target -> target.path(EPF.Phase), 
            			0, 
            			100);
            }
        }
        return phases;
    }

    public List<Iteration> getIterations() throws Exception {
        if(iterations == null){
            try(Client client = session.newClient("persistence")){
            	iterations = Queries.getCriteriaQueryResult(
            			client, 
            			new GenericType<List<Iteration>>() {}, 
            			EPF.Schema, 
            			target -> target.path(EPF.Iteration), 
            			0, 
            			100);
            }
        }
        return iterations;
    }

    public List<Task> getTasks() throws Exception {
        if(tasks == null){
            try(Client client = session.newClient("persistence")){
            	tasks = Queries.getCriteriaQueryResult(
            			client, 
            			new GenericType<List<Task>> () {}, 
            			EPF.Schema, 
            			target -> target.path(EPF.Task), 
            			0, 
            			100);
            }
        }
        return tasks;
    }
}
