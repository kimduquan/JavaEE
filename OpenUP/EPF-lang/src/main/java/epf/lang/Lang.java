package epf.lang;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import epf.lang.internal.Chat;
import epf.lang.internal.EPFChatLanguageModel;
import epf.lang.internal.EPFEmbeddingModel;
import epf.lang.internal.EmbeddingRequest;
import epf.lang.internal.Ollama;
import epf.lang.schema.Message;
import epf.lang.schema.Request;
import epf.lang.schema.Role;
import epf.naming.Naming;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

/**
 * 
 */
@ServerEndpoint("/lang/{model}")
@ApplicationScoped
public class Lang {
	
	private static final String DEFAULT_PROMPT_TEMPLATE = "%s\n\nAnswer using the following information:\n%s";

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
	
	@Inject
	@ConfigProperty(name = "quarkus.rest-client.ollama.url")
	String ollamaUrl;
	
	//private Chat chat;
	private EmbeddingStore<TextSegment> embeddingStore;
	private EmbeddingModel embeddingModel;
	
	private List<Document> loadDocuments(){
		final TextDocumentParser parser = new TextDocumentParser();
        return FileSystemDocumentLoader.loadDocumentsRecursively(Paths.get(path), parser);
	}
	
	private List<TextSegment> splitDocuments(List<Document> documents){
		final DocumentSplitter splitter = DocumentSplitters.recursive(300, 0);
        return splitter.splitAll(documents);
	}
	
	private List<Embedding> embedSegments(List<TextSegment> segments){
		return embeddingModel.embedAll(segments).content();
	}
	
	private void storeEmbeddings(final List<Embedding> embeddings, final List<TextSegment> segments) {
        embeddingStore.addAll(embeddings, segments);
	}
	
	private Chat buildService(final String model) {
        final EmbeddingStoreContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .build();
        final ChatMemory chatMemory = MessageWindowChatMemory.builder().maxMessages(10).build();
        final ChatLanguageModel chatLanguageModel = new EPFChatLanguageModel(ollama, model);
        return AiServices.builder(Chat.class)
        .chatLanguageModel(chatLanguageModel)
        .contentRetriever(contentRetriever)
        .chatMemory(chatMemory)
        .build();
	}
	
	private Embedding embedQuery(final String model, final String query) {
		final EmbeddingRequest embeddingRequest = new EmbeddingRequest();
    	embeddingRequest.setModel(model);
    	embeddingRequest.setPrompt(query);
    	final epf.lang.schema.Embedding embeddedQuery = ollama.generateEmbedding(embeddingRequest);
    	return new Embedding(embeddedQuery.getEmbedding());
	}
	
	private List<TextSegment> searchQuery(final Embedding embeddedQuery){
    	final EmbeddingSearchRequest searchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(embeddedQuery)
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
	
	private String injectPrompt(final String query, final List<TextSegment> segments) {
    	final StringBuilder contents = new StringBuilder();
    	segments.forEach(segment -> {
    		contents.append(segment.text());
    	});
    	return String.format(DEFAULT_PROMPT_TEMPLATE, query, contents.toString());
	}
	
	private void chat(final String model, final String prompt, final Consumer<String> consumer) {
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
            		String line;
        		    while ((line = reader.readLine()) != null) {
        		        consumer.accept(line);
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
        embeddingModel = new EPFEmbeddingModel(ollama, embeddingModelName);
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
                final List<Embedding> embeddings = embedSegments(segments);
                System.out.println("store embeddings:");
                storeEmbeddings(embeddings, segments);
                System.out.println("build service:");
                buildService(model);
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
        	final String prompt = injectPrompt(message, segments);
        	System.out.println("chat:");
        	chat(model, prompt, (line) -> {
        		try {
            		session.getBasicRemote().sendText(line);
        		}
            	catch(Exception ex) {
            		ex.printStackTrace();
            	}
        	});
        });
    }
}
