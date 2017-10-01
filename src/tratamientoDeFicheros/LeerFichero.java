package tratamientoDeFicheros;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class LeerFichero {
	private int nVertices;
	private	ArrayList<Integer> aristas;
	public LeerFichero(String nombreFichero) {
		aristas = new ArrayList<Integer>();
		FileReader fr = null;
		BufferedReader br = null;
		try {
			fr = new FileReader (nombreFichero);
			br = new BufferedReader(fr);
			// Lectura del fichero
			String linea;
			linea=br.readLine();
			nVertices = Integer.parseInt(linea);
			while((linea=br.readLine())!=null){
				aristas.add(Integer.parseInt(linea));
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public ArrayList<Integer> getAristas() {
		return aristas;
	}
	
	public int getnVertices(){
		return nVertices;
	}
}
