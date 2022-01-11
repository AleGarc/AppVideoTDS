package modelo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	
	//Devuelve un video concreto
	public Video getVideo(String titulo) {
		return videos.get(titulo); 
	}
	
	//Añade un video al catalogo
	public void addVideo(Video vid) {
		videos.put(vid.getTitulo(),vid);
	}
	
	//Elimina un video del catalogo
	public void removeVideo(Video vid) {
		videos.remove(vid.getTitulo());
	}
	

	//Buscamos videos por nombre y etiquetas (o solamente nombre)
	public List<Video> buscarVideos(String subCadena, List<Etiqueta> etiquetasSeleccionadas, FiltroVideo filtro){
		List<Video> resultados = new ArrayList<Video>();
		videos.forEach((t,v) ->{
				if(t.contains(subCadena) && filtro.esVideoOK(v)) {
					boolean contieneTodasEtiquetas = true;
					for(Etiqueta e: etiquetasSeleccionadas){
						if(!v.contieneEtiqueta(e)) {
							contieneTodasEtiquetas = false;
							break;
						}	
					}
					if(contieneTodasEtiquetas)
						resultados.add(v);
				}
			});
		
		return resultados;
	}
	
	//Devuelve la lista de los 10 videos mas vistos
	public List<Video> getVideosMasVistos(){
		List<Video> topTen = (List<Video>) videos.values().stream()
				.sorted((v1,v2) -> (int) v2.getReproducciones() - v1.getReproducciones()).collect(Collectors.toList());
		return topTen;
	}
	
	/*Recupera todos los Videos para trabajar con ellos en memoria*/
	private void cargarCatalogo() throws DAOException {
		 List<Video> VideosBD = adaptadorVideo.recuperarTodosVideos();
		 for (Video pro: VideosBD) 
			     videos.put(pro.getTitulo(),pro);
		 
	}
}
