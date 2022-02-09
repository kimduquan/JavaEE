package epf.util.concurrent;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author PC
 *
 */
public class Stage<C extends epf.util.AutoCloseable, T> {
	
	/**
	 * @author PC
	 *
	 * @param <S>
	 */
	public class CloseableVar<S extends epf.util.AutoCloseable> implements epf.util.AutoCloseable {
		
		/**
		 * 
		 */
		private transient S var;
		
		/**
		 * @param var
		 * @return
		 */
		public S set(final S var) {
			this.var = var;
			return this.var;
		}
		
		/**
		 * @return
		 */
		public S get() {
			return this.var;
		}

		@Override
		public CompletionStage<Void> close() {
			if(var != null) {
				return var.close();
			}
			return CompletableFuture.completedStage(null);
		}
	}

	/**
	 * 
	 */
	private transient final CloseableVar<C> var;
	
	/**
	 * 
	 */
	private transient CompletionStage<? extends Object> stage;
	
	/**
	 * @param stage
	 */
	private Stage(final CompletionStage<C> stage) {
		var = new CloseableVar<>();
		this.stage = stage.thenApply(c -> var.set(c));
	}
	
	/**
	 * @param <V>
	 * @param function
	 * @return
	 */
	public <U> Stage<C, U> apply(final Function<? super T,? extends U> function) {
		@SuppressWarnings("unchecked")
		final CompletionStage<T> curStage = stage.thenApply(t -> (T)t);
		stage = curStage.thenApply(function);
		@SuppressWarnings("unchecked")
		final Stage<C, U> thisStage = (Stage<C, U>) this;
		return thisStage;
	}
	
	/**
	 * @param consumer
	 * @return
	 */
	public Stage<C, Void> accept(final Consumer<? super T> consumer) {
		@SuppressWarnings("unchecked")
		final CompletionStage<T> curStage = stage.thenApply(t -> (T)t);
		final CompletionStage<Void> newStage = curStage.thenAccept(consumer);
		this.stage = newStage;
		@SuppressWarnings("unchecked")
		final Stage<C, Void> thisStage = (Stage<C, Void>) this;
		return thisStage;
	}
	
	/**
	 * @return
	 */
	public Stage<C, Void> accept() {
		@SuppressWarnings("unchecked")
		final CompletionStage<T> curStage = stage.thenApply(t -> (T)t);
		final CompletionStage<Void> newStage = curStage.thenAccept((e) -> {});
		this.stage = newStage;
		@SuppressWarnings("unchecked")
		final Stage<C, Void> thisStage = (Stage<C, Void>) this;
		return thisStage;
	}
	
	 /**
	 * @param <U>
	 * @param <V>
	 * @param other
	 * @param function
	 * @return
	 */
	public <U,V> Stage<C, V> combine(final CompletionStage<? extends U> other, final BiFunction<? super T,? super U,? extends V> function){
		 @SuppressWarnings("unchecked")
		 final CompletionStage<T> curStage = stage.thenApply(t -> (T)t);
		 final CompletionStage<? extends V> newStage = curStage.thenCombine(other, function);
		 this.stage = newStage;
		 @SuppressWarnings("unchecked")
		 final Stage<C, V> thisStage = (Stage<C, V>) this;
		 return thisStage;
	 }
	
	/**
	 * @param <U>
	 * @param function
	 * @return
	 */
	public <U> Stage<C, U> compose(final Function<? super T, ? extends CompletionStage<U>> function) {
		 @SuppressWarnings("unchecked")
		 final CompletionStage<T> curStage = stage.thenApply(t -> (T)t);
		 final CompletionStage<U> newStage = curStage.thenCompose(function);
		 this.stage = newStage;
		 @SuppressWarnings("unchecked")
		final Stage<C, U> thisStage = (Stage<C, U>) this;
		 return thisStage;
	}
	
	/**
	 * @param function
	 * @return
	 */
	public CompletionStage<T> complete(final BiConsumer<? super T, ? super Throwable> function) {
		@SuppressWarnings("unchecked")
		final CompletionStage<T> curStage = stage.thenApply(t -> (T)t);
		final CompletionStage<T> newStage = curStage.thenCombine(var.close(), (r, v) -> r).whenComplete(function);
		stage = newStage;
		return newStage;
	}
	
	/**
	 * @param function
	 * @return
	 */
	public CompletionStage<T> complete() {
		@SuppressWarnings("unchecked")
		final CompletionStage<T> curStage = stage.thenApply(t -> (T)t);
		final CompletionStage<T> newStage = curStage.thenCombine(var.close(), (r, v) -> r).whenComplete((r, ex) -> {});
		stage = newStage;
		return newStage;
	}
	
	/**
	 * @param <U>
	 * @param function
	 * @return
	 */
	public <U> Stage<C, U> stage(final BiFunction<? super C,? super T,? extends CompletionStage<U>> function) {
		 @SuppressWarnings("unchecked")
		 final CompletionStage<T> curStage = stage.thenApply(t -> (T)t);
		 final CompletionStage<U> newStage = curStage.thenCompose(t -> function.apply(var.get(), t));
		 this.stage = newStage;
		 @SuppressWarnings("unchecked")
		final Stage<C, U> thisStage = (Stage<C, U>) this;
		 return thisStage;
	}
	
	/**
	 * @param <U>
	 * @param stage
	 * @return
	 */
	public static <U extends epf.util.AutoCloseable> Stage<U, U> stage(final CompletionStage<U> stage) {
		return new Stage<>(stage);
	}
}
