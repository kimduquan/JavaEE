package epf.service.cache;

import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import epf.service.CacheUtil;
import epf.service.SecurityUtil;
import epf.util.websocket.Client;
import org.junit.Test;

@Ignore
public class CacheTest {
	
	private static String token;
	private Client client;
	private List<Object> messages = new ArrayList<>();
    
    @BeforeClass
    public static void beforeClass() throws Exception{
    	token = SecurityUtil.login(null, "any_role1", "any_role");
    }
    
    @AfterClass
    public static void afterClass(){
    	SecurityUtil.logOut(null, token);
    }
    
    @After
    public void after() throws Exception {
    	client.close();
    	messages.clear();
    }
    
    @Before
    public void before() throws Exception {
    	client = CacheUtil.connectToServer();
    	client.onMessage(messages::add);
    }

    @Test
    public void test() {
    	
    }
}
