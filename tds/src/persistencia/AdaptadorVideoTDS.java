package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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

	/* cuando se registra un Video se le asigna un identificador unico */
	public void registrarVideo(Video video) {
		Entidad eVideo = null;
		// Si la entidad está registrada no la registra de nuevo
		boolean existe = true; 
		try {
			eVideo = servPersistencia.recuperarEntidad(video.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) return;
		
		// crear entidad Video
		eVideo = new Entidad();
		eVideo.setNombre("video");
		eVideo.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("titulo", video.getTitulo()),
				new Propiedad("url", video.getUrl()),
				new Propiedad("etiquetas", video.getTitulo()))));
		
		// registrar entidad Video
		eVideo = servPersistencia.registrarEntidad(eVideo);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		video.setCodigo(eVideo.getId());  
	}

	public void borrarVideo(Video video) {
		// No se comprueba integridad con lineas de venta
		Entidad eVideo = servPersistencia.recuperarEntidad(video.getCodigo());
		servPersistencia.borrarEntidad(eVideo);
	}

	public void modificarVideo(Video video) {
		Entidad eVideo = servPersistencia.recuperarEntidad(video.getCodigo());

		servPersistencia.eliminarPropiedadEntidad(eVideo, "titulo");
		servPersistencia.anadirPropiedadEntidad(eVideo, "titulo", String.valueOf(video.getTitulo()));
		servPersistencia.eliminarPropiedadEntidad(eVideo, "url");
		servPersistencia.anadirPropiedadEntidad(eVideo, "url", video.getUrl());
		servPersistencia.eliminarPropiedadEntidad(eVideo, "etiquetas");
		servPersistencia.anadirPropiedadEntidad(eVideo, "etiquetas", video.getTitulo());
	}

	public Video recuperarVideo(int codigo) {
		Entidad eVideo;
		String titulo;
		String url;

		eVideo = servPersistencia.recuperarEntidad(codigo);
		titulo = servPersistencia.recuperarPropiedadEntidad(eVideo, "titulo");
		url = servPersistencia.recuperarPropiedadEntidad(eVideo, "url");
		
		Video video = new Video(titulo, url);
		video.setCodigo(codigo);
		return video;
	}

	public List<Video> recuperarTodosVideos() {
		List<Video> videos = new LinkedList<Video>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("Video");

		for (Entidad eVideo : entidades) {
			videos.add(recuperarVideo(eVideo.getId()));
		}
		return videos;
	}

}
