package vista;



import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.Image;


import javax.swing.Box;
import javax.swing.BoxLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import controlador.ControladorAppVideo;
import modelo.Usuario;
import pulsador.Luz;
import tds.video.VideoWeb;

@SuppressWarnings("serial")
public class VentanaMain extends JFrame{
	

	private JButton btnExplorar, btnMisListas, btnRecientes, btnLogin, btnRegistro, btnLogout, btnPremium, btnNuevaLista, btnMasVistos;
	
	private JPanel contenedorPrincipal;
	private JPanel contenido;
	
	private PanelLogin panelLogin;
	private PanelRegistro panelRegistro;
	private PanelExplorar panelExplorar;
	private PanelMisListas panelMisListas;
	private PanelPremium panelPremium;
	
	private final String panelRegistroCard = "panelRegistroCard";
	private final String panelLoginCard = "panelLoginCard";
	private final String panelExplorarCard = "panelExplorar";
	private final String panelMisListasCard = "panelMisListas";
	private final String panelPremiumCard = "panelPremium";
	
	private Usuario usuario;
	private JLabel lbSaludoUsuario;
	private Luz pulsador;
	private JComboBox<String> boxFiltros;
	private JPanel panelSaludo, panelSeparador3;
	private static ControladorAppVideo controladorAppVideo = ControladorAppVideo.getUnicaInstancia();
	private static VideoWeb videoWeb = controladorAppVideo.getVideoWeb();
	
	public VentanaMain(){
		//Creamos la ventana con sus propiedades y layout
		setBounds(2560/2-(Constantes.ventana_x_size/2),1440/2-(Constantes.ventana_y_size/2),Constantes.ventana_x_size,Constantes.ventana_y_size );
		setTitle("AppVideo");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		//Icono
		ImageIcon imageIcon = new ImageIcon(VentanaMain.class.getResource("iconoAppVideo.jpg")); 
		Image image = imageIcon.getImage(); 
		Image newimg = image.getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH);
		this.setIconImage(newimg);
		
		
		contenido = new JPanel();
		contenido.setLayout(new CardLayout(0,0));
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		
		//Creamos el menú y lo añadimos a la ventana
		configurarMenu();
		this.add(contenedorPrincipal);
		
		//Creamos los paneles de nuestra aplicación
		panelLogin =  new PanelLogin(this);
		panelRegistro = new PanelRegistro(this);
		panelExplorar = new PanelExplorar(this);
		panelMisListas = new PanelMisListas(this);
		panelPremium = new PanelPremium(this);
		
		//Los añadimos a la ventana. Cambiaremos de panel usando el CardLayout
		contenido.add(panelLogin, panelLoginCard);
		contenido.add(panelRegistro, panelRegistroCard);
		contenido.add(panelExplorar, panelExplorarCard);
		contenido.add(panelMisListas, panelMisListasCard);
		contenido.add(panelPremium, panelPremiumCard);
		
