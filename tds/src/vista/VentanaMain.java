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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import modelo.Venta;

@SuppressWarnings("serial")
public class VentanaMain extends JFrame implements ActionListener{
	

	//Comentario
	private JMenuBar menuPrincipal;
	private JMenu mCliente, mVenta, mProducto, mOtros;
	private JMenuItem mniAltaCli, mniListadoCli;
	private JMenuItem mniCrearVenta, mniListadoVentas,mniListadoFechas;
	private JMenuItem mniAltaPro, mniListadoPro;
	private JButton btnNewButton_3, btnNewButton_4;
	private JRadioButtonMenuItem mniTiendaCutre,mniTiendaUniversidad;
	
	private JPanel contenedorPrincipal;
	private JPanel contenido;
	
	//private PanelVerImagen pantallaVerImagen;
	private PanelLogin pantallaLogin;
	private PanelAltaCliente pantallaAltaCliente;
	private PanelAltaVideo pantallaAltaProducto;
	private PanelCrearVenta pantallaCrearVenta;
	
	
	private final String pantallaLoginCard = "pantallaLoginCard";

	
	
	public VentanaMain(){
		//setSize(Constantes.ventana_x_size,Constantes.ventana_y_size);
		setBounds(2560/2-(Constantes.ventana_x_size/2),1440/2-(Constantes.ventana_y_size/2),Constantes.ventana_x_size,Constantes.ventana_y_size );
		setTitle("AppMusic");
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		//contenedorPrincipal= (JPanel) this.getContentPane();
		configurarMenu();
		this.add(contenedorPrincipal);
		//this.add(contenido);
		
		/*crear pantallas*/
		//pantallaVerImagen = new PanelVerImagen();
		pantallaLogin =  new PanelLogin(this);
		pantallaAltaCliente = new PanelAltaCliente(this);
		pantallaAltaProducto = new PanelAltaVideo(this);
		pantallaCrearVenta = new PanelCrearVenta(this);
		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		//Pantalla inicial
		//pantallaVerImagen.cambiarImagen("/otroFondoTienda.jpg");
		//setContentPane(pantallaLogin);
		contenido = new JPanel();
		contenido.setLayout(new CardLayout(0,0));
		contenido.add(pantallaLogin, pantallaLoginCard);
		
		contenido.add(pantallaAltaCliente, "altaCliente");
		
		CardLayout c1 = (CardLayout)(contenido.getLayout());
		c1.show(contenido, "pantallaLoginCard");
		
		add(contenido);
		//add(pantallaLogin);
		//pack();
		setVisible(true);
	}
	
	/*Crea la barra del menu principal de la aplicaci�n*/
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
		
