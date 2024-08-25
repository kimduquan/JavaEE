package erp.base.schema.res.country;

import org.eclipse.microprofile.graphql.Description;
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
public class State {

	/**
	 * 
	 */
	@Column(nullable = false)
	@ManyToOne(targetEntity = Country.class)
	@NotNull
	@Description("Country")
	private String country_id;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("State Name")
	private String name;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("State Code")
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
}
