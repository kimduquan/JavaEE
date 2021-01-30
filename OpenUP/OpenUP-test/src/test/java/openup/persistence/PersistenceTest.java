/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import epf.schema.EPF;
import epf.schema.OpenUP;
import epf.schema.openup.DeliveryProcess;
import java.util.ArrayList;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import openup.TestUtil;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author FOXCONN
 */
public class PersistenceTest {
    
    private static String token;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
        TestUtil.beforeClass();
        token = TestUtil.login("any_role1", "any_role");
    }
    
    @AfterClass
    public static void afterClass() throws Exception{
        TestUtil.logout(token);
        TestUtil.afterClass();
    }
    
    @Test
    public void testPersistOK() throws Exception{
        epf.schema.delivery_processes.DeliveryProcess epfDP = TestUtil
                .client()
                .target(TestUtil.url().toString() + "persistence/")
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
        TestUtil.client().target(TestUtil.url().toString() + "persistence/")
                .path(OpenUP.Schema)
                .path(OpenUP.DeliveryProcess)
                .path(String.valueOf(dp.getId()))
                .request()
                .post(Entity.json(dp));
        
        ArrayList<DeliveryProcess> deliveryProcesses = TestUtil.client().target(TestUtil.url().toString() + "persistence/")
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
        
        TestUtil.client().target(TestUtil.url().toString() + "persistence/")
                .path(OpenUP.Schema)
                .path(OpenUP.DeliveryProcess)
                .path(String.valueOf(deliveryProcess.getId()))
                .request()
                .delete();
    }
}
