package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import controlador.ControladorTienda;


public class PanelReproductor extends JPanel implements ActionListener{
	private Font fuenteGrande = new Font("Arial",Font.BOLD,32);
	private JLabel rotulo;
	private JPanel datosProducto;
	private JLabel lnombre, ldescripcion, lprecio, lalerta;
	private JTextField txtUsuario, txtPassword;
	private JTextArea descripcion;
	private JButton btnLogin, btnCancelar;
	private VentanaMain ventana;
	
	private JButton loginMainButton;
	
	private String usuario;
	
	public PanelReproductor(VentanaMain v){
		ventana=v; 
		crearPantalla();
	}
	
	private void crearPantalla() {
		JPanel Ventana = new JPanel();
		Ventana.setBorder(new LineBorder(new Color(0, 0, 0)));
		Ventana.setBackground(Color.LIGHT_GRAY);
		Ventana.setPreferredSize(new Dimension(Constantes.ventana_x_size-35, Constantes.ventana_y_size-180));
		Ventana.setMaximumSize(Ventana.getPreferredSize());
		
		//frmAppVideo.getContentPane().add(Ventana);
		
		//Ventana.setLayout(new BoxLayout(Ventana, BoxLayout.Y_AXIS));
		Ventana.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		Component rigidArea5 = Box.createRigidArea(new Dimension(700, 100));
		Ventana.add(rigidArea5);
		
		JPanel panel = new JPanel();
		
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		//panel.setAlignmentX(75);
		panel.setBorder(new LineBorder(Color.DARK_GRAY));
		panel.setPreferredSize(new Dimension(400, 300));
		panel.setMaximumSize(new Dimension(400, 300));
		Ventana.add(panel);
		
		Component rigidArea_3_1 = Box.createRigidArea(new Dimension(400, 60));
		panel.add(rigidArea_3_1);
		
		JLabel lblNewLabel_2 = new JLabel("Usuario:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblNewLabel_2);
		
		txtUsuario = new JTextField();
		panel.add(txtUsuario);
		txtUsuario.setColumns(20);
		
		Component rigidArea_3_1_1_1 = Box.createRigidArea(new Dimension(400, 50));
		panel.add(rigidArea_3_1_1_1);
		
		JLabel lblNewLabel_2_1 = new JLabel("Password");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblNewLabel_2_1);
		
		txtPassword = new JTextField();
		txtPassword.setHorizontalAlignment(SwingConstants.LEFT);
		txtPassword.setColumns(20);
		panel.add(txtPassword);
		
		Component rigidArea_3_1_1_2 = Box.createRigidArea(new Dimension(3, 20));
		panel.add(rigidArea_3_1_1_2);
		
		Component rigidArea_3_1_1 = Box.createRigidArea(new Dimension(400, 50));
		panel.add(rigidArea_3_1_1);
		
		btnLogin = new JButton("Aceptar");
		panel.add(btnLogin);
		
		Component rigidArea_4 = Box.createRigidArea(new Dimension(60, 20));
		panel.add(rigidArea_4);
		
		JButton btnNewButton_6 = new JButton("Cancelar");
		panel.add(btnNewButton_6);
		
		add(Ventana);
		
		btnLogin.addActionListener(this);
		/*setSize(Constantes.x_size,Constantes.y_size);
		setLayout(new BorderLayout());
		rotulo=new JLabel("Alta Producto",JLabel.CENTER);
		fixedSize(rotulo,Constantes.x_size,60);
		rotulo.setFont(fuenteGrande);
		add(rotulo,BorderLayout.NORTH);

		datosProducto=new JPanel();
		datosProducto.setLayout(new FlowLayout(FlowLayout.LEFT));
	
		lnombre=new JLabel("Nombre:",JLabel.RIGHT);	
		fixedSize(lnombre,170,24);
		nombre=new JTextField(); 
		fixedSize(nombre,140,24); 
	
		lprecio=new JLabel("Precio:",JLabel.RIGHT); 
		fixedSize(lprecio,60,24);
		precio=new JTextField(); 
		fixedSize(precio,90,24);

		ldescripcion=new JLabel("Descripcion:",JLabel.RIGHT); 
		fixedSize(ldescripcion,170,24);
		descripcion=new JTextArea(); 
		fixedSize(descripcion,300,100);
		descripcion.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	
		btnRegistrar=new JButton("Registrar"); fixedSize(btnRegistrar,100,30);
		btnCancelar=new JButton("Cancelar"); fixedSize(btnCancelar,100,30);
		
		lalerta=new JLabel("Los campos Nombre y Precio son obligtorios",JLabel.CENTER);
		lalerta.setForeground(Color.RED); fixedSize(lalerta,Constantes.x_size,30);
		lalerta.setVisible(false);
		
		datosProducto.add(Box.createRigidArea(new Dimension(Constantes.x_size,35)));
		datosProducto.add(lnombre); datosProducto.add(nombre);
		datosProducto.add(lprecio); datosProducto.add(precio);
		datosProducto.add(ldescripcion);
		datosProducto.add(descripcion);
		datosProducto.add(Box.createRigidArea(new Dimension(Constantes.x_size,39)));
		datosProducto.add(Box.createRigidArea(new Dimension(170,20)));
		datosProducto.add(btnRegistrar);
		datosProducto.add(Box.createRigidArea(new Dimension(90,20)));
		datosProducto.add(btnCancelar);
		datosProducto.add(lalerta);
		
		add(datosProducto,BorderLayout.CENTER);
		
		//Manejadores
		*/
		/*btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				String auxUsuario=usuarioText.getText().trim();
				String auxPassword=passwordText.getText().trim();
				//double doubleprecio=Double.parseDouble(auxPrecio);
				if (auxUsuario.isEmpty()||auxPassword.isEmpty()) showErrorAuth();//lalerta.setVisible(true);
				else if (!ControladorTienda.getUnicaInstancia().autenticarUsuario(auxUsuario, auxPassword)) showErrorAuth();
				else { /*lalerta.setVisible(false);
					   ControladorTienda.getUnicaInstancia().registrarVideo(auxUsuario, descripcion.getText());
					   JOptionPane.showMessageDialog(ventana,
								"Producto dado de alta",
								"Registrar producto",JOptionPane.PLAIN_MESSAGE);
					   precio.setText(""); usuarioText.setText(""); 
					   descripcion.setText(""); lalerta.setVisible(false); 
					
					usuarioText.setText(""); passwordText.setText("");
					for(ActionListener a: loginMainButton.getActionListeners()) {
					    a.actionPerformed(new ActionEvent(loginMainButton, ActionEvent.ACTION_PERFORMED, null) {
					          //Nothing need go here, the actionPerformed method (with the
					          //above arguments) will trigger the respective listener
					    });
					    }
					}
				}
			}	
		});*/
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()== btnLogin) { 
				String auxUsuario=txtUsuario.getText().trim();
				String auxPassword=txtPassword.getText().trim();
				//double doubleprecio=Double.parseDouble(auxPrecio);
				if (auxUsuario.isEmpty()||auxPassword.isEmpty()) showErrorAuth();//lalerta.setVisible(true);
				else if (!ControladorTienda.getUnicaInstancia().autenticarUsuario(auxUsuario, auxPassword)) showErrorAuth();
				else { /*lalerta.setVisible(false);
					   ControladorTienda.getUnicaInstancia().registrarVideo(auxUsuario, descripcion.getText());
					   JOptionPane.showMessageDialog(ventana,
								"Producto dado de alta",
								"Registrar producto",JOptionPane.PLAIN_MESSAGE);
					   precio.setText(""); usuarioText.setText(""); 
					   descripcion.setText(""); lalerta.setVisible(false); */
					
					txtUsuario.setText(""); txtPassword.setText("");
					usuario = auxUsuario;
					for(ActionListener a: loginMainButton.getActionListeners()) {
					    a.actionPerformed(new ActionEvent(loginMainButton, ActionEvent.ACTION_PERFORMED, null) {
					          //Nothing need go here, the actionPerformed method (with the
					          //above arguments) will trigger the respective listener
					    });
					    }
					}
				}
				return;	
			
		
	}
	
	private void showErrorAuth() {
		JOptionPane.showMessageDialog(ventana,
				"Usuario o contraseña no valido",
				"Error",JOptionPane.ERROR_MESSAGE);
	}
	
	public void setLoginMainButton(JButton b) {
		loginMainButton = b;
	}
		
	private void fixedSize(JComponent c,int x, int y) {
		c.setMinimumSize(new Dimension(x,y));
		c.setMaximumSize(new Dimension(x,y));
		c.setPreferredSize(new Dimension(x,y));
	}
	
	public String getUsuario() {
		return usuario;
	}

}
