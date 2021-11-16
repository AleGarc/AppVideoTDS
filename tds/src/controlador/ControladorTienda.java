package controlador;

import java.util.Date;
import java.util.List;

import modelo.CatalogoUsuarios;
import modelo.CatalogoVideos;
import modelo.CatalogoVentas;
import modelo.Usuario;
import modelo.Video;
import modelo.Venta;
import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorClienteDAO;
import persistencia.IAdaptadorVideoDAO;
import persistencia.IAdaptadorVentaDAO;

public class ControladorTienda {

	private static ControladorTienda unicaInstancia;

	private IAdaptadorClienteDAO adaptadorCliente;
	private IAdaptadorVideoDAO adaptadorVideo;
	private IAdaptadorVentaDAO adaptadorVenta;

	private CatalogoUsuarios catalogoUsuarios;
	private CatalogoVentas catalogoVentas;
	private CatalogoVideos catalogoVideos;

	private Venta ventaActual;

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

	public void registrarVideo(String titulo, String url) {
		// No se controla que el valor del string precio sea un double
		Video video = new Video(titulo, url);
		adaptadorVideo.registrarVideo(video);

		catalogoVideos.addVideo(video);
	}

	public void crearVenta() {
		ventaActual = new Venta();
	}
	
	public void anadirLineaVenta(int unidades, Video video) {
		ventaActual.addLineaVenta(unidades, video);
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
	}

	private void inicializarCatalogos() {
		catalogoUsuarios = CatalogoUsuarios.getUnicaInstancia();
		catalogoVentas = CatalogoVentas.getUnicaInstancia();
		catalogoVideos = CatalogoVideos.getUnicaInstancia();
	}

	public boolean existeCliente(String dni) {
		return CatalogoUsuarios.getUnicaInstancia().getCliente(dni) != null;
	}
	
	public boolean autenticarUsuario(String user, String password) {
		return CatalogoUsuarios.getUnicaInstancia().authUsuario(user, password);
	}

	public List<Video> getVideos() {
		return catalogoVideos.getVideos();
	}
}
