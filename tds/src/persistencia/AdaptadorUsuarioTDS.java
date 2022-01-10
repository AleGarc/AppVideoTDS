package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import beans.Entidad;
import beans.Propiedad;

import modelo.Usuario;

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

	//Registra un usuario asignandole un código en el proceso
	public void registrarUsuario(Usuario usuario) {
		Entidad eUsuario = null;
		
		// Si la entidad está registrada no la registra de nuevo
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		} catch (NullPointerException e) {}
		if (eUsuario != null) return;

		//Necesitamos obtener los objetos guardados como entidad.
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
			
	
		
		// registrar entidad usuario
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		usuario.setCodigo(eUsuario.getId()); 

	}

	//Borrar un usuario de la base de datos
	public void borrarUsuario(Usuario usuario) {
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		
		servPersistencia.borrarEntidad(eUsuario);
	}

	//Modificar un usuario guardado en la base de datos
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

	//Recuperar un usuario de la base de datos dado su codigo
	public Usuario recuperarUsuario(int codigo) {

		Entidad eUsuario;
		String nombre_completo;
		String fecha_nacimiento;
		String email;
		String usuario;
		String password;
		boolean premium;
		String filtro;
		
		eUsuario = servPersistencia.recuperarEntidad(codigo);
		nombre_completo = servPersistencia.recuperarPropiedadEntidad(eUsuario, "nombre_completo");
		fecha_nacimiento = servPersistencia.recuperarPropiedadEntidad(eUsuario, "fecha_nacimiento");
		email = servPersistencia.recuperarPropiedadEntidad(eUsuario, "email");
		usuario = servPersistencia.recuperarPropiedadEntidad(eUsuario, "usuario");
		password = servPersistencia.recuperarPropiedadEntidad(eUsuario, "password");
		premium = Boolean.parseBoolean(servPersistencia.recuperarPropiedadEntidad(eUsuario, "premium"));
		filtro = servPersistencia.recuperarPropiedadEntidad(eUsuario, "filtro");
		
		//Recuperar las entidades que son objetos
		AdaptadorVideoTDS adaptadorVideo = AdaptadorVideoTDS.getUnicaInstancia();
		String listaCodigosVideos = servPersistencia.recuperarPropiedadEntidad(eUsuario, "recientes");
		
		Usuario user = new Usuario(nombre_completo, fecha_nacimiento, email, usuario, password, premium, 
				adaptadorVideo.obtenerVideosDesdeCodigos(listaCodigosVideos), filtro);
		user.setCodigo(codigo);
		return user;
	}

	//Recuperar todos los usuarios de la base de datos
	public List<Usuario> recuperarTodosUsuarios() {

		List<Entidad> eUsuarios = servPersistencia.recuperarEntidades("usuario");
		List<Usuario> usuarios = new LinkedList<Usuario>();

		for (Entidad eUsuario : eUsuarios) {
			usuarios.add(recuperarUsuario(eUsuario.getId()));
		}
		return usuarios;
	}


}
