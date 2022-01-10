package modelo;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import persistencia.DAOException;
import persistencia.FactoriaDAO;
import persistencia.IAdaptadorUsuarioDAO;


/* El catálogo mantiene los objetos en memoria, en una tabla hash
 * para mejorar el rendimiento. Esto no se podría hacer en una base de
 * datos con un número grande de objetos. En ese caso se consultaria
 * directamente la base de datos
 */
public class CatalogoUsuarios {
	private Map<String,Usuario> usuarios; 
	private static CatalogoUsuarios unicaInstancia = new CatalogoUsuarios();
	
	private FactoriaDAO dao;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	
	private CatalogoUsuarios() {
		try {
  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
  			adaptadorUsuario = dao.getUsuarioDAO();
  			usuarios = new HashMap<String,Usuario>();
  			this.cargarCatalogo();
  		} catch (DAOException eDAO) {
  			eDAO.printStackTrace();
  		}
	}
	
	public static CatalogoUsuarios getUnicaInstancia(){
		return unicaInstancia;
	}
	
	public Usuario getUsuario(String usuario) {
		return usuarios.get(usuario); 
	}
	
	public void addUsuario(Usuario cli) {
		usuarios.put(cli.getUsuario(),cli);
	}

	public boolean authUsuario(String user, String password) {
		Usuario u = usuarios.get(user);
		if(u == null) return false;
		return u.getPassword().equals(password);
	}
	
	/*Recupera todos los clientes para trabajar con ellos en memoria*/
	private void cargarCatalogo() throws DAOException {
		 List<Usuario> clientesBD = adaptadorUsuario.recuperarTodosUsuarios();
		 for (Usuario cli: clientesBD) 
			     usuarios.put(cli.getUsuario(),cli);
	}
	
	
}
