package epf.nosql.schema;

import java.io.Serializable;
import jakarta.json.bind.annotation.JsonbTransient;

public class Either<L, R> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonbTransient
	private L left;
	
	@JsonbTransient
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
	
	public boolean isNull() {
		return left == null && right == null;
	}
	
	public void copy(final Either<L, R> from) {
		left = from.left;
		right = from.right;
	}
}
