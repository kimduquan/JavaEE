/**
 * 
 */
package epf.tests.shell;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
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
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import epf.client.schema.Entity;
import epf.file.util.PathUtil;
import epf.naming.Naming;
import epf.security.schema.Token;
import epf.tests.TestUtil;
import epf.tests.file.FileUtil;
import epf.tests.persistence.PersistenceUtil;
import epf.tests.rules.RulesUtil;
import epf.tests.security.SecurityUtil;
import epf.util.StringUtil;
import epf.util.config.ConfigUtil;
import epf.work_products.schema.Artifact;
import epf.work_products.schema.WorkProducts;
import epf.work_products.schema.section.Description;
import epf.work_products.schema.section.Illustrations;
import epf.work_products.schema.section.MoreInformation;
import epf.work_products.schema.section.Relationships;
import epf.work_products.schema.section.Tailoring;

/**
 * @author PC
 *
 */
public class ShellTest {
	
	@Rule
    public TestName testName = new TestName();
	
	private static Path workingDir;
	private static Path tempDir;
	private static Entry<String, String> credential;
	private static String token;
	private static String tokenID;
	private static String otherToken;
	private static String otherTokenID;
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
		credential = SecurityUtil.peekCredential();
		token = SecurityUtil.login(credential.getKey(), credential.getValue());
		Entry<String, String> otherCredential = SecurityUtil.peekCredential();
		otherToken = SecurityUtil.login(otherCredential.getKey(), otherCredential.getValue());
		
		Path out = Files.createTempFile(tempDir, "out", "out");
		ProcessBuilder builder = new ProcessBuilder();
		builder.directory(workingDir.toFile());
		builder.environment().put(Naming.Gateway.GATEWAY_URL, ConfigUtil.getString(Naming.Gateway.GATEWAY_URL));
		builder.redirectOutput(out.toFile());
		tokenID = ShellUtil.securityAuth(builder, token, out).getTokenID();
		Files.delete(out);
		
		out = Files.createTempFile(tempDir, "out", "out");
		builder.redirectOutput(out.toFile());
		otherTokenID = ShellUtil.securityAuth(builder, otherToken, out).getTokenID();
		Files.delete(out);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		ProcessBuilder builder = new ProcessBuilder();
		builder.directory(workingDir.toFile());
		builder.environment().put(Naming.Gateway.GATEWAY_URL, ConfigUtil.getString(Naming.Gateway.GATEWAY_URL));
		ShellUtil.securityLogout(builder, tokenID);
		ShellUtil.securityLogout(builder, otherTokenID);
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
		builder.environment().put(Naming.Gateway.GATEWAY_URL, ConfigUtil.getString(Naming.Gateway.GATEWAY_URL));
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
	public void testSecurity_Login() throws Exception {
		Entry<String, String> credential = SecurityUtil.peekCredential();
		builder = ShellUtil.command(builder, Naming.SECURITY, "login", "-u", credential.getKey(), "-p");
		process = builder.start();
		ShellUtil.waitFor(builder, in, credential.getValue());
		List<String> lines = ShellUtil.getOutput(out);
		Assert.assertTrue(lines.get(lines.size() - 2).startsWith("Enter value for --password (Password): "));
		String newToken = lines.get(lines.size() - 1);
		Assert.assertTrue(newToken.length() > 256);
		SecurityUtil.logOut(newToken);
		lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
	}

	@Test(expected = NotAuthorizedException.class)
	public void testSecurity_Logout() throws Exception {
		Entry<String, String> credential = SecurityUtil.peekCredential();
		String newToken = SecurityUtil.login(credential.getKey(), credential.getValue());
		builder = ShellUtil.command(builder, Naming.SECURITY, "logout", "-t", newToken);
		process = ShellUtil.waitFor(builder);
		List<String> lines = ShellUtil.getOutput(out);
		Assert.assertEquals(credential.getKey(), lines.get(lines.size() - 1));
		SecurityUtil.auth(newToken);
		lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
	}
	
	@Test
	public void testSecurity_Auth() throws Exception {
		builder = ShellUtil.command(builder, Naming.SECURITY, "auth", "-t", token);
		process = ShellUtil.waitFor(builder);
		List<String> lines = ShellUtil.getOutput(out);
		try(Jsonb jsonb = JsonbBuilder.create()){
			Token securityToken = jsonb.fromJson(lines.get(lines.size() - 1), Token.class);
			Assert.assertNotNull("Token", securityToken);
			Assert.assertEquals("Token.name", credential.getKey(), securityToken.getName());
		}
		lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
	}
	
	@Test
	public void testSecurity_UpdatePassword() throws Exception {
		builder = ShellUtil.command(builder, Naming.SECURITY, "update", "-tid", tokenID, "-p");
		process = ShellUtil.waitFor(builder, in, credential.getValue());
		ShellUtil.getOutput(out);
		List<String> lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
	}
	
