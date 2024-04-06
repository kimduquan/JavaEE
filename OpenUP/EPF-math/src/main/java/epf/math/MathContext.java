package epf.math;

import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 */
public class MathContext {
	
	/**
	 * 
	 */
	private final List<Long> primeNumbers = new ArrayList<>();
	
	/**
	 * 
	 */
	private final Map<Long, MathObject<?>> objects = new LinkedHashMap<>();
	
	/**
	 * 
	 */
	private final MathObject<Object> first = new MathObject<>(new Object(), (long)2);
	
	private static long firstPrime() {
		return 2;
	}
	
	private long lastPrime() {
		return primeNumbers.isEmpty() ? firstPrime() : primeNumbers.getLast();
	}
	
	private static boolean isPrime(final long first, long number) {
		for(long i = first; i <= number / 2; i++)
		{
			if(number % i == 0) {
				return  false;
			}
		}
		return true;
	}
	
	private static long nextPrime(final long first, final long number) {
		long nextNumber = number + 1;
		do {
			if(isPrime(first, nextNumber)) {
				return nextNumber;
			}
			nextNumber++;
		}
		while(true);
	}
	
	private long newPrime() {
		final long prime = nextPrime(firstPrime(), lastPrime());
		primeNumbers.add(prime);
		return prime;
	}
	
	private static int length(final MathObject<?> first, MathObject<?> mathObject) {
		final BigInteger key = BigInteger.valueOf(first.getKey());
		BigInteger value = mathObject.getValue();
		int length = 0;
		BigInteger[] divideAndReminder = value.divideAndRemainder(key);
		while(!BigInteger.ZERO.equals(divideAndReminder[0]) && BigInteger.ZERO.equals(divideAndReminder[1])) {
			length++;
			value = divideAndReminder[0];
			divideAndReminder = value.divideAndRemainder(key);
		}
		return length;
	}
	
	private static MathObject<?> elementAt(final MathObject<?> array, final int index, final Collection<MathObject<?>> objects) {
		for(MathObject<?> object : objects) {
			final BigInteger key = BigInteger.valueOf(object.getKey());
			BigInteger value = array.getValue();
			int exponent = 0;
			BigInteger[] divideAndReminder = value.divideAndRemainder(key);
			while(!BigInteger.ZERO.equals(divideAndReminder[0]) && BigInteger.ZERO.equals(divideAndReminder[1])) {
				exponent++;
				value = divideAndReminder[0];
				divideAndReminder = value.divideAndRemainder(key);
			}
			if(exponent == index + 1) {
				return object;
			}
		}
		return null;
	}
	
	private static boolean isArray(final MathObject<?> object) {
		return !BigInteger.valueOf(object.getKey()).equals(object.getValue());
	}
	
	private static BigInteger valueOf(final MathObject<?>[] array, final MathObject<?> first) {
		BigInteger value = first.getValue().pow(array.length);
		System.out.print(first.getKey() + "." + array.length);
		for(int i = 0; i < array.length; i++) {
			System.out.print("," + array[i].getKey() + "." + i);
			final BigInteger beta = valueOf(array[i].getKey(), i);
			value = value.multiply(beta);
		}
		return value;
	}
	
	private static void print(final MathObject<?> object, final PrintStream out, final MathObject<?> first, final Collection<MathObject<?>> objects) {
		if(isArray(object)) {
			final int length = length(first, object);
			for(int i = 0; i < length; i++) {
				final MathObject<?> element = elementAt(object, i, objects);
				out.print(element.getObject());
			}
		}
		else {
			out.println(object.getObject());
		}
	}
	
	private static MathObject<?> getObject(final Object object, final MathObject<?> first, final Collection<MathObject<?>> objects){
		MathObject<?> mathObject = null;
		if(object.getClass().isArray()) {
			final Object[] array = (Object[]) object;
			final MathObject<?>[] mathArray = new MathObject<?>[array.length];
			mathObject = getArray(array, mathArray, first, objects);
		}
		else {
			for(MathObject<?> math : objects) {
				if(math.getObject().equals(object)) {
					mathObject = math;
				}
			}
		}
		return mathObject;
	}
	
	private static MathObject<?> getArray(final Object[] array, final MathObject<?>[] mathArray, final MathObject<?> first, final Collection<MathObject<?>> objects){
		boolean allElementsExist = true;
		for(int i = 0; i < array.length; i++) {
			mathArray[i] = getObject(array[i], first, objects);
			if(mathArray[i] == null) {
				allElementsExist = false;
			}
		}
		if(allElementsExist) {
			final BigInteger value = valueOf(mathArray, first);
			for(MathObject<?> mathObject : objects) {
				if(isArray(mathObject) && mathObject.getValue().equals(value)) {
					return mathObject;
				}
			}
		}
		return null;
	}
	
	private MathObject<?> newObject(final Object object, final Long key){
		final MathObject<?> mathObject = new MathObject<>(object, key);
		objects.put(key, mathObject);
		return mathObject;
	}
	
	private MathObject<?> newArray(final Long key, final BigInteger value){
		final MathObject<?> mathObject = new MathObject<>("#" + key, key, value);
		objects.put(key, mathObject);
		return mathObject;
	}
	
	private static BigInteger valueOf(final Long element, final int index) {
		return BigInteger.valueOf(element).pow(index + 1);
	}
	
	private MathObject<?> numbering(final Object[] array, final MathObject<Object> first, final Map<Long, MathObject<?>> objects){
		final MathObject<?>[] mathArray = new MathObject<?>[array.length];
		MathObject<?> numbering = getArray(array, mathArray, first, objects.values());
		if(numbering == null) {
			for(int i = 0; i < array.length; i++) {
				if(mathArray[i] == null) {
					mathArray[i] = newObject(array[i], newPrime());
				}
			}
			final Long key = newPrime();
			System.out.println("#" + key + ":");
			final BigInteger value = valueOf(mathArray, first);
			numbering = newArray(key, value);
		}
		return numbering;
	}
	
	public static void main(final String[] args) throws IOException {
		final MathContext context = new MathContext();
		Files.lines(Path.of("C:\\GIT\\document\\specification.txt")).forEach(line -> {
			final Object[] array = new Object[line.length()];
			for(int i = 0; i < array.length; i++) {
				array[i] = "" + line.charAt(i);
			}
			final MathObject<?> mathObject = context.numbering(array, context.first, context.objects);
			print(mathObject, System.out, context.first, context.objects.values());
		});
	}
}
