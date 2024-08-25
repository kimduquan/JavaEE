package epf.util.io;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.SequenceInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 
 */
public interface InputStreamUtil {

	/**
	 * @param input
	 * @param charset
	 * @param separator
	 * @return
	 * @throws Exception
	 */
	static String readString(final InputStream input, final String charset, final CharSequence separator) throws Exception {
		try(InputStreamReader reader = new InputStreamReader(input, charset)){
			try(BufferedReader bufferReader = new BufferedReader(reader)){
				return bufferReader.lines().collect(Collectors.joining(separator));
			}
		}
	}
	
	/**
	 * @return
	 */
	static InputStream empty() {
		return new ByteArrayInputStream(new byte[0]);
	}
	
	/**
	 * 
	 */
	class InputStreamJoiner {
		
		/**
		 * 
		 */
		private final Optional<InputStream> separator;
		
		/**
		 * 
		 */
		private List<InputStream> inputStreams;
		
		/**
		 * 
		 */
		private final Optional<InputStream> last;
		
		/**
		 * @param separator
		 * @param first
		 * @param last
		 */
		public InputStreamJoiner(final Optional<InputStream> separator, final Optional<InputStream> first, Optional<InputStream> last) {
			this.separator = separator;
			inputStreams = new ArrayList<>();
			if(first.isPresent()) {
				inputStreams.add(first.get());
			}
			this.last = last;
		}

		/**
		 * @return
		 */
		public InputStream sequence() {
			if(last.isPresent()) {
				inputStreams.add(last.get());
			}
			return new SequenceInputStream(Collections.enumeration(inputStreams));
		}
		
		/**
		 * @param inputStream
		 */
		public void add(final InputStream inputStream) {
			if(separator.isPresent() && !inputStreams.isEmpty()) {
				inputStreams.add(separator.get());
			}
			inputStreams.add(inputStream);
		}
		
		/**
		 * @param other
		 * @return
		 */
		public InputStreamJoiner addAll(final InputStreamJoiner other) {
			this.inputStreams.addAll(other.inputStreams);
			return this;
		}
	}
	
	/**
	 * @param separator
	 * @param first
	 * @param last
	 * @return
	 */
	static Collector<InputStream, ?, InputStream> joining(final Optional<InputStream> separator, final Optional<InputStream> first, Optional<InputStream> last) {
		return Collector.of(() -> new InputStreamJoiner(separator, first, last), InputStreamJoiner::add, InputStreamJoiner::addAll, InputStreamJoiner::sequence);
	}
	
	/**
	 * @param separator
	 * @return
	 */
	static Collector<InputStream, ?, InputStream> joining(final Optional<InputStream> separator) {
		return joining(separator, Optional.empty(), Optional.empty());
	}
}
