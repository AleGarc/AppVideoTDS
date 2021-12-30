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

import controlador.ControladorTienda;
import tds.video.VideoWeb;
import modelo.Video;
import modelo.VideoDisplay;
import modelo.VideoDisplayListRenderer;


public class PanelExplorar extends JPanel implements ActionListener{
	private JTextField textField;
	private JTextField txtLista;
	private VentanaMain ventana;
	private static VideoWeb videoWeb;
	private JButton playButton;
	private JButton btnPlay;
	private JButton btnNuevaBusqueda;
	private JButton btnBusqueda;
	private String usuario;
	private Video selectedVideo;
	private List<Video> videosEncontrados = new ArrayList<Video>();
	private JPanel panelIzquierdo,panelCentral, panelDerecho;
	//private List<JLabel> videosBuscados = new ArrayList<JLabel>();
	
	DefaultListModel<String> model = new DefaultListModel<String>();
	DefaultListModel<String> model2 = new DefaultListModel<String>();
	
	DefaultListModel<VideoDisplay> modelLista = new DefaultListModel<VideoDisplay>();
	DefaultListModel<VideoDisplay> modelVideos = new DefaultListModel<VideoDisplay>();
	
	private String subCadenaBusqueda;
	private List<String> etiquetasBusqueda = new ArrayList<String>();
	
	ControladorTienda controladorTienda = ControladorTienda.getUnicaInstancia();
	
