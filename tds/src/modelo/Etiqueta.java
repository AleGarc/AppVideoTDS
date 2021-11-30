package modelo;

public class Etiqueta {
	private String nombre;
	private int codigo;

	public Etiqueta(String nombre) {
		this.nombre = nombre;
		this.codigo = 0;
	}

	public String getNombre() {
		return nombre;
	}

		

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getCodigo() {
		return codigo;
	}
	
	/*public void setNombre(String nombre) {
		this.nombre = nombre;
	}*/
	
	
}
