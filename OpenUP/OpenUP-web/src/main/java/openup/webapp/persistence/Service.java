/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.webapp.persistence;

import epf.DeliveryProcesses;
import epf.schema.roles.RoleSet;
import epf.schema.tasks.Discipline;
import epf.schema.work_products.Domain;
import java.net.URI;
import java.util.List;
import java.util.stream.Stream;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import openup.client.config.ConfigNames;
import openup.client.config.ConfigSource;
import openup.client.Client;
import openup.client.Session;

/**
 *
 * @author FOXCONN
 */
@RequestScoped
public class Service implements Persistence {
    
    @Inject
    private ConfigSource config;
    
    @Inject
    private Session session;

    @Override
    public Stream<DeliveryProcesses> getDeliveryProcesses() throws Exception {
        String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
        try(Client client = new Client(session, new URI(url))){
            Response response = client
                    .target()
                    .path("queries")
                    .path("OpenUP")
                    .path("DeliveryProcesses")
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            List<DeliveryProcesses> deliveryProcesses = response.readEntity(new GenericType<List<DeliveryProcesses>> () {});
            return deliveryProcesses.stream();
        }
    }

    @Override
    public Stream<RoleSet> getRoleSets() throws Exception {
        String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
        try(Client client = new Client(session, new URI(url))){
            Response response = client
                    .target()
                    .path("queries")
                    .path("OpenUP")
                    .path("RoleSet")
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            List<RoleSet> roleSets = response.readEntity(new GenericType<List<RoleSet>> () {});
            return roleSets.stream();
        }
    }

    @Override
    public Stream<Domain> getDomains() throws Exception {
        String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
        try(Client client = new Client(session, new URI(url))){
            Response response = client
                    .target()
                    .path("queries")
                    .path("OpenUP")
                    .path("Domain")
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            List<Domain> domains = response.readEntity(new GenericType<List<Domain>> () {});
            return domains.stream();
        }
    }

    @Override
    public Stream<Discipline> getDisciplines() throws Exception {
        String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
        try(Client client = new Client(session, new URI(url))){
            Response response = client
                    .target()
                    .path("queries")
                    .path("OpenUP")
                    .path("Discipline")
                    .request(MediaType.APPLICATION_JSON)
                    .get();
            List<Discipline> disciplines = response.readEntity(new GenericType<List<Discipline>> () {});
            return disciplines.stream();
        }
    }
    
}