	//JPanel resultados = new JPanel();
	public PanelExplorar(VentanaMain v, VideoWeb vWeb){
		//videoWeb = new VideoWeb();
		ventana=v; 
		videoWeb = vWeb;
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
		fixedSize(panelIzquierdo, 270,525 );
		Ventana.add(panelIzquierdo);
		JLabel lbLista = new JLabel("Introducir nombre lista:");
		panelIzquierdo.add(lbLista);
		
		txtLista = new JTextField();
		fixedSize(txtLista, 177,20);
		panelIzquierdo.add(txtLista);
		
		
		JButton btnBuscarLista = new JButton("Buscar");
		btnBuscarLista.addActionListener(ev ->{
			if(controladorTienda.checkVideoListExiste(txtLista.getText(), usuario)) {
				
			}
				
		});
		panelIzquierdo.add(btnBuscarLista);
		
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
				if (e.getClickCount() == 2)
				{
					modelLista.removeElement(listaVideos.getSelectedValue());
				}
			}
		});
		
		JScrollPane scrollerListaVideos = new JScrollPane(listaVideos);
		scrollerListaVideos.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollerListaVideos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		fixedSize(scrollerListaVideos, 258,460);
		panelIzquierdo.add(scrollerListaVideos);
		
		//fixedSize(scrollerResultados, 204, 400);
		//JButton btnCancelar = new JButton("Cancelar");
		//scrollerResultados.setViewportView(Ventana);
		//panelIzquierdo.add(scrollerResultados);
		//panelIzquierdo.add(btnCancelar);
		//scrollerResultados.setVisible(false);
		
		//panelIzquierdo.setVisible(false);
		
		
		panelCentral = new JPanel();
		panelCentral.setBackground(Color.LIGHT_GRAY);
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		fixedSize(panelCentral, 700, Constantes.ventana_y_size-195);
		Ventana.add(panelCentral);
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(Color.LIGHT_GRAY);
		fixedSize(panel, 700,80);
		panelCentral.add(panel);
		//panelCentral.setBackground(Color.YELLOW);
		
		Component rigidArea2 = Box.createRigidArea(new Dimension(5, 5));
		panelCentral.add(rigidArea2);
		
		/*resultados = new JPanel();
		resultados.setBorder(new LineBorder(new Color(0, 0, 0)));
		resultados.setBackground(Color.LIGHT_GRAY);
		//fixedSize(resultados, 700, 439);
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
				if (e.getClickCount() == 2)
				{
					if(!modelLista.contains(lista.getSelectedValue()))
						modelLista.addElement(lista.getSelectedValue());
				}
			}
		});
		
		JScrollPane scrollerResultados = new JScrollPane(lista);
		scrollerResultados.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollerResultados.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		fixedSize(scrollerResultados, 700,439);
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
			for(ActionListener a: btnBusqueda.getActionListeners()) {
			    a.actionPerformed(new ActionEvent(btnBusqueda, ActionEvent.ACTION_PERFORMED, null) {
			          //Nothing need go here, the actionPerformed method (with the
			          //above arguments) will trigger the respective listener
			    	
			    });
			    }
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
		fixedSize(panelDerecho, 270, Constantes.ventana_y_size-195);
		panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		fixedSize(panel_1, 270, (Constantes.ventana_y_size-195)/2-5);
		panelDerecho.add(panel_1);
		
		Component rigidArea = Box.createRigidArea(new Dimension(5, 5));
		panelDerecho.add(rigidArea);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		fixedSize(panel_2, 270, (Constantes.ventana_y_size-195)/2);
		panelDerecho.add(panel_2);
		
		JLabel lblNewLabel_1 = new JLabel("Etiquetas disponibles");
		panel_1.add(lblNewLabel_1);
		JPanel panel_lista_1 = new JPanel();
		fixedSize(panel_lista_1, 220,220);
		panel_lista_1.setBackground(Color.LIGHT_GRAY);
		panel_1.add(panel_lista_1);
		
		JLabel lblNewLabel_2 = new JLabel("Etiquetas seleccionadas");
		panel_2.add(lblNewLabel_2);
		
		JPanel panel_lista_2 = new JPanel();
		fixedSize(panel_lista_2, 220,200);
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

	private void fixedSize(JComponent c,int x, int y) {
		c.setMinimumSize(new Dimension(x,y));
		c.setMaximumSize(new Dimension(x,y));
		c.setPreferredSize(new Dimension(x,y));
	}
	
	public String getUsuario() {
		return usuario;
	}
	
	public void setPlayMainButton(JButton playMainButton)
	{
		playButton = playMainButton;
	}

	public void setBotonBusqueda(JButton btnBusqueda) {
		this.btnBusqueda = btnBusqueda; 
	}
	
	public void update(List<String> etiquetasDadas) {	
		model.removeAllElements();
		for (String etiqueta: etiquetasDadas) 
			model.addElement(etiqueta);
		model2.removeAllElements();
				//HAY QUE USAR VALIDATE() MIRAR 
	}
	
	/*public void updateResultados() {
		resultados.removeAll();
		for(JLabel label: videosBuscados) {
			resultados.add(label);
		}
	}*/
	
	public void limpiar() {
		textField.setText("");
		model2.removeAllElements();
		modelVideos.removeAllElements();
		etiquetasBusqueda.clear();
	}
	
	public void updateVideos(List<Video> videos) {
		videosEncontrados = videos;
		modelVideos.removeAllElements();
		for(Video v: videos) {
			modelVideos.addElement(new VideoDisplay(v.getTitulo(),v.getUrl(),videoWeb.getThumb(v.getUrl())));
		}
		validate();
	}
	
	public void setVideoWeb(VideoWeb v){
		videoWeb = v;
	}
	
	public Video getSelectedVideo() {
		return selectedVideo;
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
			panelIzquierdo.setVisible(false);
			//fixedSize(panelDerecho, 977,525 );
			panelDerecho.setVisible(true);
			//panelDerechoInv.setVisible(false);
			
		}
		else if (m == Mode.NUEVALISTA){
			panelIzquierdo.setVisible(true);
			//fixedSize(panelDerecho, 770,525 );
			panelDerecho.setVisible(false);
			//panelDerechoInv.setVisible(true);
		}
	}
	
	public void setUsuario(String user) {
		usuario = user;
	}
}


