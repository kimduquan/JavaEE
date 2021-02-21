/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.view;

import epf.client.config.ConfigNames;
import epf.client.config.ConfigSource;
import epf.client.persistence.Queries;
import epf.schema.EPF;
import epf.schema.tasks.Discipline;
import epf.schema.tasks.Task;
import epf.util.client.Client;
import openup.webapp.Session;
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
@Named("tasks")
public class Tasks implements Serializable {
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Discipline> disciplines;
    private List<Task> tasks;
    
    @Inject
    private ConfigSource config;
    
    @Inject
    private Session session;

    public List<Discipline> getDisciplines() throws Exception {
        if(disciplines == null){
            String url = config.getValue(ConfigNames.PERSISTENCE_URL);
            try(Client client = session.newClient(new URI(url))){
            	Queries.getCriteriaQueryResult(
            			client, 
            			new GenericType<List<Discipline>> () {}, 
            			null, 
            			target -> target.path(EPF.Discipline), 
            			0, 
            			100);
            }
        }
        return disciplines;
    }

    public List<Task> getTasks() throws Exception {
        if(tasks == null){
            String url = config.getValue(ConfigNames.PERSISTENCE_URL);
            try(Client client = session.newClient(new URI(url))){
            	tasks = Queries.getCriteriaQueryResult(
            			client, 
            			new GenericType<List<Task>> () {}, 
            			null, 
            			target -> target.path(EPF.Task), 
            			0, 
            			100);
            }
        }
        return tasks;
    }
}
