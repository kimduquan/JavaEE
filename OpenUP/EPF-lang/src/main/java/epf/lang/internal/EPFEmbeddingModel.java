package epf.lang.internal;

import java.util.LinkedList;
import java.util.List;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;

/**
 * 
 */
public class EPFEmbeddingModel implements EmbeddingModel {
	
	/**
	 * 
	 */
	private final Ollama ollama;
	
	/**
	 * 
	 */
	private final String model;
	
	/**
	 * @param ollama
	 * @param model
	 */
	public EPFEmbeddingModel(final Ollama ollama, final String model) {
		this.ollama = ollama;
		this.model = model;
	}

	@Override
	public Response<List<Embedding>> embedAll(List<TextSegment> textSegments) {
		final List<Embedding> embeddings = new LinkedList<>();
		textSegments.forEach(textSegment -> {
			final EmbeddingRequest request = new EmbeddingRequest();
			request.setModel(model);
			request.setPrompt(textSegment.text());
			final Embedding embedding = new Embedding(ollama.generateEmbedding(request).getEmbedding());
			embeddings.add(embedding);
		});
		return Response.from(embeddings);
	}
}
