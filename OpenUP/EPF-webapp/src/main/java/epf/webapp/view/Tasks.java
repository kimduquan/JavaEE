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
@Named("tasks")
public class Tasks implements Serializable {
   
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	private transient List<Discipline> disciplines;
    /**
     * 
     */
    private transient List<Task> taskList;
    
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
     * @return
     */
    public List<Discipline> getDisciplines() {
        if(disciplines == null){
            try(Client client = session.newClient("persistence")){
            	disciplines = Queries.executeQuery(
            			client, 
            			new GenericType<List<Discipline>> () {}, 
            			EPF.SCHEMA, 
            			target -> target.path(EPF.DISCIPLINE), 
            			0, 
            			100);
            } 
            catch (Exception e) {
            	logger.throwing(Queries.class.getName(), "executeQuery", e);
			}
        }
        return disciplines;
    }

    /**
     * @return
     */
    public List<Task> getTasks() {
        if(taskList == null){
            try(Client client = session.newClient("persistence")){
            	taskList = Queries.executeQuery(
            			client, 
            			new GenericType<List<Task>> () {}, 
            			EPF.SCHEMA, 
            			target -> target.path(EPF.TASK), 
            			0, 
            			100);
            } 
            catch (Exception e) {
            	logger.throwing(Queries.class.getName(), "executeQuery", e);
			}
        }
        return taskList;
    }
}
