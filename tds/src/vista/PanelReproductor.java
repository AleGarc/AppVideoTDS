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
import javax.swing.BoxLayout;
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
import tds.video.VideoWeb;


public class PanelReproductor extends JPanel implements ActionListener{

	private VentanaMain ventana;
	
	private JButton loginMainButton;
	
	private String usuario;
	
	private static VideoWeb videoWeb;
	
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
		
		
		videoWeb = new VideoWeb();
	      
		Ventana.setLayout(new BoxLayout(Ventana, BoxLayout.Y_AXIS));
		
		
		
		
		add(Ventana);
		Component h1 = Box.createRigidArea(new Dimension(15, 15));
		Ventana.add(h1);
		
		
		JLabel tituloVideo = new JLabel();
		tituloVideo.setText("Titulo del video");
		tituloVideo.setFont(new Font("Arial",Font.BOLD,32));
		tituloVideo.setHorizontalTextPosition(JLabel.CENTER);
		tituloVideo.setVerticalTextPosition(JLabel.CENTER);
		
		tituloVideo.setAlignmentX(Component.CENTER_ALIGNMENT);
		Ventana.add(tituloVideo);
		
		Component h2 = Box.createRigidArea(new Dimension(40, 40));
		Ventana.add(h2);
		//videoWeb.playVideo("https://www.youtube.com/watch?v=EdVMSYomYJY");
	
		validate();
		Ventana.add(videoWeb);
		
		JLabel nuevaEtiqueta = new JLabel("Nueva etiqueta:");
		JTextField txtEtiqueta = new JTextField();
		txtEtiqueta.setColumns(10);
		JButton btnAnadir = new JButton("Añadir");
		
		JPanel anadirNuevaEtiqueta = new JPanel();
		anadirNuevaEtiqueta.setLayout(new FlowLayout( FlowLayout.LEFT));
		fixedSize(anadirNuevaEtiqueta,300,40);
		anadirNuevaEtiqueta.setBackground(Color.LIGHT_GRAY);
		anadirNuevaEtiqueta.add(nuevaEtiqueta);
		anadirNuevaEtiqueta.add(txtEtiqueta);
		anadirNuevaEtiqueta.add(btnAnadir);
		
		Component h3 = Box.createRigidArea(new Dimension(80, 80));
		Ventana.add(h3);
		
		Ventana.add(anadirNuevaEtiqueta);
		
		
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
