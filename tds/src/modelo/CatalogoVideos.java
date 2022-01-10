package modelo;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorVideoDAO;


/* El cat�logo mantiene los objetos en memoria, en una tabla hash
 * para mejorar el rendimiento. Esto no se podr�a hacer en una base de
 * datos con un n�mero grande de objetos. En ese caso se consultar�a
 * directamente la base de datos
 */
public class CatalogoVideos {
	private Map<String,Video> videos; 
	private Map<String,List<Video>> videosIndexadosEtiquetas;
	private static CatalogoVideos unicaInstancia = new CatalogoVideos();
	private CatalogoEtiquetas catalogoEtiquetas = CatalogoEtiquetas.getUnicaInstancia();
	
	private FactoriaDAO dao;
	private IAdaptadorVideoDAO adaptadorVideo;
	
	private CatalogoVideos() {
		try {
  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
  			adaptadorVideo = dao.getVideoDAO();
  			videos = new HashMap<String,Video>();
  			videosIndexadosEtiquetas =  new HashMap<String, List<Video>>();
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
	
	//Devuelve un video concreto
	public Video getVideo(String titulo) {
		return videos.get(titulo); 
	}
	
	//A�ade un video al catalogo
	public void addVideo(Video vid) {
		videos.put(vid.getTitulo(),vid);
		for(Etiqueta e: vid.getEtiquetas()) {
			if(videosIndexadosEtiquetas.containsKey(e.getNombre())) {
				List<Video> videosEtiquetados = videosIndexadosEtiquetas.get(e.getNombre());
				videosEtiquetados.add(vid);
			}
			else {
				List<Video> videosEtiquetados = new ArrayList<Video>();
				videosEtiquetados.add(vid);
				videosIndexadosEtiquetas.put(e.getNombre(), videosEtiquetados);
			}
		}
	}

	//Buscamos videos por nombre y etiquetas (o solamente nombre)
	public List<Video> buscarVideos(String subCadena, List<Etiqueta> etiquetasSeleccionadas, FiltroVideo filtro){
		List<Video> resultados = new ArrayList<Video>();
		if(etiquetasSeleccionadas.isEmpty()) {
			videos.forEach((t,v) ->{
				if(t.contains(subCadena) && filtro.esVideoOK(v))
					resultados.add(v);
			});
		}
		else{
			resultados.addAll(buscarVideosEtiquetados(subCadena, etiquetasSeleccionadas, filtro));
		}
		return resultados;
	}
	
	//Buscamos videos por etiquetas. Usado cuando en la busqueda se selecciona, al menos, una etiqueta
	public List<Video> buscarVideosEtiquetados(String subCadena, List<Etiqueta> etiquetasSeleccionadas, FiltroVideo filtro){
		List<Video> resultadosEtiquetados = new ArrayList<Video>();
		for(Etiqueta etiqueta : etiquetasSeleccionadas){
			List<Video> posiblesVideos = videosIndexadosEtiquetas.get(etiqueta.getNombre());
			for(Video v: posiblesVideos){
				if(v.getTitulo().contains(subCadena))
					if(!resultadosEtiquetados.contains(v) && filtro.esVideoOK(v))
						resultadosEtiquetados.add(v);
			}
		}
		List<Video> resultadosTodasEtiquetas = new ArrayList<Video>();
		for(Video v: resultadosEtiquetados) {
			boolean tieneTodasEtiquetas = true;
			for(Etiqueta e: etiquetasSeleccionadas) {
				if(!v.contieneEtiqueta(e)) {
					tieneTodasEtiquetas = false;
					break;
				}
			}
			if(tieneTodasEtiquetas)
				resultadosTodasEtiquetas.add(v);
		}
		return resultadosTodasEtiquetas;
	}
	
	//Devuelve la lista de los 10 videos mas vistos
	public List<Video> getVideosMasVistos(){
		List<Video> topTen = new ArrayList<Video>();
		
		Map<Video,Integer> candidatos = new HashMap<Video,Integer>();
		for(Video v: videos.values()) {
			candidatos.put(v, v.getReproducciones());
		}
		int max = 0;
		Video quitar;
		while(!candidatos.isEmpty() && topTen.size() < 10) {
			max = Collections.max(candidatos.values());
			quitar = null;
			for(Video v: candidatos.keySet()) {
				if(topTen.size() >= 10)
					break;
				else if(v.getReproducciones() == max) {
					topTen.add(v);
					quitar = v;
					break;
				}
			}
			candidatos.remove(quitar);
		}
		return topTen;
	}
	
	/*Recupera todos los Videos para trabajar con ellos en memoria*/
	private void cargarCatalogo() throws DAOException {
		 List<Video> VideosBD = adaptadorVideo.recuperarTodosVideos();
		 for (Video pro: VideosBD) 
			     videos.put(pro.getTitulo(),pro);
		 
		 for(Etiqueta e: catalogoEtiquetas.getEtiquetas()) {
			 List<Video> videosEtiquetados = new ArrayList<Video>();
			 for (Video pro: VideosBD) {
			     if(pro.contieneEtiqueta(e)) {
			    	 videosEtiquetados.add(pro);
			     }
			 }
			 videosIndexadosEtiquetas.put(e.getNombre(), videosEtiquetados);
		 }
	}
}
