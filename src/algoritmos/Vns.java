package algoritmos;

import java.util.ArrayList;

import main.Vertice;

public class Vns {
	private ArrayList<Vertice> vertices = new ArrayList<Vertice>();
	private ArrayList<Integer> resultado = new ArrayList<Integer>();
	private ArrayList<Integer> nodosRestantes = new ArrayList<Integer>();
	private ArrayList<Integer> distancias = new ArrayList<Integer>();
	private float afinidad;
	private int entornos;
	private boolean seguir = false;
	//private Vns vnsOriginal;
	
	public Vns(int k, ArrayList<Vertice> v, ArrayList<Integer> r, ArrayList<Integer> d, float a, ArrayList<Integer> nr){
		vertices = v;
		entornos = k;
		resultado = r;
		distancias = d;
		afinidad = a;
		nodosRestantes = nr;
		//vnsOriginal = new Vns(k, v, r, d, a, nr);
	}
	
	public void vns(){
		//calculamos x prima
		entornoSub(); //entorno 1
		//busqueda local sobre x prima
		int resultadoAuxiliar = resultado.size();
		entornoSub();
		if(resultadoAuxiliar > resultado.size()) {//si quitamos algun nodo esque mejoramos x, por lo que la sustituimos y empezamos de nuevo
			seguir = true;
		}
		else {
			if(entornos > 1) {
				entornoAdd(); //entorno 2
				if(resultadoAuxiliar < resultado.size()) {//si añadimos un nodo que mejora
					seguir = true;
				}
			}
		}
	}
	
	public ArrayList<Vertice> getVertices() {
		return vertices;
	}

	public void setVertices(ArrayList<Vertice> vertices) {
		this.vertices = vertices;
	}

	public ArrayList<Integer> getResultado() {
		return resultado;
	}

	public void setResultado(ArrayList<Integer> resultado) {
		this.resultado = resultado;
	}

	public ArrayList<Integer> getNodosRestantes() {
		return nodosRestantes;
	}

	public void setNodosRestantes(ArrayList<Integer> nodosRestantes) {
		this.nodosRestantes = nodosRestantes;
	}

	public ArrayList<Integer> getDistancias() {
		return distancias;
	}

	public void setDistancias(ArrayList<Integer> distancias) {
		this.distancias = distancias;
	}

	public float getAfinidad() {
		return afinidad;
	}

	public void setAfinidad(float afinidad) {
		this.afinidad = afinidad;
	}

	public int getEntornos() {
		return entornos;
	}

	public void setEntornos(int entornos) {
		this.entornos = entornos;
	}

	/*public Vns getVnsOriginal() {
		return vnsOriginal;
	}

	public void setVnsOriginal(Vns vnsOriginal) {
		this.vnsOriginal = vnsOriginal;
	}*/
	
	public boolean getSeguir() {
		return seguir;
	}

	public void entornoAdd(){
		//FASE estructura de entorno add-greedy
		float afinidadTemporal = afinidad;
		int nodoTemporal = -1;
		for(int i = 0; i < nodosRestantes.size(); i++) {
			float aux = 0;
			for(int j = 0; j < resultado.size(); j++) {
				aux += vertices.get(nodosRestantes.get(i)).getDistancia(resultado.get(j));
			}
			for(int j = 0; j < distancias.size(); j++) {
				aux += distancias.get(j);
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
				distancias.add(vertices.get(nodoTemporal).getDistancia(resultado.get(i)));
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
	}
	
	public void entornoSub(){
		//quitamos todos los nodos para recalcular la afinidad y nos quedamos por el que mayor consiga al quitar un nodo
		float afinidadTemporal = afinidad;
		ArrayList<Integer> distanciaTemporal = new ArrayList<Integer>();
		int nodoTemporal = -1;
		for(int i = 0; i < resultado.size(); i++) {
			float aux = 0;
			ArrayList<Integer> copiaDistancias = new ArrayList<Integer>();
			for(int j = 0; j < distancias.size(); j++) {
				copiaDistancias.add(distancias.get(j));
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
			distancias.clear();
			distancias = distanciaTemporal;
		}
	}
}
