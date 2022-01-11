package vista;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import controlador.ControladorAppVideo;
import tds.video.VideoWeb;
import modelo.Usuario;
import modelo.Video;
import modelo.VideoDisplay;
import modelo.VideoDisplayListRenderer;
import modelo.VideoList;


@SuppressWarnings("serial")
public class PanelExplorar extends JPanel{
	private JTextField textField;
	private JTextField txtLista;
	private VentanaMain ventanaMain;
	private JButton btnNuevaBusqueda;
	private JButton btnEliminar, btnPDF;
	private Usuario usuario;
	private Video videoSeleccionado;
	private List<Video> videosEncontrados = new ArrayList<Video>();
	private JPanel panelIzquierdo,panelCentral, panelDerecho;
	
	DefaultListModel<String> modelEtDisponibles = new DefaultListModel<String>();
	DefaultListModel<String> modelEtSeleccionadas = new DefaultListModel<String>();
	
	DefaultListModel<VideoDisplay> modelLista = new DefaultListModel<VideoDisplay>();
	DefaultListModel<VideoDisplay> modelVideos = new DefaultListModel<VideoDisplay>();
	
	private String subCadenaBusqueda;
	private List<String> etiquetasBusqueda = new ArrayList<String>();
	
	
	private VideoList videoLista = null;
	
	private Mode modo = Mode.EXPLORAR;
	private static ControladorAppVideo controladorAppVideo = ControladorAppVideo.getUnicaInstancia();
	private static VideoWeb videoWeb = controladorAppVideo.getVideoWeb();
	
	public PanelExplorar(VentanaMain v){
		ventanaMain=v;
		crearPantalla();
	}
	
	private void crearPantalla() {	
		JPanel contenido = new JPanel();
		contenido.setBorder(new LineBorder(new Color(0, 0, 0)));
		contenido.setBackground(Color.LIGHT_GRAY);
		contenido.setPreferredSize(new Dimension(Constantes.ventana_x_size-35, Constantes.ventana_y_size-180));
		contenido.setMaximumSize(contenido.getPreferredSize());
		contenido.setLayout(new FlowLayout(FlowLayout.LEFT, 5,5 ));  
		add(contenido);

		//Creación del panelIzquierdo, encargado de mostrar el motor de búsqueda de listas de video junto con los videos que alberga
		panelIzquierdo = new JPanel();
		panelIzquierdo.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelIzquierdo.setBackground(Color.LIGHT_GRAY);
		panelIzquierdo.setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
		Constantes.fixedSize(panelIzquierdo, 270,525 );
		contenido.add(panelIzquierdo);
		
		JLabel lbLista = new JLabel("Introducir nombre lista:");
		panelIzquierdo.add(lbLista);
		
		txtLista = new JTextField();
		Constantes.fixedSize(txtLista, 177,20);
		panelIzquierdo.add(txtLista);
		
		//Buscaremos la VideoList escrita por el usuario, dando la opción de crearla si no existe.
		//Si existe, mostrarán los videos que alberga la VideoList en una lista (JList)
		JButton btnBuscarLista = new JButton("Buscar");
		btnBuscarLista.addActionListener(ev ->{
			if(txtLista.getText().equals(""))
				JOptionPane.showMessageDialog(contenido,"Escribe un nombre válido","Nombre no válido", JOptionPane.ERROR_MESSAGE);
			else{
				if(controladorAppVideo.checkVideoListExiste(txtLista.getText())) {
					videoLista = controladorAppVideo.getListaVideo(txtLista.getText());
					updateVideosLista(videoLista.getListaVideos());
					btnEliminar.setEnabled(true);
					
				}
				else {
					int eleccion = JOptionPane.showConfirmDialog(contenido, "¿Desea crear la lista " + txtLista.getText() + "?", "Lista inexistente",JOptionPane.YES_NO_OPTION);
					if(eleccion == 0) {
						videoLista = controladorAppVideo.registrarVideoLista(txtLista.getText());
						modelLista.removeAllElements();
						btnEliminar.setEnabled(true);
					}	
				}
			}
		});
		panelIzquierdo.add(btnBuscarLista);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(30, 5));
		panelIzquierdo.add(rigidArea_1);
		
