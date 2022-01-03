package modelo;

import controlador.ControladorAppVideo;

public class FiltroMisListas implements FiltroVideo {

	ControladorAppVideo controladorAppVideo = ControladorAppVideo.getUnicaInstancia();
	
	@Override
	public boolean esVideoOK(Video v) {
		return !controladorAppVideo.checkVideoInVideoList(controladorAppVideo.getUsuario(), v);
	}

}
