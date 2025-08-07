package epf.tests.schedule;

import java.net.URI;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.TestName;
import epf.client.util.Client;
import epf.messaging.client.Messaging;
import epf.naming.Naming;
import epf.tests.client.ClientUtil;
import epf.tests.security.SecurityUtil;
import epf.util.config.ConfigUtil;

public class ScheduleTest {
	
	@Rule
    public TestName testName = new TestName();
	
	static URI scheduleUrl;
	static Client client;
	static String token;
	static epf.util.websocket.Client shell;
	static Queue<String> messages;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		scheduleUrl = ConfigUtil.getURI(Naming.Schedule.SCHEDULE_URL);
		client = ClientUtil.newClient(scheduleUrl);
		token = SecurityUtil.login();
		client.authorization(token.toCharArray());
		URI messagingUrl = ConfigUtil.getURI(Naming.Gateway.MESSAGING_URL);
		URI url = Messaging.getUrl(messagingUrl, Optional.empty(), Naming.SCHEDULE, Optional.of(token), Naming.SHELL);
		shell = epf.util.websocket.Client.connectToServer(url);
    	System.out.println("client.session.id=" + shell.getSession().getId());
		messages = new ConcurrentLinkedQueue<>();
		shell.onMessage((message, session) -> messages.add(message));
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		client.close();
		shell.close();
		messages.clear();
		SecurityUtil.logOut(token);
		ClientUtil.afterClass();
	}

	@Before
	public void setUp() throws Exception {
		messages.clear();
	}

	@After
	public void tearDown() throws Exception {
		messages.clear();
	}
}
