package epf.lang;

import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import epf.lang.ollama.Ollama;
import epf.lang.schema.ollama.EmbeddingsRequest;
import epf.lang.schema.ollama.EmbeddingsResponse;
import epf.naming.Naming;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import static dev.langchain4j.internal.Utils.isNullOrBlank;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import dev.langchain4j.data.document.BlankDocumentException;
import dev.langchain4j.data.document.Document;

/**
 * 
 */
@ApplicationScoped
public class Persistence {

	private final DocumentSplitter splitter = DocumentSplitters.recursive(300, 0);
	
	private InMemoryEmbeddingStore<TextSegment> embeddingStore;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Lang.Internal.EMBEDDING_LANGUAGE_MODEL)
	String embeddingModelName;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = "epf.lang.store")
	String store;
	
	/**
     * 
     */
    @Inject 
    ManagedExecutor executor;
	
	/**
	 * 
	 */
	@RestClient
	Ollama ollama;
	
	@PostConstruct
	void postConstruct() {
		if(Files.exists(Paths.get(store))) {
			embeddingStore = InMemoryEmbeddingStore.fromFile(store);
		}
		else {
			embeddingStore = new InMemoryEmbeddingStore<>();
		}
	}
	
	private Document parse(InputStream inputStream) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();

            String text = new String(buffer.toByteArray(), StandardCharsets.UTF_8);

            if (isNullOrBlank(text)) {
                throw new BlankDocumentException();
            }

            return Document.from(text);
        } catch (BlankDocumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	private List<EmbeddingsResponse> embedSegments(final String model, List<TextSegment> segments){
		final List<EmbeddingsResponse> embeddings = new LinkedList<>();
		segments.forEach(segment -> {
			final EmbeddingsRequest request = new EmbeddingsRequest();
			request.setModel(model);
			request.setPrompt(segment.text());
			final EmbeddingsResponse embedding = ollama.generateEmbeddings(request);
			embeddings.add(embedding);
		});
		return embeddings;
	}
	
	private void storeEmbeddings(final List<EmbeddingsResponse> embeddings, final List<TextSegment> segments) {
        embeddingStore.addAll(embeddings.stream().map(e -> dev.langchain4j.data.embedding.Embedding.from(e.getEmbedding())).collect(Collectors.toList()), segments);
	}
	
	private EmbeddingsResponse embedQuery(final String model, final String query) {
		final EmbeddingsRequest embeddingRequest = new EmbeddingsRequest();
    	embeddingRequest.setModel(model);
    	embeddingRequest.setPrompt(query);
    	return ollama.generateEmbeddings(embeddingRequest);
	}
	
	private List<TextSegment> searchQuery(final EmbeddingsResponse embeddedQuery){
    	final EmbeddingSearchRequest searchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(dev.langchain4j.data.embedding.Embedding.from(embeddedQuery.getEmbedding()))
                .maxResults(3)
                .minScore(0.0)
                .build();
    	final EmbeddingSearchResult<TextSegment> searchResult = embeddingStore.search(searchRequest);
    	final List<TextSegment> segments = new LinkedList<>();
    	searchResult.matches().forEach(match -> {
    		segments.add(match.embedded());
    	});
    	return segments;
	}
	
	public void persist(final InputStream entity) {
		final Document document = parse(entity);
		final List<TextSegment> segments = splitter.split(document);
		final List<EmbeddingsResponse> embeddings = embedSegments(embeddingModelName, segments);
		storeEmbeddings(embeddings, segments);
		embeddingStore.serializeToFile(store);
	}
	
	public List<TextSegment> executeQuery(final String query) {
		final EmbeddingsResponse embeddedQuery = embedQuery(embeddingModelName, query);
    	return searchQuery(embeddedQuery);
	}
}
