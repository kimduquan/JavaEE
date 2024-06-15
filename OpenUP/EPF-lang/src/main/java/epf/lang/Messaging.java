package epf.lang;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
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
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import epf.lang.ollama.Ollama;
import epf.lang.schema.ollama.ChatRequest;
import epf.lang.schema.ollama.ChatResponse;
import epf.lang.schema.ollama.EmbeddingsRequest;
import epf.lang.schema.ollama.EmbeddingsResponse;
import epf.lang.schema.ollama.Message;
import epf.lang.schema.ollama.Role;
import epf.naming.Naming;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
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
@ServerEndpoint("/messaging/{model}")
@ApplicationScoped
public class Messaging {
	
	/**
	 * 
	 */
	private static final String DEFAULT_PROMPT_TEMPLATE = "%s\n\nAnswer using the following information:\n%s";
	
	private final Map<String, String> promptTemplates = Map.of(
			//"llama3:instruct", "<|start_header_id|>user<|end_header_id|>\n\n%s<|eot_id|><|start_header_id|>assistant<|end_header_id|>\n\n%s<|eot_id|>",
			//"nous-hermes2:10.7b", "<|im_start|>user\n%s<|im_end|>\n<|im_start|>assistant\n%s<|im_end|>\n"
			);
	
	private final List<Session> sessions = new CopyOnWriteArrayList<>();
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = "epf.lang.path")
	String path;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = "epf.lang.model")
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
	
	/**
	 * 
	 */
	@Inject
	Cache cache;
	
	/**
	 * 
	 */
	@Inject
	Persistence persistence;
	
	private InMemoryEmbeddingStore<TextSegment> embeddingStore;
	
	private List<Document> loadDocuments(){
		final TextDocumentParser parser = new TextDocumentParser();
        return FileSystemDocumentLoader.loadDocumentsRecursively(Paths.get(path), parser);
	}
	
	private List<TextSegment> splitDocuments(List<Document> documents){
		final DocumentSplitter splitter = DocumentSplitters.recursive(300, 0);
        return splitter.splitAll(documents);
	}
	
	private List<EmbeddingsResponse> embedSegments(List<TextSegment> segments, Consumer<Float> progress){
		final int size = segments.size();
		final List<EmbeddingsResponse> embeddings = new LinkedList<>();
		final AtomicInteger count = new AtomicInteger();
		segments.forEach(segment -> {
			final EmbeddingsRequest request = new EmbeddingsRequest();
			request.setModel(embeddingModelName);
			request.setPrompt(segment.text());
			final EmbeddingsResponse embedding = ollama.generateEmbeddings(request);
			embeddings.add(embedding);
			progress.accept((float)count.incrementAndGet() * 100 / size);
		});
		return embeddings;
	}
	
	private void storeEmbeddings(final List<EmbeddingsResponse> embeddings, final List<TextSegment> segments) {
        embeddingStore.addAll(embeddings.stream().map(e -> dev.langchain4j.data.embedding.Embedding.from(e.getEmbedding())).collect(Collectors.toList()), segments);
	}
    
    private void send(final Session session, final String promt) {
    	final Message chatMessage = new Message();
    	chatMessage.setContent(promt);
    	chatMessage.setRole(Role.user);
    	final ChatRequest chat = cache.get(session.getId());
    	chat.getMessages().add(chatMessage);
    	final Basic remote = session.getBasicRemote();
    	Ollama.stream(ollama.chat(chat), ChatResponse.class, (response) -> {
    		if(session.isOpen()) {
    			try {
        			remote.sendText(response.getMessage().getContent());
    			}
            	catch(Exception ex) {
            		ex.printStackTrace();
            	}
			}
			System.out.print(response.getMessage().getContent());
			if(response.isDone()) {
            	chat.getMessages().add(response.getMessage());
            	cache.put(session.getId(), chat);
			}
    		return session.isOpen();
    	});
    }
	
	@PostConstruct
	void postConstruct() {
        if(Files.exists(Paths.get(store))) {
			embeddingStore = InMemoryEmbeddingStore.fromFile(store);
		}
		else {
	        embeddingStore = new InMemoryEmbeddingStore<>();
	        executor.submit(() -> {
	        	try {
	        		System.out.println("load documents:");
	                final List<Document> documents = loadDocuments();
	                System.out.println("split documents:");
	                final List<TextSegment> segments = splitDocuments(documents);
	                System.out.println("embed segments:");
	                final List<EmbeddingsResponse> embeddings = embedSegments(segments, (progress) -> {
	                	System.out.println(progress);
	                });
	                System.out.println("store embeddings:");
	                storeEmbeddings(embeddings, segments);
	                System.out.println("serialize to file:");
	                embeddingStore.serializeToFile(store);
	                System.out.println("done.");
	        	}
	        	catch(Exception ex) {
	        		ex.printStackTrace();
	        	}
	        });
		}
	}

    @OnOpen
    public void onOpen(Session session, @PathParam(Naming.Lang.Internal.MODEL) String model) {
        System.out.println("onOpen> " + model);
        final ChatRequest chat = new ChatRequest();
        chat.setModel(model);
        chat.setMessages(new ArrayList<>());
        cache.put(session.getId(), chat);
        sessions.clear();
        sessions.addAll(session.getOpenSessions());
        session.getAsyncRemote().sendText(session.getId());
    }

    @OnClose
    public void onClose(Session session, @PathParam(Naming.Lang.Internal.MODEL) String model) {
        System.out.println("onClose> " + model);
        cache.remove(session.getId());
        sessions.clear();
        sessions.addAll(session.getOpenSessions());
    }

    @OnError
    public void onError(Session session, @PathParam(Naming.Lang.Internal.MODEL) String model, Throwable throwable) {
        System.out.println("onError> " + model + ": " + throwable);
        throwable.printStackTrace();
        cache.remove(session.getId());
        sessions.clear();
        sessions.addAll(session.getOpenSessions());
    }

    @OnMessage
    public void onMessage(final Session session, @PathParam(Naming.Lang.Internal.MODEL) String model, String message) throws Exception {
        System.out.println("onMessage> " + model + ": " + message);
        executor.submit(() -> {
        	try {
                System.out.println("execute query:");
            	final List<TextSegment> segments = persistence.executeQuery(message);
            	System.out.println("inject prompt:");
            	final String prompt = injectPrompt(model, message, segments);
            	System.out.print(prompt);
            	System.out.println("send:");
            	send(session, prompt);
        	}
        	catch(Exception ex) {
        		ex.printStackTrace();
        	}
        });
    }
	
	public String injectPrompt(final String model, final String query, final List<TextSegment> segments) {
    	final StringBuilder contents = new StringBuilder();
    	segments.forEach(segment -> {
    		contents.append(segment.text());
    		contents.append("\n\n");
    	});
    	return String.format(promptTemplates.getOrDefault(model, DEFAULT_PROMPT_TEMPLATE), query, contents.toString());
	}
    
    public void send(final String sid, final String promt) {
    	final Optional<Session> session = sessions.stream().filter(ss -> ss.getId().equals(sid)).findFirst();
    	if(session.isPresent()) {
    		send(session.get(), promt);
    	}
    }
}
