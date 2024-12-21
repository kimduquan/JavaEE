package erp.base.schema.res.country;

import org.eclipse.microprofile.graphql.Description;
import erp.base.schema.res.Country;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "res_country_state")
@Description("Country state")
public class State {
	
	@Id
	private int id;

	@Transient
	private Integer country_id;

	@ManyToOne(targetEntity = Country.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "country_id", nullable = false)
	@NotNull
	@Description("Country")
	private Country country;
	
	@Column(nullable = false)
	@NotNull
	@Description("State Name")
	private String name;
	
	@Column(nullable = false)
	@NotNull
	@Description("State Code")
	private String code;
}
