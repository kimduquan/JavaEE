/**
 * 
 */
package epf.service.file;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import epf.service.ClientUtil;
import epf.service.RegistryUtil;
import epf.service.SecurityUtil;
import epf.util.client.Client;
import epf.util.logging.Logging;

/**
 * @author PC
 *
 */
public class FilesTest {
	
	private static String token;
	private static URI filesUrl;
	private static Path tempDir;
	private static final Logger logger = Logging.getLogger(FilesTest.class.getName());
	private Client client;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		token = SecurityUtil.login(null, "any_role1", "any_role");
		filesUrl = RegistryUtil.lookup("file");
		tempDir = Files.createTempDirectory("file");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		SecurityUtil.logOut(null, token);
		Files.walk(tempDir).parallel().forEach(path -> path.toFile().delete());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		client = ClientUtil.newClient(filesUrl);
    	client.authorization(token);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		client.close();
	}

	@Test
	public void test() throws Exception {
		Path file = Files.createTempFile("", "");
		Files.writeString(file, "this is a test");
		try (InputStream input = Files.newInputStream(file)){
			Response response = epf.client.file.Files.createFile(client, input, "test");
			Link link = response.getLink("self");
			Assert.assertNotNull(link);
			try(Client newclient = ClientUtil.newClient(link.getUri())){
				newclient.authorization(token);
				InputStream input2 = newclient
						.request(
						target -> target, 
						req -> req.accept(MediaType.APPLICATION_OCTET_STREAM)
						)
						.get(InputStream.class);
				try(InputStreamReader reader = new InputStreamReader(input2)){
					try(BufferedReader buffer = new BufferedReader(reader)){
						String text = buffer.lines().collect(Collectors.joining());
						Assert.assertEquals("this is a test", text);
					}
				}
				newclient.request(target -> target, req -> req).delete();
			}
		}
		finally {
			file.toFile().delete();
		}
	}

}
