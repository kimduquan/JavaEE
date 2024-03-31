package epf.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Math {
	
	static private final List<Symbol> symbols = new ArrayList<>();
	static private final List<Long> primeNumbers = new ArrayList<>();
	
	static void defineSymbol(final String string, final long number) {
		symbols.add(new Symbol(string, number));
	}
	
	static boolean isPrime(long number) {
		for(int i = 2; i <= number / 2; i++)
		{
			if((number%i) == 0) {
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
	
	static GNumber numbering(final Iterator<String> stringIt) {
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
				symbols.add(new Symbol(string, number));
			}
			if(number == 0) {
				break;
			}
			else if(number == 1) {
			}
			else {
				sequenceNumbers.add(number);
			}
		}
		if(!sequenceNumbers.isEmpty()) {
			final List<SequenceNumber> numbers = encode(sequenceNumbers);
			final GNumber numberingNumber = new GNumber(numbers);
			return numberingNumber;
		}
		return null;
	}
	
	static GNumber numbering(final List<String> strings) {
		return numbering(strings.iterator());
	}
	
	public static void main(final String[] args) {
		defineSymbol(".", 0);
		defineSymbol(" ", 1);
		GNumber number = numbering(Arrays.asList("this", " ", "is", "", "a", " ", "test"));
		System.out.println(number.toString());
		number = numbering(Arrays.asList("is", " ", "not", "", "a", " ", "test"));
		System.out.println(number.toString());
		for(Symbol symbol : symbols) {
			System.out.print(" " + symbol.getString());
		}
	}
}
