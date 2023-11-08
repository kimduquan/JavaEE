package epf.workflow.schema.util;

import java.io.Serializable;

/**
 * 
 */
public class Either<L, R> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private L left;
	/**
	 * 
	 */
	private R right;
	
	public L getLeft() {
		return left;
	}
	public void setLeft(final L left) {
		this.left = left;
		this.right = null;
	}
	public R getRight() {
		return right;
	}
	public void setRight(final R right) {
		this.right = right;
		this.left = null;
	}
	
	public boolean isLeft() {
		return left != null;
	}
	public boolean isRight() {
		return right != null;
	}
}
