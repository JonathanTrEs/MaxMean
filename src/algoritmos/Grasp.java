package algoritmos;

import java.awt.Point;
import java.util.ArrayList;

import main.Vertice;

public class Grasp {
	private ArrayList<Vertice> vertices = new ArrayList<Vertice>();
	private ArrayList<Integer> resultado = new ArrayList<Integer>();
	private float afinidad;
	private int lrc;
	
	public Grasp(int listaCandidatos, ArrayList<Vertice> v){
		lrc = listaCandidatos;
		vertices = v;
	}
	
	public void grasp(){
		//FASE CONSTRUCTIVA ACABADA
		ArrayList<Point> nodos = new ArrayList<Point>();
		for(int i = 0; i < lrc; i++) {
			nodos.add(new Point());
		}
		//cogemos la afinidad de lrc nodos, y luego empezamos a comprobar si hay alguna mayor. 0-1, 0-2, etc..
		for(int i = 0; i < lrc; i++) {
			afinidad = -1;
			for(int j = 0; j < vertices.size(); j++) {
				for(int k = 0; k < vertices.size(); k++) {
					if(j != k) { // para no mirar de un nodo a si mismo
						if(afinidad < ((float) vertices.get(j).getDistancia(k)/2)) { //para si mejora la aginidad
							Point p = new Point(j,k);
							boolean existe = false;
							for(int l = 0; l < nodos.size(); l++) {
								if(p.equals(nodos.get(l))) {
									existe = true;
								}
								else if((p.getX() == nodos.get(l).getY()) && (p.getY() == nodos.get(l).getX())){
									existe = true;
								}
							}
							if(!existe) { //para ver si ese nodo ya lo tenemos dentro
								afinidad = ((float) vertices.get(j).getDistancia(k)/2);
								nodos.get(i).setLocation(j, k);
							}
						}
					}
				}
			}
		}
		//aÃnadimos el que nos diga el random al resultado
		int random = (int) Math.floor(Math.random()*((lrc-1)-0+1)+0);
		ArrayList<Integer> dist = new ArrayList<Integer>();
		resultado.add((int)nodos.get(random).getX());
		resultado.add((int) nodos.get(random).getY());
		dist.add(vertices.get(resultado.get(0)).getDistancia(resultado.get(1)));
		afinidad = (float)dist.get(0)/2;
		//hacemos una lista con todos los vertices para irlos borrando cuando los anadimos al resultado
		ArrayList<Integer> nodosRestantes = new ArrayList<Integer>();
		for(int i = 0; i < vertices.size(); i++) {
			nodosRestantes.add(i);
		}
		//borramos los metidos anteriormente en la lista
		for(int i = 0; i < resultado.size(); i++){
			for(int j = 0; j < nodosRestantes.size(); j++) {
				if(resultado.get(i) == nodosRestantes.get(j)) {
					nodosRestantes.remove(j);
				}
			}
		}
		ArrayList<Integer> n = new ArrayList<Integer>();
		//bucle para ir anadiendo nodos si mejora la afinidad
		boolean salir = false;
		while(!salir) {
			for(int i = 0; i < lrc; i++) {
				n.add(-1);
				float afinidadTemporal = afinidad;
				for(int j = 0; j < nodosRestantes.size(); j++) {
					float aux = 0;
					for(int k = 0; k < resultado.size(); k++) {
						aux += vertices.get(nodosRestantes.get(j)).getDistancia(resultado.get(k));
					}
					for(int k = 0; k < dist.size(); k++) {
						aux += dist.get(k);
					}
					aux = aux/(resultado.size()+1);
					if(aux >= afinidadTemporal) { //si al añadir el nodo mejora o sigue igual lo apuntamos para añadirlo
						afinidadTemporal = aux;
						n.set(i, nodosRestantes.get(j));
					}
				}
			}
			int contador = 0;
			for(int i = 0; i < lrc; i++) {
				if(n.get(i) != -1){
					contador++;
				}
			}
			lrc = contador;
			if(contador != 0) {
				random = (int) Math.floor(Math.random()*((lrc-1)-0+1)+0);
				int aux = 0;
				for(int i = 0; i < resultado.size(); i++) {
					aux += vertices.get(n.get(random)).getDistancia(resultado.get(i));
				}
				for(int i = 0; i < dist.size(); i++) {
					aux += dist.get(i);
				}
				afinidad = (float) aux/(resultado.size()+1);
				for(int i = 0; i < resultado.size(); i++) {
					dist.add(vertices.get(n.get(random)).getDistancia(resultado.get(i)));
				}
				resultado.add(n.get(random));
				//borramos los metidos anteriormente en la lista
				for(int i = 0; i < resultado.size(); i++){
					for(int j = 0; j < nodosRestantes.size(); j++) {
						if(resultado.get(i) == nodosRestantes.get(j)) {
							nodosRestantes.remove(j);
						}
					}
				}
				n.clear();
			}
			else {
				salir = true;
			}
		}
		//FASE estructura de entorno add-greedy
		salir = false;
		while(!salir) {
			float afinidadTemporal = afinidad;
			int nodoTemporal = -1;
			for(int i = 0; i < nodosRestantes.size(); i++) {
				float aux = 0;
				for(int j = 0; j < resultado.size(); j++) {
					aux += vertices.get(nodosRestantes.get(i)).getDistancia(resultado.get(j));
				}
				for(int j = 0; j < dist.size(); j++) {
					aux += dist.get(j);
				}
				aux = aux/(resultado.size()+1);
				if(aux >= afinidadTemporal) { //si al añadir el nodo mejora o sigue igual lo apuntamos para añadirlo
					afinidadTemporal = aux;
					nodoTemporal = nodosRestantes.get(i);
				}
			}
			if(nodoTemporal != -1) {
				afinidad = afinidadTemporal;
				for(int i = 0; i < resultado.size(); i++) {
					dist.add(vertices.get(nodoTemporal).getDistancia(resultado.get(i)));
				}
				resultado.add(nodoTemporal);
				//borramos los metidos anteriormente en la lista
				for(int i = 0; i < resultado.size(); i++){
					for(int j = 0; j < nodosRestantes.size(); j++) {
						if(resultado.get(i) == nodosRestantes.get(j)) {
							nodosRestantes.remove(j);
						}
					}
				}
			}
			else {
				salir = true;
			}
		}
	}
	public float getAfinidad(){
		return afinidad;
	}
	
	public ArrayList<Integer> getResultado(){
		return resultado;
	}
}
