package vista;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
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


public class PanelExplorar extends JPanel implements ActionListener{
	private JTextField textField;
	private JTextField txtLista;
	private VentanaMain ventana;
	private JButton playButton;
	private JButton btnPlay;
	private JButton btnNuevaBusqueda;
	private JButton btnBusqueda, btnEliminar, btnPDF;
	private Usuario usuario;
	private Video videoSeleccionado;
	private List<Video> videosEncontrados = new ArrayList<Video>();
	private JPanel panelIzquierdo,panelCentral, panelDerecho;
	//private List<JLabel> videosBuscados = new ArrayList<JLabel>();
	
	DefaultListModel<String> model = new DefaultListModel<String>();
	DefaultListModel<String> model2 = new DefaultListModel<String>();
	
	DefaultListModel<VideoDisplay> modelLista = new DefaultListModel<VideoDisplay>();
	DefaultListModel<VideoDisplay> modelVideos = new DefaultListModel<VideoDisplay>();
	
	private String subCadenaBusqueda;
	private List<String> etiquetasBusqueda = new ArrayList<String>();
	
	
	private VideoList videoLista = null;
	
	private Mode modo = Mode.EXPLORAR;
	private static ControladorAppVideo controladorAppVideo = ControladorAppVideo.getUnicaInstancia();
	private static VideoWeb videoWeb = controladorAppVideo.getVideoWeb();
	
	//JPanel resultados = new JPanel();
	public PanelExplorar(VentanaMain v){
		//videoWeb = new VideoWeb();
		ventana=v;
		crearPantalla();
	}
	
