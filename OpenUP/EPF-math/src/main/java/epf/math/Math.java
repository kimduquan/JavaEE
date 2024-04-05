package epf.math;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Math {
	
	static private final List<Symbol> symbols = new ArrayList<>();
	static private final List<Long> primeNumbers = new ArrayList<>();
	
	static void defineSymbol(final String string, final long number) {
		symbols.add(new Symbol(string, number));
	}
	
	static boolean isPrime(long number) {
		for(int i = 2; i <= number / 2; i++)
		{
			if(number % i == 0) {
				return  false;
			}
		}
		return true;
	}
	
	static long nextPrime(final long number) {
		long nextNumber = number + 1;
		do {
			if(isPrime(nextNumber)) {
				return nextNumber;
			}
			nextNumber++;
		}
		while(true);
	}
	
	static long getPrimeNumber(final int index) {
		if(index < primeNumbers.size()) {
			return primeNumbers.get(index);
		}
		final long nextPrime = nextPrime(primeNumbers.isEmpty()? 1 : primeNumbers.getLast());
		primeNumbers.add(nextPrime);
		return nextPrime;
	}
	
	static List<SequenceNumber> encode(final List<Long> sequenceNumbers){
		final List<SequenceNumber> numbers = new ArrayList<>();
		int index = 0;
		final Iterator<Long> sequenceNumberIt = sequenceNumbers.iterator();
		while(sequenceNumberIt.hasNext()) {
			final long sequenceNumber = sequenceNumberIt.next();
			final long primeNumber = getPrimeNumber(index);
			final SequenceNumber number = new SequenceNumber(primeNumber, sequenceNumber);
			numbers.add(number);
			index++;
		}
		return numbers;
	}
	
	static BigNumber numbering(final Iterator<String> stringIt, BigNumber context, final List<BigNumber> numbers) {
		final List<Long> sequenceNumbers = new ArrayList<>();
		while(stringIt.hasNext()) {
			final String string = stringIt.next();
			Long number = null;
			Symbol lastSymbol = null;
			for(Symbol symbol : symbols) {
				lastSymbol = symbol;
				if(symbol.getString().equals(string)) {
					number = symbol.getNumber();
					break;
				}
			}
			if(number == null) {
				number = nextPrime(lastSymbol.getNumber());
				lastSymbol = new Symbol(string, number);
				symbols.add(lastSymbol);
			}
			if(string.equals(" ")) {
				break;
			}
			else if(string.equals(".")) {
				break;
			}
			else if("(".equals(string)
					|| "{".equals(string)
					|| "[".equals(string)){
				context = new BigNumber(context, Arrays.asList(context.getNumbers()));
				break;
			}
			else if(")".equals(string)
					|| "}".equals(string)
					|| "]".equals(string)){
				context = context.getContext();
				break;
			}
			else if("\"".equals(string)) {
				break;
			}
			else if(",".equals(string)) {
				break;
			}
			else if("	".equals(string)) {
				break;
			}
			else if(":".equals(string)) {
				break;
			}
			else if("$".equals(string)) {
				break;
			}
			else {
				sequenceNumbers.add(number);
			}
		}
		if(!sequenceNumbers.isEmpty()) {
			final BigNumber numberingNumber = new BigNumber(context, encode(sequenceNumbers));
			numbers.add(numberingNumber);
		}
		return context;
	}
	
	static List<BigNumber> numbering(final List<String> strings, final BigNumber context) {
		final List<BigNumber> numbers = new LinkedList<>();
		final Iterator<String> it = strings.iterator();
		while(it.hasNext()) {
			numbering(it, context, numbers);
		}
		return numbers;
	}
	
	public static void main(final String[] args) throws IOException {
		defineSymbol(".", 0);
		defineSymbol(" ", 1);
		
		List<String> strings = new LinkedList<>();
		Files.lines(Path.of("C:\\GIT\\document\\specification.txt")).forEach(line -> {
			for(int i = 0; i < line.length(); i++) {
				strings.add("" + line.charAt(i));
			}
		});
		BigNumber context = new BigNumber(null, Arrays.asList());
		List<BigNumber> numbers = numbering(strings, context);
		Map<Long, Symbol> mapSymbols = new HashMap<>();
		for(Symbol symbol : symbols) {
			System.out.println(symbol.getNumber() + "->" + symbol.getString());
			mapSymbols.put(symbol.getNumber(), symbol);
		}
		for(BigNumber number : numbers) {
			System.out.println(number.toString(primeNumbers, mapSymbols));
		}
	}
}
