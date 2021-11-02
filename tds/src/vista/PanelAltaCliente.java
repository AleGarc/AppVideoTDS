package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controlador.ControladorTienda;

@SuppressWarnings("serial")
public class PanelAltaCliente extends JPanel {
	private Font fuenteGrande = new Font("Arial",Font.BOLD,32);
	private JLabel rotulo, subtitulo;
	private JPanel titulos;
	private JPanel datosCliente;
	private JLabel lpassword, lnombre, lapellidos, lfecha, lemail, lalerta, luser;
	private JTextField password, nombre, apellidos, fecha, email, user;
	private JButton btnRegistrar, btnCancelar;
	private VentanaMain ventana;
	
	
	public PanelAltaCliente(VentanaMain v){
		ventana=v; 
		crearPantalla();
	}

	private void crearPantalla() {
		setSize(Constantes.x_size,Constantes.y_size);
		setLayout(new BorderLayout());
		/*rotulo=new JLabel("AppMusic",JLabel.CENTER);
		fixedSize(rotulo,Constantes.x_size,20);
		rotulo.setFont(fuenteGrande);
		add(rotulo,BorderLayout.NORTH);
		subtitulo=new JLabel("vavav",JLabel.CENTER);
		fixedSize(subtitulo,Constantes.x_size,20);
		//rotulo.setFont(fuenteGrande);
		add(subtitulo,BorderLayout.NORTH);*/
		titulos = new JPanel();
		titulos.setLayout(new BoxLayout(titulos,BoxLayout.Y_AXIS));
		
		rotulo=new JLabel("AppMusic",JLabel.CENTER);
		fixedSize(rotulo,Constantes.x_size, 80);
		rotulo.setFont(fuenteGrande);
		
		
		subtitulo=new JLabel("Registro de usuarios",JLabel.CENTER);
		fixedSize(subtitulo,Constantes.x_size,20);
		//subtitulo.setFont(fuenteGrande);
		
		titulos.add(rotulo);
		titulos.add(subtitulo);
		
		add(titulos,BorderLayout.NORTH);
		
		datosCliente=new JPanel();
		datosCliente.setLayout(new FlowLayout(FlowLayout.LEFT));
	
		lpassword=new JLabel("Contraseña*:",JLabel.RIGHT); 
		fixedSize(lpassword,170,24);
		password=new JTextField(); 
		fixedSize(password,300,24); 
	
		lnombre=new JLabel("Nombre*:",JLabel.RIGHT);	
		fixedSize(lnombre,170,24);
		nombre=new JTextField(); 
		fixedSize(nombre,300,24); 
		
		lapellidos=new JLabel("Apellidos*:",JLabel.RIGHT);	
		fixedSize(lapellidos,170,24);
		apellidos=new JTextField(); 
		fixedSize(apellidos,300,24); 
		
		lfecha=new JLabel("Fecha de nacimiento*:",JLabel.RIGHT); 
		fixedSize(lfecha,170,24);
		fecha=new JTextField(); 
		fixedSize(fecha,300,24); 
		
		lemail=new JLabel("Email*:",JLabel.RIGHT); 
		fixedSize(lemail,170,24);
		email=new JTextField(); 
		fixedSize(email,300,24); 
		
		luser=new JLabel("User*:",JLabel.RIGHT); 
		fixedSize(luser,170,24);
		user=new JTextField(); 
		fixedSize(user,300,24); 
	
		btnRegistrar=new JButton("Registrar"); fixedSize(btnRegistrar,100,30);
		btnCancelar=new JButton("Cancelar"); fixedSize(btnCancelar,100,30);
		
		lalerta=new JLabel("Todos los campos son obligatorios",JLabel.CENTER);
		lalerta.setForeground(Color.RED); fixedSize(lalerta,Constantes.x_size,20);
		lalerta.setVisible(false);
		
		datosCliente.add(Box.createRigidArea(new Dimension(Constantes.x_size,30)));
		datosCliente.add(lnombre); datosCliente.add(nombre);
		datosCliente.add(lapellidos); datosCliente.add(apellidos);
		datosCliente.add(lfecha); datosCliente.add(fecha);
		datosCliente.add(lemail); datosCliente.add(email);
		datosCliente.add(luser); datosCliente.add(user);
		datosCliente.add(lpassword); datosCliente.add(password);
		datosCliente.add(lalerta);
		//datosCliente.add(Box.createRigidArea(new Dimension(Constantes.x_size,20)));
		datosCliente.add(Box.createRigidArea(new Dimension(170,20)));
		datosCliente.add(btnRegistrar);
		datosCliente.add(Box.createRigidArea(new Dimension(90,20)));
		datosCliente.add(btnCancelar);
		
		
		add(datosCliente,BorderLayout.CENTER);
		
		//Manejadores
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String auxpassword=password.getText().trim();	
				String auxNombre=nombre.getText().trim();
				String auxEmail=email.getText().trim();
				String auxFecha=fecha.getText().trim();
				String auxUsuario=user.getText().trim();
				if (auxpassword.isEmpty()||auxNombre.isEmpty() || auxEmail.isEmpty() || auxFecha.isEmpty() || auxUsuario.isEmpty()) lalerta.setVisible(true);
				else { lalerta.setVisible(false);
					   ControladorTienda.getUnicaInstancia().registrarCliente(auxpassword, auxNombre);
					   JOptionPane.showMessageDialog(ventana,
								"Cliente dado de alta correctamente",
								"Registrar cliente",JOptionPane.PLAIN_MESSAGE);
					   password.setText(""); nombre.setText(""); apellidos.setText(""); 
					   email.setText(""); fecha.setText(""); 
					   user.setText(""); lalerta.setVisible(false); 
				}
			}	
		});
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				password.setText(""); nombre.setText(""); lalerta.setVisible(false); 
			}
		});
	}
		
	private void fixedSize(JComponent c,int x, int y) {
		c.setMinimumSize(new Dimension(x,y));
		c.setMaximumSize(new Dimension(x,y));
		c.setPreferredSize(new Dimension(x,y));
	}
}
