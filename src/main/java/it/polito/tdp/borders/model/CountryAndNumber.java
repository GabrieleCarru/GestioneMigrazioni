package it.polito.tdp.borders.model;

public class CountryAndNumber implements Comparable<CountryAndNumber> {
		
	private Country country;
	private Integer numAdiacenze;
	/**
	 * @param country
	 * @param numAdiacenze
	 */
	public CountryAndNumber(Country country, int numAdiacenze) {
		super();
		this.country = country;
		this.numAdiacenze = numAdiacenze;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public Integer getNumAdiacenze() {
		return numAdiacenze;
	}
	public void setNumAdiacenze(int numAdiacenze) {
		this.numAdiacenze = numAdiacenze;
	}
	@Override
	public String toString() {
		return country + " - numero paesi adiacenti = " + numAdiacenze;
	}
	@Override
	public int compareTo(CountryAndNumber other) {
		return numAdiacenze.compareTo(other.numAdiacenze);
	}
	
	
	
}
