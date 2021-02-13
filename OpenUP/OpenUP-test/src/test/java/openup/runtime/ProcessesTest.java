/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package openup.runtime;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import openup.TestUtil;
import openup.client.security.Header;
import openup.client.security.Security;
import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import epf.client.runtime.Processes;

/**
 *
 * @author FOXCONN
 */
public class ProcessesTest {
    
	private static Header header;
    private static ClientBuilder clientBuilder;
    private static RestClientBuilder restBuilder;
    private static Client client;
    private static Security security;
    private static Processes processes;
    
    @BeforeClass
    public static void beforeClass() throws Exception{
    	clientBuilder = ClientBuilder.newBuilder();
        restBuilder = RestClientBuilder.newBuilder();
        header = TestUtil.buildClient(restBuilder, clientBuilder);
        client = clientBuilder.build();
        security = restBuilder.build(Security.class);
        processes = restBuilder.build(Processes.class);
        TestUtil.login(security, header, "any_role1", "any_role");
    }
    
    @AfterClass
    public static void afterClass() throws Exception{
        processes.stop();
        TestUtil.logout(security, header);
        client.close();
    }
    
    @Test
    public void testStartOK() throws Exception {
        List<String> command = new ArrayList<>();
        command.add("git");
        command.add("pull");
        long pid = processes.start(command, "D:\\projects\\JavaEE2\\OpenUP");
        
        Assert.assertNotEquals("Process", 0, pid);
    }
}
