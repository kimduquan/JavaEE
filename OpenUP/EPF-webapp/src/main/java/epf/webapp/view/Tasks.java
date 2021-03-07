/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.webapp.view;

import epf.client.persistence.Queries;
import epf.schema.EPF;
import epf.schema.tasks.Discipline;
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
@Named("tasks")
public class Tasks implements Serializable {
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Discipline> disciplines;
    private List<Task> tasks;
    
    @Inject
    private Session session;

    public List<Discipline> getDisciplines() throws Exception {
        if(disciplines == null){
            try(Client client = session.newClient("persistence")){
            	disciplines = Queries.getCriteriaQueryResult(
            			client, 
            			new GenericType<List<Discipline>> () {}, 
            			EPF.Schema, 
            			target -> target.path(EPF.Discipline), 
            			0, 
            			100);
            }
        }
        return disciplines;
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
