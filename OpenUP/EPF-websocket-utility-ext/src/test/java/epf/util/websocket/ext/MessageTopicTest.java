package epf.util.websocket.ext;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Session;
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
public class MessageTopicTest {
	
	static ExecutorService executor;
	MessageTopic topic;
	Session session1;
	Session session2;
	MessageHandler handler1;
	MessageHandler handler2;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		executor = Executors.newFixedThreadPool(2);
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
		session1 = Mockito.mock(Session.class);
		Mockito.when(session1.getId()).thenReturn("1");
		session2 = Mockito.mock(Session.class);
		Mockito.when(session2.getId()).thenReturn("2");
		handler1 = new MessageHandler(session1);
		handler2 = new MessageHandler(session2);
		topic = new MessageTopic();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link epf.util.websocket.MessageTopic#MessageTopic()}.
	 */
	@Test
	public void testMessageTopic() {
		
	}

	/**
	 * Test method for {@link epf.util.websocket.MessageTopic#run()}.
	 * @throws EncodeException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	@Test
	public void testRun() throws IOException, EncodeException, InterruptedException {
		topic.addConsumer("1", handler1);
		topic.addConsumer("2", handler2);
		executor.submit(topic);
		Message message1 = Mockito.mock(Message.class);
		Message message2 = Mockito.mock(Message.class);
		
		Mockito.doNothing().when(message1).send(Mockito.same(session1));
		Mockito.doNothing().when(message1).send(Mockito.same(session2));
		Mockito.doNothing().when(message2).send(Mockito.same(session1));
		Mockito.doNothing().when(message2).send(Mockito.same(session2));
		
		InOrder inOrder = Mockito.inOrder(message1, message2);
		topic.add(message1);
		topic.add(message2);
		Thread.sleep(160);
		inOrder.verify(message1, Mockito.times(1)).send(Mockito.same(session1));
		inOrder.verify(message2, Mockito.times(1)).send(Mockito.same(session1));
		//inOrder.verify(message1, Mockito.times(1)).send(Mockito.same(session2));
		//inOrder.verify(message2, Mockito.times(1)).send(Mockito.same(session2));
		topic.close();
	}

	/**
	 * Test method for {@link epf.util.websocket.MessageTopic#close()}.
	 * @throws IOException 
	 */
	@Test
	public void testClose() throws IOException {
		executor.submit(topic);
		topic.close();
	}

	/**
	 * Test method for {@link epf.util.websocket.MessageTopic#add(epf.util.websocket.Message)}.
	 */
	@Test
	public void testAdd() {
		
	}

	/**
	 * Test method for {@link epf.util.websocket.MessageTopic#addSession(javax.websocket.Session)}.
	 */
	@Test
	public void testAddSession() {
		
	}

	/**
	 * Test method for {@link epf.util.websocket.MessageTopic#removeSession(javax.websocket.Session)}.
	 */
	@Test
	public void testRemoveSession() {
		topic.removeConsumer("2");
	}

}
