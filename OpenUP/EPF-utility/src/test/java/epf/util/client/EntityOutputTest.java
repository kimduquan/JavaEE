/**
 * 
 */
package epf.util.client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author PC
 *
 */
public class EntityOutputTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link epf.util.client.EntityOutput#EntityOutput(java.io.InputStream)}.
	 */
	@Test
	public void testEntityOutput() {
		InputStream mockInputStream = Mockito.mock(InputStream.class);
		new EntityOutput(mockInputStream);
	}
	
	/**
	 * Test method for {@link epf.util.client.EntityOutput#EntityOutput(java.io.InputStream)}.
	 */
	@Test(expected = NullPointerException.class)
	public void testEntityOutput_NullInput() {
		new EntityOutput(null);
	}

	/**
	 * Test method for {@link epf.util.client.EntityOutput#write(java.io.OutputStream)}.
	 * @throws Exception 
	 */
	@Test
	public void testWrite() throws Exception {
		Path input = Files.createTempFile("EntityOutput", ".input");
		Path out = Files.createTempFile("EntityOutput", ".output");
		EntityOutput output = new EntityOutput(Files.newInputStream(input));
		output.write(Files.newOutputStream(out));
		input.toFile().delete();
		out.toFile().delete();
	}

	/**
	 * Test method for {@link epf.util.client.EntityOutput#write(java.io.OutputStream)}.
	 * @throws Exception 
	 */
	@Test(expected = IOException.class)
	public void testWrite_ReadonlyOutput() throws Exception {
		Path input = Files.createTempFile("EntityOutput", ".input");
		Path out = Files.createTempFile("EntityOutput", ".output");
		input.toFile().setReadable(false);
		input.toFile().setWritable(false);
		out.toFile().setReadable(false);
		out.toFile().setWritable(false);
		EntityOutput output = new EntityOutput(Files.newInputStream(input));
		try {
			output.write(Files.newOutputStream(out));
		}
		finally {
			input.toFile().delete();
			out.toFile().delete();
		}
	}
}
