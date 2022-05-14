package epf.transaction.api.media;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import epf.transaction.api.Extensible;
import epf.transaction.api.ExternalDocumentation;

/**
 * 
 */
public class Schema extends Extensible {

	/**
	 *
	 */
	private String ref;
	
	/**
	 * 
	 */
	enum SchemaType {
		integer("integer"), 
		number("number"), 
		_boolean("boolean"), 
		string("string"), 
		object("object"), 
		array("array");
		
		private final String value;

       SchemaType(final String value) {
           this.value = value;
       }

       @Override
       public String toString() {
           return value;
       }
	};
	
	/**
	 *
	 */
	private Discriminator discriminator;
	
	/**
	 *
	 */
	private String title;
	
	/**
	 *
	 */
	private Object defaultValue;
	
	/**
	 *
	 */
	private List<Object> enumeration;
	
	/**
	 *
	 */
	private BigDecimal multipleOf;
	
	/**
	 *
	 */
	private BigDecimal maximum;
	
	/**
	 *
	 */
	private Boolean exclusiveMaximum;
	
	/**
	 *
	 */
	private BigDecimal minimum;
	
	/**
	 *
	 */
	private Boolean exclusiveMinimum;
	
	/**
	 *
	 */
	private Integer maxLength;
	
	/**
	 *
	 */
	private Integer minLength;
	
	/**
	 *
	 */
	private String pattern;
	
	/**
	 *
	 */
	private Integer maxItems;
	
	/**
	 *
	 */
	private Integer minItems;
	
	/**
	 *
	 */
	private Boolean uniqueItems;
	
	/**
	 *
	 */
	private Integer maxProperties;
	
	/**
	 *
	 */
	private Integer minProperties;
	
	/**
	 *
	 */
	private List<String> required;
	
	/**
	 *
	 */
	private SchemaType type;
	
	/**
	 *
	 */
	private Schema not;
	
	/**
	 *
	 */
	private Map<String, Schema> properties;
	
	/**
	 *
	 */
	private Schema additionalPropertiesSchema;
	
	/**
	 *
	 */
	private Boolean additionalPropertiesBoolean;
	
	/**
	 *
	 */
	private String description;
	
	/**
	 *
	 */
	private String format;
	
	/**
	 *
	 */
	private Boolean nullable;
	
	/**
	 *
	 */
	private Boolean readOnly;
	
	/**
	 *
	 */
	private Boolean writeOnly;
	
	/**
	 *
	 */
	private Object example;
	
	/**
	 *
	 */
	private ExternalDocumentation externalDocs;
	
	/**
	 *
	 */
	private Boolean deprecated;
	
	/**
	 *
	 */
	private XML xml;
	
	/**
	 *
	 */
	private Schema items;
	
	/**
	 *
	 */
	private List<Schema> allOf;
	
	/**
	 *
	 */
	private List<Schema> anyOf;
	
	/**
	 *
	 */
	private List<Schema> oneOf;

	public String getRef() {
		return ref;
	}

	public void setRef(final String ref) {
		this.ref = ref;
	}

	public Discriminator getDiscriminator() {
		return discriminator;
	}

