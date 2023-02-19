package epf.file;

import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.file.cache.FileCache;
import epf.file.internal.PathBuilder;
import epf.file.validation.PathValidator;
import epf.naming.Naming;
import epf.naming.Naming.Security;

/**
 * 
 */
@javax.ws.rs.Path(Naming.CACHE)
@RolesAllowed(Security.DEFAULT_ROLE)
@ApplicationScoped
public class Cache implements epf.file.client.FileCache {
	
	/**
	 * 
	 */
	@Inject
	private transient FileSystem system;
	
	/**
	 * 
	 */
	@ConfigProperty(name = Naming.File.Cache.ROOT)
	@Inject
	private transient String rootFolder;
	
	/**
	 *
	 */
	@Inject
	private transient FileCache cache;

	@Override
	public Response putFile(final String tenant, final List<PathSegment> paths, final UriInfo uriInfo, final InputStream input, final SecurityContext security) throws Exception {
		PathValidator.validate(paths, security, HttpMethod.POST);
		final PathBuilder builder = new PathBuilder(system, rootFolder, tenant);
		final Path targetFolder = builder
				.paths(paths)
				.build();
		targetFolder.toFile().mkdirs();
		final Path targetFile = Files.createTempFile(targetFolder, "", "");
		Files.copy(input, targetFile, StandardCopyOption.REPLACE_EXISTING);
		return Response.ok().build();
	}

	@Override
	public StreamingOutput getFile(final String tenant, final UriInfo uriInfo, final List<PathSegment> paths, final SecurityContext security)
			throws Exception {
		PathValidator.validate(paths, security, HttpMethod.GET);
		final PathBuilder builder = new PathBuilder(system, rootFolder, tenant);
		final Path targetFile = builder
				.paths(paths)
				.build();
		return cache.getFile(targetFile).orElseThrow(NotFoundException::new);
	}

}
