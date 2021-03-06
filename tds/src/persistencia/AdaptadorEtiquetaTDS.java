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

import modelo.Etiqueta;

public class AdaptadorEtiquetaTDS implements IAdaptadorEtiquetaDAO {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorEtiquetaTDS unicaInstancia = null;

	public static AdaptadorEtiquetaTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null) {
			return new AdaptadorEtiquetaTDS();
		} else
			return unicaInstancia;
	}

	private AdaptadorEtiquetaTDS() { 
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	//Registra una etiqueta asignandole un c?digo en el proceso
	public void registrarEtiqueta(Etiqueta etiqueta) {
		Entidad eEtiqueta = null;
		
		// Si la entidad est? registrada no la registra de nuevo
		try {
			eEtiqueta = servPersistencia.recuperarEntidad(etiqueta.getCodigo());
		} catch (NullPointerException e) {}
		if (eEtiqueta != null) return;
		
		// crear entidad Etiqueta
		eEtiqueta = new Entidad();
		eEtiqueta.setNombre("etiqueta");
		eEtiqueta.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("nombre", etiqueta.getNombre()))));

		
		// registrar entidad Etiqueta
		eEtiqueta = servPersistencia.registrarEntidad(eEtiqueta);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		etiqueta.setCodigo(eEtiqueta.getId()); 
	}

	//Borrar una etiqueta de la base de datos
	public void borrarEtiqueta(Etiqueta etiqueta) {
		// No se comprueba integridad con lineas de venta
		Entidad eEtiqueta = servPersistencia.recuperarEntidad(etiqueta.getCodigo());
		servPersistencia.borrarEntidad(eEtiqueta);
	}

	//Modificar un usuario guardado en la base de datos
	public void modificarEtiqueta(Etiqueta etiqueta) {
		Entidad eEtiqueta = servPersistencia.recuperarEntidad(etiqueta.getCodigo());

		servPersistencia.eliminarPropiedadEntidad(eEtiqueta, "nombre");
		servPersistencia.anadirPropiedadEntidad(eEtiqueta, "nombre", String.valueOf(etiqueta.getNombre()));
	}

	//Recuperar una etiqueta de la base de datos dado su codigo
	public Etiqueta recuperarEtiqueta(int codigo) {
		Entidad eEtiqueta;
		String nombre;

		eEtiqueta = servPersistencia.recuperarEntidad(codigo);
		nombre = servPersistencia.recuperarPropiedadEntidad(eEtiqueta, "nombre");
		
		Etiqueta etiqueta = new Etiqueta(nombre);
		etiqueta.setCodigo(codigo);
		return etiqueta;
	}

	//Recuperar todas las etiquetas de la base de datos
	public List<Etiqueta> recuperarTodosEtiquetas() {
		List<Etiqueta> etiquetas = new LinkedList<Etiqueta>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("etiqueta");

		for (Entidad eEtiqueta : entidades) {
			etiquetas.add(recuperarEtiqueta(eEtiqueta.getId()));
		}
		return etiquetas;
	}
	
	
	//---------------------Funciones auxiliares----------------------------//
	public String obtenerCodigosListaEtiquetas(List<Etiqueta> listaEtiquetas) { 
		String codigoEtiquetas = ""; 
		for (Etiqueta e: listaEtiquetas)
			codigoEtiquetas += e.getCodigo() + " ";
		return codigoEtiquetas.trim();
	} 
	
	public List<Etiqueta> obtenerEtiquetasDesdeCodigos(String listaCodigoEtiquetas) { 
		List<Etiqueta> listaEtiquetas = new LinkedList<Etiqueta>();
		StringTokenizer strTok = new StringTokenizer(listaCodigoEtiquetas, " "); 
		while (strTok.hasMoreTokens()) { 
			listaEtiquetas.add(recuperarEtiqueta(Integer.valueOf((String)strTok.nextElement()))); 
		} 
		return listaEtiquetas; 
	}
	

}
