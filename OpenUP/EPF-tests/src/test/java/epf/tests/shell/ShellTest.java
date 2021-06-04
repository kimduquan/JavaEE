/**
 * 
 */
package epf.tests.shell;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.GenericType;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import epf.client.gateway.Gateway;
import epf.client.persistence.Entity;
import epf.client.security.Token;
import epf.schema.EPF;
import epf.schema.work_products.Artifact;
import epf.schema.work_products.section.Description;
import epf.schema.work_products.section.Illustrations;
import epf.schema.work_products.section.MoreInformation;
import epf.schema.work_products.section.Relationships;
import epf.schema.work_products.section.Tailoring;
import epf.tests.TestUtil;
import epf.tests.gateway.GatewayUtil;
import epf.tests.persistence.PersistenceUtil;
import epf.tests.registry.RegistryUtil;
import epf.tests.security.SecurityUtil;

/**
 * @author PC
 *
 */
public class ShellTest {
	
	private static Path workingDir;
	private static Path tempDir;
	private static String token;
	private static String adminToken;
	private ProcessBuilder builder;
	private Process process;
	Path out;
	Path in;
	Path err;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		workingDir = ShellUtil.getShellPath().toRealPath();
		tempDir = Files.createTempDirectory("temp");
		token = SecurityUtil.login(null, "any_role1", "any_role");
		adminToken = SecurityUtil.login(null, "admin1", "admin");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		tempDir.toFile().delete();
		SecurityUtil.logOut(null, token);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		in = Files.createTempFile(tempDir, "in", "in");
		out = Files.createTempFile(tempDir, "out", "out");
		err = Files.createTempFile(tempDir, "err", "err");
		builder = new ProcessBuilder();
		builder.directory(workingDir.toFile());
		builder.environment().put(Gateway.GATEWAY_URL, System.getProperty(Gateway.GATEWAY_URL));
		builder.redirectError(err.toFile());
		builder.redirectInput(in.toFile());
		builder.redirectOutput(out.toFile());
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		process.destroyForcibly();
		Files.lines(err).forEach(System.err::println);
		Files.lines(out).forEach(System.out::println);
		in.toFile().delete();
		out.toFile().delete();
		err.toFile().delete();
	}

	@Test
	public void testSecurity_Login() throws IOException, InterruptedException {
		builder.command("powershell", "./epf", "security", "login", "-u", "any_role1", "-p");
		process = builder.start();
		ShellUtil.waitFor(builder, in, "any_role");
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(2, lines.size());
		Assert.assertTrue(lines.get(0).startsWith("Enter value for --password (Password): "));
		String newToken = lines.get(1);
		Assert.assertTrue(newToken.length() > 256);
		SecurityUtil.logOut(null, newToken);
		lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
	}

	@Test(expected = NotAuthorizedException.class)
	public void testSecurity_Logout() throws Exception {
		String newToken = SecurityUtil.login(null, "any_role1", "any_role");
		builder.command("powershell", "./epf", "security", "logout", "-t", newToken);
		process = ShellUtil.waitFor(builder);
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(2, lines.size());
		Assert.assertEquals("any_role1", lines.get(1));
		SecurityUtil.auth(null, newToken);
		lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
	}
	
	@Test
	public void testSecurity_Auth() throws Exception {
		builder.command("powershell", "./epf", "security", "auth", "-t", token);
		process = ShellUtil.waitFor(builder);
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(2, lines.size());
		try(Jsonb jsonb = JsonbBuilder.create()){
			Token securityToken = jsonb.fromJson(lines.get(1), Token.class);
			Assert.assertNotNull("Token", securityToken);
			Assert.assertEquals("Token.name", "any_role1", securityToken.getName());
		}
		lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
	}
	
	@Test
	public void testSecurity_UpdatePassword() throws InterruptedException, IOException {
		builder.command("powershell", "./epf", "security", "update", "-t", token, "-p");
		process = ShellUtil.waitFor(builder, in, "any_role");
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(1, lines.size());
	}
	
	@Test
	public void testSecurity_Revoke() throws InterruptedException, IOException {
		String token = SecurityUtil.login(null, "any_role1", "any_role");
		builder.command("powershell", "./epf", "security", "revoke", "-t", token);
		process = ShellUtil.waitFor(builder);
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(2, lines.size());
		String newToken = lines.get(1);
		Assert.assertNotEquals("", newToken);
		Assert.assertTrue(newToken.length() > 256);
		Assert.assertNotEquals(token, newToken);
		SecurityUtil.logOut(null, newToken);
	}
	
	@Test
	public void testPersistence_Persist() throws Exception {
		Artifact artifact = new Artifact();
		artifact.setName("Artifact Shell" + System.currentTimeMillis());
        artifact.setSummary("Artifact Shell Summary" + UUID.randomUUID());
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        
		builder.command("powershell", "./epf", "persistence", "persist", "-t", adminToken, "-u", EPF.SCHEMA, "-n", EPF.ARTIFACT, "-e");
		process = builder.start();
		TestUtil.waitUntil(o -> process.isAlive(), Duration.ofSeconds(10));
		ShellUtil.writeJson(in, artifact);
		process.waitFor(20, TimeUnit.SECONDS);
		
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(2, lines.size());
        Artifact updatedArtifact = null;
        try(Jsonb jsonb = JsonbBuilder.create()){
        	updatedArtifact = jsonb.fromJson(lines.get(1), Artifact.class);
        }
        Assert.assertNotNull("Artifact", updatedArtifact);
        Assert.assertEquals("Artifact.name", artifact.getName(), updatedArtifact.getName());
        Assert.assertEquals("Artifact.summary", artifact.getSummary(), updatedArtifact.getSummary());
        
        PersistenceUtil.remove(adminToken, EPF.SCHEMA, EPF.ARTIFACT, artifact.getName());;
	}
	
	@Test
	public void testPersistence_Merge() throws Exception {
		Artifact artifact = new Artifact();
        artifact.setName("Artifact_Shell" + System.currentTimeMillis());
        artifact.setSummary("Artifact Shell Summary" + UUID.randomUUID());
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        PersistenceUtil.persist(adminToken, Artifact.class, EPF.SCHEMA, EPF.ARTIFACT, artifact);
    	
        builder.command("powershell", "./epf", "persistence", "merge", "-t", adminToken, "-u", EPF.SCHEMA, "-n", EPF.ARTIFACT, "-i", artifact.getName(), "-e");
		process = builder.start();
		TestUtil.waitUntil(o -> process.isAlive(), Duration.ofSeconds(10));
        Artifact updatedArtifact = new Artifact();
        updatedArtifact.setName("Artifact_Shell" + System.currentTimeMillis());
        updatedArtifact.setSummary("Artifact Shell Summary" + UUID.randomUUID());
        updatedArtifact.setDescription(new Description());
        updatedArtifact.setIllustrations(new Illustrations());
        updatedArtifact.setMoreInformation(new MoreInformation());
        updatedArtifact.setRelationships(new Relationships());
        updatedArtifact.setTailoring(new Tailoring());
		ShellUtil.writeJson(in, artifact);
		process.waitFor(20, TimeUnit.SECONDS);
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(1, lines.size());
        
        PersistenceUtil.remove(adminToken, EPF.SCHEMA, EPF.ARTIFACT, updatedArtifact.getName());
	}
	
	@Test
	public void testPersistence_Remove() throws Exception {
		Artifact artifact = new Artifact();
        artifact.setName("Artifact_Shell" + System.currentTimeMillis());
        artifact.setSummary("Artifact Shell Summary" + UUID.randomUUID());
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        PersistenceUtil.persist(adminToken, Artifact.class, EPF.SCHEMA, EPF.ARTIFACT, artifact);
    	
        builder.command("powershell", "./epf", "persistence", "remove", "-t", adminToken, "-u", EPF.SCHEMA, "-n", EPF.ARTIFACT, "-i", artifact.getName());
		process = ShellUtil.waitFor(builder);
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(1, lines.size());
		lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
	}
	
	@Test
	public void testPersistence_GetEntities() throws Exception {
		builder.command("powershell", "./epf", "persistence", "get-entities", "-t", token, "-u", EPF.SCHEMA);
		process = ShellUtil.waitFor(builder);
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(2, lines.size());
		List<Entity> entities;
		try(Jsonb jsonb = JsonbBuilder.create()){
			entities = jsonb.fromJson(lines.get(1), (new GenericType<List<Entity>>() {}).getType());
		}
		Assert.assertFalse(entities.isEmpty());
	}
	
	@Test
	public void testFile_Create() throws Exception {
		Path file = Files.createTempFile("file", ".txt");
		Files.write(file, Arrays.asList("this is a test"));
		Path path = Path.of("any_role1", "this", "is", "a", "test");
		builder.command(
				"powershell", "./epf", 
				"file", "create", 
				"-t", token, 
				"-f", "\"" + file.toString() + "\"", 
				"-p", "\"" + path.toString() + "\"" 
				);
		process = ShellUtil.waitFor(builder);
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(2, lines.size());
		URI fileUrl = RegistryUtil.lookup("file", null);
		String expected = fileUrl.toString() + "/any_role1/this/is/a/test/";
		System.out.println(expected);
		Assert.assertTrue(
				lines.get(1)
				.startsWith(expected)
				);
	}
}
