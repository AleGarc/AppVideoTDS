package persistencia;

public class TDSFactoriaDAO extends FactoriaDAO {
	public TDSFactoriaDAO () {
	}
	
	@Override
	public IAdaptadorVentaDAO getVentaDAO() {
		return AdaptadorVentaTDS.getUnicaInstancia();
	}

	@Override
	public IAdaptadorLineaVentaDAO getLineaVentaDAO() {
		return AdaptadorLineaVentaTDS.getUnicaInstancia();
	}

	@Override
	public IAdaptadorVideoDAO getVideoDAO() {
		return AdaptadorVideoTDS.getUnicaInstancia();
	}

	@Override
	public IAdaptadorUsuarioDAO getUsuarioDAO() {
		return AdaptadorUsuarioTDS.getUnicaInstancia();
	}

	@Override
	public IAdaptadorEtiquetaDAO getEtiquetaDAO() {
		return AdaptadorEtiquetaTDS.getUnicaInstancia();
	}

	@Override
	public IAdaptadorVideoListDAO getVideoListDAO() {
		return AdaptadorVideoListTDS.getUnicaInstancia();
	}
}
