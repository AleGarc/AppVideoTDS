package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import controlador.ControladorAppVideo;
import modelo.Usuario;


public class PanelPremium extends JPanel {
	private Font fuenteGrande = new Font("Arial",Font.BOLD,32);
	private JLabel rotulo;
	private JPanel datosProducto;
	private JLabel lnombre, ldescripcion, lprecio, lalerta;
	private JTextField txtUsuario, txtPassword;
	private JTextArea descripcion;
	private JButton btnLogin, btnCancelar;
	private VentanaMain ventana;
	ControladorAppVideo controladorAppVideo = ControladorAppVideo.getUnicaInstancia();
	private JButton loginMainButton;
	private Usuario usuario;
	private JPanel panelPremium, panelNoPremium;
	private JLabel tituloNoPremium1, tituloNoPremium2, tituloNoPremium3;
	private JLabel tituloPremium1, tituloPremium2;
	private JButton btnUnete, btnAbandonar;
	public PanelPremium(VentanaMain v){
		ventana=v; 
		crearPantalla();
	}
	
	private void crearPantalla() {
		JPanel Ventana = new JPanel();
		Ventana.setBorder(new LineBorder(new Color(0, 0, 0)));
		Ventana.setBackground(Color.LIGHT_GRAY);
		//Ventana.setPreferredSize(new Dimension(Constantes.ventana_x_size-35, Constantes.ventana_y_size-180));
		//Ventana.setMaximumSize(Ventana.getPreferredSize());
		fixedSize(Ventana, Constantes.ventana_x_size-35, Constantes.ventana_y_size-180);
		
		add(Ventana);
		
		
		
		
		panelPremium = new JPanel();
		//panelPremium.setLayout(new BoxLayout(panelPremium, BoxLayout.Y_AXIS));
		fixedSize(panelPremium,500,500);
		Ventana.add(panelPremium);
		panelPremium.setBackground(Color.LIGHT_GRAY);
		
		tituloNoPremium1 = new JLabel();
		tituloNoPremium1.setText("¿Aun no eres usuario premium?");
		tituloNoPremium1.setFont(new Font("Arial",Font.BOLD,32));
		tituloNoPremium1.setHorizontalTextPosition(JLabel.CENTER);
		tituloNoPremium1.setVerticalTextPosition(JLabel.CENTER);
		panelPremium.add(tituloNoPremium1);
		
		tituloPremium1 = new JLabel();
		tituloPremium1.setText("¿Quieres dejar de ser premium?");
		tituloPremium1.setFont(new Font("Arial",Font.BOLD,32));
		tituloPremium1.setHorizontalTextPosition(JLabel.CENTER);
		tituloPremium1.setVerticalTextPosition(JLabel.CENTER);
		panelPremium.add(tituloPremium1);
		
		
		tituloNoPremium2 = new JLabel();
		tituloNoPremium2.setText("¿A qué estás esperando?");
		tituloNoPremium2.setFont(new Font("Arial",Font.BOLD,28));
		tituloNoPremium2.setHorizontalTextPosition(JLabel.CENTER);
		tituloNoPremium2.setVerticalTextPosition(JLabel.CENTER);
		panelPremium.add(tituloNoPremium2);
		
		tituloNoPremium3 = new JLabel();
		tituloNoPremium3.setText("Unete ahora para conseguir fabulosas ventajas como:");
		tituloNoPremium3.setFont(new Font("Arial",0,16));
		tituloNoPremium3.setHorizontalTextPosition(JLabel.CENTER);
		tituloNoPremium3.setVerticalTextPosition(JLabel.CENTER);
		panelPremium.add(tituloNoPremium3);
		
		tituloPremium2 = new JLabel();
		tituloPremium2.setText("Dejarás de tener acceso a ventajas como:");
		tituloPremium2.setFont(new Font("Arial",0,16));
		tituloPremium2.setHorizontalTextPosition(JLabel.CENTER);
		tituloPremium2.setVerticalTextPosition(JLabel.CENTER);
		panelPremium.add(tituloPremium2);
		
		JPanel panelPremium2 = new JPanel();
		panelPremium.add(panelPremium2);
		panelPremium2.setBackground(Color.LIGHT_GRAY);
		panelPremium2.setLayout(new BoxLayout(panelPremium2, BoxLayout.Y_AXIS));
		fixedSize(panelPremium2, 700, 300);
		
		JLabel imPDF = new JLabel("Generacion de pdf de tus listas de reproducción");
		imPDF.setHorizontalTextPosition(JLabel.CENTER);
		imPDF.setVerticalTextPosition(JLabel.BOTTOM);
		imPDF.setAlignmentX(CENTER_ALIGNMENT);
		ImageIcon imageIcon = new ImageIcon(VentanaMain.class.getResource("pdfIcon.png")); 
		Image image = imageIcon.getImage(); 
		Image newimg = image.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newimg); 
		imPDF.setIcon(imageIcon);
		panelPremium2.add(imPDF);
		panelPremium2.add(Box.createRigidArea(new Dimension(60, 20)));
		
