package modelo;

import java.util.ArrayList;
import java.util.List;

public class VideoList {

	private final String autor;
	private String nombre;
	private List<Video> videos;
	private int codigo;
	
	public VideoList(String nombre, String autor) {
		this.nombre = nombre;
		this.autor = autor;
		videos = new ArrayList<Video>();
		codigo = 0;
	}
	
	public VideoList(String nombre, String autor, List<Video> listaVideos) {
		this(nombre,autor);
		videos = listaVideos;
		
	}
	
	public void addVideo(Video v){
		videos.add(v);
	}
	
	public void removeVideo(Video v) {
		videos.remove(v);
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public String getAutor() {
		return autor;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public List<Video> getListaVideos(){
		return videos;
	}
	
	public boolean contieneVideo(Video video) {
		for(Video v: videos) {
			if(v.getUrl().equals(video.getUrl()))
				return true;
		}
		
		return false;
	}
}
