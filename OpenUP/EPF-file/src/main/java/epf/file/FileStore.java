package epf.file;

import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotEmpty;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.MatrixParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.PathSegment;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.StreamingOutput;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.Readiness;
import epf.file.util.EntityOutput;
import epf.file.internal.FileWatchService;
import epf.file.internal.PathBuilder;
import epf.file.util.FileUtil;
import epf.file.util.PathUtil;
import epf.file.validation.PathValidator;
import epf.naming.Naming;
import epf.naming.Naming.Security;
import epf.util.logging.LogManager;

/**
 *
 * @author FOXCONN
 */
@jakarta.ws.rs.Path(Naming.FILE)
@RolesAllowed(Security.DEFAULT_ROLE)
@ApplicationScoped
public class FileStore {
	
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
	@Readiness
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

	/**
	 * @param tenant
	 * @param paths
	 * @param uriInfo
	 * @param input
	 * @param security
	 * @return
	 * @throws Exception
	 */
	@POST
	@jakarta.ws.rs.Path("{paths: .+}")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	public Response createFile(
			@MatrixParam(Naming.Management.TENANT)
			final String tenant,
			@PathParam("paths")
			@NotEmpty
			final List<PathSegment> paths,
			@Context 
    		final UriInfo uriInfo,
			final InputStream input,
			@Context
			final SecurityContext security
			) throws Exception {
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
					final String title = targetFilePaths.get(path.getFileName().toString());
					return Link.fromPath(root.relativize(path).toString()).rel("self").title(title).build();
				})
				.collect(Collectors.toList())
				.toArray(new Link[0]);
		return Response.ok().links(links).build();
	}

	/**
	 * @param tenant
	 * @param uriInfo
	 * @param paths
	 * @param security
	 * @return
	 * @throws Exception
	 */
	@GET
    @jakarta.ws.rs.Path("{paths: .+}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public StreamingOutput read(
			@MatrixParam(Naming.Management.TENANT)
			final String tenant,
    		@Context 
    		final UriInfo uriInfo, 
    		@PathParam("paths")
    		@NotEmpty
    		final List<PathSegment> paths,
    		@Context
			final SecurityContext security
    		) throws Exception {
		PathValidator.validate(paths, security, HttpMethod.GET);
		final PathBuilder builder = new PathBuilder(system, rootFolder, tenant);
		final Path targetFile = builder
				.paths(paths)
				.build();
		return new EntityOutput(Files.newInputStream(targetFile));
	}

	/**
	 * @param tenant
	 * @param uriInfo
	 * @param paths
	 * @param security
	 * @return
	 * @throws Exception
	 */
	@DELETE
    @jakarta.ws.rs.Path("{paths: .+}")
    public Response delete(
			@MatrixParam(Naming.Management.TENANT)
			final String tenant,
    		@Context 
    		final UriInfo uriInfo, 
    		@PathParam("paths")
    		@NotEmpty
    		final List<PathSegment> paths,
    		@Context
			final SecurityContext security) throws Exception {
		PathValidator.validate(paths, security, HttpMethod.DELETE);
		final PathBuilder builder = new PathBuilder(system, rootFolder, tenant);
		final Path targetFile = builder
				.paths(paths)
				.build();
		if(Files.isDirectory(targetFile)) {
			if(PathUtil.isEmpty(targetFile)) {
				Files.delete(targetFile);
			}
			else {
				FileUtil.deleteDirectory(targetFile);
			}
		}
		else {
			Files.delete(targetFile);
		}
		return Response.ok(targetFile.toString()).build();
	}
}
