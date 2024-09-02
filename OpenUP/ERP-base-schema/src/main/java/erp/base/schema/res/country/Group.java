package erp.base.schema.res.country;

import java.util.List;
import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import erp.schema.util.NameAttributeConverter;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "res_country_group")
@Description("Country Group")
@NodeEntity("Country Group")
public class Group {
	
	/**
	 * 
	 */
	@jakarta.persistence.Id
	@Id
	private int id;

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Property
	@Convert(NameAttributeConverter.class)
	private String name;
	
	/**
	 * 
	 */
	@Column
	@ManyToMany(targetEntity = Country.class)
	@ElementCollection(targetClass = Country.class)
	@CollectionTable(name = "res_country")
	@Description("Countries")
	@Property
	@Relationship(type = "COUNTRIES")
	private List<String> country_ids;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getCountry_ids() {
		return country_ids;
	}

	public void setCountry_ids(List<String> country_ids) {
		this.country_ids = country_ids;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
