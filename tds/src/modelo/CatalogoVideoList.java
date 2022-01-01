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
	private Map<String,List<VideoList>> videoListMap; 
	private static CatalogoVideoList unicaInstancia = new CatalogoVideoList();
	
	private FactoriaDAO dao;
	private IAdaptadorVideoListDAO adaptadorVideoList;
	
	private CatalogoVideoList() {
		try {
  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
  			adaptadorVideoList = dao.getVideoListDAO();
  			videoListMap = new HashMap<String,List<VideoList>>();
  			this.cargarCatalogo();
  		} catch (DAOException eDAO) {
  			eDAO.printStackTrace();
  		}
	}
	
	public static CatalogoVideoList getUnicaInstancia(){
		return unicaInstancia;
	}
	
	public void addVideoList(VideoList vid) {
		adaptadorVideoList.registrarVideoList(vid);
		if(videoListMap.containsKey(vid.getAutor())) {
			videoListMap.get(vid.getAutor()).add(vid);	
		}
		else {
			List<VideoList> lista = new ArrayList<VideoList>();
			lista.add(vid);
			videoListMap.put(vid.getAutor(), lista);
		}
	}
	
	public void removeVideoList(VideoList videoLista) {
		List<VideoList> lista =  videoListMap.get(videoLista.getAutor());
		/*for(VideoList v : lista){
			if(v.getNombre().equals(nombre)) {
				lista.remove(v);
				adaptadorVideoList.borrarVideoList(v);
			}
		}*/
		lista.remove(videoLista);
		adaptadorVideoList.borrarVideoList(videoLista);
	}
	
	public List<VideoList> getVideoListAutor(String autor){
		if(videoListMap.containsKey(autor))
			return videoListMap.get(autor);
		return new ArrayList<VideoList>();
		
	}
	
	public void addVideoToList(String nombre, String autor, Video v){
		List<VideoList> list = videoListMap.get(autor);
		for(VideoList vL: list){
			if(vL.getNombre().equals(nombre)) {
				vL.addVideo(v);
				adaptadorVideoList.modificarVideoList(vL);
			}
		}
		
	}
	
	public void removeVideoFromList(String nombre, String autor, Video v){
		List<VideoList> list = videoListMap.get(autor);
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
			    if(videoListMap.containsKey(pro.getAutor()))
			    	videoListMap.get(pro.getAutor()).add(pro);
			    else{
			    	List<VideoList> nuevaLista = new ArrayList<VideoList>();
			    	nuevaLista.add(pro);
			    	videoListMap.put(pro.getAutor(), nuevaLista);
			    }    	
		 }
	}
	
	public boolean existeVideoList(String nombre, String autor) {
		if(videoListMap.containsKey(autor)) {
			List<VideoList> lista =  videoListMap.get(autor);
			for(VideoList v : lista){
				if(v.getNombre().equals(nombre)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public VideoList getListaVideo(String nombre, String autor) {
		List<VideoList> lista =  videoListMap.get(autor);
		//if(lista == null)
			
		for(VideoList v : lista){
			if(v.getNombre().equals(nombre)) {
				return v;
			}
		}
		return new VideoList(nombre,autor);
	}
	
	public void actualizarVideoList(VideoList videoLista) {
		adaptadorVideoList.modificarVideoList(videoLista);
	}
}
