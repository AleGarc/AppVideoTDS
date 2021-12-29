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
import modelo.Usuario;
import modelo.Video;
import modelo.Venta;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorClienteDAO;
import persistencia.IAdaptadorVideoDAO;
import pulsador.IEncendidoListener;

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

	private Venta ventaActual;
	
	private CargadorVideos cargadorVideos;

	private ControladorTienda() {
		inicializarAdaptadores(); // debe ser la primera linea para evitar error
								  // de sincronización
		inicializarCatalogos();
	}

	public static ControladorTienda getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new ControladorTienda();
		return unicaInstancia;
	}

	public boolean registrarCliente(String nombre_completo, String fecha_nacimiento, String email, String usuario, String password) {
		// No se controla que existan dnis duplicados
		Usuario user = new Usuario(nombre_completo, fecha_nacimiento, email, usuario, password);
		adaptadorCliente.registrarCliente(user);
		catalogoUsuarios.addCliente(user);
		return true;		//CAMBIAr
	}

	public void registrarVideo(String titulo, String url, List<Etiqueta> etiquetas) {
		// No se controla que el valor del string precio sea un double
		Video video = new Video(titulo, url, etiquetas);
		adaptadorVideo.registrarVideo(video);
		catalogoVideos.addVideo(video);
	}
	
	public List<Etiqueta> stringToEtiquetas(List<String> etiquetasString){
		ArrayList<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
		for(String e: etiquetasString) {
			etiquetas.add(new Etiqueta(e));
		}
		return etiquetas;
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
			List<String> etiquetas = new ArrayList<String>();
			for(umu.tds.componente.Video video: listaVideos.getVideo()) {
				etiquetas.addAll(video.getEtiqueta());
				registrarVideo(video.getTitulo(), video.getURL(), stringToEtiquetas(etiquetas));
				etiquetas.clear();
			}
		}
		
	}
}
