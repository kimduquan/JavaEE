package epf.client.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class EntityOutputTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEntityOutput() {
		InputStream mockInputStream = Mockito.mock(InputStream.class);
		new EntityOutput(mockInputStream);
	}
	
	@Test(expected = NullPointerException.class)
	public void testEntityOutput_NullInput() {
		new EntityOutput(null);
	}

	@Test
	public void testWrite() throws Exception {
		Path input = Files.createTempFile("EntityOutput", ".input");
		Path out = Files.createTempFile("EntityOutput", ".output");
		EntityOutput output = new EntityOutput(Files.newInputStream(input));
		output.write(Files.newOutputStream(out));
		input.toFile().delete();
		out.toFile().delete();
	}

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
