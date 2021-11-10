package persistencia;

import java.util.List;
import modelo.Usuario;

public interface IAdaptadorClienteDAO {

	public void registrarCliente(Usuario usuario);
	public void borrarCliente(Usuario usuario);
	public void modificarCliente(Usuario usuario);
	public Usuario recuperarCliente(int codigo);
	public List<Usuario> recuperarTodosClientes();
}
