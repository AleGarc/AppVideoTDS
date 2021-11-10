package modelo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Venta {

	private int codigo;
	private Usuario usuario;
	private Date fecha;
	private List<LineaVenta> lineasVenta;
	private boolean esCompleta;

	public Venta() {
		this(null);
	}

	public Venta(Date fecha) {
		codigo = 0;
		usuario = null;
		this.fecha = fecha;
		lineasVenta = new LinkedList<LineaVenta>();
	}

	public boolean isCompleta() {
		return esCompleta;
	}

	public void setCompleta(boolean estado) {
		esCompleta = estado;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public void setCliente(Usuario usuario) {
		this.usuario = usuario;
	}

	public Usuario getCliente() {
		return usuario;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public List<LineaVenta> getLineasVenta() {
		return lineasVenta;
	}

	public boolean esEnPeriodo(Date inicio, Date fin) {
		return (fecha.after(inicio)) && (fecha.before(fin));
	}

	public double getTotal() {
		double total = 0;
		for (LineaVenta lv : lineasVenta) {
			total += lv.getSubTotal();
		}
		return total;
	}
    
	// se invoca desde la clase Venta para añadir una nueva línea de venta
	public LineaVenta addLineaVenta(int unidades, Video video) {
		LineaVenta lv = new LineaVenta(unidades, video);
		lineasVenta.add(lv);
		return lv;
	}
    // se invoca desde el adaptadorVentaTDS
	public void addLineaVenta(LineaVenta lv) {
		lineasVenta.add(lv);
	}

	public boolean incluyeProducto(int codigo) {
		for (LineaVenta lv : lineasVenta)
			if (lv.getProducto().getCodigo() == codigo)
				return true;
		return false;
	}
}
