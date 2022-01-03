package vista;

import java.awt.EventQueue;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import controlador.ControladorAppVideo;
import modelo.CatalogoUsuarios;
import modelo.Usuario;
import modelo.LineaVenta;
import modelo.Video;
import pulsador.IEncendidoListener;
import pulsador.Luz;
import tds.video.VideoWeb;
import umu.tds.componente.Videos;

@SuppressWarnings("serial")
public class VentanaMain extends JFrame implements ActionListener{
	

	private JButton btnExplorar, btnMisListas, btnRecientes, btnLogin, btnRegistro, btnLogout, btnPremium, btnNuevaLista, btnMasVistos;
	private final String panelRegistroCard = "panelRegistroCard";
	private JPanel contenedorPrincipal;
	private JPanel contenido;
	
	//private PanelVerImagen panelVerImagen;
	private PanelLogin panelLogin;
	private PanelAltaCliente panelAltaCliente;
	private PanelExplorar panelExplorar;
	private PanelMisListas panelMisListas;
	private PanelPremium panelPremium;
	
	private JButton loginMainButton;
	private JButton playMainButton;
	private JButton btnBusqueda;
	
	private final String panelLoginCard = "panelLoginCard";
	private final String panelExplorarCard = "panelExplorar";
	private final String panelMisListasCard = "panelMisListas";
	private final String panelPremiumCard = "panelPremium";
	private Usuario usuario;
	private JLabel saludoUsuario;
	private boolean logeado = false;
	
	private Luz pulsador;
	
	private JComboBox<String> boxFiltros;
	
	private JPanel separador;
	
	private static ControladorAppVideo controladorAppVideo = ControladorAppVideo.getUnicaInstancia();
	
	private static VideoWeb videoWeb = controladorAppVideo.getVideoWeb();
	
	public VentanaMain(){
		//setSize(Constantes.ventana_x_size,Constantes.ventana_y_size);
		setBounds(2560/2-(Constantes.ventana_x_size/2),1440/2-(Constantes.ventana_y_size/2),Constantes.ventana_x_size,Constantes.ventana_y_size );
		setTitle("AppVideo");
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		//contenedorPrincipal= (JPanel) this.getContentPane();
		configurarMenu();
		this.add(contenedorPrincipal);
		//this.add(contenido);
		
		/*crear panels*/
		//panelVerImagen = new PanelVerImagen();
		panelLogin =  new PanelLogin(this);
		loginMainButton = new JButton();
		loginMainButton.addActionListener(this);
		playMainButton = new JButton();
		playMainButton.addActionListener(this);
		panelLogin.setLoginMainButton(loginMainButton);
		panelAltaCliente = new PanelAltaCliente(this);
		panelExplorar = new PanelExplorar(this, videoWeb);
		panelExplorar.setPlayMainButton(playMainButton);
		panelExplorar.update(controladorAppVideo.getEtiquetasDisponibles());
		btnBusqueda = new JButton();
		btnBusqueda.addActionListener(ev -> {
			panelExplorar.updateVideos(controladorAppVideo.buscarVideos(panelExplorar.getSubcadenaBusqueda(), panelExplorar.getEtiquetasBusqueda()));
		});
		panelExplorar.setBotonBusqueda(btnBusqueda);
		panelMisListas = new PanelMisListas(this, videoWeb);
		panelPremium = new PanelPremium(this);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		//panel inicial
		//panelVerImagen.cambiarImagen("/otroFondoTienda.jpg");
		//setContentPane(panelLogin);
		contenido = new JPanel();
		contenido.setLayout(new CardLayout(0,0));
		contenido.add(panelLogin, panelLoginCard);
		
		contenido.add(panelAltaCliente, panelRegistroCard);
		
		contenido.add(panelExplorar, panelExplorarCard);
		
		contenido.add(panelMisListas, panelMisListasCard);
		
		contenido.add(panelPremium, panelPremiumCard);
		//contenido.add(panelAltaCliente, panelRegistroCard);
		
		CardLayout c1 = (CardLayout)(contenido.getLayout());
		c1.show(contenido, "panelLoginCard");
		
		add(contenido);
		//add(panelLogin);
		//pack();
		setVisible(true);
	}
	
