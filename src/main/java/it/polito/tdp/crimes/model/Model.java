package it.polito.tdp.crimes.model;

import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.crimes.db.EventsDao;

public class Model {
	EventsDao dao;
	List<Integer> vertici;
	Graph<Integer, DefaultWeightedEdge> grafo;
	
	public Model () {
		dao = new EventsDao();
	}
	
	public List<Integer> listAllYears(){
		return dao.listAllYears();
	}
	
	public void creaGrafo(Integer anno) {
		
		grafo = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		vertici = dao.getDistricts();
		
		//Aggiunta vertici
		Graphs.addAllVertices(this.grafo, vertici);
		//ci sono solo 7 vertici, quindi si può scegliere un approccio molto semplice
		//usiamo un doppio ciclo for
		
		for(Integer v1 : this.grafo.vertexSet()) {
			for(Integer v2 : this.grafo.vertexSet()) {
				if(!v1.equals(v2)) {
					if(this.grafo.getEdge(v1, v2) == null) {
						Double latMediaV1 = dao.getLatMedia(anno, v1);
						Double latMediaV2 = dao.getLatMedia(anno, v2);
						
						Double lonMediaV1 = dao.getLonMedia(anno, v1);
						Double lonMediaV2 = dao.getLonMedia(anno, v2);
						
						//data la longitudine e latitudine media, fornisce distanza media
						Double distanzaMedia = LatLngTool.distance(new LatLng(latMediaV1, lonMediaV1), 
															new LatLng (latMediaV1, lonMediaV1), LengthUnit.KILOMETER);
						Graphs.addEdgeWithVertices(this.grafo, v1, v2, distanzaMedia);
						
					}
				}
			}
		}
		
		System.out.println("Grafo creato!");
		System.out.println("# Vertici: " + this.grafo.vertexSet().size());
		System.out.println("# Archi: " + this.grafo.edgeSet().size());
	}
}
