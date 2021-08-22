/**
 * 
 */
package epf.util.websocket;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.websocket.EncodeException;
import javax.websocket.Session;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

/**
 * @author PC
 *
 */
public class MessageQueueTest {
	
	static ExecutorService executor;
	MessageQueue queue;
	Session session;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		executor = Executors.newFixedThreadPool(5);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		executor.shutdownNow();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		session = Mockito.mock(Session.class);
		queue = new MessageQueue(session);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		queue.close();
	}

	/**
	 * Test method for {@link epf.util.websocket.MessageQueue#MessageQueue(javax.websocket.Session)}.
	 */
	@Test
	public void testMessageQueue() {
		
	}

	/**
	 * Test method for {@link epf.util.websocket.MessageQueue#run()}.
	 * @throws EncodeException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	@Test
	public void testRun() throws IOException, EncodeException, InterruptedException {
		executor.submit(queue);
		Message message1 = Mockito.mock(Message.class);
		Message message2 = Mockito.mock(Message.class);
		
		Mockito.doNothing().when(message1).send(Mockito.same(session));
		Mockito.doNothing().when(message2).send(Mockito.same(session));
		
		InOrder inOrder = Mockito.inOrder(message1, message2);
		queue.add(message1);
		queue.add(message2);
		Thread.sleep(80);
		inOrder.verify(message1, Mockito.times(1)).send(Mockito.same(session));
		inOrder.verify(message2, Mockito.times(1)).send(Mockito.same(session));
	}

	/**
	 * Test method for {@link epf.util.websocket.MessageQueue#close()}.
	 */
	@Test
	public void testClose() {
		
	}

	/**
	 * Test method for {@link epf.util.websocket.MessageQueue#add(epf.util.websocket.Message)}.
	 */
	@Test
	public void testAdd() {
		
	}

}
