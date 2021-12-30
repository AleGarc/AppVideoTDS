package modelo;

import java.util.LinkedList;
import java.util.List;

public class Video {

	private int codigo;
	private String titulo;
	private String url;
	private List<Etiqueta> etiquetas;
	
	public Video(String titulo, String url) {
		this.codigo = 0;
		this.titulo = titulo;
		this.url = url;
		this.etiquetas = new LinkedList<Etiqueta>();
	}
	
	public Video(String titulo, String url, List<Etiqueta> etiquetas) {
		this.codigo = 0;
		this.titulo = titulo;
		this.url = url;
		this.etiquetas = etiquetas;
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

	public List<Etiqueta> getEtiquetas() {
		return new LinkedList<Etiqueta>(etiquetas);	//PUEDE SER??
	}

	public void setEtiquetas(List<Etiqueta> etiquetas) {
		this.etiquetas = etiquetas;
	}

	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public boolean contieneEtiqueta(Etiqueta e) {
		for(Etiqueta et: etiquetas){
			if(et.getNombre().equals(e.getNombre()))
				return true;
		}
		return false;
	}
	
	/*Util para mostrar el objeto en ComboBox
	@Override
	public String toString() {
		return nombre;
	}*/
	

}
