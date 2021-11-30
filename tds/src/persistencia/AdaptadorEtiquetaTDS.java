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

	/* cuando se registra un Etiqueta se le asigna un identificador unico */
	public void registrarEtiqueta(Etiqueta etiqueta) {
		Entidad eEtiqueta = null;
		// Si la entidad está registrada no la registra de nuevo
		boolean existe = true; 
		try {
			eEtiqueta = servPersistencia.recuperarEntidad(etiqueta.getCodigo());
		} catch (NullPointerException e) {
			existe = false;
		}
		if (existe) return;
		
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

	public void borrarEtiqueta(Etiqueta etiqueta) {
		// No se comprueba integridad con lineas de venta
		Entidad eEtiqueta = servPersistencia.recuperarEntidad(etiqueta.getCodigo());
		servPersistencia.borrarEntidad(eEtiqueta);
	}

	public void modificarEtiqueta(Etiqueta etiqueta) {
		Entidad eEtiqueta = servPersistencia.recuperarEntidad(etiqueta.getCodigo());

		servPersistencia.eliminarPropiedadEntidad(eEtiqueta, "nombre");
		servPersistencia.anadirPropiedadEntidad(eEtiqueta, "nombre", String.valueOf(etiqueta.getNombre()));
	}

	public Etiqueta recuperarEtiqueta(int codigo) {
		Entidad eEtiqueta;
		String nombre;

		eEtiqueta = servPersistencia.recuperarEntidad(codigo);
		nombre = servPersistencia.recuperarPropiedadEntidad(eEtiqueta, "nombre");
		
		Etiqueta etiqueta = new Etiqueta(nombre);
		etiqueta.setCodigo(codigo);
		return etiqueta;
	}

	public List<Etiqueta> recuperarTodosEtiquetas() {
		List<Etiqueta> etiquetas = new LinkedList<Etiqueta>();
		List<Entidad> entidades = servPersistencia.recuperarEntidades("Etiqueta");

		for (Entidad eEtiqueta : entidades) {
			etiquetas.add(recuperarEtiqueta(eEtiqueta.getId()));
		}
		return etiquetas;
	}
	
	
	//---------------------Funciones auxiliares----------------------------
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
			listaEtiquetas.add(recuperarEtiqueta(Integer.valueOf((String)strTok.nextElement()))); //QUIZAS ADAPTADORETIQUETA UNICA INSTANCIA. RECUPERAR?
		} 
		return listaEtiquetas; 
	}
	
	public void registrarListaEtiqueta(List<Etiqueta> listaEtiquetas) {
		for(Etiqueta e: listaEtiquetas) {
			registrarEtiqueta(e);
		}
	}

}
