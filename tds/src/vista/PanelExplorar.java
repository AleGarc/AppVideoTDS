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
	private VentanaMain ventana;
	private static VideoWeb videoWeb;
	private JButton playButton;
	private JButton btnPlay;
	private JButton btnNuevaBusqueda;
	private String usuario;
	private Video selectedVideo;
	private List<Video> videosEncontrados = new ArrayList<Video>();
	//private List<JLabel> videosBuscados = new ArrayList<JLabel>();
	
	DefaultListModel<String> model = new DefaultListModel<String>();
	DefaultListModel<String> model2 = new DefaultListModel<String>();
	
	DefaultListModel<VideoDisplay> modelVideos = new DefaultListModel<VideoDisplay>();
	
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
		
		JPanel PanelIzquierdo = new JPanel();
		PanelIzquierdo.setBackground(Color.LIGHT_GRAY);
		PanelIzquierdo.setLayout(new BoxLayout(PanelIzquierdo, BoxLayout.Y_AXIS));
		fixedSize(PanelIzquierdo, 700, Constantes.ventana_y_size-195);
		Ventana.add(PanelIzquierdo);
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBackground(Color.LIGHT_GRAY);
		fixedSize(panel, 700,80);
		PanelIzquierdo.add(panel);
		//PanelIzquierdo.setBackground(Color.YELLOW);
		
		Component rigidArea2 = Box.createRigidArea(new Dimension(5, 5));
		PanelIzquierdo.add(rigidArea2);
		
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
					selectedVideo = videosEncontrados.get(lista.getSelectedIndex());
					for(ActionListener a: playButton.getActionListeners()) {
					    a.actionPerformed(new ActionEvent(playButton, ActionEvent.ACTION_PERFORMED, null) {
					          //Nothing need go here, the actionPerformed method (with the
					          //above arguments) will trigger the respective listener
					    	
					    });
					    }
				}
			}
		});
		
		JScrollPane scrollerResultados = new JScrollPane(lista);
		scrollerResultados.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollerResultados.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		fixedSize(scrollerResultados, 700,439);
		PanelIzquierdo.add(scrollerResultados);
		//PanelIzquierdo.add(resultados);
		
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
		panel.add(btnNewButton);
		
		btnNuevaBusqueda = new JButton("Nueva Busqueda");
		btnNuevaBusqueda.addActionListener(this);
		panel.add(btnNuevaBusqueda);
			
		
		JPanel panelDerecho = new JPanel();
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
					if(!model2.contains(selected)) model2.addElement(selected);
					
				}
			}
		});
		
		etiquetasSeleccionadas.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()){
					 int index = etiquetasSeleccionadas.getSelectedIndex();
					 if (index != -1) {
				        model2.remove(index);
					 }
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
	
	public void updateVideos(List<Video> videos) {
		videosEncontrados = videos;
		for(Video v: videos) {
			modelVideos.addElement(new VideoDisplay(v.getTitulo(),v.getUrl(),videoWeb.getThumb(v.getUrl())));
		}
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
}
