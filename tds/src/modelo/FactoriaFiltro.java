package modelo;

import java.util.ArrayList;
import java.util.List;

public class FactoriaFiltro {

	public static FiltroVideo crearFiltro(String nombreFiltro) {
	    FiltroVideo filtro = null;
	    if ("FiltroMisListas".equalsIgnoreCase(nombreFiltro)) {
	    	filtro = new FiltroMisListas();
	    } else if ("FiltroNombresLargos".equalsIgnoreCase(nombreFiltro)) {
	    	filtro = new FiltroNombresLargos();
	    } else {
	    	filtro = new NoFiltro();
	    }
	    return filtro;
	}
	
	public static List<String> getFiltrosDisponibles(){
		List<String> filtros = new ArrayList<String>();
		filtros.add("NoFiltro");
		filtros.add("FiltroMisListas");
		filtros.add("FiltroNombresLargos");
		return filtros;
	}
}
