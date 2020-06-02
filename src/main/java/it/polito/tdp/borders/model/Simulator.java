package it.polito.tdp.borders.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulator {

	// Modello di cui dobbiamo tenere traccia
	private Graph<Country, DefaultEdge> graph;
	
	// Tipi di evento ---> Coda prioritaria
	private PriorityQueue<Event> queue;
	
	// Parametri della simulazione
	private static int N_MIGRANTI = 1000;
	private Country partenza;
	
	// Valori in output
	private int T = -1;
	private Map<Country, Integer> stanziali;
	
	public void init(Country partenza, Graph<Country, DefaultEdge> graph) {
		
		this.partenza = partenza;
		this.graph = graph;
		
		// impostazione dello stato iniziale
		this.T = 1;
		stanziali = new HashMap<>();
		for(Country c : this.graph.vertexSet()) {
			stanziali.put(c, 0);
		}
		// creo la coda
		this.queue = new PriorityQueue<Event>();
		// vado ad inserire il primo evento
		this.queue.add(new Event(T, partenza, N_MIGRANTI));
		
	}
	
	public void run() {
		while(!queue.isEmpty()) {
			Event e = queue.poll();
			processEvent(e);
		}
	}
	
	private void processEvent(Event e) {
		this.T = e.getT();
		int nPersone = e.getN();
		Country stato = e.getStato();
		// Cerco i vicini di stato 
		List<Country> vicini = Graphs.neighborListOf(this.graph, stato);
		
		int migranti = (nPersone/2) / vicini.size();
		
		if(migranti > 0) {
			// le persone si possono muovere
			for(Country confinante : vicini) {
				queue.add(new Event(e.getT()+1, confinante, migranti));
			}
		}
		
		int stanz = nPersone - migranti * vicini.size();
		this.stanziali.put(stato, this.stanziali.get(stato) + stanz);
		
		
	}
	
	public Integer getT() {
		return this.T;
	}
	
	public Map<Country, Integer> getStanziali() {
		return this.stanziali;
	}
	
}
