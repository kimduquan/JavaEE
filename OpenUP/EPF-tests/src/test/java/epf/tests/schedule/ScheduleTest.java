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
import org.junit.Test;
import epf.client.schedule.Schedule;
import epf.tests.TestUtil;
import epf.tests.client.ClientUtil;
import epf.tests.health.HealthUtil;
import epf.util.client.Client;
import epf.util.config.ConfigUtil;

/**
 * @author PC
 *
 */
public class ScheduleTest {
	
	static URI scheduleUrl;
	static Client client;
	static epf.util.websocket.Client shell;
	static Queue<String> messages;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		HealthUtil.readỵ̣();
		scheduleUrl = ConfigUtil.getURI(epf.client.schedule.Schedule.SCHEDULE_URL);
		client = ClientUtil.newClient(scheduleUrl);
		URI messagingUrl = ConfigUtil.getURI(epf.client.messaging.Messaging.MESSAGING_URL);
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
