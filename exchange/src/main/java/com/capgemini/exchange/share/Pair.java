package com.capgemini.exchange.share;

public class Pair<F, S> {
	
	public Pair(F first, S second) {
		this.first = first;
		this.second = second;
	}
	
	public F first;
	public S second;
	
	@Override
	public String toString() {
		return "<first: " + first.toString() + "; second: " + second.toString() + ">";
	}

	public boolean equals(Pair<F, S> other) {
		return first.equals(other.first) && second.equals(other.second);
	}
}
