package algoritmos;

import java.util.ArrayList;

import main.Vertice;

public class VorazConstructivo {
	private ArrayList<Vertice> vertices = new ArrayList<Vertice>();
	private ArrayList<Integer> resultado = new ArrayList<Integer>();
	private ArrayList<Integer> distancias = new ArrayList<Integer>();
	private ArrayList<Integer> nr = new ArrayList<Integer>();
	private float afinidad;
	
	public VorazConstructivo(ArrayList<Vertice> v) {
		vertices = v;
	}
	
	public void vorazConstructivo() {
		ArrayList<Integer> nodos = new ArrayList<Integer>();
		//cogemos la afinidad del nodo 0 al 1 como la mayor, y luego empezamos a comprobar si hay alguna mayor
		afinidad = (float) vertices.get(0).getDistancia(1)/2;
		nodos.add(0);
		nodos.add(1);
		//buscamos la mayor afinidad para empezar
		for(int i = 0; i < vertices.size(); i++) {
			for(int j = 0; j < vertices.size(); j++) {
				if(i != j) {//para que no salga la distancia del nodo i a si mismo.
					if(afinidad < ((float) vertices.get(i).getDistancia(j)/2)) {
						afinidad = ((float) vertices.get(i).getDistancia(j)/2);
						nodos.set(0, i);
						nodos.set(1,j);
					}
				}
			}
		}
		//metemos esos dos nodos en el resultado
		ArrayList<Integer> dist = new ArrayList<Integer>();
		resultado.add(nodos.get(0));
		resultado.add(nodos.get(1));
		dist.add(vertices.get(resultado.get(0)).getDistancia(resultado.get(1)));
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
		
		//bucle para ir añadiendo nodos si mejora la afinidad
		boolean salir = false;
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
		distancias = dist;
		nr = nodosRestantes;
	}
	
	public ArrayList<Integer> getResultado() {
		return resultado;
	}
	
	public float getAfinidad() {
		return afinidad;
	}
	
	public ArrayList<Integer> getDist() {
		return distancias;
	}
	
	public ArrayList<Integer> getNodosRestantes() {
		return nr;
	}
}
