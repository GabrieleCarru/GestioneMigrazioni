package it.polito.tdp.borders.model;

public class Event implements Comparable<Event>{

	private int t;
	private Country stato; // lo stato in cui arrivano i migranti al tempo t
	private int n; // numero di migranti che arrivano al tempo t (la metà di essi si sposterà)
	/**
	 * @param t
	 * @param stato
	 * @param n
	 */
	public Event(int t, Country stato, int n) {
		super();
		this.t = t;
		this.stato = stato;
		this.n = n;
	}
	public int getT() {
		return t;
	}
	public void setT(int t) {
		this.t = t;
	}
	public Country getStato() {
		return stato;
	}
	public void setStato(Country stato) {
		this.stato = stato;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}
	@Override
	public int compareTo(Event o) {
		return (this.t-o.t);
	}
	
	
	
}
