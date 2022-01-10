package controlador;

import java.io.File;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.JFileChooser;

import modelo.CatalogoEtiquetas;
import modelo.CatalogoUsuarios;
import modelo.CatalogoVideos;
import modelo.Etiqueta;
import modelo.FactoriaFiltro;
import modelo.CatalogoVideoList;
import modelo.Usuario;
import modelo.Video;
import modelo.VideoList;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorUsuarioDAO;
import persistencia.IAdaptadorEtiquetaDAO;
import persistencia.IAdaptadorVideoDAO;
import persistencia.IAdaptadorVideoListDAO;
import pulsador.IEncendidoListener;
import tds.video.VideoWeb;
import umu.tds.componente.ArchivoVideosEvent;
import umu.tds.componente.CargadorVideos;
import umu.tds.componente.IArchivoVideosListener;
import umu.tds.componente.Videos;

public class ControladorAppVideo implements IEncendidoListener, IArchivoVideosListener{

	private static ControladorAppVideo unicaInstancia;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorVideoDAO adaptadorVideo;
	private IAdaptadorEtiquetaDAO adaptadorEtiqueta;
	private IAdaptadorVideoListDAO adaptadorVideoList;
	
	private CatalogoUsuarios catalogoUsuarios;
	private CatalogoVideos catalogoVideos;
	private CatalogoEtiquetas catalogoEtiquetas;
	private CatalogoVideoList catalogoVideoList;

	
	private CargadorVideos cargadorVideos;

	private static VideoWeb videoWeb;
	
	private Usuario usuario;
	
	private ControladorAppVideo() {
		inicializarAdaptadores(); // debe ser la primera linea para evitar error
								  // de sincronización
		inicializarCatalogos();
		
		videoWeb = new VideoWeb();
		
	}

