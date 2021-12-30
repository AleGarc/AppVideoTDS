package modelo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorVideoListDAO;


/* El catálogo mantiene los objetos en memoria, en una tabla hash
 * para mejorar el rendimiento. Esto no se podría hacer en una base de
 * datos con un número grande de objetos. En ese caso se consultaría
 * directamente la base de datos
 */
public class CatalogoVideoList {
	private Map<String,List<VideoList>> videoLists; 
	private static CatalogoVideoList unicaInstancia = new CatalogoVideoList();
	
	private FactoriaDAO dao;
	private IAdaptadorVideoListDAO adaptadorVideoList;
	
	private CatalogoVideoList() {
		try {
  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
  			adaptadorVideoList = dao.getVideoListDAO();
  			videoLists = new HashMap<String,List<VideoList>>();
  			this.cargarCatalogo();
  		} catch (DAOException eDAO) {
  			eDAO.printStackTrace();
  		}
	}
	
	public static CatalogoVideoList getUnicaInstancia(){
		return unicaInstancia;
	}
	
	public void addVideoList(VideoList vid) {
		videoLists.get(vid.getAutor()).add(vid);
		adaptadorVideoList.registrarVideoList(vid);
	}
	
	public void removeVideoList(String nombre, String autor) {
		List<VideoList> lista =  videoLists.get(autor);
		for(VideoList v : lista){
			if(v.getNombre().equals(nombre)) {
				lista.remove(v);
				adaptadorVideoList.borrarVideoList(v);
			}
		}
	}
	
	public List<VideoList> getVideoListAutor(String autor){
		return videoLists.get(autor);
	}
	
	public void addVideoToList(String nombre, String autor, Video v){
		List<VideoList> list = videoLists.get(autor);
		for(VideoList vL: list){
			if(vL.getNombre().equals(nombre)) {
				vL.addVideo(v);
				adaptadorVideoList.modificarVideoList(vL);
			}
		}
		
	}
	
	public void removeVideoFromList(String nombre, String autor, Video v){
		List<VideoList> list = videoLists.get(autor);
		for(VideoList vL: list){
			if(vL.getNombre().equals(nombre)) {
				vL.removeVideo(v);
				adaptadorVideoList.modificarVideoList(vL);
			}
		}
	}
	
	/*Recupera todos los VideoLists para trabajar con ellos en memoria*/
	private void cargarCatalogo() throws DAOException {
		 List<VideoList> VideoListsBD = adaptadorVideoList.recuperarTodosVideoLists();
		 for (VideoList pro: VideoListsBD) {
			    if(videoLists.containsKey(pro.getAutor()))
			    	videoLists.get(pro.getAutor()).add(pro);
			    else{
			    	List<VideoList> nuevaLista = new ArrayList<VideoList>();
			    	nuevaLista.add(pro);
			    	videoLists.put(pro.getAutor(), nuevaLista);
			    }    	
		 }
	}
	
	public boolean existeVideoList(String nombre, String autor) {
		List<VideoList> lista =  videoLists.get(autor);
		for(VideoList v : lista){
			if(v.getNombre().equals(nombre)) {
				return true;
			}
		}
		return false;
	}
}
