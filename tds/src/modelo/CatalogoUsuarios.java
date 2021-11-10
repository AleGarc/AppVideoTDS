package modelo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorClienteDAO;


/* El catálogo mantiene los objetos en memoria, en una tabla hash
 * para mejorar el rendimiento. Esto no se podría hacer en una base de
 * datos con un número grande de objetos. En ese caso se consultaria
 * directamente la base de datos
 */
public class CatalogoUsuarios {
	private Map<String,Usuario> usuarios; 
	private static CatalogoUsuarios unicaInstancia = new CatalogoUsuarios();
	
	private FactoriaDAO dao;
	private IAdaptadorClienteDAO adaptadorCliente;
	
	private CatalogoUsuarios() {
		try {
  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
  			adaptadorCliente = dao.getClienteDAO();
  			usuarios = new HashMap<String,Usuario>();
  			this.cargarCatalogo();
  		} catch (DAOException eDAO) {
  			eDAO.printStackTrace();
  		}
	}
	
	public static CatalogoUsuarios getUnicaInstancia(){
		return unicaInstancia;
	}
	
	/*devuelve todos los clientes*/
	public List<Usuario> getClientes(){
		ArrayList<Usuario> lista = new ArrayList<Usuario>();
		for (Usuario c:usuarios.values()) 
			lista.add(c);
		return lista;
	}
	
	public Usuario getCliente(int codigo) {
		for (Usuario c:usuarios.values()) {
			if (c.getCodigo()==codigo) return c;
		}
		return null;
	}
	public Usuario getCliente(String dni) {
		return usuarios.get(dni); 
	}
	
	public void addCliente(Usuario cli) {
		usuarios.put(cli.getUsuario(),cli);
	}
	public void removeCliente (Usuario cli) {
		usuarios.remove(cli.getUsuario());
	}
	
	/*Recupera todos los clientes para trabajar con ellos en memoria*/
	private void cargarCatalogo() throws DAOException {
		 List<Usuario> clientesBD = adaptadorCliente.recuperarTodosClientes();
		 for (Usuario cli: clientesBD) 
			     usuarios.put(cli.getUsuario(),cli);
	}
	
}
