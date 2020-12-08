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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import openup.client.Client;
import openup.webapp.Session;
import openup.client.config.ConfigNames;
import openup.client.config.ConfigSource;
import openup.client.security.Security;

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
        String header = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_HEADER, Security.REQUEST_HEADER_NAME);
        String format = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_FORMAT, Security.REQUEST_HEADER_FORMAT);
        List<DeliveryProcesses> deliveryProcesses = new ArrayList<>();
        try(Client client = new Client(url)){
            try(Response response = client
                    .getWebTarget()
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
                    .get()){
                deliveryProcesses = response.readEntity(new GenericType<List<DeliveryProcesses>> () {});
            }
        }
        return deliveryProcesses.stream();
    }

    @Override
    public Stream<RoleSet> getRoleSets() throws Exception {
        String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
        String header = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_HEADER, Security.REQUEST_HEADER_NAME);
        String format = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_FORMAT, Security.REQUEST_HEADER_FORMAT);
        List<RoleSet> roleSets = new ArrayList<>();
        try(Client client = new Client(url)){
            try(Response response = client
                    .getWebTarget()
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
                    .get()){
                roleSets = response.readEntity(new GenericType<List<RoleSet>> () {});
            }
        }
        return roleSets.stream();
    }

    @Override
    public Stream<Domain> getDomains() throws Exception {
        String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
        String header = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_HEADER, Security.REQUEST_HEADER_NAME);
        String format = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_FORMAT, Security.REQUEST_HEADER_FORMAT);
        List<Domain> domains = new ArrayList<>();
        try(Client client = new Client(url)){
            try(Response response = client
                    .getWebTarget()
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
                    .get()){
                domains = response.readEntity(new GenericType<List<Domain>> () {});
            }
        }
        return domains.stream();
    }

    @Override
    public Stream<Discipline> getDisciplines() throws Exception {
        String url = config.getConfig(ConfigNames.OPENUP_PERSISTENCE_URL, "");
        String header = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_HEADER, Security.REQUEST_HEADER_NAME);
        String format = config.getConfig(ConfigNames.OPENUP_SECURITY_JWT_FORMAT, Security.REQUEST_HEADER_FORMAT);
        List<Discipline> disciplines = new ArrayList<>();
        try(Client client = new Client(url)){
            try(Response response = client
                    .getWebTarget()
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
                    .get()){
                disciplines = response.readEntity(new GenericType<List<Discipline>> () {});
            }
        }
        return disciplines.stream();
    }
    
}
