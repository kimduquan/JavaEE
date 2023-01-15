package epf.tests.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map.Entry;
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
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import epf.client.gateway.GatewayUtil;
import epf.client.util.Client;
import epf.file.util.PathUtil;
import epf.naming.Naming;
import epf.tests.client.ClientUtil;
import epf.tests.security.SecurityUtil;

/**
 * @author PC
 *
 */
public class FilesTest {
	
	@Rule
    public TestName testName = new TestName();
	
	private static Entry<String, String> credential;
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
		credential = SecurityUtil.peekCredential();
		token = SecurityUtil.login(credential.getKey(), credential.getValue());
		filesUrl = GatewayUtil.get(Naming.FILE);
		tempDir = Files.createTempDirectory("file");
		rootDir = Paths.get(System.getProperty("epf.tests.file.root", "")).toAbsolutePath();
		if(Files.exists(rootDir)) {
			try {
				epf.file.util.FileUtil.deleteDirectory(rootDir);
				rootDir.toFile().mkdirs();
			}
			catch(Exception ex) {
				System.err.print(ex.getMessage());
			}
		}
		else {
			rootDir.toFile().mkdirs();
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		SecurityUtil.logOut(token);
		try(Stream<Path> paths = Files.walk(tempDir)){
			paths.forEach(path -> path.toFile().delete());
		}
		tempDir.toFile().delete();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		client = ClientUtil.newClient(filesUrl);
    	client.authorization(token.toCharArray());
    	tempFile = Files.createTempFile(tempDir, "", "");
    	Files.write(tempFile, Arrays.asList("this is a test"));
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		client.close();
		tempFile.toFile().delete();
		ClientUtil.afterClass();
	}

