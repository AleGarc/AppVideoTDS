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
import persistencia.IAdaptadorClienteDAO;
import persistencia.IAdaptadorEtiquetaDAO;
import persistencia.IAdaptadorVideoDAO;
import pulsador.IEncendidoListener;
import tds.video.VideoWeb;
import persistencia.IAdaptadorVentaDAO;
import umu.tds.componente.ArchivoVideosEvent;
import umu.tds.componente.CargadorVideos;
import umu.tds.componente.IArchivoVideosListener;
import umu.tds.componente.Videos;

public class ControladorTienda implements IEncendidoListener, IArchivoVideosListener{

	private static ControladorTienda unicaInstancia;
	private IAdaptadorClienteDAO adaptadorCliente;
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
	
	private ControladorTienda() {
		inicializarAdaptadores(); // debe ser la primera linea para evitar error
								  // de sincronización
		inicializarCatalogos();
		
		videoWeb = new VideoWeb();
	}

	public static ControladorTienda getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new ControladorTienda();
		return unicaInstancia;
	}

	public boolean registrarCliente(String nombre_completo, String email, String usuario, String password, String fecha_nacimiento) {
		if(catalogoUsuarios.getCliente(usuario) == null) {
			Usuario user = new Usuario(nombre_completo, fecha_nacimiento, email, usuario, password);
			adaptadorCliente.registrarCliente(user);
			catalogoUsuarios.addCliente(user);
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

	public void registrarVenta(String dni, Date fecha) {
		Usuario usuario = catalogoUsuarios.getCliente(dni);
		ventaActual.setCliente(usuario);
		ventaActual.setFecha(fecha);

		adaptadorVenta.registrarVenta(ventaActual);

		catalogoVentas.addVenta(ventaActual);

		adaptadorCliente.modificarCliente(usuario);
	}

	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorCliente = factoria.getClienteDAO();
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

	public boolean existeCliente(String dni) {
		return catalogoUsuarios.getCliente(dni) != null;
	}
	
	public boolean autenticarUsuario(String user, String password) {
		return catalogoUsuarios.authUsuario(user, password);
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
		}
		
	}
	
	public VideoWeb getVideoWeb() {
		return videoWeb;
	}
	
	public List<VideoList> getListasAutor(String autor){
		return catalogoVideoList.getVideoListAutor(autor);
	}
	
	public void crearListaVideo(String nombre, String autor) {
		VideoList videoLista = new VideoList(nombre, autor);
		catalogoVideoList.addVideoList(videoLista);
	}
	
	public void borrarListaVideo(String nombre, String autor) {
		catalogoVideoList.removeVideoList(nombre, autor);
	}
	
	public void addVideoToLista(String nombre,String autor, Video v) {
		catalogoVideoList.addVideoToList(nombre, autor, v);
	}

	public void removeVideoFromLista(String nombre,String autor, Video v) {
		catalogoVideoList.removeVideoFromList(nombre, autor, v);
	}
	
	public boolean checkVideoListExiste(String nombre, String autor){
		return catalogoVideoList.existeVideoList(nombre, autor);
	}

}