		JButton btnNewButton = new JButton("Registro");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));

		
		
		JLabel lblNewLabel = new JLabel("");
		
		//Escalado del icono.
		ImageIcon imageIcon = new ImageIcon(VentanaMain.class.getResource("/Titulo.png")); 
		Image image = imageIcon.getImage(); 
		Image newimg = image.getScaledInstance(656/7, 278/7,  java.awt.Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newimg); 
		
		lblNewLabel.setIcon(imageIcon);
		
		Menu.add(lblNewLabel);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(70, 50));
		Menu.add(rigidArea_1);
		
		JLabel lblNewLabel_1 = new JLabel("Hola nosequien");
		Menu.add(lblNewLabel_1);
		
		Component rigidArea_1_1 = Box.createRigidArea(new Dimension(70, 50));
		Menu.add(rigidArea_1_1);
		Menu.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Login");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Menu.add(btnNewButton_1);
		
		
		Component rigidArea = Box.createRigidArea(new Dimension(70, 50));
		Menu.add(rigidArea);			//CAMBIAR Y PONER CONSTANTES
		
		JButton btnNewButton_2 = new JButton("Logout");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Menu.add(btnNewButton_2);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(140, 50));
		Menu.add(rigidArea_2);
		
		JButton btnNewButton_2_1 = new JButton("Premium");
		btnNewButton_2_1.setForeground(Color.RED);
		btnNewButton_2_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Menu.add(btnNewButton_2_1);
		
		JPanel Opciones = new JPanel();
		Opciones.setLayout(new FlowLayout(FlowLayout.LEFT));
		Opciones.setPreferredSize(new Dimension(Constantes.ventana_x_size-35, 105));
		Opciones.setMaximumSize(new Dimension(Constantes.ventana_x_size-35, 105));
		//frmAppVideo.getContentPane().add(Opciones);
		 btnNewButton_3 = new JButton("Explorar");
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Opciones.add(btnNewButton_3);
		
		 btnNewButton_4 = new JButton("Mis Listas");
		btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Opciones.add(btnNewButton_4);
		
		JButton btnNewButton_4_1 = new JButton("Recientes");
		btnNewButton_4_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Opciones.add(btnNewButton_4_1);
		
		JButton btnNewButton_4_2 = new JButton("Nueva Lista");
		btnNewButton_4_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Opciones.add(btnNewButton_4_2);
		contenedorPrincipal.add(Opciones);
		
		
		/*menuPrincipal=new JMenuBar(); //barra del menu
		 
		
		//opciones principales
		mCliente= new JMenu("Cliente");	mVenta= new JMenu("Venta");
		mProducto= new JMenu("Producto"); mOtros= new JMenu("Otros");
		//Submenu cliente
		mniAltaCli= new JMenuItem("Alta de cliente");
		mniListadoCli= new JMenuItem("Listado de clientes");
		//Submenu venta
		mniCrearVenta= new JMenuItem("Crear Venta");
		mniListadoVentas= new JMenuItem("Listado de ventas");
		mniListadoFechas= new JMenuItem("Listado de un periodo");
		//*************************mascosas
		//Submenu producto
		mniAltaPro= new JMenuItem("Alta de Producto");
		mniListadoPro= new JMenuItem("Listado de Productos");
		//Submenu otros
		mniTiendaCutre= new JRadioButtonMenuItem("Ver tienda cutre");
		mniTiendaUniversidad= new JRadioButtonMenuItem("Ver tienda universitaria");
		mniTiendaUniversidad.setSelected(true);
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(mniTiendaCutre); buttonGroup.add(mniTiendaUniversidad);
		
		mCliente.add(mniAltaCli); mCliente.add(mniListadoCli);
		mVenta.add(mniCrearVenta); mVenta.add(mniListadoVentas); mVenta.add(mniListadoFechas);
		mProducto.add(mniAltaPro); mProducto.add(mniListadoPro);
		mOtros.add(mniTiendaCutre); mOtros.add(mniTiendaUniversidad);
		
		menuPrincipal.add(mCliente); 
		menuPrincipal.add(mVenta);
		menuPrincipal.add(mProducto); 
		menuPrincipal.add(mOtros);*/
		
		//Manejadores
		/*mniAltaCli.addActionListener(this);
		mniListadoCli.addActionListener(this);
		
		mniCrearVenta.addActionListener(this);
		mniListadoVentas.addActionListener(this);
		mniListadoFechas.addActionListener(this);
		
		mniAltaPro.addActionListener(this);
		mniListadoPro.addActionListener(this);
		
		mniTiendaCutre.addActionListener(this);
		mniTiendaUniversidad.addActionListener(this);*/
		
		btnNewButton_3.addActionListener(this);
		btnNewButton_4.addActionListener(this);
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
		if (e.getSource()== mniAltaCli) { 
			setContentPane(pantallaAltaCliente);
			validate();
			return;	
		}
		
		if (e.getSource()== mniListadoCli) {
			VentanaListados lc= new VentanaListados("Listado de clientes");
			lc.setLocationRelativeTo(this);
			lc.setVisible(true);
			listadoClientes(lc.getTextArea());
			return;
		}
		
		if (e.getSource()== mniCrearVenta) {
			ControladorTienda.getUnicaInstancia().crearVenta();
			pantallaCrearVenta.cargarVideos(); //actualizar combobox con productos nuevos
			setContentPane(pantallaCrearVenta);
			validate();
			return;	
		}
		
		if (e.getSource()== mniListadoVentas) {
			VentanaListados lc= new VentanaListados("Listado de Ventas");
			lc.setLocationRelativeTo(this);
			lc.setVisible(true);
			listadoVentas(lc.getTextArea(),null,null);
			return;
		}

		if (e.getSource()== mniListadoFechas) {
			VentanaEntreFechas lef= new VentanaEntreFechas(this);
			lef.setLocationRelativeTo(this);
			lef.setVisible(true);
			return;
		}
		
		if (e.getSource()== mniAltaPro) { 
			setContentPane(pantallaAltaProducto);
			validate();
			return;	
		}
		
		if (e.getSource()== mniListadoPro) {
			VentanaListados lc= new VentanaListados("Listado de Productos");
			lc.setLocationRelativeTo(this);
			lc.setVisible(true);
			listadoProductos(lc.getTextArea());
			return;
		}
		
		/*if (e.getSource()== mniTiendaCutre) {
			pantallaVerImagen.cambiarImagen("/fondoTienda.jpg");
			setContentPane(pantallaVerImagen);
			pack();
			return;
		}
		if (e.getSource()== mniTiendaUniversidad) {
			pantallaVerImagen.cambiarImagen("/otroFondoTienda.jpg");
			setContentPane(pantallaVerImagen);
			pack();
			return;
		}*/
		if (e.getSource() == btnNewButton_3) {
			CardLayout cl = (CardLayout)(contenido.getLayout());
		    cl.show(contenido, pantallaLoginCard);
		    
		}
		if (e.getSource() == btnNewButton_4) {
			CardLayout cl = (CardLayout)(contenido.getLayout());
		    cl.show(contenido, "altaCliente");
		}
	}
	
	public void listadoProductos(JTextArea listado) {
  		listado.setText("Codigo     Nombre                         Precio\n");
  		listado.append("---------- ------------------------------ --------\n");
  		
  		for (Video p: ControladorTienda.getUnicaInstancia().getVideos()) {
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
