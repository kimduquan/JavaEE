package epf.lang;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;
import org.eclipse.rdf4j.model.BNode;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Triple;
import org.eclipse.rdf4j.query.Binding;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.BooleanQuery;
import org.eclipse.rdf4j.query.GraphQuery;
import org.eclipse.rdf4j.query.GraphQueryResult;
import org.eclipse.rdf4j.query.Query;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.repository.sail.SailRepositoryConnection;
import org.eclipse.rdf4j.sail.Sail;
import org.eclipse.rdf4j.sail.solr.SolrIndex;
import org.eclipse.rdf4j.sail.solr.config.SolrSailConfig;
import org.eclipse.rdf4j.sail.solr.config.SolrSailFactory;
import epf.naming.Naming;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonValue;

/**
 * 
 */
@ApplicationScoped
@Readiness
public class RDF implements HealthCheck {
	
	private SailRepository repository;
	
	@Inject
	@ConfigProperty(name = Naming.Lang.Internal.RDF.SERVER)
	String server;
	
	@PostConstruct
	protected void postConstruct() {
    	final SolrSailConfig config = new SolrSailConfig();
    	config.setParameter(SolrIndex.SERVER_KEY, server);
    	final SolrSailFactory factory = new SolrSailFactory();
    	final Sail sail = factory.getSail(config);
    	repository = new SailRepository(sail);
	}
	
	@PreDestroy
	protected void preDestroy() {
		repository.shutDown();
	}

	@Override
	public HealthCheckResponse call() {
		if(repository != null) {
	    	repository.init();
	    	if(repository.isInitialized()) {
				return HealthCheckResponse.up("EPF-lang-rdf");
	    	}
		}
		return HealthCheckResponse.down("EPF-lang-rdf");
	}
	
	private void bind(final BindingSet bindingSet, final JsonArrayBuilder parent) {
		final JsonObjectBuilder builder = Json.createObjectBuilder();
		bindingSet.forEach(binding -> bind(binding, builder));
		parent.add(builder);
	}
	
	private void bindLiteral(final Binding binding, final JsonObjectBuilder parent) {
		final Literal literal = (Literal) binding.getValue();
		if(literal.getCoreDatatype().isRDFDatatype()) {
			switch(literal.getCoreDatatype().asRDFDatatypeOrNull()) {
				case HTML:
					break;
				case LANGSTRING:
					parent.add(binding.getName(), literal.toString());
					break;
				case XMLLITERAL:
					final IRI dataType = literal.getDatatype();
					switch(dataType.getLocalName()) {
						case "boolean":
							parent.add(binding.getName(), literal.booleanValue());
							break;
						case "decimal":
							parent.add(binding.getName(), literal.decimalValue());
							break;
						case "double":
							parent.add(binding.getName(), literal.doubleValue());
							break;
						case "integer":
							parent.add(binding.getName(), literal.integerValue());
							break;
						case "int":
							parent.add(binding.getName(), literal.intValue());
							break;
						case "long":
							parent.add(binding.getName(), literal.longValue());
							break;
						case "string":
							parent.add(binding.getName(), literal.stringValue());
							break;
						case "byte":
							parent.add(binding.getName(), literal.byteValue());
							break;
						case "float":
							parent.add(binding.getName(), literal.floatValue());
							break;
						case "short":
							parent.add(binding.getName(), literal.shortValue());
							break;
					}
					break;
				default:
					break;
			}
		}
	}
	
	private void bind(final Binding binding, final JsonObjectBuilder parent) {
		if(binding.getValue().isTriple()) {
			final Triple triple = (Triple) binding.getValue();
			
		}
		else if(binding.getValue().isBNode()) {
			final BNode node = (BNode) binding.getValue();
			parent.add(binding.getName(), node.getID());
		}
		else if(binding.getValue().isIRI()) {
			final IRI iri = (IRI) binding.getValue();
			final JsonObjectBuilder iriObject = Json.createObjectBuilder().add("namespace", iri.getNamespace()).add("localName", iri.getLocalName());
			parent.add(binding.getName(), iriObject.build());
		}
		else if(binding.getValue().isLiteral()) {
			bindLiteral(binding, parent);
		}
	}
	
	private JsonValue transform(final Statement statement) {
		final JsonObjectBuilder subject = Json.createObjectBuilder();
	}
	
	private JsonValue transform(final TupleQuery query) {
		try(TupleQueryResult result = query.evaluate()){
			final JsonArrayBuilder builder = Json.createArrayBuilder();
			result.forEach(data -> bind(data, builder));
			return builder.build();
		}
	}
	
	private JsonValue transform(final BooleanQuery query) {
		return query.evaluate() ? JsonValue.TRUE : JsonValue.FALSE;
	}
	
	private JsonValue transform(final GraphQuery query) {
		try(GraphQueryResult result = query.evaluate()){
			final JsonArrayBuilder builder = Json.createArrayBuilder();
			result.forEach(statement -> builder.add(transform(statement)));
			return builder.build();
		}
	}
	
	public JsonValue executeQuery(final String query) {
		try(SailRepositoryConnection connection = repository.getConnection()){
			final Query rdfQuery = connection.prepareQuery(QueryLanguage.SPARQL, query);
			if(rdfQuery instanceof TupleQuery) {
				final TupleQuery tupleQuery = (TupleQuery)rdfQuery;
				return transform(tupleQuery);
			}
			else if(rdfQuery instanceof BooleanQuery) {
				final BooleanQuery booleanQuery = (BooleanQuery)rdfQuery;
				return transform(booleanQuery);
			}
			if(rdfQuery instanceof GraphQuery) {
				final GraphQuery graphQuery = (GraphQuery)rdfQuery;
				return transform(graphQuery);
			}
		}
		return null;
	}
}
