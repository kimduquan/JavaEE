/**
 * 
 */
package epf.tests.shell;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.GenericType;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import epf.client.gateway.Gateway;
import epf.client.schema.Entity;
import epf.client.security.Token;
import epf.schema.EPF;
import epf.schema.work_products.Artifact;
import epf.schema.work_products.section.Description;
import epf.schema.work_products.section.Illustrations;
import epf.schema.work_products.section.MoreInformation;
import epf.schema.work_products.section.Relationships;
import epf.schema.work_products.section.Tailoring;
import epf.tests.TestUtil;
import epf.tests.file.FileUtil;
import epf.tests.persistence.PersistenceUtil;
import epf.tests.rules.RulesUtil;
import epf.tests.security.SecurityUtil;
import epf.util.StringUtil;
import epf.util.file.PathUtil;

/**
 * @author PC
 *
 */
public class ShellTest {
	
	private static Path workingDir;
	private static Path tempDir;
	private static String token;
	private static String tokenID;
	private static String adminToken;
	private static String adminTokenID;
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
		token = SecurityUtil.login("any_role1", "any_role");
		adminToken = SecurityUtil.login("admin1", "admin");
		
		Path out = Files.createTempFile(tempDir, "out", "out");
		ProcessBuilder builder = new ProcessBuilder();
		builder.directory(workingDir.toFile());
		builder.environment().put(Gateway.GATEWAY_URL, System.getProperty(Gateway.GATEWAY_URL));
		builder.redirectOutput(out.toFile());
		tokenID = ShellUtil.securityAuth(builder, token, out).getTokenID();
		Files.delete(out);
		
