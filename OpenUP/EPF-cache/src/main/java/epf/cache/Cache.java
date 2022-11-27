package epf.cache;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonValue;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import org.eclipse.microprofile.health.Readiness;
import epf.cache.internal.Manager;
import epf.cache.internal.Provider;
import epf.naming.Naming;
import epf.util.StringUtil;
import epf.util.json.JsonUtil;

/**
 * @author PC
 *
 */
@ApplicationScoped
@Path(Naming.CACHE)
public class Cache implements epf.client.cache.Cache {
	
	/**
	 * 
	 */
	@Inject @Readiness
	private transient Provider provider;
	
	private String getKey(final List<PathSegment> paths) {
		final String[] key = paths.stream().map(path -> path.getPath()).collect(Collectors.toList()).toArray(new String[0]);
		return StringUtil.join(key);
	}

	@Override
	public Response put(final String tenant, final String name, final List<PathSegment> paths, final InputStream value) throws Exception {
		final Manager manager = provider.getManager(tenant);
		if(manager != null) {
			final javax.cache.Cache<String, Object> cache = manager.getCache(name);
			if(cache != null) {
				final String key = getKey(paths);
				try {
					final Object object = JsonUtil.readObject(value);
					cache.put(key, object);
					return Response.ok().build();
				}
				catch(JsonException ex) {
					throw new BadRequestException();
				}
			}
		}
		throw new NotFoundException();
	}

	@Override
	public Response replace(final String tenant, final String name, final List<PathSegment> paths, final InputStream value) throws Exception {
		final Manager manager = provider.getManager(tenant);
		if(manager != null) {
			final javax.cache.Cache<String, Object> cache = manager.getCache(name);
			if(cache != null) {
				final String key = getKey(paths);
				try {
					final Object object = JsonUtil.readObject(value);
					cache.replace(key, object);
					return Response.ok().build();
				}
				catch(JsonException ex) {
					throw new BadRequestException();
				}
			}
		}
		throw new NotFoundException();
	}

	@Override
	public Response remove(final String tenant, final String name, final List<PathSegment> paths) throws Exception {
		final Manager manager = provider.getManager(tenant);
		if(manager != null) {
			final javax.cache.Cache<String, Object> cache = manager.getCache(name);
			if(cache != null) {
				final String key = getKey(paths);
				cache.remove(key);
				return Response.ok().build();
			}
		}
		throw new NotFoundException();
	}

	@Override
	public Response get(final String tenant, final String name, final List<PathSegment> paths) throws Exception {
		final Manager manager = provider.getManager(tenant);
		if(manager != null) {
			final javax.cache.Cache<String, Object> cache = manager.getCache(name);
			if(cache != null) {
				final String key = getKey(paths);
				final Object value = cache.get(key);
				if(value != null) {
					return Response.ok(value).build();	
				}
			}
		}
		throw new NotFoundException();
	}

	@Override
	public Response containsKey(final String tenant, final String name, final List<PathSegment> paths) throws Exception {
		final Manager manager = provider.getManager(tenant);
		if(manager != null) {
			final javax.cache.Cache<String, Object> cache = manager.getCache(name);
			if(cache != null) {
				final String key = getKey(paths);
				if(cache.containsKey(key)) {
					return Response.ok().build();
				}
				else {
					return Response.noContent().build();
				}
			}
		}
		throw new NotFoundException();
	}

	@Override
	public Response getAll(final String tenant, final String name, final InputStream map) {
		final Manager manager = provider.getManager(tenant);
		if(manager != null) {
			final javax.cache.Cache<String, Object> cache = manager.getCache(name);
			if(cache != null) {
				try {
					final JsonArray keys = JsonUtil.readArray(map);
					final Map<String, Object> values = cache.getAll(keys.stream().map(JsonValue::toString).collect(Collectors.toSet()));
					return Response.ok(values).build();
				}
				catch(JsonException ex) {
					throw new BadRequestException();
				}
			}
		}
		throw new NotFoundException();
	}
	
}