		//Ofrecemos la capacidad de eliminar listas de videos.
		btnEliminar = new JButton("Eliminar");
		panelIzquierdo.add(btnEliminar);
		btnEliminar.setEnabled(false);
		btnEliminar.addActionListener(ev ->{
			int eleccion = JOptionPane.showConfirmDialog(contenido, "¿Estás seguro que quieres eliminar la lista " + txtLista.getText() + "?", "Eliminar lista",JOptionPane.YES_NO_OPTION);
			if(eleccion == 0) {
				controladorAppVideo.borrarListaVideo(videoLista);
				limpiarLista();
			}
		});
		
		//Ofrecemos la capacidad de crear PDFs con la información de las listas de videos creadas por usuarios (Solo para usuarios premium)
		btnPDF = new JButton("Generar PDF");
		btnPDF.setForeground(Color.RED);
		panelIzquierdo.add(btnPDF);
		btnPDF.setEnabled(false);
		btnPDF.addActionListener(ev ->{    
		     generarPDF();
		});

		
		//JList encargada de mostrar los videos contenidos en una VideoList
		JList<VideoDisplay> listaVideos = new JList<VideoDisplay>();
		listaVideos.setBackground(Color.LIGHT_GRAY);
		listaVideos.setBorder(new LineBorder(new Color(0, 0, 0)));
		listaVideos.setCellRenderer(new VideoDisplayListRenderer());
		listaVideos.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listaVideos.setVisibleRowCount(-1);
		listaVideos.setModel(modelLista);
		listaVideos.addMouseListener(new MouseAdapter(){
			//Si se clica dos veces sobre un video, se eliminará de la lista
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2 && !modelLista.isEmpty())
				{
					controladorAppVideo.removeVideoFromLista(videoLista,controladorAppVideo.getVideo(listaVideos.getSelectedValue().getTitulo()));
					modelLista.removeElement(listaVideos.getSelectedValue());
				}
			}
		});
		
		JScrollPane scrollerListaVideos = new JScrollPane(listaVideos);
		scrollerListaVideos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollerListaVideos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		Constantes.fixedSize(scrollerListaVideos, 258,430);
		panelIzquierdo.add(scrollerListaVideos);
		
		//El panel central mostrará el motor de búsqueda y la lista resultante de videos.
		panelCentral = new JPanel();
		panelCentral.setBackground(Color.LIGHT_GRAY);
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		Constantes.fixedSize(panelCentral, 700, Constantes.ventana_y_size-195);
		contenido.add(panelCentral);
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(Color.LIGHT_GRAY);
		Constantes.fixedSize(panel, 700,80);
		panelCentral.add(panel);
		
		
		//Contenido del panel que alberga el motor de búsqueda
		JLabel lbBuscar = new JLabel("Buscar t\u00EDtulo:");
		panel.add(lbBuscar);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(40);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(ev -> {
			
			subCadenaBusqueda = textField.getText();
			updateVideos(controladorAppVideo.buscarVideos(subCadenaBusqueda, etiquetasBusqueda));
		});
		panel.add(btnBuscar);
		
		btnNuevaBusqueda = new JButton("Nueva Busqueda");
		btnNuevaBusqueda.addActionListener(ev ->{
			limpiar();
		});
		panel.add(btnNuevaBusqueda);
			
		
		Component rigidArea2 = Box.createRigidArea(new Dimension(5, 5));
		panelCentral.add(rigidArea2);
		
		//JList encargada de mostrar el conjunto de videos que satisfacen las directivas de búsqueda
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
				//Dependiendo del modo en el que estemos, haremos que el video se reproduzca al doble clicarlo (Explorar)
				//o que se añada a la VideoLista seleccionada en el comboBox (NuevaLista)
				if (e.getClickCount() == 2 && !modelVideos.isEmpty())
				{
					if(modo == Mode.NUEVALISTA && !(videoLista == null) && !videoLista.contieneVideo(controladorAppVideo.getVideo(lista.getSelectedValue().getTitulo()))) {
						controladorAppVideo.addVideoToVideoList(videoLista, controladorAppVideo.getVideo(lista.getSelectedValue().getTitulo()));
						modelLista.addElement(lista.getSelectedValue());
						
					}
					else if(modo == Mode.EXPLORAR) {
						videoSeleccionado = videosEncontrados.get(lista.getSelectedIndex());
						controladorAppVideo.addVideoReciente(videoSeleccionado);
						ventanaMain.cambiarContenido(Contenido.REPRODUCTOR);
					}
				}
			}
		});
		
		JScrollPane scrollerResultados = new JScrollPane(lista);
		scrollerResultados.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollerResultados.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		Constantes.fixedSize(scrollerResultados, 700,439);
		panelCentral.add(scrollerResultados);

		//El panel derecho mostrará todo lo relacionado con la etiquetas disponibles y seleccionadas para la búsqueda
		panelDerecho = new JPanel();
		contenido.add(panelDerecho);
		panelDerecho.setBackground(Color.LIGHT_GRAY);
		Constantes.fixedSize(panelDerecho, 270, Constantes.ventana_y_size-195);
		panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		Constantes.fixedSize(panel_1, 270, (Constantes.ventana_y_size-195)/2-5);
		panelDerecho.add(panel_1);
		
		Component rigidArea = Box.createRigidArea(new Dimension(5, 5));
		panelDerecho.add(rigidArea);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		Constantes.fixedSize(panel_2, 270, (Constantes.ventana_y_size-195)/2);
		panelDerecho.add(panel_2);
		
		JLabel lbEtDisponibles = new JLabel("Etiquetas disponibles");
		panel_1.add(lbEtDisponibles);
		JPanel panel_lista_1 = new JPanel();
		Constantes.fixedSize(panel_lista_1, 220,220);
		panel_lista_1.setBackground(Color.LIGHT_GRAY);
		panel_1.add(panel_lista_1);
		
		JLabel lbEtSeleccionadas = new JLabel("Etiquetas seleccionadas");
		panel_2.add(lbEtSeleccionadas);
		
		JPanel panel_lista_2 = new JPanel();
		Constantes.fixedSize(panel_lista_2, 220,200);
		panel_lista_2.setBackground(Color.LIGHT_GRAY);
		panel_2.add(panel_lista_2);
		
		//JList etiquetas disponibles junto con su ListSelectionListener 
		JList<String> etiquetasDisponibles = new JList<String>(); 	
		etiquetasDisponibles.setSelectedIndex(0);
		etiquetasDisponibles.setModel(modelEtDisponibles);
		JScrollPane scrollerEtDisponibles = new JScrollPane(etiquetasDisponibles);
		Constantes.fixedSize(scrollerEtDisponibles, 150, 200);
		scrollerEtDisponibles.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel_lista_1.add(scrollerEtDisponibles);
		
		//Cuando se cliquee sobre una etiqueta de esta lista, pasará a la lista de etiquetas seleccionadas
		etiquetasDisponibles.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()){
					@SuppressWarnings("unchecked")
					JList<String> source = (JList<String>)event.getSource();
					String selected = source.getSelectedValue();
					if(selected != null && !modelEtSeleccionadas.contains(selected)) {
						modelEtSeleccionadas.addElement(selected);
						etiquetasBusqueda.add(selected);
					}
					
				}
			}
		});
		
		
		//JList etiquetas seleccionadas junto con su ListSelectionListener 
		JList<String> etiquetasSeleccionadas = new JList<String>(); 	
		etiquetasSeleccionadas.setSelectedIndex(0);
		etiquetasSeleccionadas.setModel(modelEtSeleccionadas);
		JScrollPane scrollerEtSeleccionadas = new JScrollPane(etiquetasSeleccionadas);
		Constantes.fixedSize(scrollerEtSeleccionadas, 150, 200);
		scrollerEtSeleccionadas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel_lista_2.add(scrollerEtSeleccionadas);
		
		//Se elimina de la lista de etiquetas seleccionadas las etiquetas clicadas.
		etiquetasSeleccionadas.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()){
					String selected = etiquetasSeleccionadas.getSelectedValue();
					if(selected != null) {
				        modelEtSeleccionadas.removeElement(selected);
						etiquetasBusqueda.remove(selected);
					}
					 
				}
			}	
		});
				
	}

	

	//Método utilizado por VentanaMain para actualizar las etiquetas disponibles.
	public void update(List<String> etiquetasDadas) {	
		modelEtDisponibles.removeAllElements();
		modelEtDisponibles.addAll(etiquetasDadas);
		modelEtSeleccionadas.removeAllElements();
	}
	
	//Método de limpieza del buscador de videos y etiquetas seleccionadas cuando se pulsa el botón "Nueva Búsqueda" (Explorar)
	public void limpiar() {
		textField.setText("");
		modelVideos.removeAllElements();
		subCadenaBusqueda = "";
		etiquetasBusqueda.clear();
		modelEtSeleccionadas.clear();
	}
	
	//Método de limpieza de la video lista (y los videos representados en JList) cuando se pulsa el botón eliminar (Nueva Lista)
	public void limpiarLista() {
		modelLista.removeAllElements();
		btnEliminar.setEnabled(false);
		btnPDF.setEnabled(false);
		if(usuario.esPremium())
			btnPDF.setEnabled(true);
		videoLista = null;
		txtLista.setText("");
	}
	
	//Método encargado de mostrar los videos resultado de la búsqueda.
	//Se crean objetos tipo VideoDisplay que juegan el papel de representación gráfica de un video para el renderizador de la lista (JList)
	private void updateVideos(List<Video> videos) {
		videosEncontrados = videos;
		modelVideos.removeAllElements();
		modelVideos.addAll(videos.stream().map(v -> new VideoDisplay(v.getTitulo(),v.getUrl(),videoWeb.getThumb(v.getUrl()))).collect(Collectors.toList()));
		validate();
	}
	
	//Misma función que el método anterior pero ahora con los videos de la lista de videos (VideoList) seleccionada
	private void updateVideosLista(List<Video> videos) {
		modelLista.removeAllElements();
		modelLista.addAll(videos.stream().map(v -> new VideoDisplay(v.getTitulo(),v.getUrl(),videoWeb.getThumb(v.getUrl()))).collect(Collectors.toList()));
		validate();
	}
	
	//Función que devuelve el video seleccionado para pasarselo al panel Reproductor.
	public Video getSelectedVideo() {
		return videoSeleccionado;
	}
	
	//Método encargado de cambiar de modo entre Explorar y NuevaLista mostrando los componentes de uno u otro.
	public void switchMode(Mode m) {
		if (m == Mode.EXPLORAR){
			modo = Mode.EXPLORAR;
			panelIzquierdo.setVisible(false);
			panelDerecho.setVisible(true);
			
		}
		else if (m == Mode.NUEVALISTA){
			modo = Mode.NUEVALISTA;
			panelIzquierdo.setVisible(true);
			panelDerecho.setVisible(false);
		}
	}
	
	public void setUsuario(Usuario user) {
		usuario = user;
	}

	//Método encargado de generar un PDF (usuario premium) en el que se muestran todas las listas de videos creadas por el usuario junto con sus
	//videos e información acerca de ellos.
	public void generarPDF() {
		 String dest = "C:\\temp\\VideoList.pdf";    
	      PdfWriter writer = null;
	      try {
				writer = new PdfWriter(dest);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} 
	      
	      PdfDocument pdfDoc = new PdfDocument(writer);              
	   
	      pdfDoc.addNewPage();               
	         
	      Document document = new Document(pdfDoc);    
	      String info = "Nombre: "+ usuario.getNombre_completo() + "\n" + "Usuario: " + usuario.getUsuario() + "\n" +
	      "Email: "+ usuario.getEmail() + "\n" + "Fecha de nacimiento: "+ usuario.getFecha_nacimiento();
	      document.add(new Paragraph (info));
	      document.add(new Paragraph ("\n"));
	      
	      List<VideoList> listasVideos = controladorAppVideo.getListasAutor();
	      for(VideoList v: listasVideos) {
	    	  document.add(new Paragraph ("Lista de reproducción: "+ v.getNombre() ));
		      for(Video vid: v.getListaVideos()) {
		    	  document.add(new Paragraph ("Video: " + vid.getTitulo() + "\n URL: " + vid.getUrl() + "\n Reproducciones: " + vid.getReproducciones()));
		      }	
		      document.add(new Paragraph ("\n\n"));
	      }
	      
	            
	      document.close();     
	      JOptionPane.showMessageDialog(null, "El PDF se ha generado con éxito en el directorio C:\\temp\\.", "PDF generado",
					JOptionPane.INFORMATION_MESSAGE);
	}
}


