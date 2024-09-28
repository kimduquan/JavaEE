package epf.lang;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import epf.util.logging.LogManager;
import erp.base.schema.ir.model.Model;
import graphql.GraphQL;
import graphql.language.Description;
import graphql.language.EnumTypeDefinition;
import graphql.language.EnumValueDefinition;
import graphql.language.FieldDefinition;
import graphql.language.ListType;
import graphql.language.ObjectTypeDefinition;
import graphql.language.ScalarTypeDefinition;
import graphql.language.TypeName;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLSchema;
import graphql.schema.StaticDataFetcher;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaPrinter;
import graphql.schema.idl.SchemaPrinter.Options;
import graphql.schema.idl.TypeDefinitionRegistry;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.PluralAttribute;

/**
 * 
 */
@ApplicationScoped
@Readiness
public class Graph implements HealthCheck {
	
	/**
	 * 
	 */
	private static final transient Logger LOGGER = LogManager.getLogger(Graph.class.getName());
	
	/**
	 * 
	 */
	private GraphQL graph;
	
	/**
     * 
     */
    @Inject
    EntityManager manager;
    
    @PostConstruct
	void postConstruct() {
    	final Set<EntityType<?>> entities = manager.getMetamodel().getEntities();
    	graph = generateGraph(entities);
    	LOGGER.info(printSchema());
    }
    
    private Description getDescription(final EntityType<?> entityType) {
    	final org.eclipse.microprofile.graphql.Description description = entityType.getJavaType().getAnnotation(org.eclipse.microprofile.graphql.Description.class);
    	if(description != null) {
    		return new Description(description.value(), null, false);
    	}
    	return null;
    }
    
    private Description getDescription(final Attribute<?, ?> attribute) {
    	final Field field = (Field) attribute.getJavaMember();
    	final org.eclipse.microprofile.graphql.Description description = field.getAnnotation(org.eclipse.microprofile.graphql.Description.class);
    	if(description != null) {
    		return new Description(description.value(), null, false);
    	}
    	return null;
    }
    
    private boolean isTransient(final Attribute<?, ?> attribute) {
    	final Field field = (Field) attribute.getJavaMember();
    	final org.neo4j.ogm.annotation.Transient _transient = field.getAnnotation(org.neo4j.ogm.annotation.Transient.class);
    	return _transient != null;
    }
    
