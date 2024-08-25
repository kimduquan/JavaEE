package epf.util.io;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class InputStreamUtilTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testReadString() {
		
	}

	@Test
	public void testEmpty() throws Exception {
		InputStream empty = InputStreamUtil.empty();
		Assert.assertEquals("InputStream.available", 0, empty.available());
	}

	@Test
	public void testJoining() throws Exception {
		InputStream empty = InputStreamUtil.empty();
		InputStream streams = Arrays.asList(new ByteArrayInputStream(new byte[2]),
				new ByteArrayInputStream(new byte[3]),
				new ByteArrayInputStream(new byte[1]),
				new ByteArrayInputStream(new byte[4]))
		.stream()
		.collect(InputStreamUtil.joining(Optional.of(empty)));
		byte[] bytes = streams.readAllBytes();
		Assert.assertArrayEquals("InputStream.readAllBytes", new byte[10], bytes);
	}

}
