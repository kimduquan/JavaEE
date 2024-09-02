package erp.base.schema.res.country;

import org.eclipse.microprofile.graphql.Description;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "res_country_state")
@Description("Country state")
@NodeEntity("Country state")
public class State {
	
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
	@ManyToOne(targetEntity = Country.class)
	@NotNull
	@Description("Country")
	@Property
	@Relationship(type = "COUNTRY")
	private String country_id;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("State Name")
	@Property
	private String name;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("State Code")
	@Property
	private String code;

	public String getCountry_id() {
		return country_id;
	}

	public void setCountry_id(String country_id) {
		this.country_id = country_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
