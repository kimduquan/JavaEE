package erp.schema.res;

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
public class CountryState {

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
}
