/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.view;

import epf.schema.EPF;
import openup.schema.OpenUP;
import epf.schema.delivery_processes.Iteration;
import epf.schema.delivery_processes.Phase;
import epf.schema.tasks.Task;
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
@Named("delivery_processes")
public class DeliveryProcesses implements Serializable {
    
    private List<Phase> phases;
    private List<Iteration> iterations;
    private List<Task> tasks;
    
    @Inject
    private Session session;
    
    @Inject
    private ConfigSource config;

    public List<Phase> getPhases() throws Exception {
        if(phases == null){
            String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
            try(Client client = new Client(session.getClient(), new URI(url))){
                Response response = client
                        .target()
                        .path(OpenUP.Schema)
                        .path(EPF.Phase)
                        .request(MediaType.APPLICATION_JSON)
                        .get();
                phases = response.readEntity(new GenericType<List<Phase>> () {});
            }
        }
        return phases;
    }

    public List<Iteration> getIterations() throws Exception {
        if(iterations == null){
            String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
            try(Client client = new Client(session.getClient(), new URI(url))){
                Response response = client
                        .target()
                        .path(OpenUP.Schema)
                        .path(EPF.Iteration)
                        .request(MediaType.APPLICATION_JSON)
                        .get();
                iterations = response.readEntity(new GenericType<List<Iteration>> () {});
            }
        }
        return iterations;
    }

    public List<Task> getTasks() throws Exception {
        if(tasks == null){
            String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
            try(Client client = new Client(session.getClient(), new URI(url))){
                Response response = client
                        .target()
                        .path(OpenUP.Schema)
                        .path(EPF.Task)
                        .request(MediaType.APPLICATION_JSON)
                        .get();
                tasks = response.readEntity(new GenericType<List<Task>> () {});
            }
        }
        return tasks;
    }
}