	public static ControladorAppVideo getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new ControladorAppVideo();
		return unicaInstancia;
	}
	
	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factoria.getUsuarioDAO();
		adaptadorVideo = factoria.getVideoDAO();
		adaptadorEtiqueta = factoria.getEtiquetaDAO();
		adaptadorVideoList = factoria.getVideoListDAO();
		
		cargadorVideos = new CargadorVideos();
		cargadorVideos.addArchivoListener(this);
	}

	private void inicializarCatalogos() {
		catalogoUsuarios = CatalogoUsuarios.getUnicaInstancia();
		catalogoVideos = CatalogoVideos.getUnicaInstancia();
		catalogoEtiquetas = CatalogoEtiquetas.getUnicaInstancia();
		catalogoVideoList = CatalogoVideoList.getUnicaInstancia();
	}

	//---------------------VideoWeb---------------------//
	
	public VideoWeb getVideoWeb() {
		return videoWeb;
	}
	
	//---------------------Usuarios---------------------//
	
	//Registrar usuario
	public boolean registrarUsuario(String nombre_completo, String email, String usuario, String password, String fecha_nacimiento) {
		if(catalogoUsuarios.getUsuario(usuario) == null) {
			Usuario user = new Usuario(nombre_completo, fecha_nacimiento, email, usuario, password);
			adaptadorUsuario.registrarUsuario(user);
			catalogoUsuarios.addUsuario(user);
			return true;	
		}
		return false;
	}
	
	//Intentar logearnos con usuario y contraseña
	public boolean autenticarUsuario(String user, String password) {
		if(catalogoUsuarios.authUsuario(user, password)) {
			usuario = catalogoUsuarios.getUsuario(user);
			return true;
		}
		return false;
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	//Cambiar de premium a no premium y viceversa.
	public void cambiarRolUsuario(boolean b) {
		usuario.cambiarRol(b);
		adaptadorUsuario.modificarUsuario(usuario);
	}
	
	//Establecer el filtro para el usuario 
	public void setFiltroUsuario(String filtro) {
		usuario.setFiltro(filtro);
		adaptadorUsuario.modificarUsuario(usuario);
	}

	//Añadir un video a la lista de videos recientes del usuario
	public void addVideoReciente(Video v) {
		usuario.addVideoReciente(v);
		adaptadorUsuario.modificarUsuario(usuario);
		
	}
	

	
	//---------------------Videos---------------------//
	
	//Registrar video
	public void registrarVideo(String titulo, String url, List<Etiqueta> etiquetas) {
		if(catalogoVideos.getVideo(titulo) == null){
			Video video = new Video(titulo, url, etiquetas);
			adaptadorVideo.registrarVideo(video);
			catalogoVideos.addVideo(video);
		}
		
	}
	
	//Añadir una visualización a un video concreto
	public void addReproduccion(Video v) {
		v.addReproducciones();
		adaptadorVideo.modificarVideo(v);
	}
	
	//Buscamos videos por nombre y etiquetas (o solamente nombre)
	public List<Video> buscarVideos(String subCadena, List<String> etiquetas){
		return catalogoVideos.buscarVideos(subCadena,stringToEtiquetas(etiquetas), usuario.getFiltro());
	}
	
	//Devolvemos un video específico
	public Video getVideo(String titulo){
		return catalogoVideos.getVideo(titulo);
	}
	
	//Devolvemos la lista de videos completa
	public List<Video> getVideos() {
		return catalogoVideos.getVideos();
	}
	

	//---------------------Etiquetas---------------------//
	
	//Registramos una etiqueta
	public Etiqueta registrarEtiqueta(String etiqueta) {
		Etiqueta e = catalogoEtiquetas.getEtiqueta(etiqueta);
		if(e == null) {
			e = new Etiqueta(etiqueta);
			adaptadorEtiqueta.registrarEtiqueta(e);
			catalogoEtiquetas.addEtiqueta(e);
		}
		return e;
	}
	
	//Añadimos una etiqueta a un video concreto. Si la etiqueta no existe, se crea y almacena.
	public void addEtiquetaToVideo(String etiqueta, Video v) {
		v.addEtiqueta(registrarEtiqueta(etiqueta));
		adaptadorVideo.modificarVideo(v);
	}
	
	//Dada una lista de nombres de etiqueta, devolvemos una lista con las etiquetas correspondientes
	//Se crean las etiquetas si no existen.
	public List<Etiqueta> stringToEtiquetas(List<String> etiquetasString){
		ArrayList<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
		for(String e: etiquetasString) {
			Etiqueta nueva =registrarEtiqueta(e);
			etiquetas.add(nueva);
		}
		return etiquetas;
	}
	
	//Devolvemos la lista completa de etiquetas disponibles
	public List<String> getEtiquetasDisponibles(){
		return catalogoEtiquetas.getEtiquetasString();
	}
	
	
	//---------------------VideoLists---------------------//
	
	
	//Registramos una nueva lista de videos
	public VideoList registrarVideoLista(String nombreVideoLista) {
		VideoList lista = catalogoVideoList.getVideoList(nombreVideoLista, usuario.getUsuario());
		if(lista == null) {
			lista = new VideoList(nombreVideoLista, usuario.getUsuario());
			adaptadorVideoList.registrarVideoList(lista);
			catalogoVideoList.addVideoList(lista);
		}
		return lista;
	}
	
	//Devolvemos las listas de videos creadas por el usuario.
	public List<VideoList> getListasAutor(){
		return catalogoVideoList.getVideoListAutor(usuario.getUsuario());
	}
	
	//Devolvemos una lista de video concreta
	public VideoList getListaVideo(String nombreVideoLista) {
		return catalogoVideoList.getVideoList(nombreVideoLista, usuario.getUsuario());
	}
	
	//Borramos la lista de video
	public void borrarListaVideo(VideoList videoLista) {
		catalogoVideoList.removeVideoList(videoLista);
		adaptadorVideoList.borrarVideoList(videoLista);
	}
	
	//Añadimos un video a una lista de videos concreta.
	public void addVideoToVideoList(VideoList videoLista, Video v) {
		videoLista.addVideo(v);
		adaptadorVideoList.modificarVideoList(videoLista);
	}

	//Eliminamos un video de una lista de videos concreta.
	public void removeVideoFromLista(VideoList videoLista, Video v) {
		videoLista.removeVideo(v);
		adaptadorVideoList.modificarVideoList(videoLista);
	}
	
	//Vemos si exista la lista de videos
	public boolean checkVideoListExiste(String nombreVideoLista){
		return catalogoVideoList.existeVideoList(nombreVideoLista, usuario.getUsuario());
	}

	//Obtenemos el top 10 de videos mas vistos
	public VideoList getVideosMasVistos() {
		VideoList topTen = new VideoList("topTen","controlador",catalogoVideos.getVideosMasVistos());
		return topTen;
	}
	
	//Vemos si el usuario tiene, al menos, una lista en la que se encuentre el video dado.
	public boolean checkVideoInVideoList(Video v) {
		return catalogoVideoList.checkVideoInVideoList(usuario.getUsuario(), v);
	}
	
	//---------------------Filtros---------------------//
	
	public List<String> getFiltrosDisponibles(){
		return FactoriaFiltro.getFiltrosDisponibles();
	}
	
	
	//---------------------Componente Luz---------------------//
	
	//Listener del componente luz, si se pulsa se inicia el proceso de seleccion de archivo para cargar videos
	@Override
	public void enteradoCambioEncendido(EventObject arg0) {
	
			JFileChooser eleccionFichero =  new JFileChooser();
			int returnVal = eleccionFichero.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
			        File file = eleccionFichero.getSelectedFile();
			        cargarVideos(file);
			        
			       
			} else {
				 System.out.println("Cancelado por el usuario");
			}
		
		
		
		
	}
	
	//---------------------CargadorVideos---------------------//
	private void cargarVideos(File xml) {
		cargadorVideos.setArchivoVideos(xml);
	}

	//Listener del componente cargadorVideos, cuando se recibe la lista de videos, se transforma a los videos utilizados por appVideo
	@Override
	public void enteradoNuevoArchivo(EventObject arg0) {
		if(arg0 instanceof ArchivoVideosEvent){
			ArchivoVideosEvent evento = (ArchivoVideosEvent)arg0;
			Videos listaVideos = evento.getVideos();
			for(umu.tds.componente.Video video: listaVideos.getVideo()) {
				registrarVideo(video.getTitulo(), video.getURL(), stringToEtiquetas(video.getEtiqueta()));
			}
			inicializarCatalogos();
		}
		
	}
	

	
	
}
