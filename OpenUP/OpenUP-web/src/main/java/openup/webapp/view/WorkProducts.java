/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.view;

import epf.client.config.ConfigNames;
import epf.client.config.ConfigSource;
import epf.schema.EPF;
import epf.schema.work_products.Artifact;
import epf.schema.work_products.Domain;
import epf.util.client.Client;
import epf.util.client.ClientQueue;
import openup.webapp.Session;
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
@Named("work_products")
public class WorkProducts implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Domain> domains;
    private List<Artifact> artifacts;
    
    @Inject
    private ClientQueue clients;
    
    @Inject
    private ConfigSource config;
    
    @Inject
    private Session session;

    public List<Domain> getDomains() throws Exception {
        if(domains == null){
            String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
            try(Client client = new Client(clients, new URI(url), b -> b)){
            	domains = client
            			.authorization(
            					session
            					.getPrincipal()
            					.getToken()
            					.getRawToken()
            					)
            			.request(
            					target -> target.path(EPF.Schema).path(EPF.Domain), 
            					req -> req.accept(MediaType.APPLICATION_JSON)
            					)
            			.get(new GenericType<List<Domain>> () {});
            }
        }
        return domains;
    }

    public List<Artifact> getArtifacts() throws Exception {
        if(artifacts == null){
            String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
            try(Client client = new Client(clients, new URI(url), b -> b)){
            	artifacts = client
            			.authorization(
            					session
            					.getPrincipal()
            					.getToken()
            					.getRawToken()
            					)
            			.request(
            					target -> target.path(EPF.Schema).path(EPF.Artifact), 
            					req -> req.accept(MediaType.APPLICATION_JSON)
            					)
            			.get(new GenericType<List<Artifact>> () {});
            }
        }
        return artifacts;
    }
}