	public void setDiscriminator(final Discriminator discriminator) {
		this.discriminator = discriminator;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public Object getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(final Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	public List<Object> getEnumeration() {
		return enumeration;
	}

	public void setEnumeration(final List<Object> enumeration) {
		this.enumeration = enumeration;
	}

	public BigDecimal getMultipleOf() {
		return multipleOf;
	}

	public void setMultipleOf(final BigDecimal multipleOf) {
		this.multipleOf = multipleOf;
	}

	public BigDecimal getMaximum() {
		return maximum;
	}

	public void setMaximum(final BigDecimal maximum) {
		this.maximum = maximum;
	}

	public Boolean getExclusiveMaximum() {
		return exclusiveMaximum;
	}

	public void setExclusiveMaximum(final Boolean exclusiveMaximum) {
		this.exclusiveMaximum = exclusiveMaximum;
	}

	public BigDecimal getMinimum() {
		return minimum;
	}

	public void setMinimum(final BigDecimal minimum) {
		this.minimum = minimum;
	}

	public Boolean getExclusiveMinimum() {
		return exclusiveMinimum;
	}

	public void setExclusiveMinimum(final Boolean exclusiveMinimum) {
		this.exclusiveMinimum = exclusiveMinimum;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(final Integer maxLength) {
		this.maxLength = maxLength;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(final Integer minLength) {
		this.minLength = minLength;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(final String pattern) {
		this.pattern = pattern;
	}

	public Integer getMaxItems() {
		return maxItems;
	}

	public void setMaxItems(final Integer maxItems) {
		this.maxItems = maxItems;
	}

	public Integer getMinItems() {
		return minItems;
	}

	public void setMinItems(final Integer minItems) {
		this.minItems = minItems;
	}

	public Boolean getUniqueItems() {
		return uniqueItems;
	}

	public void setUniqueItems(final Boolean uniqueItems) {
		this.uniqueItems = uniqueItems;
	}

	public Integer getMaxProperties() {
		return maxProperties;
	}

	public void setMaxProperties(final Integer maxProperties) {
		this.maxProperties = maxProperties;
	}

	public Integer getMinProperties() {
		return minProperties;
	}

	public void setMinProperties(final Integer minProperties) {
		this.minProperties = minProperties;
	}

	public List<String> getRequired() {
		return required;
	}

	public void setRequired(final List<String> required) {
		this.required = required;
	}

	public SchemaType getType() {
		return type;
	}

	public void setType(final SchemaType type) {
		this.type = type;
	}

	public Schema getNot() {
		return not;
	}

	public void setNot(final Schema not) {
		this.not = not;
	}

	public Map<String, Schema> getProperties() {
		return properties;
	}

	public void setProperties(final Map<String, Schema> properties) {
		this.properties = properties;
	}

	public Schema getAdditionalPropertiesSchema() {
		return additionalPropertiesSchema;
	}

	public void setAdditionalPropertiesSchema(final Schema additionalPropertiesSchema) {
		this.additionalPropertiesSchema = additionalPropertiesSchema;
	}

	public Boolean getAdditionalPropertiesBoolean() {
		return additionalPropertiesBoolean;
	}

	public void setAdditionalPropertiesBoolean(final Boolean additionalPropertiesBoolean) {
		this.additionalPropertiesBoolean = additionalPropertiesBoolean;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(final String format) {
		this.format = format;
	}

	public Boolean getNullable() {
		return nullable;
	}

	public void setNullable(final Boolean nullable) {
		this.nullable = nullable;
	}

	public Boolean getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(final Boolean readOnly) {
		this.readOnly = readOnly;
	}

	public Boolean getWriteOnly() {
		return writeOnly;
	}

	public void setWriteOnly(final Boolean writeOnly) {
		this.writeOnly = writeOnly;
	}

	public Object getExample() {
		return example;
	}

	public void setExample(final Object example) {
		this.example = example;
	}

	public ExternalDocumentation getExternalDocs() {
		return externalDocs;
	}

	public void setExternalDocs(final ExternalDocumentation externalDocs) {
		this.externalDocs = externalDocs;
	}

	public Boolean getDeprecated() {
		return deprecated;
	}

	public void setDeprecated(final Boolean deprecated) {
		this.deprecated = deprecated;
	}

	public XML getXml() {
		return xml;
	}

	public void setXml(final XML xml) {
		this.xml = xml;
	}

	public Schema getItems() {
		return items;
	}

	public void setItems(final Schema items) {
		this.items = items;
	}

	public List<Schema> getAllOf() {
		return allOf;
	}

	public void setAllOf(final List<Schema> allOf) {
		this.allOf = allOf;
	}

	public List<Schema> getAnyOf() {
		return anyOf;
	}

	public void setAnyOf(final List<Schema> anyOf) {
		this.anyOf = anyOf;
	}

	public List<Schema> getOneOf() {
		return oneOf;
	}

	public void setOneOf(final List<Schema> oneOf) {
		this.oneOf = oneOf;
	}
}
