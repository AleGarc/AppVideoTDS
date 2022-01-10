package modelo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorEtiquetaDAO;


/* El catálogo mantiene los objetos en memoria, en una tabla hash
 * para mejorar el rendimiento. Esto no se podría hacer en una base de
 * datos con un número grande de objetos. En ese caso se consultaría
 * directamente la base de datos
 */
public class CatalogoEtiquetas {
	private Map<String,Etiqueta> etiquetas; 
	private static CatalogoEtiquetas unicaInstancia = new CatalogoEtiquetas();
	
	private FactoriaDAO dao;
	private IAdaptadorEtiquetaDAO adaptadorEtiqueta;
	
	private CatalogoEtiquetas() {
		try {
  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
  			adaptadorEtiqueta = dao.getEtiquetaDAO();
  			etiquetas = new HashMap<String,Etiqueta>();
  			this.cargarCatalogo();
  		} catch (DAOException eDAO) {
  			eDAO.printStackTrace();
  		}
	}
	
	public static CatalogoEtiquetas getUnicaInstancia(){
		return unicaInstancia;
	}
	
	/*devuelve todas las Etiquetas*/
	public List<Etiqueta> getEtiquetas(){
		ArrayList<Etiqueta> lista = new ArrayList<Etiqueta>();
		for (Etiqueta c:etiquetas.values()) 
			lista.add(c);
		return lista;
	}
	
	//Devolvemos los nombres de las etiquetas disponibles 
	public List<String> getEtiquetasString(){
		ArrayList<String> lista = new ArrayList<String>();
		for (Etiqueta c:etiquetas.values()) 
			lista.add(c.getNombre());
		return lista;
	}
	
	
	//Devuelve la etiqueta buscada
	public Etiqueta getEtiqueta(String nombre) {
		return etiquetas.get(nombre); 
	}
	
	//Añade etiquetas
	public void addEtiqueta(Etiqueta e) {
		etiquetas.put(e.getNombre(),e);
	}
	
	/*Recupera todos los Etiquetas para trabajar con ellos en memoria*/
	private void cargarCatalogo() throws DAOException {
		 List<Etiqueta> EtiquetasBD = adaptadorEtiqueta.recuperarTodosEtiquetas();
		 for (Etiqueta pro: EtiquetasBD) 
			     etiquetas.put(pro.getNombre(),pro);
	}
	
	
}
