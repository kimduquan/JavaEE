package epf.lang;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import epf.lang.internal.Ollama;
import epf.lang.schema.Embedding;
import epf.lang.schema.EmbeddingRequest;
import epf.lang.schema.Message;
import epf.lang.schema.Request;
import epf.lang.schema.Role;
import epf.lang.schema.StreamResponse;
import epf.naming.Naming;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.RemoteEndpoint.Basic;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

/**
 * 
 */
@ServerEndpoint("/lang/{model}")
@ApplicationScoped
public class Lang {
	
	/**
	 * 
	 */
	private static final String DEFAULT_PROMPT_TEMPLATE = "%s\n\nAnswer using the following information:\n%s";
	
	private final Map<String, String> promptTemplates = Map.of(
			//"llama3:instruct", "<|start_header_id|>user<|end_header_id|>\n\n%s<|eot_id|><|start_header_id|>assistant<|end_header_id|>\n\n%s<|eot_id|>",
			//"nous-hermes2:10.7b", "<|im_start|>user\n%s<|im_end|>\n<|im_start|>assistant\n%s<|im_end|>\n"
			);

    @Inject 
    ManagedExecutor executor;
	
	@RestClient
	Ollama ollama;
	
	@Inject
	@ConfigProperty(name = "epf.lang.path")
	String path;
	
	@Inject
	@ConfigProperty(name = "epf.lang.model")
	String embeddingModelName;
	
	private EmbeddingStore<TextSegment> embeddingStore;
	
	private List<Document> loadDocuments(){
		final TextDocumentParser parser = new TextDocumentParser();
        return FileSystemDocumentLoader.loadDocumentsRecursively(Paths.get(path), parser);
	}
	
	private List<TextSegment> splitDocuments(List<Document> documents){
		final DocumentSplitter splitter = DocumentSplitters.recursive(300, 0);
        return splitter.splitAll(documents);
	}
	
	private List<Embedding> embedSegments(List<TextSegment> segments, Consumer<Float> progress){
		final int size = segments.size();
		final List<Embedding> embeddings = new LinkedList<>();
		final AtomicInteger count = new AtomicInteger();
		segments.forEach(segment -> {
			final EmbeddingRequest request = new EmbeddingRequest();
			request.setModel(embeddingModelName);
			request.setPrompt(segment.text());
			final Embedding embedding = ollama.generateEmbedding(request);
			embeddings.add(embedding);
			progress.accept((float)count.incrementAndGet() * 100 / size);
		});
		return embeddings;
	}
	
	private void storeEmbeddings(final List<Embedding> embeddings, final List<TextSegment> segments) {
        embeddingStore.addAll(embeddings.stream().map(e -> dev.langchain4j.data.embedding.Embedding.from(e.getEmbedding())).collect(Collectors.toList()), segments);
	}
	
	private Embedding embedQuery(final String model, final String query) {
		final EmbeddingRequest embeddingRequest = new EmbeddingRequest();
    	embeddingRequest.setModel(model);
    	embeddingRequest.setPrompt(query);
    	return ollama.generateEmbedding(embeddingRequest);
	}
	
	private List<TextSegment> searchQuery(final Embedding embeddedQuery){
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
	
	private String injectPrompt(final String model, final String query, final List<TextSegment> segments) {
    	final StringBuilder contents = new StringBuilder();
    	segments.forEach(segment -> {
    		contents.append(segment.text());
    		contents.append("\n\n");
    	});
    	return String.format(promptTemplates.getOrDefault(model, DEFAULT_PROMPT_TEMPLATE), query, contents.toString());
	}
	
	private void chat(final String model, final String prompt, final Function<StreamResponse, Boolean> consumer) {
		final Request request = new Request();
        request.setModel(model);
        final Message requestMessage = new Message();
        requestMessage.setContent(prompt);
        requestMessage.setRole(Role.user);
        request.setMessages(Arrays.asList(requestMessage));
        final CompletionStage<InputStream> response = ollama.stream(request);
        System.out.println("stream:");
        response.handleAsync((stream, error) -> {
        	if(error != null) {
        		error.printStackTrace();
        	}
        	else {
            	try(BufferedReader reader = new BufferedReader(new InputStreamReader(stream))){
            		try(Jsonb jsonb = JsonbBuilder.create()){
                		String line;
            		    while ((line = reader.readLine()) != null) {
            		        final StreamResponse res = jsonb.fromJson(line, StreamResponse.class);
            		        if(false == consumer.apply(res)) {
            		        	break;
            		        }
            		    }
            		}
        		}
            	catch(Exception ex) {
            		ex.printStackTrace();
            	}
        	}
        	return stream;
        });
	}
	
	@PostConstruct
	void postConstruct() {
        embeddingStore = new InMemoryEmbeddingStore<>();
	}

    @OnOpen
    public void onOpen(Session session, @PathParam(Naming.Lang.Internal.MODEL) String model) {
        System.out.println("onOpen> " + model);
        executor.submit(() -> {
        	try {
        		System.out.println("load documents:");
                final List<Document> documents = loadDocuments();
                System.out.println("split documents:");
                final List<TextSegment> segments = splitDocuments(documents);
                System.out.println("embed segments:");
                final List<Embedding> embeddings = embedSegments(segments, (progress) -> {
                	System.out.println(progress);
                });
                System.out.println("store embeddings:");
                storeEmbeddings(embeddings, segments);
                System.out.println("done.");
        	}
        	catch(Exception ex) {
        		ex.printStackTrace();
        	}
        });
    }

    @OnClose
    public void onClose(Session session, @PathParam(Naming.Lang.Internal.MODEL) String model) {
        System.out.println("onClose> " + model);
    }

    @OnError
    public void onError(Session session, @PathParam(Naming.Lang.Internal.MODEL) String model, Throwable throwable) {
        System.out.println("onError> " + model + ": " + throwable);
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(final Session session, @PathParam(Naming.Lang.Internal.MODEL) String model, String message) throws Exception {
        System.out.println("onMessage> " + model + ": " + message);
        executor.submit(() -> {
            System.out.println("embed query:");
        	final Embedding embeddedQuery = embedQuery(embeddingModelName, message);
        	System.out.println("search query:");
        	final List<TextSegment> segments = searchQuery(embeddedQuery);
        	System.out.println("inject prompt:");
        	final String prompt = injectPrompt(model, message, segments);
        	System.out.print(prompt);
        	System.out.println("chat:");
        	final Basic remote = session.getBasicRemote();
        	chat(model, prompt, (response) -> {
        		try {
        			if(session.isOpen()) {
            			remote.sendText(response.getMessage().getContent());
        			}
        			System.out.print(response.getMessage().getContent());
        		}
            	catch(Exception ex) {
            		ex.printStackTrace();
            	}
        		return session.isOpen();
        	});
        });
    }
}
