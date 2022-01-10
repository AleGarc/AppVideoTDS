package modelo;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	private int codigo;
	private String nombre_completo;
	private String fecha_nacimiento;
	private String email;
	private String usuario;
	private String password;
	private boolean premium;
	private List<Video> videosRecientes;
	private String filtro;
	
	public Usuario(String nombre_completo, String fecha_nacimiento, String email, String usuario, String password) {
		codigo = 0;
		this.nombre_completo = nombre_completo;
		this.fecha_nacimiento = fecha_nacimiento;
		this.email = email;
		this.usuario = usuario;
		this.password = password;
		this.premium = false;
		this.videosRecientes = new ArrayList<Video>();
		this.filtro = "NoFiltro";
	}
	
	public Usuario(String nombre_completo, String fecha_nacimiento, String email, String usuario, String password, boolean premium, List<Video> videosRecientes, String filtro) {
		this(nombre_completo,fecha_nacimiento,email,usuario,password);
		this.premium = premium;
		this.videosRecientes = videosRecientes;
		this.filtro = filtro;
	}

	public String getNombre_completo() {
		return nombre_completo;
	}

	public String getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public String getEmail() {
		return email;
	}

	public String getUsuario() {
		return usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getCodigo() {
		return codigo;
	}

	public void cambiarRol(boolean b) {
		this.premium = b;
	}
	
	public boolean esPremium() {
		return this.premium;
	}
	
	public List<Video> getVideosRecientes(){
		return this.videosRecientes;
	}

	//Añadir un video a la lista de videos recientes del usuario
	public void addVideoReciente(Video v) {
		List<Video> nuevosRecientes = new ArrayList<Video>();
		
		for(Video vid : videosRecientes) {
			if(!vid.equals(v)) 
				nuevosRecientes.add(vid);	
		}
		videosRecientes = nuevosRecientes;
		videosRecientes.add(0, v);
		if(videosRecientes.size() > 5) videosRecientes.remove(5);
	}
	
	public FiltroVideo getFiltro() {
		return FactoriaFiltro.crearFiltro(this.filtro);
	}
	
	public String getFiltroString() {
		return this.filtro;
	}
	
	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}
}
