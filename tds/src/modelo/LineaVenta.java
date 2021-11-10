package modelo;

public class LineaVenta {

	private int codigo;
	private int unidades;
	private Video video;
	
	public LineaVenta(int unidades, Video video){
		codigo = 0;
		this.unidades = unidades;
		this.video = video;
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public int getUnidades() {
		return unidades;
	}
	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}
	public Video getProducto() {
		return video;
	}
	public void setProducto(Video video) {
		this.video = video;
	}
	
	public double getSubTotal() {
		//return unidades * video.getTitulo();		//CAMBIAR
		return 0;
	}
}
