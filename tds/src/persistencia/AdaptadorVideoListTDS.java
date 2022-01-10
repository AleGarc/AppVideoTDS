package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;
import modelo.VideoList;

public class AdaptadorVideoListTDS implements IAdaptadorVideoListDAO {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorVideoListTDS unicaInstancia = null;

	public static AdaptadorVideoListTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null) {
			return new AdaptadorVideoListTDS();
		} else
			return unicaInstancia;
	}

	private AdaptadorVideoListTDS() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	//Registra una lista de video asignandole un código en el proceso
	public void registrarVideoList(VideoList videoList) {
		Entidad eVideoList = null;
		// Si la entidad esta registrada no la registra de nuevo
		try {
			eVideoList = servPersistencia.recuperarEntidad(videoList.getCodigo());
		} catch (NullPointerException e) {}
		if (eVideoList != null) return;
		
		// Los Videos ya los tenemos registrados pero hace falta registrar la coleccion de videos por su cadena de codigos
		AdaptadorVideoTDS adaptadorVideo = AdaptadorVideoTDS.getUnicaInstancia();
		
		// crear entidad VideoList
		eVideoList = new Entidad();
		eVideoList.setNombre("videoList");
		eVideoList.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("nombre", videoList.getNombre()),
				new Propiedad("autor", videoList.getAutor()),
				new Propiedad("videos", adaptadorVideo.obtenerCodigosListaVideos(videoList.getListaVideos())))));
		
		// registrar entidad VideoList
		eVideoList = servPersistencia.registrarEntidad(eVideoList);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		videoList.setCodigo(eVideoList.getId());  
	}
	
	
	//Borrar una lista de video de la base de datos
	public void borrarVideoList(VideoList videoList) {
		Entidad eVideoList = servPersistencia.recuperarEntidad(videoList.getCodigo());
		servPersistencia.borrarEntidad(eVideoList);
	}

	//Modificar una lista de video guardada en la base de datos
	public void modificarVideoList(VideoList videoList) {
		AdaptadorVideoTDS adaptadorVideo = AdaptadorVideoTDS.getUnicaInstancia();
	
		Entidad eCliente = servPersistencia.recuperarEntidad(videoList.getCodigo());

		for (Propiedad prop : eCliente.getPropiedades()) {
			if (prop.getNombre().equals("codigo")) {
				prop.setValor(String.valueOf(videoList.getCodigo()));
			} else if (prop.getNombre().equals("nombre")) {
				prop.setValor(videoList.getNombre());
			} else if (prop.getNombre().equals("autor")) {
				prop.setValor(videoList.getAutor());
			} else if (prop.getNombre().equals("videos")) {
				String ventas = adaptadorVideo.obtenerCodigosListaVideos(videoList.getListaVideos());
				prop.setValor(ventas);
			}
			servPersistencia.modificarPropiedad(prop);
		}
	}

	//Recuperar una lista de video de la base de datos dado su codigo
	public VideoList recuperarVideoList(int codigo) {
		Entidad eVideoList;
		String nombre;
		String autor;
		eVideoList = servPersistencia.recuperarEntidad(codigo);
		nombre = servPersistencia.recuperarPropiedadEntidad(eVideoList, "nombre");
		autor = servPersistencia.recuperarPropiedadEntidad(eVideoList, "autor");
		
		//Recuperar las entidades que son objetos
		AdaptadorVideoTDS adaptadorVideo = AdaptadorVideoTDS.getUnicaInstancia();
		String listaCodigosVideos = servPersistencia.recuperarPropiedadEntidad(eVideoList, "videos");
		
		VideoList videoList = new VideoList(nombre, autor, adaptadorVideo.obtenerVideosDesdeCodigos(listaCodigosVideos));
		videoList.setCodigo(codigo);
		return videoList;
	}

	//Recuperar todas las listas de video de la base de datos
	public List<VideoList> recuperarTodosVideoLists() {
		List<VideoList> videoLists = new LinkedList<VideoList>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("videoList");

		for (Entidad eVideoList : entidades) {
			videoLists.add(recuperarVideoList(eVideoList.getId()));
		}
		return videoLists;
	}
	
	

}
