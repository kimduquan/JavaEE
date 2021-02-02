/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.view;

import epf.schema.EPF;
import openup.schema.OpenUP;
import epf.schema.tasks.Discipline;
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
@Named("tasks")
public class Tasks implements Serializable {
   
    private List<Discipline> disciplines;
    private List<Task> tasks;
    
    @Inject
    private Session session;
    
    @Inject
    private ConfigSource config;

    public List<Discipline> getDisciplines() throws Exception {
        if(disciplines == null){
            String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
            try(Client client = new Client(session.getClient(), new URI(url))){
                Response response = client
                        .target()
                        .path(OpenUP.Schema)
                        .path(EPF.Discipline)
                        .request(MediaType.APPLICATION_JSON)
                        .get();
                disciplines = response.readEntity(new GenericType<List<Discipline>> () {});
            }
        }
        return disciplines;
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
