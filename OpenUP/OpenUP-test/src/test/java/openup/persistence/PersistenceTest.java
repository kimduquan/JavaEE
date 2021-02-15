/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.persistence;

import epf.client.persistence.Queries;
import epf.client.persistence.Target;
import epf.client.security.Header;
import epf.client.security.Security;
import epf.schema.EPF;
import openup.schema.OpenUP;
import openup.schema.Artifact;
import openup.schema.DeliveryProcess;
import java.util.List;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import openup.TestUtil;
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
    private static Queries queries;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
        clientBuilder = ClientBuilder.newBuilder();
        restBuilder = RestClientBuilder.newBuilder();
        header = TestUtil.buildClient(restBuilder, clientBuilder);
        client = clientBuilder.build();
        security = restBuilder.build(Security.class);
        queries = restBuilder.build(Queries.class);
        TestUtil.login(security, header, "any_role1", "any_role");
    }
    
    @AfterClass
    public static void afterClass() throws Exception{
        TestUtil.logout(security, header);
        client.close();
    }
    
    @Test
    public void testPersistOK() throws Exception{
        List<epf.schema.work_products.Artifact> epfArtifacts = client
                .target(TestUtil.url().toString() + "persistence/")
                .path(OpenUP.Schema)
                .path(EPF.Artifact)
                .matrixParam("name", "Work Items List")
                .request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<epf.schema.work_products.Artifact>>() {});
        
        epf.schema.work_products.Artifact epfArtifact = epfArtifacts.get(0);
        Artifact artifact = new Artifact();
        artifact.setArtifact(epfArtifact);
        artifact.setName("Artifact 1");
        artifact.setSummary("Artifact 1 Summary");
        artifact = client.target(TestUtil.url().toString() + "persistence/")
                .path(OpenUP.Schema)
                .path(OpenUP.Artifact)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(artifact), Artifact.class);
        
        Assert.assertNotNull("Artifact", artifact);
        Assert.assertNotNull("Artifact.id", artifact.getId());
        Assert.assertEquals("Artifact.name", "Artifact 1", artifact.getName());
        Assert.assertEquals("Artifact.summary", "Artifact 1 Summary", artifact.getSummary());
        Assert.assertNotNull("Artifact.artifact", artifact.getArtifact());
        Assert.assertEquals("Artifact.artifact.name", "Work Items List", artifact.getArtifact().getName());
        
        client.target(TestUtil.url().toString() + "persistence/")
                .path(OpenUP.Schema)
                .path(OpenUP.Artifact)
                .path(String.valueOf(artifact.getId()))
                .request()
                .delete();
    }
    
    @Test(expected = ForbiddenException.class)
    public void testPersist_InvalidPermission() throws Exception {
    	DeliveryProcess dp = new DeliveryProcess();
        dp.setName("OpenUP Lifecycle 1");
        dp.setSummary("OpenUP Lifecycle 1");
        dp = client.target(TestUtil.url().toString() + "persistence/")
                .path(OpenUP.Schema)
                .path(OpenUP.DeliveryProcess)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.json(dp), DeliveryProcess.class);
    }
    
    @Test
    public void testSearchOK() throws Exception {
    	List<Target> results = queries.search("Any", 0, 100);
    	Assert.assertNotEquals("results", 0, results.size());
    	results.forEach(target -> {
    		Assert.assertNotNull("Target", target);
    		Assert.assertNotNull("Target.path", target.getPath());
    		Assert.assertNotEquals("Target.path", "", target.getPath());
    		Response response = client.target(TestUtil.url().toString() + "persistence/" + target.getPath())
    		.request(MediaType.APPLICATION_JSON)
    		.get();
    		Assert.assertEquals("Response.status", Status.OK.getStatusCode(), response.getStatus());
    	});
    }
}
