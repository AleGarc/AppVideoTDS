package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import controlador.ControladorAppVideo;


@SuppressWarnings("serial")
public class PanelLogin extends JPanel{

	private JTextField txtUsuario, txtPassword;
	private JButton btnLogin, btnCancelar;
	private VentanaMain ventanaMain;
	ControladorAppVideo controladorAppVideo = ControladorAppVideo.getUnicaInstancia();
	
	
	public PanelLogin(VentanaMain v){
		ventanaMain=v; 
		crearPantalla();
	}
	
	private void crearPantalla() {
		JPanel contenido = new JPanel();
		contenido.setBorder(new LineBorder(new Color(0, 0, 0)));
		contenido.setBackground(Color.LIGHT_GRAY);
		Constantes.fixedSize(contenido, Constantes.ventana_x_size-35, Constantes.ventana_y_size-180);
		contenido.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		Component rA0 = Box.createRigidArea(new Dimension(700, 100));
		contenido.add(rA0);
		
		JPanel panelLogin = new JPanel();
		
		panelLogin.setBackground(Color.LIGHT_GRAY);
		panelLogin.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelLogin.setBorder(new LineBorder(Color.DARK_GRAY));
		panelLogin.setPreferredSize(new Dimension(400, 300));
		panelLogin.setMaximumSize(new Dimension(400, 300));
		contenido.add(panelLogin);
		
		Component rA1 = Box.createRigidArea(new Dimension(400, 60));
		panelLogin.add(rA1);
		
		JLabel lbUsuario = new JLabel("Usuario:");
		lbUsuario.setHorizontalAlignment(SwingConstants.LEFT);
		panelLogin.add(lbUsuario);
		
		txtUsuario = new JTextField();
		panelLogin.add(txtUsuario);
		txtUsuario.setColumns(20);
		
		Component rA2 = Box.createRigidArea(new Dimension(400, 50));
		panelLogin.add(rA2);
		
		JLabel lbPassword = new JLabel("Password");
		lbPassword.setHorizontalAlignment(SwingConstants.LEFT);
		panelLogin.add(lbPassword);
		
		txtPassword = new JTextField();
		txtPassword.setHorizontalAlignment(SwingConstants.LEFT);
		txtPassword.setColumns(20);
		panelLogin.add(txtPassword);
		
		Component rA3 = Box.createRigidArea(new Dimension(3, 20));
		panelLogin.add(rA3);	
		Component rA4 = Box.createRigidArea(new Dimension(400, 50));
		panelLogin.add(rA4);
		
		btnLogin = new JButton("Aceptar");
		panelLogin.add(btnLogin);
		
		Component rA5 = Box.createRigidArea(new Dimension(60, 20));
		panelLogin.add(rA5);
		
		btnCancelar = new JButton("Cancelar");
		panelLogin.add(btnCancelar);
		
		add(contenido);
		actionListeners();
		
	}
	
	private void showErrorAuth() {
		JOptionPane.showMessageDialog(ventanaMain,
				"Usuario o contraseña no valido",
				"Error",JOptionPane.ERROR_MESSAGE);
	}
	
	private void actionListeners() {
		btnLogin.addActionListener(ev->{
			String auxUsuario=txtUsuario.getText().trim();
			String auxPassword=txtPassword.getText().trim();
			if (auxUsuario.isEmpty()||auxPassword.isEmpty()) showErrorAuth();
			else if (!controladorAppVideo.autenticarUsuario(auxUsuario, auxPassword)) showErrorAuth();
			else { 
				limpiarCampos();
				ventanaMain.cambiarContenido(Contenido.LOGGED);
			}
			
		});
		
		btnCancelar.addActionListener(ev->{
			limpiarCampos();
		});
	}
	
	private void limpiarCampos(){
		txtUsuario.setText("");
		txtPassword.setText("");
	}
		
}
