/**
 * 
 */
package epf.tests.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import epf.tests.client.ClientUtil;
import epf.tests.registry.RegistryUtil;
import epf.tests.security.SecurityUtil;
import epf.util.client.Client;

/**
 * @author PC
 *
 */
public class FilesTest {
	
	private static String token;
	private static URI filesUrl;
	private static Path tempDir;
	private static Path rootDir;
	private Client client;
	private Path tempFile;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		token = SecurityUtil.login(null, "any_role1", "any_role");
		filesUrl = RegistryUtil.lookup("file", null);
		tempDir = Files.createTempDirectory("file");
		rootDir = Paths.get(System.getProperty("epf.tests.file.root", "")).toAbsolutePath();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		SecurityUtil.logOut(null, token);
		try(Stream<Path> paths = Files.walk(tempDir)){
			paths.forEach(path -> path.toFile().delete());
		}
		tempDir.toFile().delete();
		try(Stream<Path> paths = Files.walk(rootDir)){
			paths.forEach(path -> path.toFile().delete());
		}
		rootDir.toFile().delete();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		client = ClientUtil.newClient(filesUrl);
    	client.authorization(token);
    	tempFile = Files.createTempFile(tempDir, "", "");
    	Files.writeString(tempFile, "this is a test");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		client.close();
		tempFile.toFile().delete();
	}

	@Test
	public void testCreateFileOK_User() throws Exception {
		try (InputStream input = Files.newInputStream(tempFile)){
			Response response = epf.client.file.Files.createFile(client, input, Path.of("any_role1"));
			Assert.assertEquals("Response.status", Response.Status.OK.getStatusCode(), response.getStatusInfo().getStatusCode());
			Link link = response.getLink("self");
			Assert.assertNotNull("Response.link", link);
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
	}

	@Test
	public void testCreateFileOK_Group() throws Exception {
		try (InputStream input = Files.newInputStream(tempFile)){
			Response response = epf.client.file.Files.createFile(client, input, Path.of("Any_Role", "any_role1"));
			Assert.assertEquals("Response.status", Response.Status.OK.getStatusCode(), response.getStatusInfo().getStatusCode());
			Link link = response.getLink("self");
			Assert.assertNotNull("Response.link", link);
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
	}
	
	@Test//(expected = ForbiddenException.class)
	public void testCreateFile_InvalidUser() throws IOException {
		try (InputStream input = Files.newInputStream(tempFile)){
			Response response = epf.client.file.Files.createFile(client, input, Path.of("any_role2"));
			Assert.assertEquals("Response.status", Response.Status.FORBIDDEN.getStatusCode(), response.getStatusInfo().getStatusCode());
		}
	}
	
	@Test//(expected = ForbiddenException.class)
	public void testCreateFile_InvalidGroup_ValidUser() throws IOException {
		try (InputStream input = Files.newInputStream(tempFile)){
			Response response = epf.client.file.Files.createFile(client, input, Path.of("Developer", "any_role1"));
			Assert.assertEquals("Response.status", Response.Status.FORBIDDEN.getStatusCode(), response.getStatusInfo().getStatusCode());
		}
	}
	
	@Test//(expected = ForbiddenException.class)
	public void testCreateFile_ValidGroup_InvalidUser() throws IOException {
		try (InputStream input = Files.newInputStream(tempFile)){
			Response response = epf.client.file.Files.createFile(client, input, Path.of("Any_Role", "any_role2"));
			Assert.assertEquals("Response.status", Response.Status.FORBIDDEN.getStatusCode(), response.getStatusInfo().getStatusCode());
		}
	}
	
	@Test//(expected = ForbiddenException.class)
	public void testCreateFile_InvalidGroup_InvalidUser() throws IOException {
		try (InputStream input = Files.newInputStream(tempFile)){
			Response response = epf.client.file.Files.createFile(client, input, Path.of("Developer", "any_role2"));
			Assert.assertEquals("Response.status", Response.Status.FORBIDDEN.getStatusCode(), response.getStatusInfo().getStatusCode());
		}
	}
	
	@Test//(expected = ForbiddenException.class)
	public void testDelete_InvalidUser() {
		Response response = epf.client.file.Files.delete(client, Path.of("any_role2"));
		Assert.assertEquals("Response.status", Response.Status.FORBIDDEN.getStatusCode(), response.getStatusInfo().getStatusCode());
	}
	
	@Test//(expected = ForbiddenException.class)
	public void testDelete_InvalidGroup_ValidUser() {
		Response response = epf.client.file.Files.delete(client, Path.of("Developer", "any_role1"));
		Assert.assertEquals("Response.status", Response.Status.FORBIDDEN.getStatusCode(), response.getStatusInfo().getStatusCode());
	}
	
	@Test//(expected = ForbiddenException.class)
	public void testDelete_ValidGroup_InvalidUser() {
		Response response = epf.client.file.Files.delete(client, Path.of("Any_Role", "any_role2"));
		Assert.assertEquals("Response.status", Response.Status.FORBIDDEN.getStatusCode(), response.getStatusInfo().getStatusCode());
	}
	
	@Test//(expected = ForbiddenException.class)
	public void testDelete_InvalidGroup_InvalidUser() {
		Response response = epf.client.file.Files.delete(client, Path.of("Developer", "any_role2"));
		Assert.assertEquals("Response.status", Response.Status.FORBIDDEN.getStatusCode(), response.getStatusInfo().getStatusCode());
	}
	
	@Test(expected = ForbiddenException.class)
	public void testLines_InvalidUser() {
		epf.client.file.Files.read(client, Path.of("any_role2"));
	}
	
	@Test(expected = ForbiddenException.class)
	public void testLines_InvalidGroup_ValidUser() {
		epf.client.file.Files.read(client, Path.of("Developer", "any_role1"));
	}
	
	@Test
	public void testLines_ValidGroup_InvalidUser() throws Exception {
		String otherToken = SecurityUtil.login(null, "developer1", "developer");
		Link link;
		try(InputStream input = Files.newInputStream(tempFile)){
			try(Client otherClient = ClientUtil.newClient(filesUrl)){
				otherClient.authorization(otherToken);
				Response response = epf.client.file.Files.createFile(otherClient, input, Path.of("Any_Role", "developer1"));
				Assert.assertEquals("Response.status", Response.Status.OK.getStatusCode(), response.getStatusInfo().getStatusCode());
				link = response.getLink("self");
				Assert.assertNotNull("Response.link", link);
			}
		}
		try(Client newclient = ClientUtil.newClient(link.getUri())){
			newclient.authorization(token);
			InputStream input = newclient
					.request(
					target -> target, 
					req -> req.accept(MediaType.APPLICATION_OCTET_STREAM)
					)
					.get(InputStream.class);
			try(InputStreamReader reader = new InputStreamReader(input)){
				try(BufferedReader buffer = new BufferedReader(reader)){
					String text = buffer.lines().collect(Collectors.joining());
					Assert.assertEquals("this is a test", text);
				}
			}
		}
		try(Client otherClient = ClientUtil.newClient(filesUrl)){
			otherClient.authorization(otherToken);
			otherClient.request(target -> target, req -> req).delete();
		}
	}
	
	@Test(expected = ForbiddenException.class)
	public void testLines_InvalidGroup_InvalidUser() {
		epf.client.file.Files.read(client, Path.of("Developer", "any_role2"));
	}
}
