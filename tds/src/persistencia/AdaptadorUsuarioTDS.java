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

//Usa un pool para evitar problemas doble referencia con ventas
public class AdaptadorUsuarioTDS implements IAdaptadorClienteDAO {
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
	public void registrarCliente(Usuario usuario) {
		Entidad eCliente = null;
		
		// Si la entidad está registrada no la registra de nuevo
		try {
			eCliente = servPersistencia.recuperarEntidad(usuario.getCodigo());
		} catch (NullPointerException e) {}
		if (eCliente != null) return;

		// crear entidad Cliente
		eCliente = new Entidad();
		eCliente.setNombre("cliente");
		eCliente.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("nombre_completo", usuario.getNombre_completo()), 
						new Propiedad("fecha_nacimiento", usuario.getFecha_nacimiento()),
						new Propiedad("email", usuario.getEmail()),
						new Propiedad("usuario", usuario.getUsuario()),
						new Propiedad("password", usuario.getPassword()))));
		/*eCliente.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("dni", cliente.getDni()), new Propiedad("nombre", cliente.getNombre()),
						new Propiedad("ventas", obtenerCodigosVentas(cliente.getVentas())))));*/
		
		// registrar entidad cliente
		eCliente = servPersistencia.registrarEntidad(eCliente);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		usuario.setCodigo(eCliente.getId()); 

	}

	public void borrarCliente(Usuario usuario) {
		// No se comprueban restricciones de integridad con Venta
		Entidad eCliente = servPersistencia.recuperarEntidad(usuario.getCodigo());
		
		servPersistencia.borrarEntidad(eCliente);
	}

	public void modificarCliente(Usuario usuario) {

		Entidad eCliente = servPersistencia.recuperarEntidad(usuario.getCodigo());

		servPersistencia.eliminarPropiedadEntidad(eCliente, "nombre_completo");
		servPersistencia.anadirPropiedadEntidad(eCliente, "nombre_completo", usuario.getNombre_completo());
		servPersistencia.eliminarPropiedadEntidad(eCliente, "fecha_nacimiento");
		servPersistencia.anadirPropiedadEntidad(eCliente, "fecha_nacimiento", usuario.getFecha_nacimiento());
		servPersistencia.eliminarPropiedadEntidad(eCliente, "email");
		servPersistencia.anadirPropiedadEntidad(eCliente, "email", usuario.getEmail());
		servPersistencia.eliminarPropiedadEntidad(eCliente, "usuario");
		servPersistencia.anadirPropiedadEntidad(eCliente, "usuario", usuario.getUsuario());
		servPersistencia.eliminarPropiedadEntidad(eCliente, "password");
		servPersistencia.anadirPropiedadEntidad(eCliente, "password", usuario.getPassword());
		
		/*String ventas = obtenerCodigosVentas(cliente.getVentas());
		servPersistencia.eliminarPropiedadEntidad(eCliente, "ventas");
		servPersistencia.anadirPropiedadEntidad(eCliente, "ventas", ventas);*/
	}

	public Usuario recuperarCliente(int codigo) {

		// Si la entidad está en el pool la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(codigo))
			return (Usuario) PoolDAO.getUnicaInstancia().getObjeto(codigo);

		// si no, la recupera de la base de datos
		Entidad eCliente;
		//List<Venta> ventas = new LinkedList<Venta>();
		String nombre_completo;
		String fecha_nacimiento;
		String email;
		String usuario;
		String password;
		
		// recuperar entidad
		eCliente = servPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		nombre_completo = servPersistencia.recuperarPropiedadEntidad(eCliente, "nombre_completo");
		fecha_nacimiento = servPersistencia.recuperarPropiedadEntidad(eCliente, "fecha_nacimiento");
		email = servPersistencia.recuperarPropiedadEntidad(eCliente, "email");
		usuario = servPersistencia.recuperarPropiedadEntidad(eCliente, "usuario");
		password = servPersistencia.recuperarPropiedadEntidad(eCliente, "password");
		
		Usuario user = new Usuario(nombre_completo, fecha_nacimiento, email, usuario, password);
		user.setCodigo(codigo);

		// IMPORTANTE:añadir el cliente al pool antes de llamar a otros
		// adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(codigo, usuario);

		// recuperar propiedades que son objetos llamando a adaptadores
		// ventas
		/*ventas = obtenerVentasDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eCliente, "ventas"));

		for (Venta v : ventas)
			cliente.addVenta(v);*/

		return user;
	}

	public List<Usuario> recuperarTodosClientes() {

		List<Entidad> eClientes = servPersistencia.recuperarEntidades("cliente");
		List<Usuario> usuarios = new LinkedList<Usuario>();

		for (Entidad eCliente : eClientes) {
			usuarios.add(recuperarCliente(eCliente.getId()));
		}
		return usuarios;
	}

}
