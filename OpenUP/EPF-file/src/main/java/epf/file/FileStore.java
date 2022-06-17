package epf.file;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
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
import epf.file.cache.FileCache;
import epf.file.cache.FileOutput;
import epf.file.util.FileUtil;
import epf.file.validation.PathValidator;
import epf.util.EPFException;
import epf.naming.Naming;
import epf.naming.Naming.Security;

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
	private transient IWatchService watchService;
	
	/**
	 *
	 */
	@Inject
	private transient FileCache cache;
	
	/**
	 * 
	 */
	@PostConstruct
	protected void postConstruct() {
		watchService.register(system.getPath(rootFolder));
	}

	@Override
	public Response createFile(
			final List<PathSegment> paths,
			final UriInfo uriInfo,
			final InputStream input, 
			final SecurityContext security) {
		PathValidator.validate(paths, security, HttpMethod.POST);
		final PathBuilder builder = new PathBuilder(rootFolder, system);
		final Path targetFolder = builder
				.paths(paths)
				.build();
		final String relativePath = builder.buildRelative();
		final List<Path> files = new ArrayList<>();
		final Map<String, String> targetFilePaths = new ConcurrentHashMap<>();
		try {
			targetFolder.toFile().mkdirs();
			final Path targetFile = Files.createTempFile(targetFolder, "", "");
			Files.copy(input, targetFile, StandardCopyOption.REPLACE_EXISTING);
			files.add(targetFile);
			targetFilePaths.put(targetFile.getFileName().toString(), relativePath + "/" + targetFile.getFileName().toString());
		} 
		catch (IOException e) {
			throw new EPFException(e);
		}
		final Path root = system.getPath(rootFolder);
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
			final UriInfo uriInfo, 
			final List<PathSegment> paths,
			final SecurityContext security) throws Exception {
		PathValidator.validate(paths, security, HttpMethod.GET);
		final PathBuilder builder = new PathBuilder(rootFolder, system);
		final Path targetFile = builder
				.paths(paths)
				.build();
		final Optional<FileOutput> fileOutput = cache.getFile(targetFile);
		if(!fileOutput.isPresent()) {
			return new EntityOutput(Files.newInputStream(targetFile));
		}
		return fileOutput.get();
	}

	@Override
	public Response delete(
			final UriInfo uriInfo, 
			final List<PathSegment> paths, 
			final SecurityContext security) {
		PathValidator.validate(paths, security, HttpMethod.DELETE);
		final PathBuilder builder = new PathBuilder(rootFolder, system);
		final Path targetFile = builder
				.paths(paths)
				.build();
		try {
			if(Files.isDirectory(targetFile)) {
				FileUtil.deleteDirectories(targetFile);
			}
			else {
				Files.delete(targetFile);
			}
		} 
		catch (IOException e) {
			throw new EPFException(e);
		}
		return Response.ok(targetFile.toString()).build();
	}
}
