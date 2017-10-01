package main;

import java.util.ArrayList;

public class Vertice {
	private Integer numero; //nombre del vertice
	private ArrayList<Integer> distancias = new ArrayList<Integer>(); //distancias del vertice hacia los otros vertices
	public Vertice(int numeroVertices, int n) {
		numero = n;
		for(int i = 0; i < numeroVertices; i++) {
			distancias.add(-1);
		}
	}
	public void setDistancia(int vertice, int valor){
		distancias.set(vertice, valor);
	}
	
	public void mostrarVertice() {
		System.out.println("Vertice numero " + numero + ": ");
		for(int i = 0; i < distancias.size(); i++) {
			System.out.println("Distancia de " + numero + " a " + i + ": " + distancias.get(i));
		}
	}
	
	public int getDistancia(int indice) {
		return distancias.get(indice);
	}
}