	@Test
	public void testCreateFileOK_User() throws Exception {
		try (InputStream input = Files.newInputStream(tempFile)){
			Response response = epf.client.file.Files.createFile(client, input, PathUtil.of(credential.getKey()));
			Assert.assertEquals("Response.status", Response.Status.OK.getStatusCode(), response.getStatusInfo().getStatusCode());
			Link link = response.getLink("self");
			Assert.assertNotNull("Response.link", link);
			System.out.println("Response.self.uri=" + link.getUri());
			try(Client newclient = ClientUtil.newClient(filesUrl.resolve(link.getUri()))){
				newclient.authorization(token.toCharArray());
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
			Response response = epf.client.file.Files.createFile(client, input, PathUtil.of("Any_Role", credential.getKey()));
			Assert.assertEquals("Response.status", Response.Status.OK.getStatusCode(), response.getStatusInfo().getStatusCode());
			Link link = response.getLink("self");
			Assert.assertNotNull("Response.link", link);
			System.out.println("Response.self.uri=" + link.getUri());
			try(Client newclient = ClientUtil.newClient(filesUrl.resolve(link.getUri()))){
				newclient.authorization(token.toCharArray());
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
			Response response = epf.client.file.Files.createFile(client, input, PathUtil.of("any_role2"));
			Assert.assertEquals("Response.status", Response.Status.FORBIDDEN.getStatusCode(), response.getStatusInfo().getStatusCode());
		}
	}
	
	@Test//(expected = ForbiddenException.class)
	public void testCreateFile_InvalidGroup_ValidUser() throws IOException {
		try (InputStream input = Files.newInputStream(tempFile)){
			Response response = epf.client.file.Files.createFile(client, input, PathUtil.of("Developer", credential.getKey()));
			Assert.assertEquals("Response.status", Response.Status.FORBIDDEN.getStatusCode(), response.getStatusInfo().getStatusCode());
		}
	}
	
	@Test//(expected = ForbiddenException.class)
	public void testCreateFile_ValidGroup_InvalidUser() throws IOException {
		try (InputStream input = Files.newInputStream(tempFile)){
			Response response = epf.client.file.Files.createFile(client, input, PathUtil.of("Any_Role", "any_role2"));
			Assert.assertEquals("Response.status", Response.Status.FORBIDDEN.getStatusCode(), response.getStatusInfo().getStatusCode());
		}
	}
	
	@Test//(expected = ForbiddenException.class)
	public void testCreateFile_InvalidGroup_InvalidUser() throws IOException {
		try (InputStream input = Files.newInputStream(tempFile)){
			Response response = epf.client.file.Files.createFile(client, input, PathUtil.of("Developer", "any_role2"));
			Assert.assertEquals("Response.status", Response.Status.FORBIDDEN.getStatusCode(), response.getStatusInfo().getStatusCode());
		}
	}
	
	@Test
	public void testDeleteFileOK_User() throws Exception {
		try (InputStream input = Files.newInputStream(tempFile)){
			Response response = epf.client.file.Files.createFile(client, input, PathUtil.of(credential.getKey()));
			Link link = response.getLink("self");
			System.out.println("Response.self.uri=" + link.getUri());
			try(Client newclient = ClientUtil.newClient(filesUrl.resolve(link.getUri()))){
				newclient.authorization(token.toCharArray());
				response = newclient.request(target -> target, req -> req).delete();
				Assert.assertEquals("Response.status", Response.Status.OK.getStatusCode(), response.getStatusInfo().getStatusCode());
			}
		}
	}
	
	@Test @Ignore
	public void testDeleteFileOK_Group() throws Exception {
		try (InputStream input = Files.newInputStream(tempFile)){
			Response response = epf.client.file.Files.createFile(client, input, PathUtil.of("Any_Role", credential.getKey()));
			Link link = response.getLink("self");
			System.out.println("Response.self.uri=" + link.getUri());
			try(Client newclient = ClientUtil.newClient(filesUrl.resolve(link.getUri()))){
				response = newclient.request(target -> target, req -> req).delete();
				Assert.assertEquals("Response.status", Response.Status.OK.getStatusCode(), response.getStatusInfo().getStatusCode());
			}
		}
	}
	
	@Test
	public void testDeleteFileOKAfterRead_User() throws Exception {
		Link link = null;
		try (InputStream input = Files.newInputStream(tempFile)){
			Response response = epf.client.file.Files.createFile(client, input, PathUtil.of(credential.getKey()));
			link = response.getLink("self");
			System.out.println("Response.self.uri=" + link.getUri());
		}
		Thread.sleep(100);
		try(Client newclient = ClientUtil.newClient(filesUrl.resolve(link.getUri()))){
			newclient.authorization(token.toCharArray());
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
		}
		try(Client newclient = ClientUtil.newClient(filesUrl.resolve(link.getUri()))){
			newclient.authorization(token.toCharArray());
			Response response = newclient.request(target -> target, req -> req).delete();
			Assert.assertEquals("Response.status", Response.Status.OK.getStatusCode(), response.getStatusInfo().getStatusCode());
		}
	}
	
	@Test//(expected = ForbiddenException.class)
	public void testDelete_InvalidUser() {
		Response response = epf.client.file.Files.delete(client, PathUtil.of("any_role2"));
		Assert.assertEquals("Response.status", Response.Status.FORBIDDEN.getStatusCode(), response.getStatusInfo().getStatusCode());
	}
	
	@Test//(expected = ForbiddenException.class)
	public void testDelete_InvalidGroup_ValidUser() {
		Response response = epf.client.file.Files.delete(client, PathUtil.of("Developer", credential.getKey()));
		Assert.assertEquals("Response.status", Response.Status.FORBIDDEN.getStatusCode(), response.getStatusInfo().getStatusCode());
	}
	
	@Test//(expected = ForbiddenException.class)
	public void testDelete_ValidGroup_InvalidUser() {
		Response response = epf.client.file.Files.delete(client, PathUtil.of("Any_Role", "any_role2"));
		Assert.assertEquals("Response.status", Response.Status.FORBIDDEN.getStatusCode(), response.getStatusInfo().getStatusCode());
	}
	
	@Test//(expected = ForbiddenException.class)
	public void testDelete_InvalidGroup_InvalidUser() {
		Response response = epf.client.file.Files.delete(client, PathUtil.of("Developer", "any_role2"));
		Assert.assertEquals("Response.status", Response.Status.FORBIDDEN.getStatusCode(), response.getStatusInfo().getStatusCode());
	}
	
	@Test(expected = ForbiddenException.class)
	public void testRead_InvalidUser() {
		epf.client.file.Files.read(client, PathUtil.of("any_role2"));
	}
	
	@Test(expected = ForbiddenException.class)
	public void testRead_InvalidGroup_ValidUser() {
		epf.client.file.Files.read(client, PathUtil.of("Developer", credential.getKey()));
	}
	
	@Test
	public void testRead_ValidGroup_InvalidUser() throws Exception {
		Entry<String, String> otherCredential = SecurityUtil.peekCredential();
		String otherToken = SecurityUtil.login(otherCredential.getKey(), otherCredential.getValue());
		Link link;
		try(InputStream input = Files.newInputStream(tempFile)){
			try(Client otherClient = ClientUtil.newClient(filesUrl)){
				otherClient.authorization(otherToken.toCharArray());
				Response response = epf.client.file.Files.createFile(otherClient, input, PathUtil.of("Any_Role", otherCredential.getKey()));
				Assert.assertEquals("Response.status", Response.Status.OK.getStatusCode(), response.getStatusInfo().getStatusCode());
				link = response.getLink("self");
				Assert.assertNotNull("Response.link", link);
			}
		}
		System.out.println("Response.self.uri=" + link.getUri());
		try(Client newclient = ClientUtil.newClient(filesUrl.resolve(link.getUri()))){
			newclient.authorization(token.toCharArray());
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
			otherClient.authorization(otherToken.toCharArray());
			otherClient.request(target -> target, req -> req).delete();
		}
		SecurityUtil.logOut(otherToken);
	}
	
	@Test(expected = ForbiddenException.class)
	public void testRead_InvalidGroup_InvalidUser() {
		epf.client.file.Files.read(client, PathUtil.of("Developer", "any_role2"));
	}
}
