package modelo;


import java.util.ArrayList;
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
	
	public Video getVideo(int codigo) {
		for (Video p : videos.values()) {
			if (p.getCodigo()==codigo) return p;
		}
		return null;
	}
	public Video getVideo(String titulo) {
		return videos.get(titulo); 
	}
	
	public void addVideo(Video vid) {
		videos.put(vid.getTitulo(),vid);
		for(Etiqueta e: vid.getEtiquetas()) {
			catalogoEtiquetas.addEtiqueta(e);
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
	public void removeVideo(Video pro) {
		videos.remove(pro.getTitulo());
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
	
	
	public List<Video> buscarVideos(String subCadena, List<String> etiquetasSeleccionadas){
		List<Video> resultados = new ArrayList<Video>();
		if(etiquetasSeleccionadas.isEmpty()) {
			videos.forEach((t,v) ->{
				if(t.contains(subCadena))
					resultados.add(v);
			});
		}
		else{
			resultados.addAll(buscarVideosEtiquetados(subCadena, etiquetasSeleccionadas));
		}
		return resultados;
	}
	
	public List<Video> buscarVideosEtiquetados(String subCadena, List<String> etiquetasSeleccionadas){
		List<Video> resultadosEtiquetados = new ArrayList<Video>();
		for(String etiqueta : etiquetasSeleccionadas){
			List<Video> posiblesVideos = videosIndexadosEtiquetas.get(etiqueta);
			for(Video v: posiblesVideos){
				if(v.getTitulo().contains(subCadena))
					if(!resultadosEtiquetados.contains(v))
						resultadosEtiquetados.add(v);
			}
		}
		return resultadosEtiquetados;
	}
}
