package epf.lang;

import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.neo4j.Neo4jEmbeddingStore;
import epf.lang.ollama.Ollama;
import epf.lang.schema.ollama.EmbeddingsRequest;
import epf.lang.schema.ollama.EmbeddingsResponse;
import epf.naming.Naming;
import epf.util.logging.LogManager;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EntityType;
import jakarta.transaction.Transactional;
import static dev.langchain4j.internal.Utils.isNullOrBlank;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import dev.langchain4j.data.document.BlankDocumentException;
import dev.langchain4j.data.document.Document;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

/**
 * 
 */
@ApplicationScoped
@Readiness
public class Persistence implements HealthCheck {
	
	/**
	 * 
	 */
	private static final transient Logger LOGGER = LogManager.getLogger(Persistence.class.getName());

	/**
	 * 
	 */
	private final DocumentSplitter splitter = DocumentSplitters.recursive(300, 0);
	
	/**
	 * 
	 */
	private Neo4jEmbeddingStore embeddingStore;
	
	/**
	 * 
	 */
	private SessionFactory factory;
	
	/**
	 * 
	 */
	private Session session;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Lang.Internal.EMBED_LANGUAGE_MODEL)
	String embedModel;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Lang.Internal.CACHE_PATH)
	String cachePath;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Lang.Internal.PERSISTENCE_PATH)
	String persistencePath;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Lang.Internal.Graph.GRAPH_URL)
	String graphUrl;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Lang.Internal.Graph.GRAPH_USERNAME)
	String graphUsername;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Lang.Internal.Graph.GRAPH_PASSWORD)
	String graphPassword;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Lang.Internal.Graph.GRAPH_DATABASE)
	String graphDatabase;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Lang.Internal.Graph.Property.TEXT)
	String textProperty;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = Naming.Lang.Internal.Graph.Property.ID)
	String idProperty;
	
	/**
	 * 
	 */
	@Inject
	@ConfigProperty(name = "quarkus.hibernate-orm.packages")
	String packages;
	
	/**
     * 
     */
    @Inject
    ManagedExecutor executor;
    
    /**
     * 
     */
    @Inject
    EntityManager manager;
	
	/**
	 * 
	 */
	@RestClient
	Ollama ollama;
	
	@PostConstruct
	void postConstruct() {
		final Configuration configuration = new Configuration.Builder()
			    .uri(graphUrl)
			    .credentials(graphUsername, graphPassword)
			    .build();
		factory = new SessionFactory(configuration, "erp.base.schema");
		session = factory.openSession();
		refreshSchema();
		embeddingStore = Neo4jEmbeddingStore.builder().withBasicAuth(graphUrl, graphUsername, graphPassword).databaseName(graphDatabase).textProperty(textProperty).dimension(4096).build();
		final Set<EntityType<?>> entities = manager.getMetamodel().getEntities();
		saveEntities(entities);
		//executor.submit(() -> generateEntityEmbeddings(entities));
		generateEntityEmbeddings(entities);
	}
	
	@PreDestroy
	void preDestroy() {
		embeddingStore.getDriver().close();
		factory.close();
	}
	
	private void refreshSchema() {
		final StringBuilder schema = new StringBuilder();
		schema.append("Node properties are the following:\n");
		final List<String> nodeProperties = new ArrayList<>();
		factory.metaData().persistentEntities().forEach(classInfo -> {
			final StringBuilder nodeProperty = new StringBuilder();
			nodeProperty.append(classInfo.neo4jName());
			nodeProperty.append(" ");
			final List<String> properties = new ArrayList<>();
			classInfo.propertyFields().forEach(fieldInfo -> {
				final Class<?> convertedType = fieldInfo.convertedType();
				if(convertedType != null) {
					final String property = String.format("%s:%s", fieldInfo.propertyName(), convertedType.getSimpleName());
					properties.add(property);
				}
			});
			nodeProperty.append('{');
			nodeProperty.append(String.join(", ", properties));
			nodeProperty.append('}');
			nodeProperties.add(nodeProperty.toString());
		});
		schema.append(String.join("\n", nodeProperties));
		schema.append("\n\n");
		schema.append("The relationships are the following:\n");
		final List<String> relationships = new ArrayList<>();
		factory.metaData().persistentEntities().forEach(classInfo -> {
			classInfo.relationshipFields().forEach(fieldInfo -> {
				final String relationship = String.format("(:%s)-[:%s]->(:%s)", classInfo.neo4jName(), fieldInfo.relationshipType(), "");
				relationships.add(relationship);
			});
		});
		schema.append(String.join("\n", relationships));
		LOGGER.info(schema.toString());
	}
	
	@SuppressWarnings("unchecked")
	private void saveEntities(final Set<EntityType<?>> entities) {
		final CriteriaBuilder builder = manager.getCriteriaBuilder();
		for(EntityType<?> entityType : entities) {
			final Class<?> type = entityType.getJavaType();
			LOGGER.info("entity type:" + type.getName());
			try {
				final CriteriaQuery<?> query = builder.createQuery(type);
				@SuppressWarnings("rawtypes")
				final Root root = query.from(entityType);
				query.select(root);
				manager.createQuery(query).getResultStream().forEach(entity -> {
					session.save(entity);
					LOGGER.info("save:" + entity);
				});
			}
			catch(Exception ex) {
				LOGGER.log(Level.SEVERE, type.getName(), ex);
			}
		}
	}
	
	private void generateEntityEmbeddings(final Set<EntityType<?>> entities) {
		for(EntityType<?> entityType : entities) {
			final Class<?> type = entityType.getJavaType();
			LOGGER.info("entity type:" + type.getName());
			try {
				final Attribute<?, ?> textAttribute = entityType.getAttribute(textProperty);
				final Attribute<?, ?> idAttribute = entityType.getAttribute(idProperty);
				final Field textField = (Field)textAttribute.getJavaMember();
				final Field idField = (Field)idAttribute.getJavaMember();
				for(Object entity : session.loadAll(type)) {
					final String text = textField.get(entity).toString();
					final EmbeddingsRequest request = new EmbeddingsRequest();
					request.setModel(embedModel);
					request.setPrompt(text);
					final EmbeddingsResponse response = ollama.generateEmbeddings(request);
					final String id = idField.get(entity).toString();
					embeddingStore.add(id, Embedding.from(response.getEmbedding()));
					LOGGER.info("add:[" + id + "]" + text);
				}
				LOGGER.info("loadAll:" + entityType.getName());
			}
			catch(Exception ex) {
				LOGGER.log(Level.SEVERE, type.getName(), ex);
			}
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
        embeddingStore.addAll(embeddings.stream().map(e -> Embedding.from(e.getEmbedding())).collect(Collectors.toList()), segments);
	}
	
	private EmbeddingsResponse embedQuery(final String model, final String query) {
		final EmbeddingsRequest embeddingRequest = new EmbeddingsRequest();
    	embeddingRequest.setModel(model);
    	embeddingRequest.setPrompt(query);
    	return ollama.generateEmbeddings(embeddingRequest);
	}
	
	private List<TextSegment> searchQuery(final EmbeddingsResponse embeddedQuery){
    	final EmbeddingSearchRequest searchRequest = EmbeddingSearchRequest.builder()
                .queryEmbedding(Embedding.from(embeddedQuery.getEmbedding()))
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
		final List<EmbeddingsResponse> embeddings = embedSegments(embedModel, segments);
		storeEmbeddings(embeddings, segments);
		//embeddingStore.serializeToFile(persistencePath);
	}
	
	public List<TextSegment> executeQuery(final String query) {
		final EmbeddingsResponse embeddedQuery = embedQuery(embedModel, query);
    	return searchQuery(embeddedQuery);
	}

	@Transactional
	@Override
	public HealthCheckResponse call() {
		if(embeddingStore != null) {
			return HealthCheckResponse.up("EPF-lang-persistence");
		}
		return HealthCheckResponse.down("EPF-lang-persistence");
	}
}
