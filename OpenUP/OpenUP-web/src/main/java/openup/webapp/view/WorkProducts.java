/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.view;

import epf.schema.EPF;
import epf.schema.OpenUP;
import epf.schema.work_products.Artifact;
import epf.schema.work_products.Domain;
import java.io.Serializable;
import java.net.URI;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import openup.client.Client;
import openup.client.config.ConfigNames;
import openup.client.config.ConfigSource;
import openup.webapp.Session;

/**
 *
 * @author FOXCONN
 */
@ViewScoped
@Named("work_products")
public class WorkProducts implements Serializable {
    
    private List<Domain> domains;
    private List<Artifact> artifacts;
    
    @Inject
    private Session session;
    
    @Inject
    private ConfigSource config;

    public List<Domain> getDomains() throws Exception {
        if(domains == null){
            String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
            try(Client client = new Client(session.getClient(), new URI(url))){
                Response response = client
                        .target()
                        .path(OpenUP.Schema)
                        .path(EPF.Domain)
                        .request(MediaType.APPLICATION_JSON)
                        .get();
                domains = response.readEntity(new GenericType<List<Domain>> () {});
            }
        }
        return domains;
    }

    public List<Artifact> getArtifacts() throws Exception {
        if(artifacts == null){
            String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
            try(Client client = new Client(session.getClient(), new URI(url))){
                Response response = client
                        .target()
                        .path(OpenUP.Schema)
                        .path(EPF.Artifact)
                        .request(MediaType.APPLICATION_JSON)
                        .get();
                artifacts = response.readEntity(new GenericType<List<Artifact>> () {});
            }
        }
        return artifacts;
    }
}