    private FieldDefinition buildFieldDefinition(final TypeDefinitionRegistry registry, final Map<String, EntityType<?>> entityTypes, final Attribute<?, ?> attribute) {
		final FieldDefinition.Builder newField = FieldDefinition.newFieldDefinition();
		newField.name(attribute.getName());
		newField.description(getDescription(attribute));
		final Class<?> attributeType = attribute.getJavaType();
		switch(attribute.getPersistentAttributeType()) {
			case BASIC:
				if(attributeType.equals(Integer.class) || attributeType.equals(int.class)) {
					newField.type(TypeName.newTypeName("Int").build());
				}
				else if(attributeType.equals(Float.class) || attributeType.equals(float.class)) {
					newField.type(TypeName.newTypeName("Float").build());
				}
				else if(attributeType.equals(String.class) || attributeType.equals(Date.class)) {
					newField.type(TypeName.newTypeName("String").build());
				}
				else if(attributeType.equals(Boolean.class) || attributeType.equals(boolean.class)) {
					newField.type(TypeName.newTypeName("Boolean").build());
				}
				else if(attributeType.equals(Long.class) || attributeType.equals(long.class) || attributeType.equals(Timestamp.class)){
					newField.type(TypeName.newTypeName("Long").build());
				}
				else if(attributeType.isEnum()) {
					final EnumTypeDefinition.Builder newEnum = EnumTypeDefinition.newEnumTypeDefinition();
					newEnum.name(attributeType.getSimpleName());
					for(Object enumConstant : attributeType.getEnumConstants()) {
						newEnum.enumValueDefinition(EnumValueDefinition.newEnumValueDefinition().name(enumConstant.toString()).build());
					}
					registry.add(newEnum.build());
					newField.type(TypeName.newTypeName(attributeType.getSimpleName()).build());
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
							final EnumTypeDefinition.Builder newEnum = EnumTypeDefinition.newEnumTypeDefinition();
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
							final EnumTypeDefinition.Builder newEnum = EnumTypeDefinition.newEnumTypeDefinition();
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
				newField.type(TypeName.newTypeName(entityTypes.get(attributeType.getName()).getName()).build());
				break;
			case ONE_TO_MANY:
				final PluralAttribute<?, ?, ?> oneToManyAttribute = (PluralAttribute<?, ?, ?>) attribute;
				newField.type(ListType.newListType(TypeName.newTypeName(entityTypes.get(oneToManyAttribute.getElementType().getJavaType().getName()).getName()).build()).build());
				break;
			case ONE_TO_ONE:
				newField.type(TypeName.newTypeName(entityTypes.get(attributeType.getName()).getName()).build());
				break;
			default:
				break;
		}
		return newField.build();
    }
    
    private ObjectTypeDefinition buildTypeDefinition(final TypeDefinitionRegistry registry, final Map<String, EntityType<?>> entityTypes, final EntityType<?> entityType) {
    	final ObjectTypeDefinition.Builder newType = ObjectTypeDefinition.newObjectTypeDefinition();
		newType.name(entityType.getName());
		newType.description(getDescription(entityType));
		for(Attribute<?, ?> attribute : entityType.getDeclaredAttributes()) {
			if(isTransient(attribute)) {
				continue;
			}
			final FieldDefinition field = buildFieldDefinition(registry, entityTypes, attribute);
			if(field.getType() != null) {
				newType.fieldDefinition(field);
			}
			else {
				LOGGER.warning("FieldDefinition.type is null");
			}
		}
		return newType.build();
    }
    
    private TypeDefinitionRegistry buildRegistry(final Set<EntityType<?>> entities) {
		final Map<String, EntityType<?>> entityTypes = new HashMap<>();
		for(EntityType<?> entityType : entities) {
			entityTypes.put(entityType.getJavaType().getName(), entityType);
		}
		final TypeDefinitionRegistry registry = new TypeDefinitionRegistry();
		for(EntityType<?> entityType : entities) {
			final ObjectTypeDefinition newType = buildTypeDefinition(registry, entityTypes, entityType);
			registry.add(newType);
		}
		registry.add(ScalarTypeDefinition.newScalarTypeDefinition().name("Long").build());
		return registry;
    }
    
    private RuntimeWiring buildRuntime(final TypeDefinitionRegistry registry) {
		registry.add(ObjectTypeDefinition.newObjectTypeDefinition().name("Query").fieldDefinition(FieldDefinition.newFieldDefinition().name("models").type(ListType.newListType(TypeName.newTypeName("Model").build()).build()).build()).build());
    	final CriteriaBuilder queryBuilder = manager.getCriteriaBuilder();
		final CriteriaQuery<Model> query = queryBuilder.createQuery(Model.class);
		final Root<Model> root = query.from(Model.class);
		query.select(root);
		final List<Model> models = manager.createQuery(query).getResultList();
		final RuntimeWiring.Builder newRuntime = RuntimeWiring.newRuntimeWiring();
		newRuntime.scalar(ExtendedScalars.GraphQLLong);
		newRuntime.type("Query", builder -> builder.dataFetcher("models", new StaticDataFetcher(models)));
		return newRuntime.build();
    }
    
	private GraphQL generateGraph(final Set<EntityType<?>> entities) {
		final TypeDefinitionRegistry registry = buildRegistry(entities);
		final RuntimeWiring runtime = buildRuntime(registry);
		final SchemaGenerator generator = new SchemaGenerator();
		final GraphQLSchema schema = generator.makeExecutableSchema(registry, runtime);
		final GraphQL graph = GraphQL.newGraphQL(schema).build();
		return graph;
	}
	
	public String printSchema() {
		final Options options = Options.defaultOptions().includeDirectiveDefinitions(false);
		final SchemaPrinter printer = new SchemaPrinter(options);
		return printer.print(graph.getGraphQLSchema());
	}
	
	@Override
	public HealthCheckResponse call() {
		if(graph != null) {
			return HealthCheckResponse.up("EPF-lang-graph");
		}
		return HealthCheckResponse.down("EPF-lang-graph");
	}
}
