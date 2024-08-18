package erp.base.schema;

import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "avatar_mixin")
@Description("Avatar Mixin")
public class AvatarMixin extends ImageMixin {

	/**
	 * 
	 */
	@Column
	@Description("Avatar")
	private String avatar_1920;
	
	/**
	 * 
	 */
	@Column
	@Description("Avatar 1024")
	private String avatar_1024;
	
	/**
	 * 
	 */
	@Column
	@Description("Avatar 512")
	private String avatar_512;
	
	/**
	 * 
	 */
	@Column
	@Description("Avatar 256")
	private String avatar_256;
	
	/**
	 * 
	 */
	@Column
	@Description("Avatar 128")
	private String avatar_128;
}
