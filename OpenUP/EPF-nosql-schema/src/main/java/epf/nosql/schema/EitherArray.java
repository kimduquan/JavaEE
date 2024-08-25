package epf.nosql.schema;

import java.io.Serializable;
import java.util.List;

import jakarta.json.bind.annotation.JsonbTransient;

/**
 * 
 */
public class EitherArray<L, R> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@JsonbTransient
	private L left;
	/**
	 * 
	 */
	@JsonbTransient
	private List<R> right;
	
	public L getLeft() {
		return left;
	}
	public void setLeft(final L left) {
		this.left = left;
		this.right = null;
	}
	public List<R> getRight() {
		return right;
	}
	public void setRight(final List<R> right) {
		this.right = right;
		this.left = null;
	}
	
	public boolean isLeft() {
		return left != null;
	}
	public boolean isRight() {
		return right != null;
	}
	
	/**
	 * @param from
	 */
	public void copy(final EitherArray<L, R> from) {
		left = from.left;
		right = from.right;
	}
}
