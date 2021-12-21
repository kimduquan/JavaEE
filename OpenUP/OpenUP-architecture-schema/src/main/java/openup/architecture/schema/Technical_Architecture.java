package openup.architecture.schema;

import java.util.List;

/**
 * @author PC
 *
 */
public class Technical_Architecture {

	/**
	 * 
	 */
	private List<Architecture_Notebook> fulfillingWorkProducts;

	public List<Architecture_Notebook> getFulfillingWorkProducts() {
		return fulfillingWorkProducts;
	}

	public void setFulfillingWorkProducts(final List<Architecture_Notebook> fulfillingWorkProducts) {
		this.fulfillingWorkProducts = fulfillingWorkProducts;
	}
}
