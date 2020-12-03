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
import java.util.List;
import java.util.stream.Stream;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import openup.webapp.Session;
import openup.webapp.config.Config;
import openup.webapp.config.ConfigNames;

/**
 *
 * @author FOXCONN
 */
@RequestScoped
public class Service implements Persistence {
    
    @Inject
    private Config config;
    
    @Inject
    private Session session;

    @Override
    public Stream<DeliveryProcesses> getDeliveryProcesses() throws Exception {
        String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
        String header = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_HEADER, "");
        String format = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_FORMAT, "");
        Client client = ClientBuilder.newClient();
        List<DeliveryProcesses> deliveryProcesses = client.target(url)
                .path("query")
                .path("DeliveryProcesses")
                .request(MediaType.APPLICATION_JSON)
                .header(
                        header, 
                        String.format(
                                format,
                                session.getToken().getRawToken()
                        )
                )
                .get(new GenericType<List<DeliveryProcesses>> () {});
        client.close();
        return deliveryProcesses.stream();
    }

    @Override
    public Stream<RoleSet> getRoleSets() throws Exception {
        String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
        String header = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_HEADER, "");
        String format = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_FORMAT, "");
        Client client = ClientBuilder.newClient();
        List<RoleSet> roleSets = client.target(url)
                .path("query")
                .path("RoleSet")
                .request(MediaType.APPLICATION_JSON)
                .header(
                        header, 
                        String.format(
                                format,
                                session.getToken().getRawToken()
                        )
                )
                .get(new GenericType<List<RoleSet>> () {});
        client.close();
        return roleSets.stream();
    }

    @Override
    public Stream<Domain> getDomains() throws Exception {
        String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
        String header = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_HEADER, "");
        String format = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_FORMAT, "");
        Client client = ClientBuilder.newClient();
        List<Domain> domains = client.target(url)
                .path("query")
                .path("Domain")
                .request(MediaType.APPLICATION_JSON)
                .header(
                        header, 
                        String.format(
                                format,
                                session.getToken().getRawToken()
                        )
                )
                .get(new GenericType<List<Domain>> () {});
        client.close();
        return domains.stream();
    }

    @Override
    public Stream<Discipline> getDisciplines() throws Exception {
        String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
        String header = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_HEADER, "");
        String format = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_FORMAT, "");
        Client client = ClientBuilder.newClient();
        List<Discipline> disciplines = client.target(url)
                .path("query")
                .path("Discipline")
                .request(MediaType.APPLICATION_JSON)
                .header(
                        header, 
                        String.format(
                                format,
                                session.getToken().getRawToken()
                        )
                )
                .get(new GenericType<List<Discipline>> () {});
        client.close();
        return disciplines.stream();
    }
    
}
