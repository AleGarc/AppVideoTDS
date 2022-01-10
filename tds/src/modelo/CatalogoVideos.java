package modelo;


import java.util.ArrayList;
import java.util.Collections;
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
	
	//Devuelve un video concreto
	public Video getVideo(String titulo) {
		return videos.get(titulo); 
	}
	
	//Añade un video al catalogo
	public void addVideo(Video vid) {
		videos.put(vid.getTitulo(),vid);
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
		 
	}
}
