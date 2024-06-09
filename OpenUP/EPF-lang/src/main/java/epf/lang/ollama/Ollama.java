package epf.lang.ollama;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import epf.lang.schema.ollama.ChatRequest;
import epf.lang.schema.ollama.EmbeddingsRequest;
import epf.lang.schema.ollama.EmbeddingsResponse;
import epf.lang.schema.ollama.GenerateRequest;
import epf.naming.Naming;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

/**
 * 
 */
@RegisterRestClient(configKey = Naming.Lang.Internal.OLLAMA)
@Path("api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface Ollama {

	/**
	 * @param request
	 * @return
	 */
	@Path("generate")
	@POST
	CompletionStage<InputStream> generate(final GenerateRequest request);
	
	/**
	 * @param request
	 * @return
	 */
	@Path("chat")
	@POST
	CompletionStage<InputStream> chat(final ChatRequest request);
	
	/**
	 * @param request
	 * @return
	 */
	@Path("embeddings")
	@POST
	EmbeddingsResponse generateEmbeddings(final EmbeddingsRequest request);
	
	/**
	 * @param <T>
	 * @param response
	 * @param clazz
	 * @param consumer
	 * @throws Throwable
	 */
	static <T> void stream(final CompletionStage<InputStream> response, final Class<T> clazz, final Function<T, Boolean> consumer) throws Throwable {
		final AtomicReference<Throwable> throwable = new AtomicReference<>();
		response.handleAsync((stream, error) -> {
			if(error != null) {
				throwable.set(error);
			}
			else {
				try(BufferedReader reader = new BufferedReader(new InputStreamReader(stream))){
            		try(Jsonb jsonb = JsonbBuilder.create()){
                		String line;
            		    while ((line = reader.readLine()) != null) {
            		        final T res = jsonb.fromJson(line, clazz);
            		        if(false == consumer.apply(res)) {
            		        	break;
            		        }
            		    }
            		}
        		}
            	catch(Exception ex) {
            		throwable.set(ex);
            	}
			}
			return stream;
		});
		if(throwable.get() != null) {
			throw throwable.get();
		}
	}
}
