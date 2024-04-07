package epf.math;

import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
		return indexOf(mathObject.getValue(), first.getKey());
	}
	
	private static MathObject<?> elementAt(final MathObject<?> array, final int index, final Collection<MathObject<?>> objects) {
		for(MathObject<?> object : objects) {
			if(indexOf(array.getValue(), object.getKey()) == index + 1) {
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
		for(int i = 0; i < array.length; i++) {
			final BigInteger beta = valueOf(array[i].getKey(), i);
			value = value.multiply(beta);
		}
		return value;
	}
	
	private static int indexOf(BigInteger value, final Long key) {
		final BigInteger keyInt = BigInteger.valueOf(key);
		int exponent = 0;
		BigInteger[] divideAndReminder = value.divideAndRemainder(keyInt);
		while(!BigInteger.ZERO.equals(divideAndReminder[0]) && BigInteger.ZERO.equals(divideAndReminder[1])) {
			exponent++;
			value = divideAndReminder[0];
			divideAndReminder = value.divideAndRemainder(keyInt);
		}
		return exponent;
	}
	
	private static void print(final MathObject<?> object, final PrintStream out, final MathObject<?> first, final Collection<MathObject<?>> objects) {
		if(isArray(object)) {
			out.println();
			final int length = length(first, object);
			out.print("#" + object.getKey() + "[" + length + "]:");
			print(object, length, out, first, objects);
		}
		else {
			out.println();
			out.print("#" + object.getKey() + ":");
			out.print(object.getObject());
		}
	}
	
	private static void print(final MathObject<?> object, final int length, final PrintStream out, final MathObject<?> first, final Collection<MathObject<?>> objects) {
		if(isArray(object)) {
			for(int i = 0; i < length; i++) {
				final MathObject<?> element = elementAt(object, i, objects);
				if(isArray(element)) {
					print(element, length(first, element), out, first, objects);
				}
				else {
					out.print("[" + element.getObject() + "]");
				}
			}
		}
		else {
			out.print(object.getObject());
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
	
	private static MathObject<?> getArray(final BigInteger value, final Collection<MathObject<?>> objects){
		for(MathObject<?> mathObject : objects) {
			if(isArray(mathObject) && mathObject.getValue().equals(value)) {
				return mathObject;
			}
		}
		return null;
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
			return getArray(value, objects);
		}
		return null;
	}
	
	private MathObject<?> newObject(final Object object, final Long key){
		final MathObject<?> mathObject = new MathObject<>(object, key);
		objects.put(key, mathObject);
		return mathObject;
	}
	
	private MathObject<?> newArray(final String string, final Long key, final BigInteger value){
		final MathObject<?> mathObject = new MathObject<>(string, key, value);
		objects.put(key, mathObject);
		return mathObject;
	}
	
	private static BigInteger valueOf(final Long element, final int index) {
		return BigInteger.valueOf(element).pow(index + 1);
	}
	
	private static boolean containsKey(final BigInteger value, final Long key) {
		final BigInteger keyInt = BigInteger.valueOf(key);
		BigInteger[] divideAndReminder = value.divideAndRemainder(keyInt);
		return !BigInteger.ZERO.equals(divideAndReminder[0]) && BigInteger.ZERO.equals(divideAndReminder[1]);
	}
	
	private static MathObject<?>[] split(final MathObject<?>[] array, final int index, final Long key){
		final List<MathObject<?>> split = new LinkedList<>();
		for(int i = index; i < array.length; i++) {
			final MathObject<?> object = array[i];
			if(object.getKey().equals(key)) {
				break;
			}
			split.add(object);
		}
		return split.toArray(new MathObject<?>[0]);
	}
	
	private static MathObject<?>[] toArray(final MathObject<?> array, final MathObject<?> first, final Collection<MathObject<?>> objects) {
		final int length = length(first, array);
		final MathObject<?>[] mathArray = new MathObject<?>[length];
		for(int i = 0; i < length; i++) {
			mathArray[i] = elementAt(array, i, objects);
		}
		return mathArray;
	}
	
	private static boolean beta(final BigInteger p, final BigInteger q, final BigInteger element, final int index) {
		return p.remainder(q.multiply(BigInteger.valueOf(index).add(BigInteger.ONE)).add(BigInteger.ONE)).compareTo(element) == 0;
	}
	
	private static boolean beta(final BigInteger p, final BigInteger q, final MathObject<?>[] array) {
		for(int i = 0; i < array.length; i++) {
			if(!beta(p, q, array[i].getValue(), i)) {
				return false;
			}
		}
		return true;
	}
	
	private MathObject<?> numbering(final MathObject<?>[] array, final MathObject<?> first, final Collection<MathObject<?>> objects){
		BigInteger value = first.getValue().pow(array.length);
		final StringBuilder string = new StringBuilder();
		for(int i = 0; i < array.length; i++) {
			final MathObject<?> object = array[i];
			if(containsKey(value, object.getKey())) {
				MathObject<?>[] split = split(array, 0, object.getKey());
				final List<MathObject<?>> divideAndRemainder = new LinkedList<>();
				MathObject<?> divide = null;
				if(split.length > 1) {
					divide = numbering(split, first, objects);
				}
				else if(split.length > 0){
					divide = split[0];
				}
				if(divide != null) {
					divideAndRemainder.add(divide);
				}
				divideAndRemainder.add(object);
				split = split(array, i + 1, object.getKey());
				MathObject<?> remainder = null;
				if(split.length > 1) {
					remainder = numbering(split, first, objects);
				}
				else if(split.length > 0){
					remainder = split[0];
				}
				if(remainder != null) {
					divideAndRemainder.add(remainder);
				}
				if(divideAndRemainder.size() > 1) {
					return numbering(divideAndRemainder.toArray(new MathObject<?>[0]), first, objects);
				}
				return divideAndRemainder.get(0);
			}
			else {
				final BigInteger beta = valueOf(object.getKey(), i);
				value = value.multiply(beta);
				string.append(String.valueOf(object.getObject()));
			}
		}
		MathObject<?> mathArray = getArray(value, objects);
		if(mathArray == null) {
			mathArray = newArray(string.toString(), newPrime(), value);
		}
		return mathArray;
	}
	
	public static void beta(final MathObject<?> array, final MathObject<?> first, final Collection<MathObject<?>> objects) {
		final MathObject<?>[] mathArray = toArray(array, first, objects);
		BigInteger max = BigInteger.valueOf(0);
		for(int i = 0; i < mathArray.length; i++) {
			if(mathArray[i].getValue().compareTo(max) > 0) {
				max = mathArray[i].getValue();
			}
		}
		final BigInteger n = max.pow(mathArray.length);
		for(BigInteger p = BigInteger.ONE; p.compareTo(n) < 0; p = p.add(BigInteger.ONE)) {
			for(BigInteger q = BigInteger.ONE; q.compareTo(p) < 0; q = q.add(BigInteger.ONE)) {
				if(beta(p, q, mathArray)) {
					System.out.println();
					System.out.println("p=" + p + ",q=" + q);
				}
			}
		}
	}
	
	private MathObject<?> numbering(final Object[] array, final MathObject<Object> first, final Map<Long, MathObject<?>> objects){
		final MathObject<?>[] mathArray = new MathObject<?>[array.length];
		MathObject<?> numbering = getArray(array, mathArray, first, objects.values());
		if(numbering == null) {
			for(int i = 0; i < array.length; i++) {
				MathObject<?> mathObject = mathArray[i];
				if(mathObject == null) {
					mathObject = newObject(array[i], newPrime());
					mathArray[i] = mathObject;
				}
			}
			numbering = numbering(mathArray, first, objects.values());
		}
		return numbering;
	}
	
	public static void main(final String[] args) throws IOException {
		final MathContext context = new MathContext();
		//context.numbering(new String[] {".", ",", "?", "\t", "\n", "\r", "\"", "'", "[", "]", "{", "}", "(", ")", "-", ":", "/", " "}, context.first, context.objects);
		Files.lines(Path.of("C:\\GIT\\document\\specification.txt")).forEach(line -> {
			final Object[] array = line.split(" ");
			/*final Object[] array = new String[line.length()];
			for(int i = 0 ; i < line.length(); i++) {
				array[i] = "" + line.charAt(i);
			}*/
			context.numbering(array, context.first, context.objects);
			//beta(mathObject, context.first, context.objects.values());
		});
		//context.numbering("this is a test".split(" "), context.first, context.objects);
		//context.numbering("this is".split(" "), context.first, context.objects);
		context.objects.forEach((key, object) -> {
			print(object, System.out, context.first, context.objects.values());
		});
	}
}
