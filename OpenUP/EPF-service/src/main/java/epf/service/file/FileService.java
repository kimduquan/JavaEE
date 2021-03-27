/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package epf.service.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import epf.client.EPFException;
import epf.schema.roles.Role;
import epf.util.client.EntityOutput;

/**
 *
 * @author FOXCONN
 */
@javax.ws.rs.Path("file")
@RolesAllowed(Role.DEFAULT_ROLE)
@ApplicationScoped
public class FileService implements epf.client.file.Files {
	
	/**
	 * 
	 */
	public static final String ROOT_FOLDER_CONFIG = "epf.file.root";
	
	/**
	 * 
	 */
	@ConfigProperty(name = ROOT_FOLDER_CONFIG)
	@Inject
	private transient String rootFolder;
	
	/**
	 * 
	 */
	@Inject
	private transient Logger logger;

	@Override
	public Response createFile(
			final List<PathSegment> paths,
			final UriInfo uriInfo,
			final HttpServletRequest request, 
			final SecurityContext security) {
		final PathBuilder builder = new PathBuilder(rootFolder);
		final Path targetFolder = builder
				.principal(security.getUserPrincipal())
				.paths(paths)
				.build();
		final List<Path> files = new ArrayList<>();
		try {
			targetFolder.toFile().mkdirs();
			request.getParts().forEach(part -> {
				try {
					final Path targetFile = Files.createTempFile(targetFolder, "", "");
					Files.copy(part.getInputStream(), targetFile);
					files.add(targetFile);
				} 
				catch (IOException e) {
					logger.log(Level.SEVERE, e.getMessage(), e);
				}
			});
		} 
		catch (IOException | ServletException e) {
			throw new EPFException(e);
		}
		final Path root = Path.of(rootFolder);
		final Link[] links = files
				.stream()
				.map(path -> uriInfo
						.getBaseUri()
						.resolve(
								root
								.relativize(path)
								.toUri()
								)
						)
				.map(uri -> Link
						.fromUri(uri)
						.rel("file")
						.build()
						)
				.collect(Collectors.toList())
				.toArray(new Link[0]);
		return Response.ok().links(links).build();
	}

	@Override
	public StreamingOutput lines(
			final UriInfo uriInfo, 
			final List<PathSegment> paths,
			final SecurityContext security) {
		final PathBuilder builder = new PathBuilder(rootFolder);
		final Path targetFile = builder
				.principal(security.getUserPrincipal())
				.paths(paths)
				.build();
		StreamingOutput response;
		try {
			response = new EntityOutput(Files.newInputStream(targetFile));
		} 
		catch (IOException e) {
			throw new EPFException(e);
		}
		return response;
	}

	@Override
	public Response delete(
			final UriInfo uriInfo, 
			final List<PathSegment> paths, 
			final SecurityContext security) {
		final PathBuilder builder = new PathBuilder(rootFolder);
		final Path targetFile = builder
				.principal(security.getUserPrincipal())
				.paths(paths)
				.build();
		try {
			Files.delete(targetFile);
		} 
		catch (IOException e) {
			throw new EPFException(e);
		}
		return Response.ok().build();
	}
    
}
