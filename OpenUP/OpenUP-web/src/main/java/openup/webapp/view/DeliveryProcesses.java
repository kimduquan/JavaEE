/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.view;

import epf.client.config.ConfigNames;
import epf.client.config.ConfigSource;
import epf.schema.EPF;
import epf.schema.delivery_processes.Iteration;
import epf.schema.delivery_processes.Phase;
import epf.schema.tasks.Task;
import epf.util.client.Client;
import epf.util.client.ClientQueue;
import java.io.Serializable;
import java.net.URI;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

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
	
	private List<Phase> phases;
    private List<Iteration> iterations;
    private List<Task> tasks;
    
    @Inject
    private ConfigSource config;
    
    @Inject
    private ClientQueue clients;

    public List<Phase> getPhases() throws Exception {
        if(phases == null){
            String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
            try(Client client = new Client(clients, new URI(url))){
            	phases = client
            			.target()
                        .path(EPF.Schema)
                        .path(EPF.Phase)
                        .request(MediaType.APPLICATION_JSON)
                        .get(new GenericType<List<Phase>> () {});
            }
        }
        return phases;
    }

    public List<Iteration> getIterations() throws Exception {
        if(iterations == null){
            String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
            try(Client client = new Client(clients, new URI(url))){
            	iterations = client
                        .target()
                        .path(EPF.Schema)
                        .path(EPF.Iteration)
                        .request(MediaType.APPLICATION_JSON)
                        .get(new GenericType<List<Iteration>> () {});
            }
        }
        return iterations;
    }

    public List<Task> getTasks() throws Exception {
        if(tasks == null){
            String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
            try(Client client = new Client(clients, new URI(url))){
            	tasks = client
                        .target()
                        .path(EPF.Schema)
                        .path(EPF.Task)
                        .request(MediaType.APPLICATION_JSON)
                        .get(new GenericType<List<Task>> () {});
            }
        }
        return tasks;
    }
}