	@Test
	public void testSecurity_Revoke() throws Exception {
		String token = SecurityUtil.login();
		builder = ShellUtil.command(builder, Naming.SECURITY, "revoke", "-t", token);
		process = ShellUtil.waitFor(builder);
		List<String> lines = ShellUtil.getOutput(out);
		String newToken = lines.get(lines.size() - 1);
		Assert.assertNotEquals("", newToken);
		Assert.assertTrue(newToken.length() > 256);
		Assert.assertNotEquals(token, newToken);
		SecurityUtil.logOut(newToken);
	}
	
	@Test
	public void testPersistence_Persist() throws Exception {
		Artifact artifact = new Artifact();
		artifact.setName(StringUtil.randomString("Artifact Shell"));
        artifact.setSummary("Artifact Shell testPersistence_Persist");
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        
        builder = ShellUtil.command(builder, Naming.PERSISTENCE, "persist", "-tid", otherTokenID, "-s", WorkProducts.SCHEMA, "-e", WorkProducts.ARTIFACT, "-d");
		process = builder.start();
		TestUtil.waitUntil(o -> process.isAlive(), Duration.ofSeconds(10));
		ShellUtil.writeJson(in, artifact);
		process.waitFor(20, TimeUnit.SECONDS);
		
		List<String> lines = ShellUtil.getOutput(out);
        Artifact updatedArtifact = null;
        try(Jsonb jsonb = JsonbBuilder.create()){
        	updatedArtifact = jsonb.fromJson(lines.get(lines.size() - 1), Artifact.class);
        }
        Assert.assertNotNull("Artifact", updatedArtifact);
        Assert.assertEquals("Artifact.name", artifact.getName(), updatedArtifact.getName());
        Assert.assertEquals("Artifact.summary", artifact.getSummary(), updatedArtifact.getSummary());
        
        PersistenceUtil.remove(otherToken, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact.getName());
	}
	
	@Test
	public void testPersistence_Merge() throws Exception {
		Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact_Shell"));
        artifact.setSummary(StringUtil.randomString("Artifact_Shell testPersistence_Merge"));
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        PersistenceUtil.persist(otherToken, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact);
    	
        builder = ShellUtil.command(builder, Naming.PERSISTENCE, "merge", "-tid", otherTokenID, "-s", WorkProducts.SCHEMA, "-e", WorkProducts.ARTIFACT, "-i", artifact.getName(), "-d");
		process = builder.start();
		TestUtil.waitUntil(o -> process.isAlive(), Duration.ofSeconds(10));
        Artifact updatedArtifact = new Artifact();
        updatedArtifact.setName(artifact.getName());
        updatedArtifact.setSummary(StringUtil.randomString("Artifact Shell testPersistence_Merge"));
        updatedArtifact.setDescription(new Description());
        updatedArtifact.setIllustrations(new Illustrations());
        updatedArtifact.setMoreInformation(new MoreInformation());
        updatedArtifact.setRelationships(new Relationships());
        updatedArtifact.setTailoring(new Tailoring());
		ShellUtil.writeJson(in, artifact);
		process.waitFor(20, TimeUnit.SECONDS);
		ShellUtil.getOutput(out);
		List<String> lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
        
        PersistenceUtil.remove(otherToken, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, updatedArtifact.getName());
	}
	
	@Test
	public void testPersistence_Remove() throws Exception {
		Artifact artifact = new Artifact();
        artifact.setName(StringUtil.randomString("Artifact_Shell"));
        artifact.setSummary("Artifact Shell testPersistence_Remove");
        artifact.setDescription(new Description());
        artifact.setIllustrations(new Illustrations());
        artifact.setMoreInformation(new MoreInformation());
        artifact.setRelationships(new Relationships());
        artifact.setTailoring(new Tailoring());
        PersistenceUtil.persist(otherToken, Artifact.class, WorkProducts.SCHEMA, WorkProducts.ARTIFACT, artifact);
    	
        builder = ShellUtil.command(builder, Naming.PERSISTENCE, "remove", "-tid", otherTokenID, "-s", WorkProducts.SCHEMA, "-e", WorkProducts.ARTIFACT, "-i", artifact.getName());
		process = ShellUtil.waitFor(builder);
		List<String> lines = ShellUtil.getOutput(out);
		lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
	}
	
	@Test
	public void testSchema_GetEntities() throws Exception {
		builder = ShellUtil.command(builder, Naming.SCHEMA, "entities", "-tid", tokenID);
		process = ShellUtil.waitFor(builder);
		List<String> lines = ShellUtil.getOutput(out);
		List<Entity> entities;
		try(Jsonb jsonb = JsonbBuilder.create()){
			entities = jsonb.fromJson(lines.get(lines.size() - 1), (new GenericType<List<Entity>>() {}).getType());
		}
		Assert.assertFalse(entities.isEmpty());
	}
	
