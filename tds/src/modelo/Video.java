package modelo;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Video {



	private int codigo;
	private String titulo;
	private String url;
	private List<Etiqueta> etiquetas;
	private int reproducciones;
	
	public Video(String titulo, String url) {
		this.codigo = 0;
		this.titulo = titulo;
		this.url = url;
		this.etiquetas = new LinkedList<Etiqueta>();
		this.reproducciones = 0;
	}
	

	public Video(String titulo, String url, List<Etiqueta> etiquetas) {
		this(titulo, url);
		this.etiquetas = etiquetas;
	}
	
	public Video(String titulo, String url, List<Etiqueta> etiquetas, int reproducciones) {
		this(titulo, url, etiquetas);
		this.reproducciones = reproducciones;
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
	
	public void addEtiqueta(Etiqueta e) {
		this.etiquetas.add(e);
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
	
	public int getReproducciones() {
		return reproducciones;
	}
	
	public void addReproducciones() {
		reproducciones = reproducciones + 1;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(codigo, etiquetas, titulo, url);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		Video other = (Video) obj;
		return codigo == other.codigo && Objects.equals(titulo, other.titulo) && Objects.equals(url, other.url) && Objects.equals(reproducciones, other.reproducciones);
	}

}
