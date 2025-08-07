package epf.tests.service.script;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import epf.client.script.Script;
import epf.tests.client.ClientUtil;
import epf.tests.service.RegistryUtil;
import epf.tests.service.SecurityUtil;
import epf.util.client.Client;

public class ScriptTest {
	
	static URI scriptUrl;
	static String token;
	Client client;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		scriptUrl = RegistryUtil.lookup("script", null);
		token = SecurityUtil.login(null, "any_role1", "any_role");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		SecurityUtil.logOut(null, token);
	}

	@Before
	public void setUp() throws Exception {
		client = ClientUtil.newClient(scriptUrl);
		client.authorization(token);
	}

	@After
	public void tearDown() throws Exception {
		client.close();
	}

	@Test
	@Ignore
	public void testEvalOk() throws IOException {
		String script = "return \'this is a test\';";
		try(ByteArrayInputStream input = new ByteArrayInputStream(script.getBytes())){
			String result = Script.eval(client, "JavaScript", input, String.class);
			Assert.assertEquals("this is a test", result);
		}
	}
}
