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

    public List<Domain> getDomains() throws Exception {
        if(domains == null){
            String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
            try(Client client = new Client(clients, new URI(url))){
            	domains = client
                        .target()
                        .path(EPF.Schema)
                        .path(EPF.Domain)
                        .request(MediaType.APPLICATION_JSON)
                        .get(new GenericType<List<Domain>> () {});
            }
        }
        return domains;
    }

    public List<Artifact> getArtifacts() throws Exception {
        if(artifacts == null){
            String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
            try(Client client = new Client(clients, new URI(url))){
            	artifacts = client
                        .target()
                        .path(EPF.Schema)
                        .path(EPF.Artifact)
                        .request(MediaType.APPLICATION_JSON)
                        .get(new GenericType<List<Artifact>> () {});
            }
        }
        return artifacts;
    }
}
