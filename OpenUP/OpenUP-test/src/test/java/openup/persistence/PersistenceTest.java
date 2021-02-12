/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import epf.schema.EPF;
import openup.schema.OpenUP;
import openup.schema.DeliveryProcess;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import openup.TestUtil;
import openup.client.security.Header;
import openup.client.security.Security;
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
    
    private static Header header;
    private static ClientBuilder clientBuilder;
    private static RestClientBuilder restBuilder;
    private static Client client;
    private static Security security;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
        clientBuilder = ClientBuilder.newBuilder();
        restBuilder = RestClientBuilder.newBuilder();
        header = TestUtil.buildClient(restBuilder, clientBuilder);
        client = clientBuilder.build();
        security = restBuilder.build(Security.class);
        TestUtil.login(security, header, "any_role1", "any_role");
    }
    
    @AfterClass
    public static void afterClass() throws Exception{
        TestUtil.logout(security, header);
        client.close();
    }
    
    @Test
    public void testPersistOK() throws Exception{
        List<epf.schema.delivery_processes.DeliveryProcess> epfDPs = client
                .target(TestUtil.url().toString() + "persistence/")
                .path(OpenUP.Schema)
                .path(EPF.DeliveryProcess)
                .matrixParam("name", "OpenUP Lifecycle")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<epf.schema.delivery_processes.DeliveryProcess>>() {});
        
        epf.schema.delivery_processes.DeliveryProcess epfDP = epfDPs.get(0);
        DeliveryProcess dp = new DeliveryProcess();
        dp.setDeliveryProcess(epfDP);
        dp.setId((long)1);
        dp.setName("OpenUP Lifecycle 1");
        dp.setSummary("OpenUP Lifecycle 1");
        client.target(TestUtil.url().toString() + "persistence/")
                .path(OpenUP.Schema)
                .path(OpenUP.DeliveryProcess)
                .path(String.valueOf(dp.getId()))
                .request()
                .post(Entity.json(dp));
        
        List<DeliveryProcess> deliveryProcesses = client
        		.target(TestUtil.url().toString() + "persistence/")
                .path(OpenUP.Schema)
                .path(OpenUP.DeliveryProcess)
                .matrixParam("name", "OpenUP Lifecycle 1")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<DeliveryProcess>>(){});
        
        Assert.assertFalse("DeliveryProcesses.isEmpty", deliveryProcesses.isEmpty());
        Assert.assertEquals("DeliveryProcesses.size", 1, deliveryProcesses.size());
        DeliveryProcess deliveryProcess = deliveryProcesses.get(0);
        Assert.assertNotNull("DeliveryProcess", deliveryProcess);
        Assert.assertEquals("DeliveryProcess.Name", "OpenUP Lifecycle 1", deliveryProcess.getName());
        Assert.assertEquals("DeliveryProcess.Summary", "OpenUP Lifecycle 1", deliveryProcess.getSummary());
        Assert.assertNotNull("DeliveryProcess.DeliveryProcess", deliveryProcess.getDeliveryProcess());
        Assert.assertEquals("DeliveryProcess.DeliveryProcess.Name", "OpenUP Lifecycle", deliveryProcess.getDeliveryProcess().getName());
        
        client.target(TestUtil.url().toString() + "persistence/")
                .path(OpenUP.Schema)
                .path(OpenUP.DeliveryProcess)
                .path(String.valueOf(deliveryProcess.getId()))
                .request()
                .delete();
    }
}
