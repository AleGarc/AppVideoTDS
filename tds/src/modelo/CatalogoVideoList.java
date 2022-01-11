package modelo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
	
	//Añade una lista de video al catalogo
	public void addVideoList(VideoList vid) {
		if(videoListMap.containsKey(vid.getAutor())) {
			videoListMap.get(vid.getAutor()).add(vid);	
		}
		else {
			List<VideoList> lista = new ArrayList<VideoList>();
			lista.add(vid);
			videoListMap.put(vid.getAutor(), lista);
		}
	}
	
	
	//Borra una lista de video del catalogo
	public void removeVideoList(VideoList videoLista) {
		List<VideoList> lista =  videoListMap.get(videoLista.getAutor());
		lista.remove(videoLista);
	}
	
	//Devuelve todas las listas de video de un usuario concreto
	public List<VideoList> getVideoListAutor(String autor){
		if(videoListMap.containsKey(autor))
			return videoListMap.get(autor);
		return new ArrayList<VideoList>();	
	}
	
	//Comprueba la existencia de una lista de video 
	public boolean existeVideoList(String nombre, String autor) {
		return Stream.ofNullable(videoListMap.get(autor)).
				flatMap(Collection::stream)
				.anyMatch(vL -> vL.getNombre().equals(nombre));
	}

	//Devuelve una lista de video dado el nombre y el usuario autor.
	public VideoList getVideoList(String nombre, String autor) {
		return Stream.ofNullable(videoListMap.get(autor))
			    .flatMap(Collection::stream)
			    .filter(vl -> vl.getNombre().equals(nombre))
				.findFirst()
				.orElse(null);
	}
	
	//Comprobar si existe, al menos, una lista de video que contenga el video dado
	public boolean checkVideoInVideoList(String autor, Video v) {
		return Stream.ofNullable(videoListMap.get(autor))
				.flatMap(Collection::stream)
				.anyMatch(vL -> vL.contieneVideo(v));
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
}
