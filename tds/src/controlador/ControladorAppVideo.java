package controlador;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;
import java.util.List;

import javax.swing.JFileChooser;

import modelo.CatalogoEtiquetas;
import modelo.CatalogoUsuarios;
import modelo.CatalogoVideos;
import modelo.Etiqueta;
import modelo.CatalogoVentas;
import modelo.CatalogoVideoList;
import modelo.Usuario;
import modelo.Video;
import modelo.VideoList;
import modelo.Venta;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorUsuarioDAO;
import persistencia.IAdaptadorEtiquetaDAO;
import persistencia.IAdaptadorVideoDAO;
import pulsador.IEncendidoListener;
import tds.video.VideoWeb;
import persistencia.IAdaptadorVentaDAO;
import umu.tds.componente.ArchivoVideosEvent;
import umu.tds.componente.CargadorVideos;
import umu.tds.componente.IArchivoVideosListener;
import umu.tds.componente.Videos;

public class ControladorAppVideo implements IEncendidoListener, IArchivoVideosListener{

	private static ControladorAppVideo unicaInstancia;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorVideoDAO adaptadorVideo;
	private IAdaptadorVentaDAO adaptadorVenta;

	private CatalogoUsuarios catalogoUsuarios;
	private CatalogoVentas catalogoVentas;
	private CatalogoVideos catalogoVideos;
	private CatalogoEtiquetas catalogoEtiquetas;
	private CatalogoVideoList catalogoVideoList;

	private Venta ventaActual;
	
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

	public boolean registrarUsuario(String nombre_completo, String email, String usuario, String password, String fecha_nacimiento) {
		if(catalogoUsuarios.getUsuario(usuario) == null) {
			Usuario user = new Usuario(nombre_completo, fecha_nacimiento, email, usuario, password);
			adaptadorUsuario.registrarUsuario(user);
			catalogoUsuarios.addUsuario(user);
			return true;	
		}
		return false;
	}

	public void registrarVideo(String titulo, String url, List<Etiqueta> etiquetas) {
		if(catalogoVideos.getVideo(titulo) == null){
			Video video = new Video(titulo, url, etiquetas);
			adaptadorVideo.registrarVideo(video);
			catalogoVideos.addVideo(video);
			for(Etiqueta e: etiquetas){
				registrarEtiqueta(e);
			}
		}
		
	}
	
	public void addEtiquetaToVideo(String etiqueta, Video v) {
		Etiqueta e = catalogoEtiquetas.existeEtiqueta(etiqueta);
		if(e == null)
			e = catalogoEtiquetas.nuevaEtiqueta(etiqueta);
		catalogoVideos.addEtiquetaToVideo(e, v);
	}
	
	public void addReproduccion(Video v) {
		catalogoVideos.addReproduccion(v);
	}
	
	public void registrarEtiqueta(Etiqueta etiqueta) {
		catalogoEtiquetas.addEtiqueta(etiqueta);
	}
	
	public List<Etiqueta> stringToEtiquetas(List<String> etiquetasString){
		ArrayList<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
		for(String e: etiquetasString) {
			etiquetas.add(new Etiqueta(e));
		}
		return etiquetas;
	}
	
	public List<Video> buscarVideos(String subCadena, List<String> etiquetas){
		return catalogoVideos.buscarVideos(subCadena,etiquetas);
	}
	
	public Video getVideo(String titulo){
		return catalogoVideos.getVideo(titulo);
	}

	/*public void registrarVenta(String dni, Date fecha) {
		Usuario usuario = catalogoUsuarios.getUsuario(dni);
		ventaActual.setUsuario(usuario);
		ventaActual.setFecha(fecha);

		adaptadorVenta.registrarVenta(ventaActual);

		catalogoVentas.addVenta(ventaActual);

		adaptadorUsuario.modificarUsuario(usuario);
	}*/

	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factoria.getUsuarioDAO();
		adaptadorVideo = factoria.getVideoDAO();
		adaptadorVenta = factoria.getVentaDAO();
		
		cargadorVideos = new CargadorVideos();
		cargadorVideos.addArchivoListener(this);
	}

	private void inicializarCatalogos() {
		catalogoUsuarios = CatalogoUsuarios.getUnicaInstancia();
		catalogoVentas = CatalogoVentas.getUnicaInstancia();
		catalogoVideos = CatalogoVideos.getUnicaInstancia();
		catalogoEtiquetas = CatalogoEtiquetas.getUnicaInstancia();
		catalogoVideoList = CatalogoVideoList.getUnicaInstancia();
	}

	public boolean existeUsuario(String dni) {
		return catalogoUsuarios.getUsuario(dni) != null;
	}
	
	public boolean autenticarUsuario(String user, String password) {
		if(catalogoUsuarios.authUsuario(user, password)) {
			usuario = catalogoUsuarios.getUsuario(user);
			return true;
		}
		return false;
	}

	public List<Video> getVideos() {
		return catalogoVideos.getVideos();
	}
	
	public List<String> getEtiquetasDisponibles(){
		return catalogoEtiquetas.getEtiquetasString();
	}

	@Override
	public void enteradoCambioEncendido(EventObject arg0) {
	
			JFileChooser eleccionFichero =  new JFileChooser();
			int returnVal = eleccionFichero.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
			        File file = eleccionFichero.getSelectedFile();
			        //This is where a real application would open the file.
			        cargarVideos(file);
			        
			       
			} else {
				 System.out.println("Open command cancelled by user.");
			}
		
		
		// TODO Auto-generated method stub
		
	}
	
	private void cargarVideos(File xml) {
		cargadorVideos.setArchivoVideos(xml);
	}

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
	
	public VideoWeb getVideoWeb() {
		return videoWeb;
	}
	
	public List<VideoList> getListasAutor(Usuario usuario){
		return catalogoVideoList.getVideoListAutor(usuario.getUsuario());
	}
	
	public VideoList getListaVideo(String nombre, String autor) {
		return catalogoVideoList.getListaVideo(nombre, autor);
	}
	
	public VideoList crearListaVideo(String nombre, String autor) {
		VideoList videoLista = new VideoList(nombre, autor);
		catalogoVideoList.addVideoList(videoLista);
		return videoLista;
	}
	
	public void borrarListaVideo(VideoList videoLista) {
		catalogoVideoList.removeVideoList(videoLista);
	}
	
	public void actualizarLista(VideoList videoLista) {
		catalogoVideoList.actualizarVideoList(videoLista);
	}

	public void removeVideoFromLista(String nombre,String autor, Video v) {
		catalogoVideoList.removeVideoFromList(nombre, autor, v);
	}
	
	public boolean checkVideoListExiste(String nombre, String autor){
		return catalogoVideoList.existeVideoList(nombre, autor);
	}

	public Usuario getUsuario() {
		return usuario;
	}
	
	public void cambiarRolUsuario(Usuario usuario, boolean b) {
		catalogoUsuarios.cambiarRol(usuario, b);
		adaptadorUsuario.modificarUsuario(usuario);
	}
	
	public VideoList getVideosMasVistos() {
		return catalogoVideoList.getVideosMasVistos(catalogoVideos.getVideosMasVistos());
	}
	
	public List<Video> getVideosRecientesUsuario(){
		return usuario.getVideosRecientes();
	}
	
	public void addVideoReciente(Usuario user, Video v) {
		if(user.equals(usuario))
			user.addVideoReciente(v);
		adaptadorUsuario.modificarUsuario(usuario);
		
	}
}