		cambiarContenido(Contenido.LOGIN);
	    this.add(contenido);
		setVisible(true);
	}
	
	//Crea la barra del menu princial de la aplicación
	private void configurarMenu(){
		contenedorPrincipal = new JPanel();
		contenedorPrincipal.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));  
		JPanel Menu = new JPanel();
		Menu.setLayout(new FlowLayout(FlowLayout.LEFT,10,2));  
		Menu.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		Menu.setPreferredSize(new Dimension(Constantes.ventana_x_size-35,60));
		Menu.setMaximumSize(Menu.getPreferredSize());
		contenedorPrincipal.add(Menu);
		
		//Imagen AppVideo
		JLabel imagenMenu = new JLabel("");
		ImageIcon imageIcon = new ImageIcon(VentanaMain.class.getResource("Titulo.png")); 
		Image image = imageIcon.getImage(); 
		Image newimg = image.getScaledInstance(656/7, 278/7,  java.awt.Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newimg); 
		imagenMenu.setIcon(imageIcon);
		Menu.add(imagenMenu);
		
		
		
		//Creamos los componentes que definirán la barra del menú
		panelSaludo = new JPanel();
		Constantes.fixedSize(panelSaludo, 660, 20);
		panelSaludo.setLayout(new FlowLayout(FlowLayout.LEFT));
		lbSaludoUsuario = new JLabel();
		lbSaludoUsuario.setVisible(false);
		panelSaludo.add(lbSaludoUsuario);
		Menu.add(panelSaludo);
		
		Component rA1 = Box.createRigidArea(new Dimension(1, 50));
		Menu.add(rA1);
		
		btnRegistro = new JButton("Registro");
		btnRegistro.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Menu.add(btnRegistro);
		
		btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Menu.add(btnLogin);
			
		
		btnLogout = new JButton("Logout");
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnLogout.setVisible(false);
		Menu.add(btnLogout);
		
		btnPremium = new JButton("Premium");
		btnPremium.setForeground(Color.RED);
		btnPremium.setVisible(false);
		btnPremium.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Menu.add(btnPremium);
		
		//Creamos el panel de las opciones (Botones para cambiar de panel)
		JPanel Opciones = new JPanel();
		Opciones.setLayout(new FlowLayout(FlowLayout.LEFT));
		Opciones.setPreferredSize(new Dimension(Constantes.ventana_x_size-35, 105));
		Opciones.setMaximumSize(new Dimension(Constantes.ventana_x_size-35, 105));
		contenedorPrincipal.add(Opciones);
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
		
		
		panelSeparador3 = new JPanel();
		Constantes.fixedSize(panelSeparador3,403,2);
		Opciones.add(panelSeparador3);
		
		pulsador = new Luz();
		pulsador.setColor(Color.GREEN);
		pulsador.addEncendidoListener(controladorAppVideo);
		Opciones.add(pulsador);
		
		//Creamos los actionListeners para cada uno de los botones
		actionListeners();
		logeado(false); 
	}
	
	//Se utilizará la función "cambiarContenido" para realizar los ajustes pertinentes y cambiar el contenido del panel
	private void actionListeners() {
		btnExplorar.addActionListener(ev -> {
			cambiarContenido(Contenido.EXPLORAR);
		});
		btnMisListas.addActionListener(ev -> {
			cambiarContenido(Contenido.MISLISTAS);
		});;
		btnRecientes.addActionListener(ev -> {
			cambiarContenido(Contenido.RECIENTES);
		});
		btnLogin.addActionListener(ev -> {
			cambiarContenido(Contenido.LOGIN);
		});
		btnRegistro.addActionListener(ev -> {
			cambiarContenido(Contenido.REGISTRO);
		});
		btnLogout.addActionListener(ev -> {
			cambiarContenido(Contenido.LOGOUT);
		});
		btnNuevaLista.addActionListener(ev -> {
			cambiarContenido(Contenido.NUEVALISTA);
		});
		btnPremium.addActionListener(ev -> {
			cambiarContenido(Contenido.PREMIUM);
		});
		btnMasVistos.addActionListener(ev -> {
			cambiarContenido(Contenido.MASVISTOS);
		});
	}
	
	//Para cada caso, se realizarán unos ajustes.
	public void cambiarContenido(Contenido c) {
		switch(c) {
		case REGISTRO:
			registro();
			cambiarCard(panelRegistroCard);
			break;
		case LOGIN:
			cambiarCard(panelLoginCard);
			break;
		case LOGGED:
			login();
			cambiarCard(panelMisListasCard);
			break;
		case LOGOUT:
			logout();
			cambiarCard(panelLoginCard);
			break;
		case EXPLORAR:
			explorar();
			cambiarCard(panelExplorarCard);
			break;
		case REPRODUCTOR:
			reproductor();
			cambiarCard(panelMisListasCard);
			break;
		case MISLISTAS:
			misListas();
			cambiarCard(panelMisListasCard);
			break;
		case RECIENTES:
			recientes();
			cambiarCard(panelMisListasCard);
			break;
		case NUEVALISTA:
			nuevaLista();
			cambiarCard(panelExplorarCard);
			break;
		case MASVISTOS:
			masVistos();
			cambiarCard(panelMisListasCard);
			break;
		case PREMIUM:
			premium();
			cambiarCard(panelPremiumCard);
			break;
		default:
			break;
		}
		
	}
	
	//Función para cambiar el contenido de los paneles haciendo uso del CardLayout
	private void cambiarCard(String panelCard) {
		CardLayout cl = (CardLayout)(contenido.getLayout());
	    cl.show(contenido, panelCard);
	    validate();
	}
	
	//Conjunto de funciones asociadas a cada panel. Cada una de estas funciones hará cambios en la visibilidad de los componentes y 
	//actualizará las estructuras con información otorgada por el controlador.
	private void registro() {
		videoWeb.cancel();
	}
	private void login() {
		usuario = controladorAppVideo.getUsuario();
		logeado(true);
		panelMisListas.setUsuario(usuario);
		lbSaludoUsuario.setText("Hola " + usuario.getNombre_completo());
		lbSaludoUsuario.setVisible(true);
		Constantes.fixedSize(panelSaludo, 650, 20);
		btnLogin.setVisible(false);
		btnRegistro.setVisible(false);
		btnLogout.setVisible(true);
		btnPremium.setVisible(true);
		panelExplorar.setUsuario(usuario);
		panelMisListas.switchMode(Mode.RECIENTES);
	}
	private void logout() {
		logeado(false);
		lbSaludoUsuario.setVisible(false);
		Constantes.fixedSize(panelSaludo, 667, 20);
		btnLogin.setVisible(true);
		btnRegistro.setVisible(true);
		btnLogout.setVisible(false);
		btnPremium.setVisible(false);
	}
	private void explorar() {
		videoWeb.cancel();
		panelExplorar.limpiar();
		panelExplorar.update(controladorAppVideo.getEtiquetasDisponibles());
		panelExplorar.switchMode(Mode.EXPLORAR);
	}
	private void reproductor() {
		videoWeb.cancel();
		panelMisListas.switchMode(Mode.REPRODUCTOR);
		panelMisListas.setVideo(panelExplorar.getSelectedVideo());
	}
	private void misListas() {
		videoWeb.cancel();
		panelMisListas.switchMode(Mode.MISLISTAS);
		panelMisListas.updateBoxListas();
	}
	
	private void recientes() {
		videoWeb.cancel();
		panelMisListas.switchMode(Mode.RECIENTES);
		
	}
	private void nuevaLista() {
		videoWeb.cancel();
		panelExplorar.limpiar();
		panelExplorar.switchMode(Mode.NUEVALISTA);
	}
	private void masVistos() {
		videoWeb.cancel();
		panelMisListas.switchMode(Mode.MASVISTOS);
	}
	private void premium() {
		panelPremium.switchMode();
	}
	
	//Función encargada de distinguir entre los estados de estar un usuario logeado o no en la aplicación.
	private void logeado(boolean b) {
		btnExplorar.setEnabled(b);
		btnMisListas.setEnabled(b);
		btnRecientes.setEnabled(b);
		btnNuevaLista.setEnabled(b);
		funcionalidadPremium(b);
		if(b) {
			boxFiltros.setSelectedItem((Object) usuario.getFiltroString());
		}
	}

	//Función encargada de mostrar la funcionalidad premium dependiendo del rol del usuario logeado.
	public void funcionalidadPremium(boolean b) {
		if(b && usuario.esPremium()) {
			btnMasVistos.setEnabled(true);
			boxFiltros.setVisible(true);
			Constantes.fixedSize(panelSeparador3,250,2);
		}
		else {
			btnMasVistos.setEnabled(false);
			boxFiltros.setVisible(false);
			Constantes.fixedSize(panelSeparador3,403,2);
		}
	}
}
