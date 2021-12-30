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

import controlador.ControladorTienda;
import modelo.CatalogoUsuarios;
import modelo.CatalogoVentas;
import modelo.Usuario;
import modelo.LineaVenta;
import modelo.Video;
import pulsador.IEncendidoListener;
import pulsador.Luz;
import tds.video.VideoWeb;
import umu.tds.componente.Videos;
import modelo.Venta;

@SuppressWarnings("serial")
public class VentanaMain extends JFrame implements ActionListener{
	

	private JButton btnExplorar, btnMisListas, btnRecientes, btnLogin, btnRegistro, btnLogout, btnPremium, btnNuevaLista;
	private final String panelRegistroCard = "panelRegistroCard";
	private JPanel contenedorPrincipal;
	private JPanel contenido;
	
	//private PanelVerImagen panelVerImagen;
	private PanelLogin panelLogin;
	private PanelAltaCliente panelAltaCliente;
	private PanelAltaVideo panelAltaProducto;
	private PanelCrearVenta panelCrearVenta;
	private PanelExplorar panelExplorar;
	private PanelMisListas panelMisListas;
	
	
	private JButton loginMainButton;
	private JButton playMainButton;
	private JButton btnBusqueda;
	
	private final String panelLoginCard = "panelLoginCard";
	private final String panelExplorarCard = "panelExplorar";
	private final String panelMisListasCard = "panelMisListas";
	private String usuario;
	private JLabel saludoUsuario;
	private boolean logeado = false;
	
	private Luz pulsador;
	
	
	private static ControladorTienda controladorTienda = ControladorTienda.getUnicaInstancia();
	
	private static VideoWeb videoWeb = controladorTienda.getVideoWeb();
	
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
		panelAltaProducto = new PanelAltaVideo(this);
		panelCrearVenta = new PanelCrearVenta(this);
		panelExplorar = new PanelExplorar(this, videoWeb);
		panelExplorar.setPlayMainButton(playMainButton);
		panelExplorar.update(controladorTienda.getEtiquetasDisponibles());
		btnBusqueda = new JButton();
		btnBusqueda.addActionListener(ev -> {
			panelExplorar.updateVideos(controladorTienda.buscarVideos(panelExplorar.getSubcadenaBusqueda(), panelExplorar.getEtiquetasBusqueda()));
		});
		panelExplorar.setBotonBusqueda(btnBusqueda);
		panelMisListas = new PanelMisListas(this, videoWeb);
		
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
		
		Component espacio = Box.createRigidArea(new Dimension(515, 2));
		Opciones.add(espacio);
		
		pulsador = new Luz();
		pulsador.setColor(Color.GREEN);
		pulsador.addEncendidoListener(controladorTienda);
		Opciones.add(pulsador);
		
