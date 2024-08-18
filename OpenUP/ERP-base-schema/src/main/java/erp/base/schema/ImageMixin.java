package erp.base.schema;

import org.eclipse.microprofile.graphql.Description;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * 
 */
@Entity
@Table(name = "image_mixin")
@Description("Image Mixin")
public class ImageMixin {

	/**
	 * 
	 */
	@Column
	@Description("Image")
	private String image_1920;
	
	/**
	 * 
	 */
	@Column
	@Description("Image 1024")
	private String image_1024;
	
	/**
	 * 
	 */
	@Column
	@Description("Image 512")
	private String image_512;
	
	/**
	 * 
	 */
	@Column
	@Description("Image 256")
	private String image_256;
	
	/**
	 * 
	 */
	@Column
	@Description("Image 128")
	private String image_128;
}
