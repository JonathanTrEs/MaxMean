package main;

import tratamientoDeFicheros.LeerFichero;
import algoritmos.Grasp;
import algoritmos.Vns;
import algoritmos.VorazConstructivo;
import algoritmos.VorazDestructivo;

public class Main {

	public static void main(String[] args) {
		//leemos el fichero y almacenamos el numero de vertices y los valores de las aristas
		LeerFichero leer = new LeerFichero("max-mean-div-20.txt");
		//inicializamos la matriz de valores
		ArrayList<ArrayList<Integer>> matrizValores = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i < leer.getnVertices(); i++) {
			matrizValores.add(new ArrayList<Integer>());
			for(int j = 0; j < leer.getnVertices(); j++) {
				matrizValores.get(i).add(0);
			}
		}
		//rellenamos la matriz de valores
		ArrayList<Integer> aristas = new ArrayList<Integer>();
		aristas = leer.getAristas();
		int contador = 0;
		for(int i = 0; i < leer.getnVertices(); i++) {
			for(int j = i+1; j < leer.getnVertices(); j++) {
				matrizValores.get(i).set(j, aristas.get(contador));
				matrizValores.get(j).set(i, aristas.get(contador));
				contador++;
			}
		}
		//creamos los vertices con sus respectivas distancias
		ArrayList<Vertice> vertices = new ArrayList<>();
		for(int i = 0; i < leer.getnVertices(); i++) {
			vertices.add(new Vertice(leer.getnVertices(), i));
			for(int j = 0; j < leer.getnVertices(); j++) {
				vertices.get(i).setDistancia(j, matrizValores.get(i).get(j));
			}
		}
		
		//Llamamos el algoritmo voraz constructivo
		VorazConstructivo voraz1 = new VorazConstructivo(vertices);		
		voraz1.vorazConstructivo();
		System.out.println("Resultado Voraz Constructivo: " + voraz1.getResultado());
		System.out.println("Afinidad: " + voraz1.getAfinidad());
		
		System.out.println();
		//Llamamos al algoritmo voraz destructivo
		VorazDestructivo voraz2 = new VorazDestructivo(vertices);
		voraz2.vorazDestructivo();
		System.out.println("Resultado Voraz Destructivo: " + voraz2.getResultado());
		System.out.println("Afinidad: " + voraz2.getAfinidad());
		
		System.out.println();
		//Llamamos al algoritmo grasp
		Grasp grasp = new Grasp(6, vertices);
		int cont = 0;
		ArrayList<Integer> solucion = new ArrayList<Integer>();
		float afinidad = 0;
		grasp.grasp();
		solucion = grasp.getResultado();
		afinidad = grasp.getAfinidad();
		while(cont < 20) {
			grasp = new Grasp(6, vertices);
			grasp.grasp();
			if(afinidad < grasp.getAfinidad()) {
				afinidad = grasp.getAfinidad();
				solucion = grasp.getResultado();
				cont = 0;
			}
			cont++;
		}
		System.out.println("Resultado Grasp: " + solucion);
		System.out.println("Afinidad: " + afinidad);
		
		//Multiarranque el grasp ya nos genera la busqueda local
		cont = 0;
		afinidad = 0;
		int random = 0;
		while(cont < 20) {
			random = (int) Math.floor(Math.random()*((6-1)-2+1)+2);
			grasp = new Grasp(random, vertices);
			grasp.grasp();
			if(afinidad < grasp.getAfinidad()) {
				afinidad = grasp.getAfinidad();
				solucion = grasp.getResultado();
				cont = 0;
			}
			cont++;
		}
		System.out.println();
		System.out.println("Resultado Multiarranque: " + solucion);
		System.out.println("Afinidad: " + afinidad);
		
		//vns k1 a�ade, k2 suprime
		//primero con un voraz calculamos un resultado inicial
		VorazConstructivo vorazPrueba = new VorazConstructivo(vertices);
		long time_start, time_end;
		time_start = System.currentTimeMillis();
		vorazPrueba.vorazConstructivo();
		vorazPrueba.getResultado();
		vorazPrueba.getAfinidad();
		
		//vns
		Vns vns = new Vns(2,vertices, vorazPrueba.getResultado(), vorazPrueba.getDist(), vorazPrueba.getAfinidad(), vorazPrueba.getNodosRestantes());	
		boolean salir = false;
		while(!salir) {
			vns.vns();
			if(vns.getSeguir()) {
				vns = new Vns(vns.getEntornos(), vns.getVertices(), vns.getResultado(), vns.getDistancias(), vns.getAfinidad(), vns.getNodosRestantes());
			}
			else {
				salir = true;;
			}
		}
		time_end = System.currentTimeMillis();
		System.out.println();
		System.out.println("Resultado VNS: " + vns.getResultado());
		System.out.println("Afinidad: " + vns.getAfinidad());
		//System.out.println("the task has taken "+ ( time_end - time_start ) +" milliseconds");
	}
}
