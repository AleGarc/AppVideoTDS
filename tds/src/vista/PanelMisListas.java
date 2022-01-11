package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import controlador.ControladorAppVideo;
import modelo.Etiqueta;
import modelo.Usuario;
import modelo.Video;
import modelo.VideoDisplay;
import modelo.VideoDisplayListRenderer;
import modelo.VideoList;
import tds.video.VideoWeb;


@SuppressWarnings("serial")
public class PanelMisListas extends JPanel{

	private JPanel panelIzquierdo;
	private JPanel panelDerecho;
	private JPanel panelDerechoInv;
	private Usuario usuario;
	private JLabel tituloVideo, reproducciones;
	private JPanel panelEtiquetas;
	private JLabel txtLista;
	JComboBox<String> boxListas;
	private VideoList videoLista;
	DefaultListModel<VideoDisplay> modelVideos = new DefaultListModel<VideoDisplay>();
	
	private static ControladorAppVideo controladorAppVideo = ControladorAppVideo.getUnicaInstancia();
	private static VideoWeb videoWeb = controladorAppVideo.getVideoWeb();
	
	private Video videoSeleccionado;
	
	public PanelMisListas(){
		crearPantalla();
	}
	
	private void crearPantalla() {
		JPanel contenido = new JPanel();
		contenido.setBorder(new LineBorder(new Color(0, 0, 0)));
		contenido.setBackground(Color.LIGHT_GRAY);
		contenido.setPreferredSize(new Dimension(Constantes.ventana_x_size-35, Constantes.ventana_y_size-180));
		contenido.setMaximumSize(contenido.getPreferredSize());
		contenido.setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
		add(contenido);
		
		
		//Creación del panel izquierdo, panel que alberga el comboBox con las listas de video creadas por el usuario.
		panelIzquierdo = new JPanel();
		panelIzquierdo.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelIzquierdo.setBackground(Color.LIGHT_GRAY);
		panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
		Constantes.fixedSize(panelIzquierdo, 200,525 );
		contenido.add(panelIzquierdo);
		
		txtLista = new JLabel("Seleccione la lista:");
		panelIzquierdo.add(txtLista);
		boxListas = new JComboBox<String>();
		boxListas.setMaximumSize(new Dimension(292,20));
		boxListas.setEditable(false);
		actualizarLista();
		panelIzquierdo.add(boxListas);
		
		//Creación de la lista (JList) que mostrará el titulo y miniatura de los videos de una lista de videos específica.
		JList<VideoDisplay> lista = new JList<VideoDisplay>();
		lista.setBackground(Color.LIGHT_GRAY);
		lista.setBorder(new LineBorder(new Color(0, 0, 0)));
		lista.setCellRenderer(new VideoDisplayListRenderer());
		lista.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		lista.setVisibleRowCount(-1);
		lista.setModel(modelVideos);
		lista.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e)
			{
				//Si se dobleclica sobre un video de esta lista, se reproducirá
				if (e.getClickCount() == 2 && !modelVideos.isEmpty())
				{
					Video videoSeleccionado = controladorAppVideo.getVideo(lista.getSelectedValue().getTitulo());
					setVideo(videoSeleccionado);
					controladorAppVideo.addVideoReciente(videoSeleccionado);
					panelDerecho.setVisible(true);
					panelDerechoInv.setVisible(false);
				}
			}
		});
		
		JScrollPane scrollerResultados = new JScrollPane(lista);
		scrollerResultados.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollerResultados.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		panelIzquierdo.add(scrollerResultados);
		
		//Creación del panel derecho que albergará la reproducción del video seleccionado mediante el uso de videoWeb.
		panelDerecho = new JPanel();
		panelDerecho.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelDerecho.setBackground(Color.LIGHT_GRAY);
		panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
		Constantes.fixedSize(panelDerecho, 770,525 );
		contenido.add(panelDerecho);
		
		//Panel de atrezo creado para ocultar el reproductor mientras no se seleccione un video.
		panelDerechoInv = new JPanel();
		panelDerechoInv.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelDerechoInv.setBackground(Color.LIGHT_GRAY);
		Constantes.fixedSize(panelDerechoInv, 770,525 );
		contenido.add(panelDerechoInv);
		
		
		//Contenido del panelDerecho (panel del reproductor)
		Component h1 = Box.createRigidArea(new Dimension(15, 15));
		panelDerecho.add(h1);
		
		tituloVideo = new JLabel();
		tituloVideo.setText("Titulo del video");
		tituloVideo.setFont(new Font("Arial",Font.BOLD,32));
		tituloVideo.setHorizontalTextPosition(JLabel.CENTER);
		tituloVideo.setVerticalTextPosition(JLabel.CENTER);
		
		tituloVideo.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelDerecho.add(tituloVideo);
		
		reproducciones = new JLabel();
		reproducciones.setFont(new Font("Arial",0,18));
		reproducciones.setHorizontalTextPosition(JLabel.CENTER);
		reproducciones.setVerticalTextPosition(JLabel.CENTER);
		reproducciones.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelDerecho.add(reproducciones);
		
		Component h2 = Box.createRigidArea(new Dimension(40, 40));
		panelDerecho.add(h2);
		panelDerecho.add(videoWeb);

		panelEtiquetas = new JPanel();
		panelEtiquetas.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panelEtiquetas.setBackground(Color.LIGHT_GRAY);
		Constantes.fixedSize(panelEtiquetas, 500,100);
		panelDerecho.add(panelEtiquetas);
		
		JLabel nuevaEtiqueta = new JLabel("Nueva etiqueta:");
		JTextField txtEtiqueta = new JTextField();
		txtEtiqueta.setColumns(10);
		JButton btnAnadir = new JButton("Añadir");
		
		JPanel anadirNuevaEtiqueta = new JPanel();
		anadirNuevaEtiqueta.setLayout(new FlowLayout( FlowLayout.LEFT));
		Constantes.fixedSize(anadirNuevaEtiqueta,300,40);
		anadirNuevaEtiqueta.setBackground(Color.LIGHT_GRAY);
		anadirNuevaEtiqueta.add(nuevaEtiqueta);
		anadirNuevaEtiqueta.add(txtEtiqueta);
		anadirNuevaEtiqueta.add(btnAnadir);
		
		Component h3 = Box.createRigidArea(new Dimension(80, 10));
		panelDerecho.add(h3);
		panelDerecho.add(anadirNuevaEtiqueta);
		
		//Action listener para añadir una etiqueta al video seleccionado
		//Se comprueba que sea una etiqueta válida y que el video no la contenga.
		btnAnadir.addActionListener(ev ->{
			if(txtEtiqueta.getText().equals(""))
				JOptionPane.showMessageDialog(contenido,"Escribe un nombre válido","Nombre no válido", JOptionPane.ERROR_MESSAGE);
			else if(checkEtiquetas(txtEtiqueta.getText(), videoSeleccionado)){
				JOptionPane.showMessageDialog(contenido,"El video ya tiene esa etiqueta","Etiqueta no válida", JOptionPane.ERROR_MESSAGE);	
			}
			else {
				int eleccion = JOptionPane.showConfirmDialog(contenido, "¿Desea añadir la etiqueta " + txtEtiqueta.getText() + " al video " + tituloVideo.getText() + " ?", "Añadir etiqueta",JOptionPane.YES_NO_OPTION);
				if(eleccion == 0) {
					controladorAppVideo.addEtiquetaToVideo(txtEtiqueta.getText(), videoSeleccionado);
					mostrarEtiquetas(videoSeleccionado);
					validate();
					txtEtiqueta.setText("");
				}
				
			}
		});
		
	}
		
			
	
	//Método encargado de actualizar la lista (JList) con los videos que alberga la lista de video (VideoList) seleccionada
	private void updateVideos(List<Video> videos) {
		modelVideos.removeAllElements();
		for(Video v: videos) {
			modelVideos.addElement(new VideoDisplay(v.getTitulo(),v.getUrl(),videoWeb.getThumb(v.getUrl())));
		}
	}
	
	//Método utilizado para comprobar si el video alberga la etiqueta que se intenta añadir.
	private boolean checkEtiquetas(String etiqueta, Video v) {
		for(Etiqueta e: v.getEtiquetas()) {
			if(e.getNombre().equals(etiqueta))
				return true;
		}
		return false;
	}
	
	//Método utilizado para cambiar de modo este panel
	//Este panel tiene 4 modos, cada uno con sus componentes visibles (o invisibles) para reutilizar código.
	//En el caso de MASVISTOS o RECIENTES, no se mostrarán las listas (VideoList) creadas por usuarios, sino unas listas generadas por el controlador / usuario
	public void switchMode(Mode m) {
		if (m == Mode.REPRODUCTOR){
			panelIzquierdo.setVisible(false);
			Constantes.fixedSize(panelDerecho, 977,525 );
			panelDerecho.setVisible(true);
			panelDerechoInv.setVisible(false);
		}
		else if (m == Mode.MISLISTAS){
			panelIzquierdo.setVisible(true);
			Constantes.fixedSize(panelDerecho, 770,525 );
			panelDerecho.setVisible(false);
			panelDerechoInv.setVisible(true);
			txtLista.setVisible(true);
			boxListas.setVisible(true);
		}
		else if(m == Mode.MASVISTOS) {
			panelIzquierdo.setVisible(true);
			Constantes.fixedSize(panelDerecho, 770,525 );
			panelDerecho.setVisible(false);
			panelDerechoInv.setVisible(true);
			txtLista.setVisible(false);
			boxListas.setVisible(false);
			
			videoLista = controladorAppVideo.getVideosMasVistos();
			updateVideos(videoLista.getListaVideos());
			
		}
		else if(m == Mode.RECIENTES) {
			panelIzquierdo.setVisible(true);
			Constantes.fixedSize(panelDerecho, 770,525 );
			panelDerecho.setVisible(false);
			panelDerechoInv.setVisible(true);
			txtLista.setVisible(false);
			boxListas.setVisible(false);
			
			updateVideos(usuario.getVideosRecientes());
			
		}
	}
	
	//Método encargado de reproducir el video seleccionado y de mostrar toda su información en el panel (panelDerecho)
	public void setVideo(Video v){
		videoSeleccionado = v;
		videoWeb.playVideo(v.getUrl());
		tituloVideo.setText(v.getTitulo());
		reproducciones.setText("Visto por: " + v.getReproducciones() +" usuarios");
		controladorAppVideo.addReproduccion(v);
		mostrarEtiquetas(v);
	}
	
	//Método encargado de mostrar todas las etiquetas del video seleccionado.
	private void mostrarEtiquetas(Video v) {
		panelEtiquetas.removeAll();
		for(Etiqueta e : v.getEtiquetas()) {
			JLabel et = new JLabel(e.getNombre());
			panelEtiquetas.add(et);
		}
	}
	
	//Método utilizado (principalmente por VentanaMain) para actualizar el comboBox con las listas creadas por un usuario.
	public void updateBoxListas() {
		boxListas.removeAllItems();
		modelVideos.removeAllElements();
		boxListas.addItem("");
		for(VideoList v: controladorAppVideo.getListasAutor()) {
			boxListas.addItem(v.getNombre());
		}
	}
	
	//Método utilizado (principalmente por VentanaMain) para actualizar el usuario de la sesión.
	public void setUsuario(Usuario user) {
		usuario = user;
	}
	
	//Método utilizado para actualizar la lista (JList) con los videos de la lista de videos (VideoList) seleccionada por el usuario.
	private void actualizarLista() {
		boxListas.addActionListener(ev ->{
			if(boxListas.getSelectedItem() != null && !boxListas.getSelectedItem().toString().isEmpty()) {
				videoLista = controladorAppVideo.getListaVideo(boxListas.getSelectedItem().toString());
				updateVideos(videoLista.getListaVideos());
			}
		});
	}
}


