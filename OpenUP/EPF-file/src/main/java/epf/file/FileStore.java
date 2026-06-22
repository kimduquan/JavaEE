package epf.file;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotEmpty;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Link;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.PathSegment;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;
import jakarta.ws.rs.core.UriInfo;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.http.SdkHttpResponse;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.JsonWebToken;
import epf.file.util.EntityOutput;
import epf.file.internal.PathBuilder;
import epf.management.util.OrganizationUtil;
import epf.naming.Naming;
import epf.naming.Naming.Security;
import io.smallrye.common.annotation.RunOnVirtualThread;

@jakarta.ws.rs.Path(Naming.FILE)
@RolesAllowed(Security.DEFAULT_ROLE)
@ApplicationScoped
public class FileStore {
	
	@ConfigProperty(name = Naming.File.BUCKET_NAME)
	String bucketName;
	
	@Inject
	S3Client client;

	@POST
	@jakarta.ws.rs.Path("{paths: .+}")
	@Consumes(MediaType.APPLICATION_OCTET_STREAM)
	@RunOnVirtualThread
	public Response createFile(
			@PathParam("paths")
			@NotEmpty
			final List<PathSegment> paths,
			@Context 
    		final UriInfo uriInfo,
    		@Context 
    		final HttpHeaders headers,
			final InputStream input,
			@Context
			final JsonWebToken jwt
			) throws Exception {
		final String organization = OrganizationUtil.getOrganizationId(jwt).orElseThrow(ForbiddenException::new);
		final PathBuilder builder = new PathBuilder(bucketName, organization);
		final String relativePath = builder.paths(paths).build();
		final PutObjectRequest request = PutObjectRequest.builder().bucket(bucketName).key(relativePath).build();
		final RequestBody body = RequestBody.fromInputStream(input, headers.getLength());
		final SdkHttpResponse response = client.putObject(request, body).sdkHttpResponse();
		return Response.status(response.statusCode(), response.statusText().orElse("")).build();
	}

	@GET
    @jakarta.ws.rs.Path("{paths: .+}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
	@RunOnVirtualThread
    public StreamingOutput read(
    		@Context 
    		final UriInfo uriInfo, 
    		@PathParam("paths")
    		@NotEmpty
    		final List<PathSegment> paths,
    		@Context
			final JsonWebToken jwt
    		) throws Exception {
		final String organization = OrganizationUtil.getOrganizationId(jwt).orElseThrow(ForbiddenException::new);
		final PathBuilder builder = new PathBuilder(bucketName, organization);
		final String relativePath = builder.paths(paths).build();
		final GetObjectRequest request = GetObjectRequest.builder().bucket(bucketName).key(relativePath).build();
		final ResponseInputStream<GetObjectResponse> response = client.getObject(request);
		final EntityOutput output = new EntityOutput(response);
		return output;
	}

	@DELETE
    @jakarta.ws.rs.Path("{paths: .+}")
	@RunOnVirtualThread
    public Response delete(
    		@Context 
    		final UriInfo uriInfo, 
    		@PathParam("paths")
    		@NotEmpty
    		final List<PathSegment> paths,
    		@Context
			final JsonWebToken jwt) throws Exception {
		final String organization = OrganizationUtil.getOrganizationId(jwt).orElseThrow(ForbiddenException::new);
		final PathBuilder builder = new PathBuilder(bucketName, organization);
		final String relativePath = builder.paths(paths).build();
		final DeleteObjectRequest request = DeleteObjectRequest.builder().bucket(bucketName).key(relativePath).build();
		final SdkHttpResponse response = client.deleteObject(request).sdkHttpResponse();
		return Response.status(response.statusCode(), response.statusText().orElse("")).build();
	}
	
	@HEAD
	@RunOnVirtualThread
	public Response listFiles(
			@QueryParam("paths") 
			final List<String> paths,
			@Context
			final JsonWebToken jwt) throws Exception {
		final String organization = OrganizationUtil.getOrganizationId(jwt).orElseThrow(ForbiddenException::new);
		final PathBuilder builder = new PathBuilder(bucketName, organization);
		final String relativePath = builder.paths(paths.toArray(new String[0])).build() + "/";
		final ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(bucketName).prefix(relativePath).build();
		final Path tempDir = Files.createTempDirectory("");
		final Map<String, S3Object> objects = new LinkedHashMap<>();
		for(S3Object object : client.listObjectsV2(request).contents()) {
			final Path path = tempDir.resolve(object.key());
			Files.createFile(path);
			objects.put(object.key(), object);
		}
		final List<Link> links = new ArrayList<>();
		Files.list(tempDir).forEach(file -> {
			final String key = file.relativize(tempDir).toString();
			final S3Object object = objects.get(key);
			final Link link = Link.fromPath(object.key()).title(object.toString()).build();
			links.add(link);
		});
		return Response.ok().links(links.toArray(new Link[0])).build();
	}
	
	private FileMatch findText(final S3Object object, final String text) throws Exception {
		final GetObjectRequest request = GetObjectRequest.builder().bucket(bucketName).key(object.key()).build();
		FileMatch match = null;
		try(ResponseInputStream<GetObjectResponse> response = client.getObject(request)) {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(response, StandardCharsets.UTF_8))) {
				String line;
				int index = 0;
	            while ((line = reader.readLine()) != null) {
	            	if(line.contains(text)) {
	            		match = new FileMatch();
	            		match.setLine(index);
	            		match.setPath(object.key());
	            		match.setText(line);
	            		break;
	            	}
	            	index++;
	            }
			}
		}
		return match;
	}
	
	@GET
	@RunOnVirtualThread
	public Response findFiles(
			@QueryParam("paths") 
			final List<String> paths,
			@QueryParam("pattern") 
			final String pattern,
			@QueryParam("text") 
			final String text,
			@Context
			final JsonWebToken jwt) throws Exception {
		final String organization = OrganizationUtil.getOrganizationId(jwt).orElseThrow(ForbiddenException::new);
		final PathBuilder builder = new PathBuilder(bucketName, organization);
		final String relativePath = builder.paths(paths.toArray(new String[0])).build() + "/";
		final ListObjectsV2Request request = ListObjectsV2Request.builder().bucket(bucketName).prefix(relativePath).build();
		final Path tempDir = Files.createTempDirectory("");
		final Map<String, S3Object> objects = new LinkedHashMap<>();
		for(S3Object object : client.listObjectsV2(request).contents()) {
			final Path path = tempDir.resolve(object.key());
			Files.createFile(path);
			objects.put(object.key(), object);
		}
		final List<S3Object> results = new ArrayList<>();
		try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(tempDir, pattern)) {
			dirStream.forEach(file -> {
				final String key = file.relativize(tempDir).toString();
				final S3Object object = objects.get(key);
				results.add(object);
			});
		}
		final List<FileMatch> matches = new ArrayList<>();
		if(text != null) {
			final List<S3Object> results2 = new ArrayList<>();
			for(S3Object object : results) {
				final FileMatch match = findText(object, text);
				if(match != null) {
					matches.add(match);
					results2.add(object);
				}
			}
			results.clear();
			results.addAll(results2);
		}
		final List<Link> links = new ArrayList<>();
		for(S3Object object : results) {
			final Link link = Link.fromPath(object.key()).title(object.toString()).build();
			links.add(link);
		}
		return Response.ok(matches).links(links.toArray(new Link[0])).build();
	}
}
