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
import erp.base.schema.ir.model.Model;
import graphql.GraphQL;
import graphql.language.EnumTypeDefinition;
import graphql.language.EnumValueDefinition;
import graphql.language.FieldDefinition;
import graphql.language.ListType;
import graphql.language.ObjectTypeDefinition;
import graphql.language.ScalarTypeDefinition;
import graphql.language.TypeName;
import graphql.scalars.ExtendedScalars;
import graphql.schema.StaticDataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.TypeDefinitionRegistry;
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
import jakarta.persistence.metamodel.PluralAttribute;
import jakarta.transaction.Transactional;
import static dev.langchain4j.internal.Utils.isNullOrBlank;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
		refreshSchema();
		embeddingStore = Neo4jEmbeddingStore.builder().withBasicAuth(graphUrl, graphUsername, graphPassword).databaseName(graphDatabase).textProperty(textProperty).dimension(4096).build();
		final Set<EntityType<?>> entities = manager.getMetamodel().getEntities();
		generateGraph(entities);
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
	
	private GraphQL generateGraph(final Set<EntityType<?>> entities) {
		final Map<String, EntityType<?>> entityTypes = new HashMap<>();
		for(EntityType<?> entityType : entities) {
			entityTypes.put(entityType.getJavaType().getName(), entityType);
		}
		final TypeDefinitionRegistry registry = new TypeDefinitionRegistry();
		for(EntityType<?> entityType : entities) {
			final ObjectTypeDefinition.Builder newType = ObjectTypeDefinition.newObjectTypeDefinition();
			newType.name(entityType.getName());
			for(Attribute<?, ?> attribute : entityType.getDeclaredAttributes()) {
				final FieldDefinition.Builder newField = FieldDefinition.newFieldDefinition();
				newField.name(attribute.getName());
				final Class<?> fieldType = attribute.getJavaType();
				switch(attribute.getPersistentAttributeType()) {
					case BASIC:
						if(fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
							newField.type(TypeName.newTypeName("Int").build());
						}
						else if(fieldType.equals(Float.class) || fieldType.equals(float.class)) {
							newField.type(TypeName.newTypeName("Float").build());
						}
						else if(fieldType.equals(String.class) || fieldType.equals(Date.class)) {
							newField.type(TypeName.newTypeName("String").build());
						}
						else if(fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
							newField.type(TypeName.newTypeName("Boolean").build());
						}
						else if(fieldType.equals(Long.class) || fieldType.equals(long.class) || fieldType.equals(Timestamp.class)){
							newField.type(TypeName.newTypeName("Long").build());
						}
						else if(fieldType.isEnum()) {
							EnumTypeDefinition.Builder newEnum = EnumTypeDefinition.newEnumTypeDefinition();
							newEnum.name(fieldType.getSimpleName());
							for(Object enumConstant : fieldType.getEnumConstants()) {
								newEnum.enumValueDefinition(EnumValueDefinition.newEnumValueDefinition().name(enumConstant.toString()).build());
							}
							registry.add(newEnum.build());
							newField.type(TypeName.newTypeName(fieldType.getSimpleName()).build());
						}
						break;
					case ELEMENT_COLLECTION:
						final PluralAttribute<?, ?, ?> pluralAttribute = (PluralAttribute<?, ?, ?>) attribute;
						final Class<?> fieldClass = pluralAttribute.getBindableJavaType();
						switch(pluralAttribute.getBindableType()) {
							case ENTITY_TYPE:
								final String entityName = entityTypes.get(fieldClass.getName()).getName();
								newField.type(ListType.newListType(TypeName.newTypeName(entityName).build()).build());
								break;
							case PLURAL_ATTRIBUTE:
								if(fieldClass.equals(Integer.class) || fieldClass.equals(int.class)) {
									newField.type(ListType.newListType(TypeName.newTypeName("Int").build()).build());
								}
								else if(fieldClass.equals(Float.class) || fieldClass.equals(float.class)) {
									newField.type(ListType.newListType(TypeName.newTypeName("Float").build()).build());
								}
								else if(fieldClass.equals(String.class) || fieldClass.equals(Date.class)) {
									newField.type(ListType.newListType(TypeName.newTypeName("String").build()).build());
								}
								else if(fieldClass.equals(Boolean.class) || fieldClass.equals(boolean.class)) {
									newField.type(ListType.newListType(TypeName.newTypeName("Boolean").build()).build());
								}
								else if(fieldClass.equals(Long.class) || fieldClass.equals(long.class) || fieldClass.equals(Timestamp.class)) {
									newField.type(ListType.newListType(TypeName.newTypeName("Long").build()).build());
								}
								else if(fieldClass.isEnum()) {
									EnumTypeDefinition.Builder newEnum = EnumTypeDefinition.newEnumTypeDefinition();
									newEnum.name(fieldClass.getSimpleName());
									for(Object enumConstant : fieldClass.getEnumConstants()) {
										newEnum.enumValueDefinition(EnumValueDefinition.newEnumValueDefinition().name(enumConstant.toString()).build());
									}
									registry.add(newEnum.build());
									newField.type(ListType.newListType(TypeName.newTypeName(fieldClass.getSimpleName()).build()).build());
								}
								break;
							case SINGULAR_ATTRIBUTE:
								if(fieldClass.equals(Integer.class) || fieldClass.equals(int.class)) {
									newField.type(TypeName.newTypeName("Int").build());
								}
								else if(fieldClass.equals(Float.class) || fieldClass.equals(float.class)) {
									newField.type(TypeName.newTypeName("Float").build());
								}
								else if(fieldClass.equals(String.class) || fieldClass.equals(Date.class)) {
									newField.type(TypeName.newTypeName("String").build());
								}
								else if(fieldClass.equals(Boolean.class) || fieldClass.equals(boolean.class)) {
									newField.type(TypeName.newTypeName("Boolean").build());
								}
								else if(fieldClass.equals(Long.class) || fieldClass.equals(long.class) || fieldClass.equals(Timestamp.class)) {
									newField.type(TypeName.newTypeName("Long").build());
								}
								else if(fieldClass.isEnum()) {
									EnumTypeDefinition.Builder newEnum = EnumTypeDefinition.newEnumTypeDefinition();
									newEnum.name(fieldClass.getSimpleName());
									for(Object enumConstant : fieldClass.getEnumConstants()) {
										newEnum.enumValueDefinition(EnumValueDefinition.newEnumValueDefinition().name(enumConstant.toString()).build());
									}
									registry.add(newEnum.build());
									newField.type(TypeName.newTypeName(fieldClass.getSimpleName()).build());
								}
								break;
							default:
								break;
						}
						break;
					case EMBEDDED:
						break;
					case MANY_TO_MANY:
						final PluralAttribute<?, ?, ?> manyToManyAttribute = (PluralAttribute<?, ?, ?>) attribute;
						final String entityName = entityTypes.get(manyToManyAttribute.getElementType().getJavaType().getName()).getName();
						newField.type(ListType.newListType(TypeName.newTypeName(entityName).build()).build());
						break;
					case MANY_TO_ONE:
						newField.type(TypeName.newTypeName(entityTypes.get(attribute.getJavaType().getName()).getName()).build());
						break;
					case ONE_TO_MANY:
						final PluralAttribute<?, ?, ?> oneToManyAttribute = (PluralAttribute<?, ?, ?>) attribute;
						newField.type(ListType.newListType(TypeName.newTypeName(entityTypes.get(oneToManyAttribute.getElementType().getJavaType().getName()).getName()).build()).build());
						break;
					case ONE_TO_ONE:
						newField.type(TypeName.newTypeName(entityTypes.get(attribute.getJavaType().getName()).getName()).build());
						break;
					default:
						break;
				}
				final FieldDefinition field = newField.build();
				if(field.getType() != null) {
					newType.fieldDefinition(field);
				}
				else {
					LOGGER.warning("FieldDefinition.type is null");
				}
			}
			registry.add(newType.build());
		}
		registry.add(ScalarTypeDefinition.newScalarTypeDefinition().name("Long").build());
		registry.add(ObjectTypeDefinition.newObjectTypeDefinition().name("Query").fieldDefinition(FieldDefinition.newFieldDefinition().name("models").type(ListType.newListType(TypeName.newTypeName("Model").build()).build()).build()).build());
		final CriteriaBuilder queryBuilder = manager.getCriteriaBuilder();
		final CriteriaQuery<Model> query = queryBuilder.createQuery(Model.class);
		final Root<Model> root = query.from(Model.class);
		query.select(root);
		final List<Model> models = manager.createQuery(query).getResultList();
		final RuntimeWiring.Builder newRuntime = RuntimeWiring.newRuntimeWiring();
		newRuntime.scalar(ExtendedScalars.GraphQLLong);
		newRuntime.type("Query", builder -> builder.dataFetcher("models", new StaticDataFetcher(models)));
		final SchemaGenerator generator = new SchemaGenerator();
		final GraphQLSchema schema = generator.makeExecutableSchema(registry, newRuntime.build());
		final GraphQL graph = GraphQL.newGraphQL(schema).build();
		return graph;
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
