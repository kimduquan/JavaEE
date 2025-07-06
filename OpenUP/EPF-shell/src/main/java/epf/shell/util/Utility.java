package epf.shell.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.DriverManager;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Stream;
import jakarta.websocket.ContainerProvider;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.websocket.Session;
import jakarta.ws.rs.client.Invocation.Builder;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.core.Response;
import epf.file.util.PathUtil;
import epf.shell.Function;
import epf.shell.SYSTEM;
import epf.shell.client.ClientUtil;
import epf.shell.messaging.MessagingClient;
import epf.shell.util.client.Entity;
import epf.util.logging.LogManager;
import epf.util.security.KeyUtil;
import epf.util.zip.ZipUtil;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import java.sql.Connection;
import java.sql.Statement;

@Command(name = "utility")
@ApplicationScoped
@Function
public class Utility {
	
	private static final Logger LOGGER = LogManager.getLogger(Utility.class.getName());
	
	private transient Path tempDir;
	
	@Inject
	transient ClientUtil clientUtil;
	
	@Inject @Named(SYSTEM.OUT)
	transient PrintWriter out;
	
	@Inject @Named(SYSTEM.ERR)
	transient PrintWriter err;
	
	@PostConstruct
	protected void postConstruct() {
		try {
			tempDir = Files.createTempDirectory(PathUtil.of(""), "utility");
		} 
		catch (IOException e) {
			LOGGER.throwing(Files.class.getName(), "createTempDirectory", e);
		}
	}
	
	@PreDestroy
	protected void preDestroy() {
		tempDir.toFile().delete();
	}

	@Command(name = "exec")
	public int exec(
			@Option(names = {"-dir", "--directory"}, description = "Working directory")
			final File dir,
			@Option(names = {"-env", "--envirionment"}, description = "Envirionment variables")
			final String[] env,
			@Option(names = {"-args", "--arguments"}, description = "Arguments")
			final String... args
			) throws IOException, InterruptedException {
		final ProcessBuilder builder = new ProcessBuilder();
		builder.command(args);
		builder.directory(dir);
		final Map<String, String> enVars = new ConcurrentHashMap<>();
		Stream.of(env).forEach(v -> {
			final String[] data = v.split("=");
			if(data.length > 0) {
				enVars.put(data[0], data[1]);
			}
		});
		builder.environment().putAll(enVars);
		builder.inheritIO();
		final Process process = builder.start();
		return process.waitFor();
	}
	
	protected Builder buildRequest(
			final Builder builder,
			final String[] accept,
			final String[] encoding,
			final String[] language,
			final Map<String, String> cookie,
			final Map<String, String> header) {
		Builder req = builder;
		req = accept != null ? req.accept(accept) : req;
		req = encoding != null ? req.acceptEncoding(encoding) : req;
		req = language != null ? req.acceptLanguage(language) : req;
		if(cookie != null) {
			for(Map.Entry<String, String> entry : cookie.entrySet()) {
				req = req.cookie(entry.getKey(), entry.getValue());
			}
		}
		if(header != null) {
			for(Map.Entry<String, String> entry : header.entrySet()) {
				req = req.header(entry.getKey(), entry.getValue());
			}
		}
		return req;
	}
	
	protected Response buildEntity(
			final Builder builder, 
			final String method, 
			final Entity type,
			final String entity){
		jakarta.ws.rs.client.Entity<?> reqEntity = null;
		if(type != null) {
			switch(type) {
			case FORM:
				try(StringReader reader = new  StringReader(entity)){
					try(JsonReader json = Json.createReader(reader)){
						final MultivaluedMap<String, String> form = new MultivaluedHashMap<String, String>();
						final JsonObject obj = json.readObject();
						obj.keySet().forEach(name -> {
							form.add(name, obj.getString(name));
						});
						reqEntity = jakarta.ws.rs.client.Entity.form(form);
					}
				}
				break;
			case HTML:
				reqEntity = jakarta.ws.rs.client.Entity.html(entity);
				break;
			case JSON:
				reqEntity = jakarta.ws.rs.client.Entity.json(entity);
				break;
			case TEXT:
				reqEntity = jakarta.ws.rs.client.Entity.text(entity);
				break;
			case XHTML:
				reqEntity = jakarta.ws.rs.client.Entity.xhtml(entity);
				break;
			case XML:
				reqEntity = jakarta.ws.rs.client.Entity.xml(entity);
				break;
			default:
				break;
			}
		}
		return reqEntity != null ? builder.method(method, reqEntity) : builder.method(method);
	}
	
	@Command(name = "connect")
	public void connectToServer(
			@Option(names = {"-u", "--uri"}, required = true, description = "URI") 
			final URI uri) throws Exception {
		try(Session session = ContainerProvider.getWebSocketContainer().connectToServer(MessagingClient.class, uri)){
			try(Scanner scanner = new Scanner(System.in)){
				while(scanner.hasNext()) {
					final String line = scanner.next();
					if("\\q".equals(line)) {
						break;
					}
					else {
						session.getBasicRemote().sendText(line);
					}
				}
			}
		}
	}
	
	@Command(name = "zip")
	public void zip(
			@Option(names = {"-d", "--dir"}, description = "Directory")
			final Path directory, 
			@Option(names = {"-f", "--file"}, description = "File")
			final Path file) throws Exception {
		ZipUtil.zip(directory, file);
	}
	
	@Command(name = "un-zip")
	public void unZip(
			@Option(names = {"-f", "--file"}, description = "File")
			final Path file,
			@Option(names = {"-d", "--dir"}, description = "Directory")
			final Path directory 
			) throws Exception {
		ZipUtil.unZip(file, directory);
	}
	
	@Command(name = "gen-keypair")
	public void generateKeyPair(
			@Option(names = {"-a", "--algorithm"}, description = "Algorithm", defaultValue = "RSA")
			final String algorithm, 
			@Option(names = {"-s", "--keysize"}, description = "Key Size", defaultValue = "2048")
			final int keySize, 
			@Option(names = {"-pr", "--private"}, description = "Private Key")
			final Path privateFile, 
			@Option(names = {"-pu", "--public"}, description = "Public Key")
			final Path publicFile) throws Exception {
		KeyUtil.generateKeyPair(algorithm, keySize, privateFile, publicFile);
	}
	
	@Command(name = "jdbc")
	public void getConnection(
			@Option(names = {"-url", "--url"}, required = true, description = "URL")
			@NotBlank
			final String url,
			@Option(names = {"-u", "--user"}, required = true, description = "User name")
			@NotBlank
			final String user,
			@Option(names = {"-p", "--password"}, required = true, description = "Password", interactive = true)
		    @NotEmpty
			final char[] password,
			@Option(names = {"-f", "--file"}, required = true, description = "File")
			final Path file) throws Exception {
		try(Connection connection = DriverManager.getConnection(url, user, new String(password))){
			try(Statement statement = connection.createStatement()){
				Files.lines(file).forEachOrdered(sql -> {
					try {
						statement.addBatch(sql);
					} 
					catch (Exception e) {
						e.printStackTrace(err);
					}
				});
				for(final int result : statement.executeBatch()) {
					out.println(result);
				}
			}
		}
	}
}
