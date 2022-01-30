/**
 * 
 */
package epf.tests.schedule;

import java.net.URI;
import java.time.Duration;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import epf.client.schedule.Schedule;
import epf.client.util.Client;
import epf.naming.Naming;
import epf.tests.TestUtil;
import epf.tests.client.ClientUtil;
import epf.tests.health.HealthUtil;
import epf.tests.security.SecurityUtil;
import epf.util.config.ConfigUtil;

/**
 * @author PC
 *
 */
public class ScheduleTest {
	
	@Rule
    public TestName testName = new TestName();
	
	static URI scheduleUrl;
	static Client client;
	static String token;
	static epf.util.websocket.Client shell;
	static Queue<String> messages;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		HealthUtil.readỵ̣();
		scheduleUrl = ConfigUtil.getURI(Naming.Schedule.SCHEDULE_URL);
		client = ClientUtil.newClient(scheduleUrl);
		token = SecurityUtil.login();
		client.authorization(token);
		URI messagingUrl = ConfigUtil.getURI(Naming.Messaging.MESSAGING_URL);
		shell = epf.util.websocket.Client.connectToServer(messagingUrl.resolve("schedule/shell"));
		messages = new ConcurrentLinkedQueue<>();
		shell.onMessage(messages::add);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		client.close();
		shell.close();
		messages.clear();
		SecurityUtil.logOut(token);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		messages.clear();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		messages.clear();
	}

	@Test
	public void testSchedule() throws Exception {
		long id = Schedule.schedule(client, Schedule.SHELL, 1, TimeUnit.SECONDS).readEntity(Long.class);
		TestUtil.waitUntil(t -> messages.stream().anyMatch(message -> message.contains(String.valueOf(id))), Duration.ofSeconds(2));
		Assert.assertTrue(messages.stream().anyMatch(message -> message.contains(String.valueOf(id))));
	}
	
	@Test
	public void testCancel() throws Exception {
		long id = Schedule.schedule(client, Schedule.SHELL, 1, TimeUnit.SECONDS).readEntity(Long.class);
		Thread.sleep(80);
		Schedule.cancel(client, Schedule.SHELL, id).getStatus();
		TestUtil.waitUntil(t -> messages.stream().anyMatch(message -> message.contains(String.valueOf(id))), Duration.ofSeconds(1));
		Assert.assertEquals(0, messages.stream().filter(message -> message.contains(String.valueOf(id))).count());
	}
}