		btnExplorar.addActionListener(this);
		btnMisListas.addActionListener(this);
		btnRecientes.addActionListener(this);
		btnLogin.addActionListener(this);
		btnRegistro.addActionListener(this);
		btnLogout.addActionListener(this);
		btnNuevaLista.addActionListener(this);
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
			CardLayout cl = (CardLayout)(contenido.getLayout());
		    cl.show(contenido, panelLoginCard);
		    validate();
		    return;
		    
		}
		if (e.getSource() == btnRegistro) {
			CardLayout cl = (CardLayout)(contenido.getLayout());
		    cl.show(contenido, panelRegistroCard);
		    validate();
		    return;
		}
		if (e.getSource() == btnExplorar) {
			
			
			
			/*List<Video> videos = controladorTienda.getVideos();
			
			panelExplorar.updateVideos(videos);
			//panelExplorar.updateResultados();
			*/
			panelExplorar.limpiar();
			panelExplorar.switchMode(Mode.EXPLORAR);
			CardLayout cl = (CardLayout)(contenido.getLayout());
		    cl.show(contenido, panelExplorarCard);
		    validate();
		    return;
		}
		if(e.getSource() == loginMainButton) {
			usuario = panelLogin.getUsuario();
			logeado = true;
			saludoUsuario.setText("Hola " + usuario);
			saludoUsuario.setVisible(true);
			btnLogin.setVisible(false);
			btnRegistro.setVisible(false);
			btnLogout.setVisible(true);
			btnPremium.setVisible(true);
			panelExplorar.setUsuario(usuario);
			validate();
			return;
		}
		if(e.getSource() == btnLogout) {
			logeado = false;
			saludoUsuario.setVisible(false);
			btnLogin.setVisible(true);
			btnRegistro.setVisible(true);
			btnLogout.setVisible(false);
			btnPremium.setVisible(false);
			validate();
			return;
		}
		if(e.getSource() == playMainButton) {
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
			panelMisListas.switchMode(Mode.MISLISTAS);
			CardLayout cl = (CardLayout)(contenido.getLayout());
		    cl.show(contenido, panelMisListasCard);
		    validate();
		    
		    List<Video> videos = controladorTienda.getVideos();
			
			panelMisListas.updateVideos(videos);
			
			return;
		}
		if (e.getSource() == btnNuevaLista) {
			panelExplorar.limpiar();
			panelExplorar.switchMode(Mode.NUEVALISTA);
			CardLayout cl = (CardLayout)(contenido.getLayout());
		    cl.show(contenido, panelExplorarCard);
		    validate();
		    
			return;
		}
	}
	
	public void listadoProductos(JTextArea listado) {
  		listado.setText("Codigo     Nombre                         Precio\n");
  		listado.append("---------- ------------------------------ --------\n");
  		
  		for (Video p: controladorTienda.getVideos()) {
  			String codigo=String.format("%1$10d",p.getTitulo());
  			String nombre=String.format("%1$-30s",p.getUrl());
  			//String precio=String.format("%1$8.2f",p.getPrecio());
  			listado.append(codigo+" "+nombre+"\n");
  		}
  	}
	
	public void listadoClientes(JTextArea listado) {
  		List<Usuario> listaClientes = CatalogoUsuarios.getUnicaInstancia().getClientes();
  		listado.setText("dni        Nombre                         N.ventas\n");
  		listado.append("---------- ------------------------------ --------\n");
  		for (Usuario c: listaClientes) {
  			String dni=String.format("%1$-10s",c.getNombre_completo());
  			String nombre=String.format("%1$-30s",c.getUsuario());
  			//String numVentas=String.format("%1$-8s",String.valueOf(c.getVentas().size()));
  			//listado.append(dni+" "+nombre+" "+numVentas+"\n");
  			listado.append(dni+" "+nombre+"\n");
  		}
	}
	public void listadoVentas(JTextArea listado, Date f1, Date f2) {
  		SimpleDateFormat datef = new SimpleDateFormat("dd/MMM/yyyy");
  		List<Venta> listaVentas;
  		if (f1==null) listaVentas = CatalogoVentas.getUnicaInstancia().getAllVentas();
  		else listaVentas = CatalogoVentas.getUnicaInstancia().getVentasPeriodo(f1, f2);
  		
  		listado.setText("fecha       Nombre                         Total\n");
  		listado.append("----------- ------------------------------ --------\n");
  		System.out.println("hay "+listaVentas.size());
  		for (Venta v: listaVentas) {
  			String fecha=datef.format(v.getFecha());
  			//String nombre=String.format("%1$-30s",v.getCliente().getNombre());
  			String nombre = "Pruebaaaa";
  			String total=String.format("%1$-8s",String.valueOf(v.getTotal()));
  			listado.append(fecha+" "+nombre+" "+total+"\n");
  			mostrarVenta(v);
  		}
  	}
	private void mostrarVenta(Venta v) {
  		System.out.println("-------------------------------------------------------------");
  		System.out.println("Fecha:"+v.getFecha());
  		//System.out.println("Dni:"+v.getCliente().getDni());
  		for(LineaVenta lv:v.getLineasVenta()){
  			//System.out.println(""+lv.getUnidades()+" "+lv.getProducto().getNombre()+" "+lv.getSubTotal());
  		}
  		System.out.println("Total:"+v.getTotal());
  	}

	
}
