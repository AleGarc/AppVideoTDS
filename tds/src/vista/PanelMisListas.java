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
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import controlador.ControladorTienda;
import modelo.Video;
import modelo.VideoDisplay;
import modelo.VideoDisplayListRenderer;
import tds.video.VideoWeb;


public class PanelMisListas extends JPanel{

	private VentanaMain ventana;
	
	private JButton loginMainButton;
	private JPanel panelIzquierdo;
	private JPanel panelDerecho;
	private JPanel panelDerechoInv;
	private String usuario;
	private JLabel tituloVideo;

	DefaultListModel<VideoDisplay> modelVideos = new DefaultListModel<VideoDisplay>();
	
	private static VideoWeb videoWeb;
	
	public PanelMisListas(VentanaMain v, VideoWeb vWeb){
		ventana=v; 
		videoWeb = vWeb;
		crearPantalla();
	}
	
	private void crearPantalla() {
		JPanel Ventana = new JPanel();
		Ventana.setBorder(new LineBorder(new Color(0, 0, 0)));
		Ventana.setBackground(Color.LIGHT_GRAY);
		Ventana.setPreferredSize(new Dimension(Constantes.ventana_x_size-35, Constantes.ventana_y_size-180));
		Ventana.setMaximumSize(Ventana.getPreferredSize());
		
	
	      
		Ventana.setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
		add(Ventana);
		
		
		
		panelIzquierdo = new JPanel();
		panelIzquierdo.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelIzquierdo.setBackground(Color.LIGHT_GRAY);
		panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
		fixedSize(panelIzquierdo, 200,525 );
		Ventana.add(panelIzquierdo);
		JLabel txtLista = new JLabel("Seleccione la lista:");
		panelIzquierdo.add(txtLista);
		JComboBox<String> boxListas = new JComboBox<String>();
		boxListas.addItem("Prueba");
		boxListas.addItem("Prueba2");
		boxListas.setMaximumSize(new Dimension(292,20));
		boxListas.setEditable(false);
		boxListas.addItemListener(ev -> {
			System.out.println(boxListas.getSelectedItem());
		});
		panelIzquierdo.add(boxListas);
		JButton btnReproducir = new JButton("Reproducir");
		panelIzquierdo.add(btnReproducir);
		
		/*JPanel pnPrueba = new JPanel();
		pnPrueba.setLayout(new BoxLayout(pnPrueba, BoxLayout.Y_AXIS));
		pnPrueba.setBackground(Color.LIGHT_GRAY);
		//panelIzquierdo.add(pnPrueba);
		JScrollPane scrollerResultados = new JScrollPane(pnPrueba);
		scrollerResultados.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollerResultados.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		//scrollerResultados.getViewport().setBackground(Color.LIGHT_GRAY);
		scrollerResultados.setBorder(new LineBorder(new Color(0, 0, 0)));*/
		//pnPrueba.add(prueba);
		
		
		
		/*JLabel prueba = new JLabel("VAMOS");
		ImageIcon imageIcon = new ImageIcon(VentanaMain.class.getResource("/Titulo.png")); 
		Image image = imageIcon.getImage(); 
		Image newimg = image.getScaledInstance(656/7, 278/7,  java.awt.Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newimg); 
		prueba.setIcon(imageIcon);*/
		
		
		JList<VideoDisplay> lista = new JList<VideoDisplay>();
		lista.setBackground(Color.LIGHT_GRAY);
		lista.setBorder(new LineBorder(new Color(0, 0, 0)));
		lista.setCellRenderer(new VideoDisplayListRenderer());
		lista.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		lista.setVisibleRowCount(-1);
		lista.setModel(modelVideos);
		
		JScrollPane scrollerResultados = new JScrollPane(lista);
		scrollerResultados.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollerResultados.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		//fixedSize(scrollerResultados, 700,439);
		panelIzquierdo.add(scrollerResultados);
		
		//fixedSize(scrollerResultados, 204, 400);
		JButton btnCancelar = new JButton("Cancelar");
		//scrollerResultados.setViewportView(Ventana);
		//panelIzquierdo.add(scrollerResultados);
		panelIzquierdo.add(btnCancelar);
		//scrollerResultados.setVisible(false);
		
		//panelIzquierdo.setVisible(false);
		
		panelDerecho = new JPanel();
		panelDerecho.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelDerecho.setBackground(Color.LIGHT_GRAY);
		panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
		fixedSize(panelDerecho, 770,525 );
		Ventana.add(panelDerecho);
		
		panelDerechoInv = new JPanel();
		panelDerechoInv.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelDerechoInv.setBackground(Color.LIGHT_GRAY);
		fixedSize(panelDerechoInv, 770,525 );
		Ventana.add(panelDerechoInv);
		
		Component h1 = Box.createRigidArea(new Dimension(15, 15));
		panelDerecho.add(h1);
		
		
		tituloVideo = new JLabel();
		tituloVideo.setText("Titulo del video");
		tituloVideo.setFont(new Font("Arial",Font.BOLD,32));
		tituloVideo.setHorizontalTextPosition(JLabel.CENTER);
		tituloVideo.setVerticalTextPosition(JLabel.CENTER);
		
		tituloVideo.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelDerecho.add(tituloVideo);
		
		Component h2 = Box.createRigidArea(new Dimension(40, 40));
		panelDerecho.add(h2);
		//videoWeb.playVideo("https://www.youtube.com/watch?v=EdVMSYomYJY");
	
		validate();
		panelDerecho.add(videoWeb);
		
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
		panelDerecho.add(h3);
		
		panelDerecho.add(anadirNuevaEtiqueta);
		
		
		}
		
			
		

	

	private void fixedSize(JComponent c,int x, int y) {
		c.setMinimumSize(new Dimension(x,y));
		c.setMaximumSize(new Dimension(x,y));
		c.setPreferredSize(new Dimension(x,y));
	}
	
	public void updateVideos(List<Video> videos) {
		//ImageIcon n = new ImageIcon((Image) videoWeb.getThumb(videos.get(0).getUrl()));
		for(Video v: videos) {
			modelVideos.addElement(new VideoDisplay(v.getTitulo(),v.getUrl(),videoWeb.getThumb(v.getUrl())));
		}
	}
	
	public void setVideoWeb(VideoWeb v){
		videoWeb = v;
	}
	
	public void switchMode(Mode m) {
		if (m == Mode.REPRODUCTOR){
			panelIzquierdo.setVisible(false);
			fixedSize(panelDerecho, 977,525 );
			panelDerecho.setVisible(true);
			panelDerechoInv.setVisible(false);
			
		}
		else if (m == Mode.MISLISTAS){
			panelIzquierdo.setVisible(true);
			fixedSize(panelDerecho, 770,525 );
			panelDerecho.setVisible(false);
			panelDerechoInv.setVisible(true);
		}
	}
	
	public void setVideo(Video v){
		videoWeb.playVideo(v.getUrl());
		tituloVideo.setText(v.getTitulo());
	}
}


