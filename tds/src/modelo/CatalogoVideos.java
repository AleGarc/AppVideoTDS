package modelo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorVideoDAO;


/* El catálogo mantiene los objetos en memoria, en una tabla hash
 * para mejorar el rendimiento. Esto no se podría hacer en una base de
 * datos con un número grande de objetos. En ese caso se consultaría
 * directamente la base de datos
 */
public class CatalogoVideos {
	private Map<String,Video> videos; 
	private static CatalogoVideos unicaInstancia = new CatalogoVideos();
	private CatalogoEtiquetas catalogoEtiquetas = CatalogoEtiquetas.getUnicaInstancia();
	
	private FactoriaDAO dao;
	private IAdaptadorVideoDAO adaptadorVideo;
	
	private CatalogoVideos() {
		try {
  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
  			adaptadorVideo = dao.getVideoDAO();
  			videos = new HashMap<String,Video>();
  			this.cargarCatalogo();
  		} catch (DAOException eDAO) {
  			eDAO.printStackTrace();
  		}
	}
	
	public static CatalogoVideos getUnicaInstancia(){
		return unicaInstancia;
	}
	
	/*devuelve todos los Videos*/
	public List<Video> getVideos(){
		ArrayList<Video> lista = new ArrayList<Video>();
		for (Video c:videos.values()) 
			lista.add(c);
		return lista;
	}
	
	public Video getVideo(int codigo) {
		for (Video p : videos.values()) {
			if (p.getCodigo()==codigo) return p;
		}
		return null;
	}
	public Video getVideo(String nombre) {
		return videos.get(nombre); 
	}
	
	public void addVideo(Video vid) {
		videos.put(vid.getTitulo(),vid);
		for(Etiqueta e: vid.getEtiquetas()) {
			catalogoEtiquetas.addEtiqueta(e);
		}
	}
	public void removeVideo(Video pro) {
		videos.remove(pro.getTitulo());
	}
	
	/*Recupera todos los Videos para trabajar con ellos en memoria*/
	private void cargarCatalogo() throws DAOException {
		 List<Video> VideosBD = adaptadorVideo.recuperarTodosVideos();
		 for (Video pro: VideosBD) 
			     videos.put(pro.getTitulo(),pro);
	}
	
}
