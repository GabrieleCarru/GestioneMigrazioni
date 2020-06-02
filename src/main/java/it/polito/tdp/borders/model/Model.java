package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	private List<Country> allCountries;
	private List<Country> countries;
	private Map<Integer, Country> countriesMap;
	private Graph<Country, DefaultEdge> graph;
	private List<Adiacenza> adiacenze;
	
	private BordersDAO dao;

	public Model() {
		dao = new BordersDAO();
		allCountries = dao.loadAllCountries(countriesMap);
		// La mappa viene riempita nel DAO
	}
	
	public void creaGrafo(int year) {
		
		graph = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
		countries = dao.getCountryByYear(year, countriesMap);
		
		// Creare i vertici 
		Graphs.addAllVertices(graph, countries);
		
		// Creare gli archi
		adiacenze = dao.getAdiacenzeFromCountryConttype1(year, countriesMap);
		for(Adiacenza a : adiacenze) {
			Country c1 = countriesMap.get(a.getState1no());
			Country c2 = countriesMap.get(a.getState2no());
			if(!graph.containsEdge(c1, c2)) {
				graph.addEdge(c1, c2);
			}
		}
	}
	
	public int contaAdiacenze(Country c) {
		int codeCountry = c.getcCode();
		int result = 0;
		
		for (int i = 0; i < adiacenze.size(); i++) {
			if(adiacenze.get(i).getState1no() == codeCountry)
				result++;
		}
		
		return result;
	}
	
	public List<CountryAndNumber> getCountryNumber() {
		
		List<CountryAndNumber> result = new ArrayList<>();
		
		for(Country c : countries) {
			CountryAndNumber cn = new CountryAndNumber(c, contaAdiacenze(c));
			result.add(cn);
		}
		
		return result;
		
	}
	
}
