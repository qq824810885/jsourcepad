package kkckkc.syntaxpane.util;

public class Pair<T, U> {
	protected T first;
	protected U second;
	
	public Pair(T first, U second) {
		this.first = first;
		this.second = second;
	}

	public T getFirst() {
		return first;
	}
	
	public U getSecond() {
		return second;
	}
	
	public String toString() {
		return "Pair <" + first + ", " + second + ">";
	}
}
