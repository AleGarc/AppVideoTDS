package modelo;

import java.util.Objects;

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

	@Override
	public int hashCode() {
		return Objects.hash(codigo, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		Etiqueta other = (Etiqueta) obj;
		return codigo == other.codigo && Objects.equals(nombre, other.nombre);
	}
	

	
}
