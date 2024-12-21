package erp.base.schema.ir;

import org.eclipse.microprofile.graphql.DefaultValue;
import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "ir_asset")
@Description("Asset")
public class Asset {
	
	public enum Directive {
		@Description("Append")
		append,
		@Description("Prepend")
		prepend,
		@Description("After")
		after,
		@Description("Before")
		before,
		@Description("Remove")
		remove,
		@Description("Replace")
		replace,
		@Description("Include")
		include
	}
	
	@Id
	private int id;

	@Column(nullable = false)
	@NotNull
	@Description("Name")
	private String name;
	
	@Column(nullable = false)
	@NotNull
	@Description("Bundle name")
	private String bundle;
	
	@Column
	@Enumerated(EnumType.STRING)
	@DefaultValue("append")
	private Directive directive = Directive.append;
	
	@Column(nullable = false)
	@NotNull
	@Description("Path (or glob pattern)")
	private String path;
	
	@Column
	@Description("Target")
	private String target;
	
	@Column
	@Description("active")
	@DefaultValue("true")
	private Boolean active = true;
	
	@Column(nullable = false)
	@NotNull
	@DefaultValue("16")
	@Description("Sequence")
	private Integer sequence = 16;
}
