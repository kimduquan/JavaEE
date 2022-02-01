package epf.persistence.util;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.persistence.Query;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * @author PC
 *
 */
public interface QueryUtil {

	/**
	 * @param query
	 * @param firstResult
	 * @param maxResults
	 * @param response
	 * @return
	 */
	static ResponseBuilder collectResult(
			final Query query,
    		final Integer firstResult,
            final Integer maxResults,
    		final ResponseBuilder response) {
		if(firstResult != null){
            query.setFirstResult(firstResult);
        }
        if(maxResults != null){
            query.setMaxResults(maxResults);
        }
        final Stream<?> result = query.getResultStream();
        return response.entity(result.collect(Collectors.toList()));
	}
}