	private void crearPantalla() {
		JPanel panelExplorar = new JPanel();
		add(panelExplorar);
		panelExplorar.setBackground(Color.BLACK);
		//panelExplorar.setBounds(700, 250, 1024, 720);
		panelExplorar.setLayout(new BoxLayout(panelExplorar, BoxLayout.X_AXIS));
		
		
		
		JPanel Ventana = new JPanel();
		Ventana.setBorder(new LineBorder(new Color(0, 0, 0)));
		Ventana.setBackground(Color.LIGHT_GRAY);
		Ventana.setPreferredSize(new Dimension(Constantes.ventana_x_size-35, Constantes.ventana_y_size-180));
		Ventana.setMaximumSize(Ventana.getPreferredSize());
		Ventana.setLayout(new FlowLayout(FlowLayout.LEFT, 5,5 ));  
		
		//panelExplorar.getContentPane().add(Ventana);
		panelExplorar.add(Ventana);
		//Ventana.setLayout(new BoxLayout(Ventana, BoxLayout.Y_AXIS));


		panelIzquierdo = new JPanel();
		panelIzquierdo.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelIzquierdo.setBackground(Color.LIGHT_GRAY);
		panelIzquierdo.setLayout(new FlowLayout(FlowLayout.LEFT, 5,5));
		//panelIzquierdo.setAlignmentX(Component.LEFT_ALIGNMENT);
		Constantes.fixedSize(panelIzquierdo, 270,525 );
		Ventana.add(panelIzquierdo);
		JLabel lbLista = new JLabel("Introducir nombre lista:");
		panelIzquierdo.add(lbLista);
		
		txtLista = new JTextField();
		Constantes.fixedSize(txtLista, 177,20);
		panelIzquierdo.add(txtLista);
		
		
		JButton btnBuscarLista = new JButton("Buscar");
		btnBuscarLista.addActionListener(ev ->{
			if(txtLista.getText().equals(""))
				JOptionPane.showMessageDialog(Ventana,"Escribe un nombre válido","Nombre no válido", JOptionPane.ERROR_MESSAGE);
			else{
				if(controladorAppVideo.checkVideoListExiste(txtLista.getText())) {
					videoLista = controladorAppVideo.getListaVideo(txtLista.getText());
					updateVideosLista(videoLista.getListaVideos());
					btnEliminar.setEnabled(true);
					
				}
				else {
					int eleccion = JOptionPane.showConfirmDialog(Ventana, "¿Desea crear la lista " + txtLista.getText() + "?", "Lista inexistente",JOptionPane.YES_NO_OPTION);
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
		btnEliminar = new JButton("Eliminar");
		panelIzquierdo.add(btnEliminar);
		btnEliminar.setEnabled(false);
		btnEliminar.addActionListener(ev ->{
			int eleccion = JOptionPane.showConfirmDialog(Ventana, "¿Estás seguro que quieres eliminar la lista " + txtLista.getText() + "?", "Eliminar lista",JOptionPane.YES_NO_OPTION);
			if(eleccion == 0) {
				controladorAppVideo.borrarListaVideo(videoLista);
				limpiarLista();
			}
		});
		
		btnPDF = new JButton("Generar PDF");
		btnPDF.setForeground(Color.RED);
		panelIzquierdo.add(btnPDF);
		btnPDF.setEnabled(false);
		btnPDF.addActionListener(ev ->{    
		     generarPDF();
			
		});

		
		
		JList<VideoDisplay> listaVideos = new JList<VideoDisplay>();
		listaVideos.setBackground(Color.LIGHT_GRAY);
		listaVideos.setBorder(new LineBorder(new Color(0, 0, 0)));
		listaVideos.setCellRenderer(new VideoDisplayListRenderer());
		listaVideos.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listaVideos.setVisibleRowCount(-1);
		listaVideos.setModel(modelLista);
		listaVideos.addMouseListener(new MouseAdapter(){
			@SuppressWarnings("serial")
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
		
		//Constantes.fixedSize(scrollerResultados, 204, 400);
		//JButton btnCancelar = new JButton("Cancelar");
		//scrollerResultados.setViewportView(Ventana);
		//panelIzquierdo.add(scrollerResultados);
		//panelIzquierdo.add(btnCancelar);
		//scrollerResultados.setVisible(false);
		
		//panelIzquierdo.setVisible(false);
		
		
		panelCentral = new JPanel();
		panelCentral.setBackground(Color.LIGHT_GRAY);
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		Constantes.fixedSize(panelCentral, 700, Constantes.ventana_y_size-195);
		Ventana.add(panelCentral);
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(Color.LIGHT_GRAY);
		Constantes.fixedSize(panel, 700,80);
		panelCentral.add(panel);
		//panelCentral.setBackground(Color.YELLOW);
		
		Component rigidArea2 = Box.createRigidArea(new Dimension(5, 5));
		panelCentral.add(rigidArea2);
		
		/*resultados = new JPanel();
		resultados.setBorder(new LineBorder(new Color(0, 0, 0)));
		resultados.setBackground(Color.LIGHT_GRAY);
		//Constantes.fixedSize(resultados, 700, 439);
		//resultados.setMinimumSize(new Dimension(700, 439));
		//resultados.setMaximumSize(new Dimension(700,2000000));
		//resultados.setPreferredSize(resultados.getMinimumSize());
		//resultados.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
		resultados.setLayout(new GridLayout(0,4));*/
		
		JList<VideoDisplay> lista = new JList<VideoDisplay>();
		lista.setBackground(Color.LIGHT_GRAY);
		lista.setBorder(new LineBorder(new Color(0, 0, 0)));
		lista.setCellRenderer(new VideoDisplayListRenderer());
		lista.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		lista.setVisibleRowCount(-1);
		lista.setModel(modelVideos);
		lista.addMouseListener(new MouseAdapter(){
			@SuppressWarnings("serial")
			public void mouseClicked(MouseEvent e)
			{
				if (e.getClickCount() == 2 && !modelVideos.isEmpty())
				{
					if(modo == Mode.NUEVALISTA && !(videoLista == null) && !videoLista.contieneVideo(controladorAppVideo.getVideo(lista.getSelectedValue().getTitulo()))) {
						controladorAppVideo.addVideoToVideoList(videoLista, controladorAppVideo.getVideo(lista.getSelectedValue().getTitulo()));
						modelLista.addElement(lista.getSelectedValue());
						
					}
					else if(modo == Mode.EXPLORAR) {
						videoSeleccionado = videosEncontrados.get(lista.getSelectedIndex());
						controladorAppVideo.addVideoReciente(videoSeleccionado);
						ventana.cambiarContenido(Contenido.REPRODUCTOR);
					}
				}
			}
		});
		
		JScrollPane scrollerResultados = new JScrollPane(lista);
		scrollerResultados.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollerResultados.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		Constantes.fixedSize(scrollerResultados, 700,439);
		panelCentral.add(scrollerResultados);
		//panelCentral.add(resultados);
		
		//updateResultados();
		
		/*
		JLabel miniatura = new JLabel();
		miniatura.setText("Mariano Rajoy");
		//resultados.add(videoWeb);
		resultados.add(miniatura);
		miniatura.setIcon(videoWeb.getThumb("https://www.youtube.com/watch?v=EdVMSYomYJY"));
		miniatura.setHorizontalTextPosition(JLabel.CENTER);
		miniatura.setVerticalTextPosition(JLabel.BOTTOM);
		
		JLabel miniatura2 = new JLabel();
		miniatura2.setText("Perro Sanxe");
		//resultados.add(videoWeb);
		resultados.add(miniatura2);
		miniatura2.setIcon(videoWeb.getThumb("https://www.youtube.com/watch?v=YlJB3gGOluk"));
		miniatura2.setHorizontalTextPosition(JLabel.CENTER);
		miniatura2.setVerticalTextPosition(JLabel.BOTTOM);
		//miniatura.setIcon(videoWeb.getSmallThumb("https://www.youtube.com/watch?v=EdVMSYomYJY"));
	    //videoWeb.playVideo("https://www.youtube.com/watch?v=EdVMSYomYJY");
	    validate();
	    */
		
		
		
		
		
		
		
		JLabel lblNewLabel = new JLabel("Buscar t\u00EDtulo:");
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(40);
		
		
		
		JButton btnNewButton = new JButton("Buscar");
		btnNewButton.addActionListener(ev -> {
			subCadenaBusqueda = textField.getText();
			updateVideos(controladorAppVideo.buscarVideos(subCadenaBusqueda, etiquetasBusqueda));
		});
		panel.add(btnNewButton);
		
		btnNuevaBusqueda = new JButton("Nueva Busqueda");
		btnNuevaBusqueda.addActionListener(ev ->{
			limpiar();
		});
		panel.add(btnNuevaBusqueda);
			
		
		panelDerecho = new JPanel();
		Ventana.add(panelDerecho);
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
		
		JLabel lblNewLabel_1 = new JLabel("Etiquetas disponibles");
		panel_1.add(lblNewLabel_1);
		JPanel panel_lista_1 = new JPanel();
		Constantes.fixedSize(panel_lista_1, 220,220);
		panel_lista_1.setBackground(Color.LIGHT_GRAY);
		panel_1.add(panel_lista_1);
		
		JLabel lblNewLabel_2 = new JLabel("Etiquetas seleccionadas");
		panel_2.add(lblNewLabel_2);
		
		JPanel panel_lista_2 = new JPanel();
		Constantes.fixedSize(panel_lista_2, 220,200);
		panel_lista_2.setBackground(Color.LIGHT_GRAY);
		panel_2.add(panel_lista_2);
		
		
		JList<String> etiquetasDisponibles = new JList<String>(); 	//ETIQUETAS DE MAXIMO CARACTERES 30.
		etiquetasDisponibles.setSelectedIndex(0);
		etiquetasDisponibles.setVisibleRowCount(10);
		etiquetasDisponibles.setFixedCellWidth(150);
		update(new ArrayList<String>());
		etiquetasDisponibles.setModel(model);
		JScrollPane scroller = new JScrollPane(etiquetasDisponibles);
		scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel_lista_1.add(scroller);
		
		
		
		JList<String> etiquetasSeleccionadas = new JList<String>(); 	//ETIQUETAS DE MAXIMO CARACTERES 30.
		etiquetasSeleccionadas.setSelectedIndex(0);
		etiquetasSeleccionadas.setVisibleRowCount(10);
		etiquetasSeleccionadas.setFixedCellWidth(165);
		etiquetasSeleccionadas.setModel(model2);
		
		JScrollPane scroller2 = new JScrollPane(etiquetasSeleccionadas);
		scroller2.getViewport().setViewSize(panel_lista_2.getPreferredSize());
		scroller2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panel_lista_2.add(scroller2);
		
		
		
		
		
		
		
		
		
		etiquetasDisponibles.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()){
					@SuppressWarnings("unchecked")
					JList<String> source = (JList<String>)event.getSource();
					String selected = source.getSelectedValue();
					if(!model2.contains(selected)) {
						model2.addElement(selected);
						etiquetasBusqueda.add(selected);
					}
					
				}
			}
		});
		
		etiquetasSeleccionadas.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()){
					String selected = etiquetasSeleccionadas.getSelectedValue();
			        model2.removeElement(selected);
					etiquetasBusqueda.remove(selected);
					 
				}
			}	
		});
		
		
		
	}

	

	
	public void update(List<String> etiquetasDadas) {	
		model.removeAllElements();
		for (String etiqueta: etiquetasDadas) 
			model.addElement(etiqueta);
		model2.removeAllElements();
	}
	
	/*public void updateResultados() {
		resultados.removeAll();
		for(JLabel label: videosBuscados) {
			resultados.add(label);
		}
	}*/
	
	public void limpiar() {
		textField.setText("");
		modelVideos.removeAllElements();
		etiquetasBusqueda.clear();
		model2.clear();
		limpiarLista();
	}
	
	public void limpiarLista() {
		modelLista.removeAllElements();
		btnEliminar.setEnabled(false);
		btnPDF.setEnabled(false);
		if(usuario.esPremium())
			btnPDF.setEnabled(true);
		videoLista = null;
		txtLista.setText("");
	}
	
	public void updateVideos(List<Video> videos) {
		videosEncontrados = videos;
		modelVideos.removeAllElements();
		for(Video v: videos) {
			modelVideos.addElement(new VideoDisplay(v.getTitulo(),v.getUrl(),videoWeb.getThumb(v.getUrl())));
		}
		validate();
	}
	
	public void updateVideosLista(List<Video> videos) {
		modelLista.removeAllElements();
		for(Video v: videos) {
			modelLista.addElement(new VideoDisplay(v.getTitulo(),v.getUrl(),videoWeb.getThumb(v.getUrl())));
		}
		validate();
	}
	
	public void setVideoWeb(VideoWeb v){
		videoWeb = v;
	}
	
	public Video getSelectedVideo() {
		return videoSeleccionado;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public String getSubcadenaBusqueda() {
		return subCadenaBusqueda;
	}
	
	public List<String> getEtiquetasBusqueda(){
		return etiquetasBusqueda;
	}
	
	public void switchMode(Mode m) {
		if (m == Mode.EXPLORAR){
			modo = Mode.EXPLORAR;
			panelIzquierdo.setVisible(false);
			//Constantes.fixedSize(panelDerecho, 977,525 );
			panelDerecho.setVisible(true);
			//panelDerechoInv.setVisible(false);
			
		}
		else if (m == Mode.NUEVALISTA){
			modo = Mode.NUEVALISTA;
			panelIzquierdo.setVisible(true);
			//Constantes.fixedSize(panelDerecho, 770,525 );
			panelDerecho.setVisible(false);
			//panelDerechoInv.setVisible(true);
		}
	}
	
	public void setUsuario(Usuario user) {
		usuario = user;
	}
	
	public Video getVideoInLista(JList<VideoDisplay> lista) {
		System.out.println(lista.getSelectedValue().getTitulo());
		Video video = null;
		for(Video v: videosEncontrados) {
			System.out.println(v.getTitulo());
			if(v.getUrl().equals(lista.getSelectedValue().getUrl()))
				return v;
		}
		return video;
	}
	
	public void generarPDF() {
		 String dest = "C:\\Users\\Alex\\Desktop\\sample.pdf";       //CAMBIAR EL DIRECOTRIO!
	      PdfWriter writer = null;
	      try {
				writer = new PdfWriter(dest);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
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
	      JOptionPane.showMessageDialog(null, "El PDF se ha generado con éxito.", "PDF generado",
					JOptionPane.INFORMATION_MESSAGE);
	}
}


