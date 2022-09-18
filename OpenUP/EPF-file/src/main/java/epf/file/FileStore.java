package epf.file;

import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.client.util.EntityOutput;
import epf.file.internal.FileWatchService;
import epf.file.util.FileUtil;
import epf.file.validation.PathValidator;
import epf.naming.Naming;
import epf.naming.Naming.Security;
import epf.util.logging.LogManager;

/**
 *
 * @author FOXCONN
 */
@javax.ws.rs.Path(Naming.FILE)
@RolesAllowed(Security.DEFAULT_ROLE)
@ApplicationScoped
public class FileStore implements epf.client.file.Files {
	
	/**
	 * 
	 */
	private transient static final Logger LOGGER = LogManager.getLogger(FileStore.class.getName());
	
	/**
	 * 
	 */
	@Inject
	private transient FileSystem system;
	
	/**
	 * 
	 */
	@ConfigProperty(name = Naming.File.ROOT)
	@Inject
	private transient String rootFolder;
	
	/**
	 * 
	 */
	@Inject
	private transient FileWatchService watchService;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		final Path rootPath = system.getPath(rootFolder);
		try {
			LOGGER.info("[FileStore]epf.file.root=" + rootPath.toAbsolutePath().toString());
			Files.list(rootPath).forEach(watchService::register);
		}
		catch(Exception ex) {
			LOGGER.log(Level.SEVERE, "[FileStore.watchService]", ex);
		}
	}

	@Override
	public Response createFile(
			final String tenant,
			final List<PathSegment> paths,
			final UriInfo uriInfo,
			final InputStream input, 
			final SecurityContext security) throws Exception {
		PathValidator.validate(paths, security, HttpMethod.POST);
		final PathBuilder builder = new PathBuilder(system, rootFolder, tenant);
		final Path targetFolder = builder
				.paths(paths)
				.build();
		final String relativePath = builder.buildRelative();
		final List<Path> files = new ArrayList<>();
		final Map<String, String> targetFilePaths = new ConcurrentHashMap<>();
		targetFolder.toFile().mkdirs();
		final Path targetFile = Files.createTempFile(targetFolder, "", "");
		Files.copy(input, targetFile, StandardCopyOption.REPLACE_EXISTING);
		files.add(targetFile);
		targetFilePaths.put(targetFile.getFileName().toString(), relativePath + "/" + targetFile.getFileName().toString());
		final Path root = tenant != null ? system.getPath(rootFolder, tenant) : system.getPath(rootFolder);
		final Link[] links = files
				.stream()
				.map(path -> {
					UriBuilder uriBuilder = uriInfo.getBaseUriBuilder().path(getClass());
					final String title = targetFilePaths.get(path.getFileName().toString());
					final Iterator<Path> pathIt = root.relativize(path).iterator();
					while(pathIt.hasNext()) {
						uriBuilder = uriBuilder.path(pathIt.next().toString());
					}
					return Link.fromUri(uriBuilder.build()).rel("self").title(title).build();
				})
				.collect(Collectors.toList())
				.toArray(new Link[0]);
		return Response.ok().links(links).build();
	}

	@Override
	public StreamingOutput read(
			final String tenant,
			final UriInfo uriInfo, 
			final List<PathSegment> paths,
			final SecurityContext security) throws Exception {
		PathValidator.validate(paths, security, HttpMethod.GET);
		final PathBuilder builder = new PathBuilder(system, rootFolder, tenant);
		final Path targetFile = builder
				.paths(paths)
				.build();
		return new EntityOutput(Files.newInputStream(targetFile));
	}

	@Override
	public Response delete(
			final String tenant,
			final UriInfo uriInfo, 
			final List<PathSegment> paths, 
			final SecurityContext security) throws Exception {
		PathValidator.validate(paths, security, HttpMethod.DELETE);
		final PathBuilder builder = new PathBuilder(system, rootFolder, tenant);
		final Path targetFile = builder
				.paths(paths)
				.build();
		if(Files.isDirectory(targetFile)) {
			FileUtil.deleteDirectories(targetFile);
		}
		else {
			Files.delete(targetFile);
		}
		return Response.ok(targetFile.toString()).build();
	}
}
