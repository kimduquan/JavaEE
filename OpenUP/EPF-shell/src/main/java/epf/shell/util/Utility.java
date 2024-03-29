/**
 * 
 */
package epf.shell.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.sse.SseEventSource;
import epf.client.util.Client;
import epf.file.util.PathUtil;
import epf.shell.Function;
import epf.shell.SYSTEM;
import epf.shell.client.ClientUtil;
import epf.shell.util.client.Entity;
import epf.util.StringUtil;
import epf.util.logging.LogManager;
import epf.util.zip.ZipUtil;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * @author PC
 *
 */
@Command(name = "utility")
@ApplicationScoped
@Function
public class Utility {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(Utility.class.getName());
	
	/**
	 * 
	 */
	private transient Path tempDir;
	
	/**
	 * 
	 */
	@Inject
	transient ClientUtil clientUtil;
	
	/**
	 * 
	 */
	@Inject @Named(SYSTEM.OUT)
	transient PrintWriter out;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		try {
			tempDir = Files.createTempDirectory(PathUtil.of(""), "utility");
		} 
		catch (IOException e) {
			LOGGER.throwing(Files.class.getName(), "createTempDirectory", e);
		}
	}
	
	/**
	 * 
	 */
	@PreDestroy
	protected void preDestroy() {
		tempDir.toFile().delete();
	}

	/**
	 * @param dir
	 * @param env
	 * @param args
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
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
	
	/**
	 * @param method
	 * @param target
	 * @param accept
	 * @param encoding
	 * @param language
	 * @param cookie
	 * @param header
	 * @param type
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	@Command(name = "invoke")
	public String invoke(
			@Option(names = {"-m", "--method"}, required = true, description = "Method")
			final String method,
			@Option(names = {"-u", "--target"}, required = true, description = "Target")
			final URI target, 
			@Option(names = {"-a", "--accept"}, description = "Accept")
			final String[] accept, 
			@Option(names = {"-ec", "--encode"}, description = "Accept encoding")
			final String[] encoding,
			@Option(names = {"-l", "--lang"}, description = "Accept language")
			final String[] language,
			@Option(names = {"-c", "--cookie"}, description = "Cookie")
			final Map<String, String> cookie,
			@Option(names = {"-h", "--header"}, description = "header")
			final Map<String, String> header,
			@Option(names = {"-t", "--type"}, description = "Type")
			final Entity type,
			@Option(names = {"-e", "--entity"}, description = "Entity", interactive = true, echo = true)
			final String entity) throws Exception {
		try(Client client = clientUtil.newClient(target)){
			final Builder builder = client
					.request(
							reqTarget -> reqTarget, 
							req -> buildRequest(
									req,
									accept, 
									encoding, 
									language, 
									cookie,
									header
									)
					);
			try(Response response = buildEntity(builder, method, type, entity)){
					return response.readEntity(String.class);
				}
			}
		}
	
	/**
	 * @param builder
	 * @param accept
	 * @param encoding
	 * @param language
	 * @param cookie
	 * @param header
	 * @return
	 */
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
	
	/**
	 * @param builder
	 * @param method
	 * @param type
	 * @param entity
	 * @return
	 */
	protected Response buildEntity(
			final Builder builder, 
			final String method, 
			final Entity type,
			final String entity){
		javax.ws.rs.client.Entity<?> reqEntity = null;
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
						reqEntity = javax.ws.rs.client.Entity.form(form);
					}
				}
				break;
			case HTML:
				reqEntity = javax.ws.rs.client.Entity.html(entity);
				break;
			case JSON:
				reqEntity = javax.ws.rs.client.Entity.json(entity);
				break;
			case TEXT:
				reqEntity = javax.ws.rs.client.Entity.text(entity);
				break;
			case XHTML:
				reqEntity = javax.ws.rs.client.Entity.xhtml(entity);
				break;
			case XML:
				reqEntity = javax.ws.rs.client.Entity.xml(entity);
				break;
			default:
				break;
			}
		}
		return reqEntity != null ? builder.method(method, reqEntity) : builder.method(method);
	}
	
	/**
	 * @param uri
	 * @throws Exception
	 */
	@Command(name = "connect")
	public void connectToServer(
			@Option(names = {"-u", "--uri"}, required = true, description = "URI") 
			final URI uri) throws Exception {
		try(epf.util.websocket.Client client = epf.util.websocket.Client.connectToServer(uri)){
			client.onError(error -> error.printStackTrace());
			client.onMessage(msg -> System.out.println(msg));
			try(Scanner scanner = new Scanner(System.in)){
				while(scanner.hasNext()) {
					final String line = scanner.next();
					if("close".equals(line)) {
						break;
					}
					else {
						client.getSession().getBasicRemote().sendText(line);
					}
				}
			}
		}
	}
	
	/**
	 * @param uri
	 * @throws Exception 
	 */
	@Command(name = "stream")
	public void stream(
			@Option(names = {"-u", "--uri"}, required = true, description = "URI") 
			final URI uri) throws Exception {
		try(Client client = clientUtil.newClient(uri)){
			try(SseEventSource stream = client.stream(target -> target, req -> req)){
				stream.register(
						event -> {
							System.out.println(event.readData());
						},
						error -> {
							error.printStackTrace();
						});
				stream.open();
			}
		}
	}
	
	/**
	 * @param directory
	 * @param file
	 * @throws Exception
	 */
	@Command(name = "zip")
	public void zip(
			@Option(names = {"-d", "--dir"}, description = "Directory")
			final Path directory, 
			@Option(names = {"-f", "--file"}, description = "File")
			final Path file) throws Exception {
		ZipUtil.zip(directory, file);
	}
	
	/**
	 * @param file
	 * @param directory
	 * @throws Exception
	 */
	@Command(name = "un-zip")
	public void unZip(
			@Option(names = {"-f", "--file"}, description = "File")
			final Path file,
			@Option(names = {"-d", "--dir"}, description = "Directory")
			final Path directory 
			) throws Exception {
		ZipUtil.unZip(file, directory);
	}
	
	/**
	 * @param algorithm
	 * @param privateFile
	 * @param publicFile
	 * @throws Exception 
	 */
	@Command(name = "gen-keypair")
	public void generateKeyPair(
			@Option(names = {"-a", "--algorithm"}, description = "Algorithm", defaultValue = "RSA")
			final String algorithm, 
			@Option(names = {"-s", "--keysize"}, description = "Key Size", defaultValue = "2048")
			final int keySize, 
			@Option(names = {"-pr", "--private"}, description = "Private Key")
			final Path privateFile, 
			@Option(names = {"-pu", "--public"}, description = "Public Key")
			final Path publicFile,
			@Option(names = {"-e", "--encode"}, description = "Encoder", defaultValue = "")
			final String encode) throws Exception {
		final KeyPairGenerator generator = KeyPairGenerator.getInstance(algorithm);
		generator.initialize(keySize);
		final KeyPair keyPair = generator.generateKeyPair();
		final PrivateKey privateKey = keyPair.getPrivate();
		final PublicKey publicKey = keyPair.getPublic();
		Encoder encoder;
		switch(encode) {
		case "url":
			encoder = Base64.getUrlEncoder();
			break;
		case "mime":
			encoder = Base64.getMimeEncoder();
			break;
			default:
				encoder = Base64.getEncoder();
				break;
		}
		final String privateText = encoder.encodeToString(privateKey.getEncoded());
		final List<String> privateLines = StringUtil.split(privateText, 64);
		privateLines.add(0, "-----BEGIN PRIVATE KEY-----");
		privateLines.add("-----END PRIVATE KEY-----");
		out.println("Private Key");
		out.println(privateText);
		Files.write(
				privateFile, 
				privateLines,
				StandardOpenOption.TRUNCATE_EXISTING,
				StandardOpenOption.CREATE
				);
		out.println("Public Key");
		final String publicText = encoder.encodeToString(publicKey.getEncoded());
		final List<String> publicLines = StringUtil.split(publicText, 64);
		publicLines.add(0, "-----BEGIN PUBLIC KEY-----");
		publicLines.add("-----END PUBLIC KEY-----");
		out.println(publicText);
		Files.write(
				publicFile, 
				publicLines,
				StandardOpenOption.TRUNCATE_EXISTING,
				StandardOpenOption.CREATE
				);
	}
}
