package epf.math;

import java.util.Comparator;

public class SymbolComparator implements Comparator<Symbol> {

	@Override
	public int compare(Symbol o1, Symbol o2) {
		if(o1.getString().equals(o2.getString())) {
			return 0;
		}
		return (int)(o1.getNumber() - o2.getNumber());
	}

}
