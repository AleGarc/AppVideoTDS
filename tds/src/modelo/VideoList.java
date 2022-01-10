package modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
		if(!contieneVideo(v))
			videos.add(v);
	}
	
	public void removeVideo(Video v) {
		if(contieneVideo(v))
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
	
	@Override
	public int hashCode() {
		return Objects.hash(autor, codigo, nombre, videos);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VideoList other = (VideoList) obj;
		return Objects.equals(autor, other.autor) && codigo == other.codigo && Objects.equals(nombre, other.nombre)
				&& Objects.equals(videos, other.videos);
	}

}
