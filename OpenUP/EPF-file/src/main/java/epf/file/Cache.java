package epf.file;

import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotEmpty;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.MatrixParam;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.PathSegment;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.ws.rs.core.StreamingOutput;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.file.cache.FileCache;
import epf.file.internal.PathBuilder;
import epf.file.validation.PathValidator;
import epf.naming.Naming;
import epf.naming.Naming.Security;
import io.smallrye.common.annotation.RunOnVirtualThread;

@jakarta.ws.rs.Path(Naming.CACHE)
@RolesAllowed(Security.DEFAULT_ROLE)
@ApplicationScoped
public class Cache {
	
	@Inject
	transient FileSystem system;
	
	@ConfigProperty(name = Naming.File.Cache.ROOT)
	@Inject
	transient String rootFolder;
	
	@Inject
	transient FileCache cache;

	@POST
	@jakarta.ws.rs.Path("{paths: .+}")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	@RunOnVirtualThread
	public Response putFile(
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
		targetFolder.toFile().mkdirs();
		final Path targetFile = Files.createTempFile(targetFolder, "", "");
		Files.copy(input, targetFile, StandardCopyOption.REPLACE_EXISTING);
		return Response.ok().build();
	}

	@GET
    @jakarta.ws.rs.Path("{paths: .+}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
	@RunOnVirtualThread
    public StreamingOutput getFile(
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
		return cache.getFile(targetFile).orElseThrow(NotFoundException::new);
	}

}
