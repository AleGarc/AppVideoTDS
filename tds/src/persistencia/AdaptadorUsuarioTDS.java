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

import modelo.Usuario;
import modelo.Venta;
import modelo.Video;
import modelo.VideoList;

//Usa un pool para evitar problemas doble referencia con ventas
public class AdaptadorUsuarioTDS implements IAdaptadorUsuarioDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorUsuarioTDS unicaInstancia = null;

	public static AdaptadorUsuarioTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			return new AdaptadorUsuarioTDS();
		else
			return unicaInstancia;
	}

	private AdaptadorUsuarioTDS() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia(); 
	}

	/* cuando se registra un cliente se le asigna un identificador único */
	public void registrarUsuario(Usuario usuario) {
		Entidad eUsuario = null;
		
		// Si la entidad está registrada no la registra de nuevo
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		} catch (NullPointerException e) {}
		if (eUsuario != null) return;

		AdaptadorVideoTDS adaptadorVideo = AdaptadorVideoTDS.getUnicaInstancia();
		
		// crear entidad Usuario
		eUsuario = new Entidad();
		eUsuario.setNombre("usuario");
		eUsuario.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("nombre_completo", usuario.getNombre_completo()), 
						new Propiedad("fecha_nacimiento", usuario.getFecha_nacimiento()),
						new Propiedad("email", usuario.getEmail()),
						new Propiedad("usuario", usuario.getUsuario()),
						new Propiedad("password", usuario.getPassword()),
						new Propiedad("premium", String.valueOf(usuario.esPremium())),
						new Propiedad("recientes",adaptadorVideo.obtenerCodigosListaVideos(usuario.getVideosRecientes())),
						new Propiedad("filtro", usuario.getFiltroString()))));
			
		/*eUsuario.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("dni", cliente.getDni()), new Propiedad("nombre", cliente.getNombre()),
						new Propiedad("ventas", obtenerCodigosVentas(cliente.getVentas())))));*/
		
		// registrar entidad cliente
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		usuario.setCodigo(eUsuario.getId()); 

	}

	public void borrarUsuario(Usuario usuario) {
		// No se comprueban restricciones de integridad con Venta
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		
		servPersistencia.borrarEntidad(eUsuario);
	}

	public void modificarUsuario(Usuario usuario) {

		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		AdaptadorVideoTDS adaptadorVideo = AdaptadorVideoTDS.getUnicaInstancia();
			
		for (Propiedad prop : eUsuario.getPropiedades()) {
			if (prop.getNombre().equals("codigo")) {
				prop.setValor(String.valueOf(usuario.getCodigo()));
			} else if (prop.getNombre().equals("nombre_completo")) {
				prop.setValor(usuario.getNombre_completo());
			} else if (prop.getNombre().equals("fecha_nacimiento")) {
				prop.setValor(usuario.getFecha_nacimiento());
			} else if (prop.getNombre().equals("email")) {
				prop.setValor(usuario.getEmail());
			} else if (prop.getNombre().equals("usuario")) {
				prop.setValor(usuario.getUsuario());
			} else if (prop.getNombre().equals("password")) {
				prop.setValor(usuario.getPassword());
			} else if (prop.getNombre().equals("premium")) {
				prop.setValor( String.valueOf(usuario.esPremium()));
			} else if (prop.getNombre().equals("recientes")) {
				prop.setValor( adaptadorVideo.obtenerCodigosListaVideos(usuario.getVideosRecientes()));
			}else if (prop.getNombre().equals("filtro")) {
				prop.setValor(usuario.getFiltroString());
			}
			
			servPersistencia.modificarPropiedad(prop);
		}
	}

	public Usuario recuperarUsuario(int codigo) {

		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Usuario) PoolDAO.getUnicaInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
		Entidad eUsuario;
		//List<Venta> ventas = new LinkedList<Venta>();
		String nombre_completo;
		String fecha_nacimiento;
		String email;
		String usuario;
		String password;
		boolean premium;
		String filtro;
		
		// recuperar entidad
		eUsuario = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		nombre_completo = servPersistencia.recuperarPropiedadEntidad(eUsuario, "nombre_completo");
		fecha_nacimiento = servPersistencia.recuperarPropiedadEntidad(eUsuario, "fecha_nacimiento");
		email = servPersistencia.recuperarPropiedadEntidad(eUsuario, "email");
		usuario = servPersistencia.recuperarPropiedadEntidad(eUsuario, "usuario");
		password = servPersistencia.recuperarPropiedadEntidad(eUsuario, "password");
		premium = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(eUsuario, "premium"));
		filtro = servPersistencia.recuperarPropiedadEntidad(eUsuario, "filtro");
		AdaptadorVideoTDS adaptadorVideo = AdaptadorVideoTDS.getUnicaInstancia();
		String listaCodigosVideos = servPersistencia.recuperarPropiedadEntidad(eUsuario, "recientes");
		
		Usuario user = new Usuario(nombre_completo, fecha_nacimiento, email, usuario, password, premium, 
				adaptadorVideo.obtenerVideosDesdeCodigos(listaCodigosVideos), filtro);
		user.setCodigo(codigo);

		// IMPORTANTE:añadir el cliente al pool antes de llamar a otros
		// adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(codigo, usuario);

		// recuperar propiedades que son objetos llamando a adaptadores
		// ventas
		/*ventas = obtenerVentasDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "ventas"));

		for (Venta v : ventas)
			cliente.addVenta(v);*/

		return user;
	}

	public List<Usuario> recuperarTodosUsuarios() {

		List<Entidad> eUsuarios = servPersistencia.recuperarEntidades("usuario");
		List<Usuario> usuarios = new LinkedList<Usuario>();

		for (Entidad eUsuario : eUsuarios) {
			usuarios.add(recuperarUsuario(eUsuario.getId()));
		}
		return usuarios;
	}


}
