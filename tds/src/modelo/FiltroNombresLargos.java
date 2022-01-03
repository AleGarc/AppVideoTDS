package modelo;

public class FiltroNombresLargos implements FiltroVideo{

	@Override
	public boolean esVideoOK(Video v) {
		if(v.getTitulo().length()<16)
			return true;
		return false;
	}

}
