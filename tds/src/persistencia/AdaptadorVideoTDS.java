package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;
import modelo.Video;

public class AdaptadorVideoTDS implements IAdaptadorVideoDAO {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorVideoTDS unicaInstancia = null;

	public static AdaptadorVideoTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null) {
			return new AdaptadorVideoTDS();
		} else
			return unicaInstancia;
	}

	private AdaptadorVideoTDS() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	//Registra un video asignandole un código en el proceso
	public void registrarVideo(Video video) {
		Entidad eVideo = null;
		// Si la entidad esta registrada no la registra de nuevo
		try {
			eVideo = servPersistencia.recuperarEntidad(video.getCodigo());
		} catch (NullPointerException e) {}
		if (eVideo != null) return;
		
		// Las Etiquetas ya están registradas en la base de datos, solo hace falta recuperar sus códigos
		AdaptadorEtiquetaTDS adaptadorEtiqueta = AdaptadorEtiquetaTDS.getUnicaInstancia();
		// crear entidad Video
		eVideo = new Entidad();
		eVideo.setNombre("video");
		eVideo.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("titulo", video.getTitulo()),
				new Propiedad("url", video.getUrl()), 
				new Propiedad("views", Integer.toString(video.getReproducciones())),
				new Propiedad("etiquetas", adaptadorEtiqueta.obtenerCodigosListaEtiquetas(video.getEtiquetas())))));
		
		// registrar entidad Video
		eVideo = servPersistencia.registrarEntidad(eVideo);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		video.setCodigo(eVideo.getId());  
	}
	
	
	//Borrar un video de la base de datos	
	public void borrarVideo(Video video) {
		Entidad eVideo = servPersistencia.recuperarEntidad(video.getCodigo());
		servPersistencia.borrarEntidad(eVideo);
	}

	//Modificar un video guardado en la base de datos
	public void modificarVideo(Video video) {
		Entidad eVideo = servPersistencia.recuperarEntidad(video.getCodigo());
		
		AdaptadorEtiquetaTDS adaptadorEtiqueta = AdaptadorEtiquetaTDS.getUnicaInstancia();
		
		for (Propiedad prop : eVideo.getPropiedades()) {
			if (prop.getNombre().equals("codigo")) {
				prop.setValor(String.valueOf(video.getCodigo()));
			} else if (prop.getNombre().equals("titulo")) {
				prop.setValor(video.getTitulo());
			} else if (prop.getNombre().equals("url")) {
				prop.setValor(video.getUrl());
			} else if (prop.getNombre().equals("views")) {
				prop.setValor(Integer.toString(video.getReproducciones()));
			} else if (prop.getNombre().equals("etiquetas")) {
				String etiquetas = adaptadorEtiqueta.obtenerCodigosListaEtiquetas(video.getEtiquetas());
				prop.setValor(etiquetas);
			}
			servPersistencia.modificarPropiedad(prop);
		}
	
	}

	//Recuperar un video de la base de datos dado su codigo
	public Video recuperarVideo(int codigo) {
		Entidad eVideo;
		String titulo; 
		String url;
		int reproducciones;
		eVideo = servPersistencia.recuperarEntidad(codigo);
		titulo = servPersistencia.recuperarPropiedadEntidad(eVideo, "titulo");
		url = servPersistencia.recuperarPropiedadEntidad(eVideo, "url");
		reproducciones = Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eVideo, "views"));
		
		//Recuperar las entidades que son objetos
		AdaptadorEtiquetaTDS adaptadorEtiqueta = AdaptadorEtiquetaTDS.getUnicaInstancia();
		String listaCodigosEtiquetas = servPersistencia.recuperarPropiedadEntidad(eVideo, "etiquetas");
		Video video = new Video(titulo, url, adaptadorEtiqueta.obtenerEtiquetasDesdeCodigos(listaCodigosEtiquetas), reproducciones);
		video.setCodigo(codigo);
		return video;
	}

	//Recuperar todos los usuarios de la base de datos
	public List<Video> recuperarTodosVideos() {
		List<Video> videos = new LinkedList<Video>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("video");

		for (Entidad eVideo : entidades) {
			videos.add(recuperarVideo(eVideo.getId()));
		}
		return videos;
	}

	//---------------------Funciones auxiliares----------------------------//
	public String obtenerCodigosListaVideos(List<Video> listaVideos) { 
		String codigoVideos = ""; 
		for (Video v: listaVideos)
			codigoVideos += v.getCodigo() + " ";
		return codigoVideos.trim();
	} 
	
	public List<Video> obtenerVideosDesdeCodigos(String listaCodigoVideos) { 
		List<Video> listaVideos = new LinkedList<Video>();
		StringTokenizer strTok = new StringTokenizer(listaCodigoVideos, " "); 
		while (strTok.hasMoreTokens()) { 
			listaVideos.add(recuperarVideo(Integer.valueOf((String)strTok.nextElement()))); 
		} 
		return listaVideos; 
	}
	
}
