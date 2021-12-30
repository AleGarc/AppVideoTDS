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
	private Map<String,Etiqueta> etiqueta; 
	private static CatalogoEtiquetas unicaInstancia = new CatalogoEtiquetas();
	
	private FactoriaDAO dao;
	private IAdaptadorEtiquetaDAO adaptadorEtiqueta;
	
	private CatalogoEtiquetas() {
		try {
  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
  			adaptadorEtiqueta = dao.getEtiquetaDAO();
  			etiqueta = new HashMap<String,Etiqueta>();
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
		for (Etiqueta c:etiqueta.values()) 
			lista.add(c);
		return lista;
	}
	
	public List<String> getEtiquetasString(){
		ArrayList<String> lista = new ArrayList<String>();
		for (Etiqueta c:etiqueta.values()) 
			lista.add(c.getNombre());
		return lista;
	}
	
	public Etiqueta getEtiqueta(int codigo) {
		for (Etiqueta p : etiqueta.values()) {
			if (p.getCodigo()==codigo) return p;
		}
		return null;
	}
	public Etiqueta getEtiqueta(String nombre) {
		return etiqueta.get(nombre); 
	}
	
	public void addEtiqueta(Etiqueta e) {
		etiqueta.put(e.getNombre(),e);
	}
	public void removeEtiqueta(Etiqueta e) {
		etiqueta.remove(e.getNombre());
	}
	
	/*Recupera todos los Etiquetas para trabajar con ellos en memoria*/
	private void cargarCatalogo() throws DAOException {
		 List<Etiqueta> EtiquetasBD = adaptadorEtiqueta.recuperarTodosEtiquetas();
		 for (Etiqueta pro: EtiquetasBD) 
			     etiqueta.put(pro.getNombre(),pro);
	}
	
}
