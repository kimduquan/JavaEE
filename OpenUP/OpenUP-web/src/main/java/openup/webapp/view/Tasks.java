/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.view;

import epf.client.config.ConfigNames;
import epf.client.config.ConfigSource;
import epf.schema.EPF;
import epf.schema.tasks.Discipline;
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
@Named("tasks")
public class Tasks implements Serializable {
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Discipline> disciplines;
    private List<Task> tasks;
    
    @Inject
    private ClientQueue clients;
    
    @Inject
    private ConfigSource config;

    public List<Discipline> getDisciplines() throws Exception {
        if(disciplines == null){
            String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
            try(Client client = new Client(clients, new URI(url))){
            	disciplines = client
                        .target()
                        .path(EPF.Schema)
                        .path(EPF.Discipline)
                        .request(MediaType.APPLICATION_JSON)
                        .get(new GenericType<List<Discipline>> () {});
            }
        }
        return disciplines;
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