		JLabel imFiltro = new JLabel("Generacion de pdf de tus listas de reproducción");
		imFiltro.setHorizontalTextPosition(JLabel.CENTER);
		imFiltro.setVerticalTextPosition(JLabel.BOTTOM);
		imFiltro.setAlignmentX(CENTER_ALIGNMENT);
		//Escalado del icono.
		ImageIcon imageIcon2 = new ImageIcon(VentanaMain.class.getResource("filtro.png")); 
		Image image2 = imageIcon2.getImage(); 
		Image newimg2 = image2.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
		imageIcon2 = new ImageIcon(newimg2); 
		imFiltro.setIcon(imageIcon2);
		panelPremium2.add(imFiltro);
		panelPremium2.add(Box.createRigidArea(new Dimension(60, 20)));
		
		JLabel imTen = new JLabel("La lista de los 10 videos mas vistos");
		imTen.setHorizontalTextPosition(JLabel.CENTER);
		imTen.setVerticalTextPosition(JLabel.BOTTOM);
		imTen.setAlignmentX(CENTER_ALIGNMENT);
		ImageIcon imageIcon3 = new ImageIcon(VentanaMain.class.getResource("topTen.png")); 
		Image image3 = imageIcon3.getImage(); 
		Image newimg3 = image3.getScaledInstance(50, 50,  java.awt.Image.SCALE_SMOOTH);
		imageIcon3 = new ImageIcon(newimg3); 
		imTen.setIcon(imageIcon3);
		panelPremium2.add(imTen);
		
		btnUnete = new JButton("¡Unete ya!");
		panelPremium.add(btnUnete);
		
		btnUnete.addActionListener(ev ->{
			controladorAppVideo.cambiarRolUsuario(true);
			ventana.funcionalidadPremium(true);
			ventana.cambiarContenido(Contenido.RECIENTES);
			 JOptionPane.showMessageDialog(null, "¡Ahora eres usuario premium!", "Usuario premium",
						JOptionPane.INFORMATION_MESSAGE);
			 
		});
		
		btnAbandonar = new JButton("Abandonar");
		panelPremium.add(btnAbandonar);
		btnAbandonar.addActionListener(ev ->{
			controladorAppVideo.cambiarRolUsuario(false);
			ventana.funcionalidadPremium(false);
			ventana.cambiarContenido(Contenido.RECIENTES);
			 JOptionPane.showMessageDialog(null, "Has dejado de ser usuario premium", "Usuario premium",
						JOptionPane.INFORMATION_MESSAGE);
			 
		});
	}
	
	public void switchMode() {
		usuario = controladorAppVideo.getUsuario();
		if (usuario.esPremium()){
			tituloPremium1.setVisible(true);
			tituloPremium2.setVisible(true);
			tituloNoPremium1.setVisible(false);
			tituloNoPremium2.setVisible(false);
			tituloNoPremium3.setVisible(false);
			btnAbandonar.setVisible(true);
			btnUnete.setVisible(false);
			
		}
		else {
			tituloPremium1.setVisible(false);
			tituloPremium2.setVisible(false);
			tituloNoPremium1.setVisible(true);
			tituloNoPremium2.setVisible(true);
			tituloNoPremium3.setVisible(true);
			btnAbandonar.setVisible(false);
			btnUnete.setVisible(true);
		}
	}
	
	

	public void setLoginMainButton(JButton b) {
		loginMainButton = b;
	}
		
	private void fixedSize(JComponent c,int x, int y) {
		c.setMinimumSize(new Dimension(x,y));
		c.setMaximumSize(new Dimension(x,y));
		c.setPreferredSize(new Dimension(x,y));
	}
	


}
