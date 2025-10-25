package epf.query.cache;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import io.quarkus.cache.CacheKey;
import io.quarkus.cache.CacheKeyGenerator;
import io.quarkus.cache.CompositeCacheKey;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.PathSegment;

@ApplicationScoped
public class QueryCacheKeyGenerator implements CacheKeyGenerator {

	@Override
	public Object generate(final Method method, final Object... methodParams) {
		final List<Object> cacheKeys = new ArrayList<>();
		final Parameter[] parameters = method.getParameters();
		for(int index = 0; index < parameters.length; index++) {
			final Parameter parameter = parameters[index];
			if(parameter.getAnnotationsByType(CacheKey.class).length > 0) {
				final Object value = methodParams[index];
				if(value != null) {
					final Class<?> parameterType = parameter.getType();
					if(parameterType.isArray() && PathSegment.class == parameterType.getComponentType()) {
						final PathSegment[] pathSegments = (PathSegment[]) value;
						for(PathSegment pathSegment : pathSegments) {
							cacheKeys.add("/");
							cacheKeys.add(pathSegment.getPath());
							pathSegment.getMatrixParameters().forEach((name, values) -> {
								cacheKeys.add(";");
								cacheKeys.add(name);
								cacheKeys.add("=");
								cacheKeys.add(values.stream().collect(Collectors.joining(",")));
							});
						}
					}
					else {
						cacheKeys.add(value);
					}
				}
			}
		}
		return new CompositeCacheKey(cacheKeys.toArray());
	}
}
