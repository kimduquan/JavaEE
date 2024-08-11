package erp.schema;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

/**
 * 
 */
@Entity
@Table(name = "ir_asset")
@Description("Asset")
public class IrAsset {

	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Name")
	private String name;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Bundle name")
	private String bundle;
	
	/**
	 * 
	 */
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("append")
	private String directive = "append";
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@Description("Path (or glob pattern)")
	private String path;
	
	/**
	 * 
	 */
	@Column
	@Description("Target")
	private String target;
	
	/**
	 * 
	 */
	@Column
	@Description("active")
	@DefaultValue("true")
	private Boolean active = true;
	
	/**
	 * 
	 */
	@Column(nullable = false)
	@NotNull
	@DefaultValue("16")
	@Description("Sequence")
	private Integer sequence = 16;
}
