package modelo;

import java.util.ArrayList;
import java.util.LinkedList;
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
	
	public Usuario(String nombre_completo, String fecha_nacimiento, String email, String usuario, String password) {
		codigo = 0;
		this.nombre_completo = nombre_completo;
		this.fecha_nacimiento = fecha_nacimiento;
		this.email = email;
		this.usuario = usuario;
		this.password = password;
		this.premium = false;
		this.videosRecientes = new ArrayList<Video>();
	}
	
	public Usuario(String nombre_completo, String fecha_nacimiento, String email, String usuario, String password, boolean premium, List<Video> videosRecientes) {
		this(nombre_completo,fecha_nacimiento,email,usuario,password);
		this.premium = premium;
		this.videosRecientes = videosRecientes;
	}

	public String getNombre_completo() {
		return nombre_completo;
	}

	public void setNombre_completo(String nombre_completo) {
		this.nombre_completo = nombre_completo;
	}

	public String getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(String fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public void addVideoReciente(Video v) {
		List<Video> nuevosRecientes = new ArrayList<Video>();
		/*for(Video vid : videosRecientes) {
			if(vid.equals(v) && !exists)
				exists = true;
			else if(exists)
				videosRecientes.add(videosRecientes.indexOf(vid)-1, vid);
				
		}
		videosRecientes.add(0, v);
		if(videosRecientes.size() > 5) videosRecientes.remove(5);*/
		
		for(Video vid : videosRecientes) {
			if(!vid.equals(v)) 
				nuevosRecientes.add(vid);	
		}
		videosRecientes = nuevosRecientes;
		videosRecientes.add(0, v);
		if(videosRecientes.size() > 5) videosRecientes.remove(5);
	}
}
