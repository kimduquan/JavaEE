/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.service.system;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import epf.client.system.ProcessInfo;
import epf.client.system.Processes;
import epf.service.ClientUtil;
import epf.service.RegistryUtil;
import epf.service.SecurityUtil;
import epf.util.client.Client;

/**
 *
 * @author FOXCONN
 */
public class ProcessesTest {
    
	private static String token;
	private static URI processesUrl;
	private Client client;
    
    @BeforeClass
    public static void beforeClass(){
    	token = SecurityUtil.login(null, "any_role1", "any_role");
        processesUrl = RegistryUtil.lookup("system");
    }
    
    @AfterClass
    public static void afterClass(){
    	SecurityUtil.logOut(null, token);
    }
    
    @After
    public void after() throws Exception {
    	client.close();
    }
    
    @Before
    public void before() {
    	client = ClientUtil.newClient(processesUrl);
    	client.authorization(token);
    }
    
    @Test
    @Ignore
    public void testStartOK() {
        long pid = Processes.start(client, "D:\\", "java", "-version");
        Assert.assertNotEquals("Process.pid", 0, pid);
        ProcessInfo info = Processes.info(client, pid);
        Assert.assertNotNull("ProcessInfo", info);
        Assert.assertArrayEquals("ProcessInfo.arguments", new String[] { "-version" }, info.getArguments());
        Assert.assertEquals("ProcessInfo.command", "java", info.getCommand());
        Assert.assertEquals("ProcessInfo.commandLine", "java -version", info.getCommandLine());
        Assert.assertEquals("ProcessInfo.start", "java", info.getStart().toString());
        Assert.assertEquals("ProcessInfo.totalCpu.seconds", 1, info.getTotalCpu().getSeconds());
        Assert.assertEquals("ProcessInfo.user", "PC", info.getUser());
        List<ProcessInfo> processes = new ArrayList<>();
        processes.addAll(Processes.getProcesses(client, true));
        processes.addAll(Processes.getProcesses(client, false));
        Assert.assertFalse("List<ProcessInfo>.emtpty", processes.isEmpty());
        int exitValue = Processes.destroy(client, pid);
        Assert.assertEquals("exitValue", 0, exitValue);
    	Processes.stop(client);
    }
}
