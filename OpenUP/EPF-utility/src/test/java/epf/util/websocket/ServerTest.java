/**
 * 
 */
package epf.util.websocket;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author PC
 *
 */
public class ServerTest {
	
	Session session;
	Server server;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		session = Mockito.mock(Session.class);
		server = new Server();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link epf.util.websocket.Server#onOpen(javax.websocket.Session)}.
	 */
	@Test
	public void testOnOpen() {
		String id = String.valueOf(Instant.now().toEpochMilli());
		Mockito.when(session.getId()).thenReturn(id);
		server.onOpen(session);
		Session ss = server.getSession(id);
		Assert.assertSame(session, ss);
	}

	/**
	 * Test method for {@link epf.util.websocket.Server#onClose(javax.websocket.Session, javax.websocket.CloseReason)}.
	 */
	@Test
	public void testOnClose() {
		String id = String.valueOf(Instant.now().toEpochMilli());
		Mockito.when(session.getId()).thenReturn(id);
		server.onOpen(session);
		Session ss = server.getSession(id);
		Assert.assertSame(session, ss);
		server.onClose(session, null);
		ss = server.getSession(id);
		Assert.assertNull(ss);
	}

	/**
	 * Test method for {@link epf.util.websocket.Server#forEach(java.util.function.Consumer)}.
	 */
	@Test
	public void testForEach() {
		String id = String.valueOf(Instant.now().toEpochMilli());
		Mockito.when(session.getId()).thenReturn(id);
		Mockito.when(session.isOpen()).thenReturn(true);
		server.onOpen(session);
		List<Session> sessions = new ArrayList<>();
		server.forEach(sessions::add);
		Assert.assertEquals(1, sessions.size());
		Assert.assertSame(session, sessions.get(0));
		
		Mockito.when(session.isOpen()).thenReturn(false);
		sessions = new ArrayList<>();
		server.forEach(sessions::add);
		Assert.assertTrue(sessions.isEmpty());
	}

}
