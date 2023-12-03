package epf.workflow.function.openapi;

import java.net.URL;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import io.smallrye.openapi.runtime.io.OpenApiParser;

/**
 * 
 */
public interface OpenAPIUtil {

	/**
	 * @param url
	 * @return
	 * @throws Exception
	 */
	static OpenAPI read(final URL url) throws Exception {
		return OpenApiParser.parse(url);
	}
}
