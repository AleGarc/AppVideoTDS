package modelo;

import java.util.LinkedList;
import java.util.List;

public class Video {

	private int codigo;
	private String titulo;
	private String url;
	//private List<String> etiquetas;
	private int numRepro;
	
	public Video(String titulo, String url) {
		this.codigo = 0;
		this.titulo = titulo;
		this.url = url;
		//this.etiquetas = new LinkedList<String>();
		numRepro = 0;
	}
	
	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/*public List<String> getEtiquetas() {
		return new LinkedList<String>(etiquetas);	//PUEDE SER??
	}*/

	/*public void setEtiquetas(List<String> etiquetas) {
		this.etiquetas = etiquetas;
	}*/

	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public int getNumRepro() {
		return numRepro;
	}
	
	/*Util para mostrar el objeto en ComboBox
	@Override
	public String toString() {
		return nombre;
	}*/
	

}