		out = Files.createTempFile(tempDir, "out", "out");
		builder.redirectOutput(out.toFile());
		adminTokenID = ShellUtil.securityAuth(builder, adminToken, out).getTokenID();
		Files.delete(out);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		ProcessBuilder builder = new ProcessBuilder();
		builder.directory(workingDir.toFile());
		builder.environment().put(Gateway.GATEWAY_URL, System.getProperty(Gateway.GATEWAY_URL));
		ShellUtil.securityLogout(builder, tokenID);
		ShellUtil.securityLogout(builder, adminTokenID);
		tempDir.toFile().delete();
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
		if(process != null) {
			process.destroyForcibly();
		}
		Files.lines(err).forEach(System.err::println);
		Files.lines(out).forEach(System.out::println);
		in.toFile().delete();
		out.toFile().delete();
		err.toFile().delete();
	}

	@Test
	public void testSecurity_Login() throws IOException, InterruptedException {
		builder = ShellUtil.command(builder, "./epf", "security", "login", "-u", "any_role1", "-p");
		process = builder.start();
		ShellUtil.waitFor(builder, in, "any_role");
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(2, lines.size());
		Assert.assertTrue(lines.get(0).startsWith("Enter value for --password (Password): "));
		String newToken = lines.get(1);
		Assert.assertTrue(newToken.length() > 256);
		SecurityUtil.logOut(newToken);
		lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
	}

	@Test(expected = NotAuthorizedException.class)
	public void testSecurity_Logout() throws Exception {
		String newToken = SecurityUtil.login("any_role1", "any_role");
		builder = ShellUtil.command(builder, "./epf", "security", "logout", "-t", newToken);
		process = ShellUtil.waitFor(builder);
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(2, lines.size());
		Assert.assertEquals("any_role1", lines.get(1));
		SecurityUtil.auth(newToken);
		lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
	}
	
	@Test
	public void testSecurity_Auth() throws Exception {
		builder = ShellUtil.command(builder, "./epf", "security", "auth", "-t", token);
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
		builder = ShellUtil.command(builder, "./epf", "security", "update", "-tid", tokenID, "-p");
		process = ShellUtil.waitFor(builder, in, "any_role");
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(1, lines.size());
	}
	
	@Test
	public void testSecurity_Revoke() throws InterruptedException, IOException {
		String token = SecurityUtil.login("any_role1", "any_role");
		builder = ShellUtil.command(builder, "./epf", "security", "revoke", "-t", token);
		process = ShellUtil.waitFor(builder);
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(2, lines.size());
		String newToken = lines.get(1);
		Assert.assertNotEquals("", newToken);
		Assert.assertTrue(newToken.length() > 256);
		Assert.assertNotEquals(token, newToken);
		SecurityUtil.logOut(newToken);
	}
	
	@Test
	public void testPersistence_Persist() throws Exception {
		Artifact artifact = new Artifact();
		artifact.setName(StringUtil.randomString("Artifact Shell"));
        artifact.setSummary(StringUtil.randomString("Artifact Shell Summary"));
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        
        builder = ShellUtil.command(builder, "./epf", "persistence", "persist", "-tid", adminTokenID, "-n", EPF.ARTIFACT, "-e");
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
        
        PersistenceUtil.remove(adminToken, EPF.ARTIFACT, artifact.getName());;
	}
	
	@Test
	public void testPersistence_Merge() throws Exception {
		Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact_Shell"));
        artifact.setSummary(StringUtil.randomString("Artifact Shell Summary"));
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        PersistenceUtil.persist(adminToken, Artifact.class, EPF.ARTIFACT, artifact);
    	
        builder = ShellUtil.command(builder, "./epf", "persistence", "merge", "-tid", adminTokenID, "-n", EPF.ARTIFACT, "-i", artifact.getName(), "-e");
		process = builder.start();
		TestUtil.waitUntil(o -> process.isAlive(), Duration.ofSeconds(10));
        Artifact updatedArtifact = new Artifact();
        updatedArtifact.setName(StringUtil.randomString("Artifact_Shell"));
        updatedArtifact.setSummary(StringUtil.randomString("Artifact Shell Summary"));
        updatedArtifact.setDescription(new Description());
        updatedArtifact.setIllustrations(new Illustrations());
        updatedArtifact.setMoreInformation(new MoreInformation());
        updatedArtifact.setRelationships(new Relationships());
        updatedArtifact.setTailoring(new Tailoring());
		ShellUtil.writeJson(in, artifact);
		process.waitFor(20, TimeUnit.SECONDS);
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(1, lines.size());
        
        PersistenceUtil.remove(adminToken, EPF.ARTIFACT, updatedArtifact.getName());
	}
	
	@Test
	public void testPersistence_Remove() throws Exception {
		Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact_Shell"));
        artifact.setSummary(StringUtil.randomString("Artifact Shell Summary"));
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        PersistenceUtil.persist(adminToken, Artifact.class, EPF.ARTIFACT, artifact);
    	
        builder = ShellUtil.command(builder, "./epf", "persistence", "remove", "-tid", adminTokenID, "-n", EPF.ARTIFACT, "-i", artifact.getName());
		process = ShellUtil.waitFor(builder);
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(1, lines.size());
		lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
	}
	
	@Test
	public void testSchema_GetEntities() throws Exception {
		builder = ShellUtil.command(builder, "./epf", "schema", "entities", "-tid", tokenID);
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
		Path file = Files.createTempFile("file", ".in");
		Files.write(file, Arrays.asList("this is a test"));
		Path path = PathUtil.of("any_role1", "this", "is", "a", "test");
		builder = ShellUtil.command(builder, 
				"./epf", 
				"file", "create", 
				"-tid", tokenID, 
				"-f", "\"" + file.toString() + "\"", 
				"-p", "\"" + path.toString() + "\"" 
				);
		process = ShellUtil.waitFor(builder);
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(2, lines.size());
		Assert.assertTrue(lines.get(1).startsWith("any_role1/this/is/a/test/"));
		FileUtil.delete(token, PathUtil.of("any_role1/this/is/a/test"));
		file.toFile().delete();
	}
	
	@Test
	public void testFile_Delete() throws Exception {
		Path file = Files.createTempFile("file", ".in");
		Files.write(file, Arrays.asList("this is a test"));
		Path path = PathUtil.of("any_role1", "this", "is", "a", "test");
		String createdFile = FileUtil.createFile(token, file, path);
		builder = ShellUtil.command(builder, 
				"./epf", 
				"file", "delete", 
				"-tid", tokenID, 
				"-p", "\"" + createdFile + "\"" 
				);
		process = ShellUtil.waitFor(builder);
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(1, lines.size());
		lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
		file.toFile().delete();
	}
	
	@Test
	public void testFile_Read() throws Exception {
		Path file = Files.createTempFile("file", ".in");
		Files.write(file, Arrays.asList("this is a test"));
		Path path = PathUtil.of("any_role1", "this", "is", "a", "test");
		String createdFile = FileUtil.createFile(token, file, path);
		Path output = Files.createTempFile("file", ".out");
		builder = ShellUtil.command(builder, 
				"./epf", 
				"file", "read", 
				"-tid", tokenID, 
				"-p", "\"" + createdFile + "\"",
				"-o", "\"" + output.toString() + "\""
				);
		process = ShellUtil.waitFor(builder);
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(1, lines.size());
		lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
		Assert.assertArrayEquals(
				new String[] {"this is a test"},
				Files
				.lines(output)
				.collect(Collectors.toList())
				.toArray(new String[0])
				);
		file.toFile().delete();
		output.toFile().delete();
	}
	
	@Test
	public void testRules_Admin_Register() throws Exception {
		Path ruleFile = PathUtil.of("", "Artifact.drl");
		builder = ShellUtil.command(builder, 
				"./epf", 
				"rules", "admin", "register",
				"-tid", tokenID,
				"-n", "Artifact",
				"-f", ruleFile.toAbsolutePath().toString()
				);
		process = ShellUtil.waitFor(builder);
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(1, lines.size());
		lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
		RulesUtil.deregisterRuleExecutionSet(token, "Artifact1");
	}
	
	@Test
	public void testRules_Admin_Deregister() throws Exception {
		RulesUtil.registerRuleExecutionSet(token, PathUtil.of("", "Artifact.drl"), "Artifact1");
		builder = ShellUtil.command(builder, 
				"./epf", 
				"rules", "admin", "de-register",
				"-tid", tokenID,
				"-n", "Artifact1"
				);
		process = ShellUtil.waitFor(builder);
		List<String> lines = Files.readAllLines(out);
		List<String> errors = Files.readAllLines(err);
		Assert.assertEquals(1, lines.size());
		Assert.assertTrue(errors.isEmpty());
	}
	
	@Test
	public void testRules_Execute() throws Exception {
		RulesUtil.registerRuleExecutionSet(token, PathUtil.of("", "Artifact.drl"), "Artifact1");
		builder = ShellUtil.command(builder, 
				"./epf", 
				"rules", "execute",
				"-tid", tokenID,
				"-r", "Artifact1",
				"-i"
				);
		List<Object> input = new ArrayList<>();
		Artifact artifact = new Artifact();
		artifact.setName(StringUtil.randomString("Artifact"));
		input.add(artifact);
		String json = RulesUtil.encode(input);
		process = ShellUtil.waitFor(builder, in, json);
		List<String> lines = Files.readAllLines(out);
		List<String> errors = Files.readAllLines(err);
		Assert.assertEquals(2, lines.size());
		List<Object> resultList = RulesUtil.decode(lines.get(1));
		Assert.assertTrue(errors.isEmpty());
		Assert.assertFalse(resultList.isEmpty());
		Artifact resultArtifact = (Artifact) resultList.get(0);
		Assert.assertNotNull("Artifact", resultArtifact);
		Assert.assertEquals("Artifact.summary", artifact.getName() + " Summary", resultArtifact.getSummary());
		RulesUtil.deregisterRuleExecutionSet(token, "Artifact1");
	}
	
	@Test
	@Ignore
	public void testRules_Registrations() throws Exception {
		RulesUtil.registerRuleExecutionSet(token, PathUtil.of("", "Artifact.drl"), "Artifact1");
		builder = ShellUtil.command(builder, 
				"./epf", 
				"rules", "registrations",
				"-tid", tokenID
				);
		process = ShellUtil.waitFor(builder);
		List<String> lines = Files.readAllLines(out);
		List<String> errors = Files.readAllLines(err);
		Assert.assertEquals(2, lines.size());
		List<Object> resultList = RulesUtil.decode(lines.get(1));
		Assert.assertTrue(errors.isEmpty());
		Assert.assertFalse(resultList.isEmpty());
		RulesUtil.deregisterRuleExecutionSet(token, "Artifact1");
	}
	
	@Test
	public void testImage_FindContours() throws Exception {
		Path ruleFile = PathUtil.of("", "board.jpg");
		builder = ShellUtil.command(builder, 
				"./epf", 
				"image", "find-contours",
				"-tid", tokenID,
				"-f", ruleFile.toAbsolutePath().toString()
				);
		process = ShellUtil.waitFor(builder);
		List<String> lines = Files.readAllLines(out);
		Assert.assertEquals(1, lines.size());
		lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
	}
}
