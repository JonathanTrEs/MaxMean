package algoritmos;

import java.util.ArrayList;

import main.Vertice;

public class VorazDestructivo {
	private ArrayList<Vertice> vertices = new ArrayList<Vertice>();
	private ArrayList<Integer> resultado = new ArrayList<Integer>();
	private ArrayList<Integer> dist = new ArrayList<Integer>();
	private float afinidad;
	
	public VorazDestructivo(ArrayList<Vertice> v) {
		vertices = v;
	}
	
	public void vorazDestructivo() {
		//metemos todos los nodos en el resultado y calculamos su afinidad
		for(int i = 0; i < vertices.size(); i++) {
			resultado.add(i);
			for(int j = i+1; j < vertices.size();j++) {
				afinidad += vertices.get(i).getDistancia(j);
				dist.add(vertices.get(i).getDistancia(j));
			}
		}
		afinidad = (float) afinidad/vertices.size();
		//quitamos todos los nodos para recalcular la afinidad y nos quedamos por el que mayor consiga al quitar un nodo
		boolean salir = false;
		while(!salir) {
			float afinidadTemporal = afinidad;
			ArrayList<Integer> distanciaTemporal = new ArrayList<Integer>();
			int nodoTemporal = -1;
			for(int i = 0; i < resultado.size(); i++) {
				float aux = 0;
				ArrayList<Integer> copiaDistancias = new ArrayList<Integer>();
				for(int j = 0; j < dist.size(); j++) {
					copiaDistancias.add(dist.get(j));
				}
				for(int j = 0; j < resultado.size(); j++) {
					aux = vertices.get(resultado.get(i)).getDistancia(resultado.get(j));
					int k = 0;
					boolean salirBorrado = false;
					while(!salirBorrado && k < copiaDistancias.size()) {
						if(copiaDistancias.get(k) == (int)aux) {
							copiaDistancias.remove(k);
							salirBorrado = true;
						}
						else {
							k++;
						}
					}
				}
				aux = 0;
				for(int j = 0; j < copiaDistancias.size(); j++){
					aux += copiaDistancias.get(j);
				}
				aux = (float) aux/(resultado.size()-1);
				if(aux > afinidadTemporal) { //si al quitar el nodo mejora
					afinidadTemporal = aux;
					nodoTemporal = resultado.get(i);
					distanciaTemporal = copiaDistancias;
				}
			}
			if(nodoTemporal != -1) {
				afinidad = afinidadTemporal;
				//borramos el nodo que hemos decidido quitar
				for(int i = 0; i < resultado.size(); i++){
					if(resultado.get(i) == nodoTemporal) {
						resultado.remove(i);
					}
				}
				//borramos las distancias de ese nodo
				dist.clear();
				dist = distanciaTemporal;
			}
			else {
				salir = true;
			}
		}
	}
	public ArrayList<Integer> getResultado() {
		return resultado;
	}
	
	public float getAfinidad() {
		return afinidad;
	}

	public ArrayList<Integer> getDist() {
		return dist;
	}
}
