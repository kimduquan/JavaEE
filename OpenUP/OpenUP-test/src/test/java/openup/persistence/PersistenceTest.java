/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import epf.schema.EPF;
import epf.schema.OpenUP;
import epf.schema.openup.DeliveryProcess;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import openup.client.config.ConfigNames;
import openup.client.security.Header;
import openup.client.security.PasswordHash;
import openup.client.security.Security;
import openup.client.ssl.DefaultHostnameVerifier;
import openup.client.ssl.DefaultSSLContext;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author FOXCONN
 */
public class PersistenceTest {
    
    private static RestClientBuilder restBuilder;
    private static SSLContext sslContext;
    private static ClientBuilder builder;
    private static URL url;
    private static Header header;
    
    private static Security security;
    private static Client client;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
        url = new URL(System.getProperty(ConfigNames.OPENUP_GATEWAY_URL, ""));
        sslContext = DefaultSSLContext.build();
        header = new Header();
        
        restBuilder = RestClientBuilder.newBuilder()
                .hostnameVerifier(new DefaultHostnameVerifier())
                .sslContext(sslContext)
                .register(JacksonJsonProvider.class)
                .baseUrl(url)
                .register(header);
        
        builder = ClientBuilder.newBuilder()
                .hostnameVerifier(new DefaultHostnameVerifier())
                .sslContext(sslContext)
                .register(JacksonJsonProvider.class)
                .register(header);
        
        client = builder.build();
        security = restBuilder.build(Security.class);
        
        String token = security.login("OpenUP", "any_role1", PasswordHash.hash("any_role1", "any_role".toCharArray()), url);
        header.setToken(token);
    }
    
    @AfterClass
    public static void afterClass(){
        client.close();
        try {
            security.logOut("OpenUP");
        } 
        catch (Exception ex) {
            Logger.getLogger(PersistenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //@Test
    public void testPersistOK() throws Exception{
        epf.schema.delivery_processes.DeliveryProcess epfDP = client
                .target(url.toString() + "persistence/")
                .path(OpenUP.Schema)
                .path(EPF.DeliveryProcess)
                .matrixParam("name", "OpenUP Lifecycle")
                .request(MediaType.APPLICATION_JSON)
                .get(epf.schema.delivery_processes.DeliveryProcess.class);
        
        DeliveryProcess dp = new DeliveryProcess();
        dp.setDeliveryProcess(epfDP);
        dp.setId((long)1);
        dp.setName("OpenUP Lifecycle 1");
        dp.setSummary("OpenUP Lifecycle 1");
        client.target(url.toString() + "persistence/")
                .path(OpenUP.Schema)
                .path(OpenUP.DeliveryProcess)
                .path(String.valueOf(dp.getId()))
                .request()
                .post(Entity.json(dp));
        
        ArrayList<DeliveryProcess> deliveryProcesses = client.target(url.toString() + "persistence/")
                .path(OpenUP.Schema)
                .path(OpenUP.DeliveryProcess)
                .matrixParam("name", "OpenUP Lifecycle 1")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<ArrayList<DeliveryProcess>>(){});
        
        Assert.assertFalse("DeliveryProcesses.isEmpty", deliveryProcesses.isEmpty());
        Assert.assertEquals("DeliveryProcesses.size", 1, deliveryProcesses.size());
        DeliveryProcess deliveryProcess = deliveryProcesses.get(0);
        Assert.assertNotNull("DeliveryProcess", deliveryProcess);
        Assert.assertEquals("DeliveryProcess.Name", "OpenUP Lifecycle 1", deliveryProcess.getName());
        Assert.assertEquals("DeliveryProcess.Summary", "OpenUP Lifecycle 1", deliveryProcess.getSummary());
        Assert.assertNotNull("DeliveryProcess.DeliveryProcess", deliveryProcess.getDeliveryProcess());
        Assert.assertEquals("DeliveryProcess.DeliveryProcess.Name", "OpenUP Lifecycle", deliveryProcess.getDeliveryProcess().getName());
        
        client.target(url.toString() + "persistence/")
                .path(OpenUP.Schema)
                .path(OpenUP.DeliveryProcess)
                .path(String.valueOf(deliveryProcess.getId()))
                .request()
                .delete();
    }
}
