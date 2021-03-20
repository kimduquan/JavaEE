/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.webapp.view;

import epf.client.persistence.Queries;
import epf.schema.EPF;
import epf.schema.work_products.Artifact;
import epf.schema.work_products.Domain;
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
@Named("work_products")
public class WorkProducts implements Serializable {
    
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
	private transient List<Domain> domains;
    /**
     * 
     */
    private transient List<Artifact> artifacts;

    /**
     * @return
     */
    public List<Domain> getDomains() {
        if(domains == null){
            try(Client client = session.newClient("persistence")){
            	domains = Queries.getCriteriaQueryResult(
            			client, 
            			new GenericType<List<Domain>> () {}, 
            			EPF.Schema, 
            			target -> target.path(EPF.Domain), 
            			0, 
            			100);
            } 
            catch (Exception e) {
            	logger.severe(e.getMessage());
			}
        }
        return domains;
    }

    /**
     * @return
     */
    public List<Artifact> getArtifacts() {
        if(artifacts == null){
            try(Client client = session.newClient("persistence")){
            	artifacts = Queries.getCriteriaQueryResult(
            			client, 
            			new GenericType<List<Artifact>> () {}, 
            			EPF.Schema, 
            			target -> target.path(EPF.Artifact), 
            			0, 
            			100);
            } 
            catch (Exception e) {
				logger.severe(e.getMessage());
			}
        }
        return artifacts;
    }
}
