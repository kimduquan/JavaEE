/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.webapp.view;

import epf.client.config.ConfigNames;
import epf.client.config.ConfigSource;
import epf.client.persistence.Queries;
import epf.schema.EPF;
import epf.schema.delivery_processes.Iteration;
import epf.schema.delivery_processes.Phase;
import epf.schema.tasks.Task;
import epf.util.client.Client;
import epf.webapp.Session;
import java.io.Serializable;
import java.net.URI;
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
    private ConfigSource config;
    
    @Inject
    private Session session;
	
	private List<Phase> phases;
    private List<Iteration> iterations;
    private List<Task> tasks;

    public List<Phase> getPhases() throws Exception {
        if(phases == null){
            String url = config.getValue(ConfigNames.PERSISTENCE_URL);
            try(Client client = session.newClient(new URI(url))){
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
            String url = config.getValue(ConfigNames.PERSISTENCE_URL);
            try(Client client = session.newClient(new URI(url))){
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
            String url = config.getValue(ConfigNames.PERSISTENCE_URL);
            try(Client client = session.newClient(new URI(url))){
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