	/*Crea la barra del menu principal de la aplicaciï¿½n*/
	private void configurarMenu(){
		contenedorPrincipal = new JPanel();
		contenedorPrincipal.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));  
		//add(contenedorPrincipal);
		JPanel Menu = new JPanel();
		//panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		Menu.setLayout(new FlowLayout(FlowLayout.LEFT,10,2));  
		Menu.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		Menu.setPreferredSize(new Dimension(Constantes.ventana_x_size-35,60));
		Menu.setMaximumSize(Menu.getPreferredSize());
		//frmAppVideo.getContentPane().add(Menu);
		contenedorPrincipal.add(Menu);
		
		JLabel imagenMenu = new JLabel("");
		//Escalado del icono.
		ImageIcon imageIcon = new ImageIcon(VentanaMain.class.getResource("/Titulo.png")); 
		Image image = imageIcon.getImage(); 
		Image newimg = image.getScaledInstance(656/7, 278/7,  java.awt.Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newimg); 
		imagenMenu.setIcon(imageIcon);
		Menu.add(imagenMenu);
		
		
		
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(70, 50));
		Menu.add(rigidArea_1);
		
		saludoUsuario = new JLabel();
		saludoUsuario.setVisible(false);
		Menu.add(saludoUsuario);
		
		Component rigidArea_1_1 = Box.createRigidArea(new Dimension(70, 50));
		Menu.add(rigidArea_1_1);
		
		btnRegistro = new JButton("Registro");
		btnRegistro.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Menu.add(btnRegistro);
		
		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Menu.add(btnLogin);
		
		
		
		Component rigidArea = Box.createRigidArea(new Dimension(70, 50));
		Menu.add(rigidArea);			//CAMBIAR Y PONER CONSTANTES
		
		btnLogout = new JButton("Logout");
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLogout.setVisible(false);
		Menu.add(btnLogout);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(140, 50));
		Menu.add(rigidArea_2);
		
		btnPremium = new JButton("Premium");
		btnPremium.setForeground(Color.RED);
		btnPremium.setVisible(false);
		btnPremium.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Menu.add(btnPremium);
		
		JPanel Opciones = new JPanel();
		Opciones.setLayout(new FlowLayout(FlowLayout.LEFT));
		Opciones.setPreferredSize(new Dimension(Constantes.ventana_x_size-35, 105));
		Opciones.setMaximumSize(new Dimension(Constantes.ventana_x_size-35, 105));
		contenedorPrincipal.add(Opciones);
		//frmAppVideo.getContentPane().add(Opciones);
		btnExplorar = new JButton("Explorar");
		btnExplorar.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Opciones.add(btnExplorar);
		
		btnMisListas = new JButton("Mis Listas");
		btnMisListas.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Opciones.add(btnMisListas);
		
		btnRecientes = new JButton("Recientes");
		btnRecientes.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Opciones.add(btnRecientes);
		
		btnNuevaLista = new JButton("Nueva Lista");
		btnNuevaLista.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Opciones.add(btnNuevaLista);
		
		btnMasVistos = new JButton("Mas Vistos");
		btnMasVistos.setForeground(Color.RED);
		btnMasVistos.setEnabled(false);
		btnMasVistos.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Opciones.add(btnMasVistos);
		
		boxFiltros = new JComboBox<String>();
		for(String filtro : controladorAppVideo.getFiltrosDisponibles()) {
			boxFiltros.addItem(filtro);
		}
		boxFiltros.setVisible(false);
		boxFiltros.addActionListener(ev ->{
			if(boxFiltros.getSelectedItem() != null){
				controladorAppVideo.setFiltroUsuario(boxFiltros.getSelectedItem().toString());
			}
		});
		Opciones.add(boxFiltros);
		
		
		separador = new JPanel();
		Constantes.fixedSize(separador,403,2);
		
		
		//Component espacio = Box.createRigidArea(new Dimension(400, 2));
		Opciones.add(separador);
		
		pulsador = new Luz();
		pulsador.setColor(Color.GREEN);
		pulsador.addEncendidoListener(controladorAppVideo);
		Opciones.add(pulsador);
		
		btnExplorar.addActionListener(this);
		btnMisListas.addActionListener(this);
		btnRecientes.addActionListener(this);
		btnLogin.addActionListener(this);
		btnRegistro.addActionListener(this);
		btnLogout.addActionListener(this);
		btnNuevaLista.addActionListener(this);
		btnPremium.addActionListener(this);
		btnMasVistos.addActionListener(this);
		logeado(false); 
	}
	
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMain principal = new VentanaMain();
					principal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnLogin) {
			videoWeb.cancel();
			CardLayout cl = (CardLayout)(contenido.getLayout());
		    cl.show(contenido, panelLoginCard);
		    validate();
		    return;
		    
		}
		if (e.getSource() == btnRegistro) {
			videoWeb.cancel();
			CardLayout cl = (CardLayout)(contenido.getLayout());
		    cl.show(contenido, panelRegistroCard);
		    validate();
		    return;
		}
		if (e.getSource() == btnExplorar) {
			
			
			videoWeb.cancel();
			/*List<Video> videos = controladorAppVideo.getVideos();
			
			panelExplorar.updateVideos(videos);
			//panelExplorar.updateResultados();
			*/
			panelExplorar.limpiar();
			panelExplorar.update(controladorAppVideo.getEtiquetasDisponibles());
			panelExplorar.switchMode(Mode.EXPLORAR);
			CardLayout cl = (CardLayout)(contenido.getLayout());
		    cl.show(contenido, panelExplorarCard);
		    validate();
		    return;
		}
		if(e.getSource() == loginMainButton) {
			usuario = controladorAppVideo.getUsuario();
			logeado(true);
			panelMisListas.setUsuario(usuario);
			saludoUsuario.setText("Hola " + usuario.getNombre_completo());
			saludoUsuario.setVisible(true);
			btnLogin.setVisible(false);
			btnRegistro.setVisible(false);
			btnLogout.setVisible(true);
			btnPremium.setVisible(true);
			panelExplorar.setUsuario(usuario);

			panelMisListas.switchMode(Mode.RECIENTES);
			//panelMisListas.updateBoxListas();
			CardLayout cl = (CardLayout)(contenido.getLayout());
		    cl.show(contenido, panelMisListasCard);
		    validate();
			return;
		}
		if(e.getSource() == btnLogout) {
			logeado(false);
			saludoUsuario.setVisible(false);
			btnLogin.setVisible(true);
			btnRegistro.setVisible(true);
			btnLogout.setVisible(false);
			btnPremium.setVisible(false);
			CardLayout cl = (CardLayout)(contenido.getLayout());
		    cl.show(contenido, panelLoginCard);
			validate();
			return;
		}
		if(e.getSource() == playMainButton) {
			videoWeb.cancel();
			panelMisListas.switchMode(Mode.REPRODUCTOR);
			panelMisListas.setVideo(panelExplorar.getSelectedVideo());
			/*CardLayout cl = (CardLayout)(contenido.getLayout());
		    cl.show(contenido, panelReproductorCard);*/
			CardLayout cl = (CardLayout)(contenido.getLayout());
		    cl.show(contenido, panelMisListasCard);
		    validate();
			return;
		}
		if (e.getSource() == btnMisListas) {
			videoWeb.cancel();
			panelMisListas.switchMode(Mode.MISLISTAS);
			panelMisListas.updateBoxListas();
			CardLayout cl = (CardLayout)(contenido.getLayout());
		    cl.show(contenido, panelMisListasCard);
		    validate();
			return;
		}
		if (e.getSource() == btnNuevaLista) {
			videoWeb.cancel();
			panelExplorar.limpiar();
			panelExplorar.switchMode(Mode.NUEVALISTA);
			CardLayout cl = (CardLayout)(contenido.getLayout());
		    cl.show(contenido, panelExplorarCard);
		    validate();
		    
			return;
		}
		if(e.getSource() == btnPremium) {
			//btnPremium.setEnabled(false);
			panelPremium.switchMode();
			CardLayout cl = (CardLayout)(contenido.getLayout());
		    cl.show(contenido, panelPremiumCard);
		    validate();
			/*if(usuario.esPremium()) {
				controladorAppVideo.cambiarRolUsuario(usuario, false);
				funcionalidadPremium(false);
			}
			else {
				controladorAppVideo.cambiarRolUsuario(usuario, true);
				funcionalidadPremium(true);
				
			}*/
			
			
		}
		if(e.getSource() == btnMasVistos) {
			videoWeb.cancel();
			panelMisListas.switchMode(Mode.MASVISTOS);
			//panelMisListas.updateBoxListas();
			CardLayout cl = (CardLayout)(contenido.getLayout());
		    cl.show(contenido, panelMisListasCard);
		    validate();
			return;
		}
		if(e.getSource() == btnRecientes) {
			videoWeb.cancel();
			panelMisListas.switchMode(Mode.RECIENTES);
			//panelMisListas.updateBoxListas();
			CardLayout cl = (CardLayout)(contenido.getLayout());
		    cl.show(contenido, panelMisListasCard);
		    validate();
			return;
		}
	}
	
	private void logeado(boolean b) {
		btnExplorar.setEnabled(b);
		btnMisListas.setEnabled(b);
		btnRecientes.setEnabled(b);
		btnNuevaLista.setEnabled(b);
		funcionalidadPremium(b);
		if(b) {
			boxFiltros.setSelectedItem((Object) controladorAppVideo.getFiltroUsuario());
		}
	}

	public void funcionalidadPremium(boolean b) {
		if(b && usuario.esPremium()) {
			btnMasVistos.setEnabled(true);
			boxFiltros.setVisible(true);
			Constantes.fixedSize(separador,250,2);
		}
		else {
			btnMasVistos.setEnabled(false);
			boxFiltros.setVisible(false);
			Constantes.fixedSize(separador,403,2);
		}
	}
	
}