	@Test
	public void testFile_Create() throws Exception {
		Path file = Files.createTempFile("file", ".in");
		Files.write(file, Arrays.asList("this is a test"));
		Path path = PathUtil.of(credential.getKey(), "this", "is", "a", "test");
		builder = ShellUtil.command(builder,
				Naming.FILE, "create", 
				"-tid", tokenID, 
				"-f", "\"" + file.toString() + "\"", 
				"-p", "\"" + path.toString() + "\"" 
				);
		process = ShellUtil.waitFor(builder);
		List<String> lines = ShellUtil.getOutput(out);
		Assert.assertTrue(lines.get(lines.size() - 1).startsWith(credential.getKey() + "/this/is/a/test/"));
		FileUtil.delete(token, PathUtil.of(credential.getKey() + "/this/is/a/test"));
		file.toFile().delete();
	}
	
	@Test
	public void testFile_Delete() throws Exception {
		Path file = Files.createTempFile("file", ".in");
		Files.write(file, Arrays.asList("this is a test"));
		Path path = PathUtil.of(credential.getKey(), "this", "is", "a", "test");
		String createdFile = FileUtil.createFile(token, file, path);
		builder = ShellUtil.command(builder,
				Naming.FILE, "delete", 
				"-tid", tokenID, 
				"-p", "\"" + createdFile + "\"" 
				);
		process = ShellUtil.waitFor(builder);
		List<String> lines = ShellUtil.getOutput(out);
		lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
		file.toFile().delete();
	}
	
	@Test
	public void testFile_Read() throws Exception {
		Path file = Files.createTempFile("file", ".in");
		Files.write(file, Arrays.asList("this is a test"));
		Path path = PathUtil.of(credential.getKey(), "this", "is", "a", "test");
		String createdFile = FileUtil.createFile(token, file, path);
		Path output = Files.createTempFile("file", ".out");
		builder = ShellUtil.command(builder,
				Naming.FILE, "read", 
				"-tid", tokenID, 
				"-p", "\"" + createdFile + "\"",
				"-o", "\"" + output.toString() + "\""
				);
		process = ShellUtil.waitFor(builder);
		List<String> lines = ShellUtil.getOutput(out);
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
				Naming.RULES, "admin", "register",
				"-tid", tokenID,
				"-n", "Artifact",
				"-f", ruleFile.toAbsolutePath().toString()
				);
		process = ShellUtil.waitFor(builder);
		List<String> lines = ShellUtil.getOutput(out);
		lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
		RulesUtil.deregisterRuleExecutionSet(token, "Artifact1");
	}
	
	@Test
	public void testRules_Admin_Deregister() throws Exception {
		RulesUtil.registerRuleExecutionSet(token, PathUtil.of("", "Artifact.drl"), "Artifact1");
		builder = ShellUtil.command(builder,
				Naming.RULES, "admin", "de-register",
				"-tid", tokenID,
				"-n", "Artifact1"
				);
		process = ShellUtil.waitFor(builder);
		ShellUtil.getOutput(out);
		List<String> errors = Files.readAllLines(err);
		Assert.assertTrue(errors.isEmpty());
	}
	
	@Test
	public void testRules_Execute() throws Exception {
		RulesUtil.registerRuleExecutionSet(token, PathUtil.of("", "Artifact.drl"), "Artifact1");
		builder = ShellUtil.command(builder,
				Naming.RULES, "execute",
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
		List<String> lines = ShellUtil.getOutput(out);
		List<String> errors = Files.readAllLines(err);
		List<Object> resultList = RulesUtil.decode(lines.get(lines.size() - 1));
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
				Naming.RULES, "registrations",
				"-tid", tokenID
				);
		process = ShellUtil.waitFor(builder);
		List<String> lines = ShellUtil.getOutput(out);
		List<String> errors = Files.readAllLines(err);
		List<Object> resultList = RulesUtil.decode(lines.get(lines.size() - 1));
		Assert.assertTrue(errors.isEmpty());
		Assert.assertFalse(resultList.isEmpty());
		RulesUtil.deregisterRuleExecutionSet(token, "Artifact1");
	}
	
	@Test
	@Ignore
	public void testImage_FindContours() throws Exception {
		Path ruleFile = PathUtil.of("", "board.jpg");
		builder = ShellUtil.command(builder,
				Naming.IMAGE, "find-contours",
				"-tid", tokenID,
				"-f", ruleFile.toAbsolutePath().toString()
				);
		process = ShellUtil.waitFor(builder);
		List<String> lines = ShellUtil.getOutput(out);
		lines = Files.readAllLines(err);
		Assert.assertTrue(lines.isEmpty());
	}
}
