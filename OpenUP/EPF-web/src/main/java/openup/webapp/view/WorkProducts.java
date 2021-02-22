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
import epf.schema.work_products.Artifact;
import epf.schema.work_products.Domain;
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
@Named("work_products")
public class WorkProducts implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
    @Inject
    private ConfigSource config;
    
    @Inject
    private Session session;
	
	private List<Domain> domains;
    private List<Artifact> artifacts;

    public List<Domain> getDomains() throws Exception {
        if(domains == null){
            String url = config.getValue(ConfigNames.PERSISTENCE_URL);
            try(Client client = session.newClient(new URI(url))){
            	domains = Queries.getCriteriaQueryResult(
            			client, 
            			new GenericType<List<Domain>> () {}, 
            			null, 
            			target -> target.path(EPF.Domain), 
            			0, 
            			100);
            }
        }
        return domains;
    }

    public List<Artifact> getArtifacts() throws Exception {
        if(artifacts == null){
            String url = config.getValue(ConfigNames.PERSISTENCE_URL);
            try(Client client = session.newClient(new URI(url))){
            	artifacts = Queries.getCriteriaQueryResult(
            			client, 
            			new GenericType<List<Artifact>> () {}, 
            			null, 
            			target -> target.path(EPF.Artifact), 
            			0, 
            			100);
            }
        }
        return artifacts;
    }
}
