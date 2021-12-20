package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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


public class PanelExplorar extends JPanel implements ActionListener{
	private JTextField textField;
	private JList<String> etiquetasDisponibles;
	private JList<String> etiquetasSeleccionadas;
	private VentanaMain ventana;
	private static VideoWeb videoWeb;
	private JButton playButton;
	private JButton btnPlay;
	private String usuario;
	
	public PanelExplorar(VentanaMain v){
		videoWeb = new VideoWeb();
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
		
		JPanel resultados = new JPanel();
		resultados.setBorder(new LineBorder(new Color(0, 0, 0)));
		resultados.setBackground(Color.LIGHT_GRAY);
		fixedSize(resultados, 700, 439);
		PanelIzquierdo.add(resultados);
		
		
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
	    
		
		
		
		
		
		
		
		JLabel lblNewLabel = new JLabel("Buscar t\u00EDtulo:");
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(40);
		
		JButton btnNewButton = new JButton("Buscar");
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Nueva Busqueda");
		panel.add(btnNewButton_1);
		
		btnPlay = new JButton("Reproducir");
		btnPlay.addActionListener(this);
		panel.add(btnPlay);
		
		
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
		String[] items = new String[] {};
		DefaultListModel<String> model = new DefaultListModel<String>();
		for (String item: items) 
			model.addElement(item);
		etiquetasDisponibles.setModel(model);
		
		JScrollPane scroller = new JScrollPane(etiquetasDisponibles);
		panel_lista_1.add(scroller);
		
		JButton anadirEtiqueta = new JButton ("añadir");
		panel_lista_1.add(anadirEtiqueta);
		JButton eliminarEtiqueta = new JButton ("eliminar");
		panel_lista_1.add(eliminarEtiqueta);
		
		JList<String> etiquetasSeleccionadas = new JList<String>(); 	//ETIQUETAS DE MAXIMO CARACTERES 30.
		etiquetasSeleccionadas.setSelectedIndex(0);
		etiquetasSeleccionadas.setVisibleRowCount(10);
		etiquetasSeleccionadas.setFixedCellWidth(165);
		String[] items2 = new String[]{};
		DefaultListModel<String> model2 = new DefaultListModel<String>();
		for (String item: items2) 
			model2.addElement(item);
		etiquetasSeleccionadas.setModel(model2);
		
		JScrollPane scroller2 = new JScrollPane(etiquetasSeleccionadas);
		scroller2.getViewport().setViewSize(panel_lista_2.getPreferredSize());
		panel_lista_2.add(scroller2);
		
		
		
		
		
		
		
		
		
		etiquetasDisponibles.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				if (!event.getValueIsAdjusting()){
					JList<String> source = (JList<String>)event.getSource();
					String selected = source.getSelectedValue().toString();
					model2.addElement(selected);
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
		
		
		anadirEtiqueta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == anadirEtiqueta)
				{
					 JFrame f=new JFrame();   
					 String nuevaEtiqueta=JOptionPane.showInputDialog(f,"Nombre de la nueva etiqueta","Nueva etiqueta",3);
					 model.addElement(nuevaEtiqueta);
				}
			}
		});
	}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()== btnPlay) { 
					for(ActionListener a: playButton.getActionListeners()) {
					    a.actionPerformed(new ActionEvent(playButton, ActionEvent.ACTION_PERFORMED, null) {
					          //Nothing need go here, the actionPerformed method (with the
					          //above arguments) will trigger the respective listener
					    	
					    });
					    }
					}
		}
				
			
		
	
	
	private void showErrorAuth() {
		JOptionPane.showMessageDialog(ventana,
				"Usuario o contraseña no valido",
				"Error",JOptionPane.ERROR_MESSAGE);
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

}
