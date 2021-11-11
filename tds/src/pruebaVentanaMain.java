import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.border.BevelBorder;

import vista.Constantes;
import vista.VentanaMain;

import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;

public class pruebaVentanaMain {

	private JFrame frmAppVideo;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pruebaVentanaMain window = new pruebaVentanaMain();
					window.frmAppVideo.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public pruebaVentanaMain() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmAppVideo = new JFrame();
		frmAppVideo.setForeground(Color.BLACK);
		frmAppVideo.setTitle("APP VIDEO");
		frmAppVideo.setIconImage(Toolkit.getDefaultToolkit().getImage(pruebaVentanaMain.class.getResource("/vista/iconoAppVideo.jpg")));
		frmAppVideo.setBounds(700, 250, 1024, 720);
		frmAppVideo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAppVideo.getContentPane().setLayout(new BoxLayout(frmAppVideo.getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel Contenido = new JPanel();
		Contenido.setLayout(new FlowLayout(FlowLayout.LEFT,10,10));  
		frmAppVideo.getContentPane().add(Contenido);
		JPanel Menu = new JPanel();
		//panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		Menu.setLayout(new FlowLayout(FlowLayout.LEFT,10,2));  
		Menu.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		Menu.setPreferredSize(new Dimension(Constantes.ventana_x_size-35,60));
		Menu.setMaximumSize(Menu.getPreferredSize());
		//frmAppVideo.getContentPane().add(Menu);
		Contenido.add(Menu);
		
		JButton btnNewButton = new JButton("Registro");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		
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
		Opciones.setPreferredSize(new Dimension(Constantes.ventana_x_size-35, 40));
		Opciones.setMaximumSize(new Dimension(Constantes.ventana_x_size-35, 40));
		//frmAppVideo.getContentPane().add(Opciones);
		Contenido.add(Opciones);
		
		JPanel Ventana = new JPanel();
		Ventana.setBorder(new LineBorder(new Color(0, 0, 0)));
		Ventana.setBackground(Color.LIGHT_GRAY);
		Ventana.setPreferredSize(new Dimension(Constantes.ventana_x_size-35, Constantes.ventana_y_size-180));
		Ventana.setMaximumSize(Ventana.getPreferredSize());
		
		//frmAppVideo.getContentPane().add(Ventana);
		Contenido.add(Ventana);
		//Ventana.setLayout(new BoxLayout(Ventana, BoxLayout.Y_AXIS));
		Ventana.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		Component rigidArea5 = Box.createRigidArea(new Dimension(700, 100));
		Ventana.add(rigidArea5);
		
		JPanel panel = new JPanel();
		
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		//panel.setAlignmentX(75);
		panel.setBorder(new LineBorder(Color.DARK_GRAY));
		panel.setPreferredSize(new Dimension(400, 300));
		panel.setMaximumSize(new Dimension(400, 300));
		Ventana.add(panel);
		
		Component rigidArea_3_1 = Box.createRigidArea(new Dimension(400, 60));
		panel.add(rigidArea_3_1);
		
		JLabel lblNewLabel_2 = new JLabel("Usuario:");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblNewLabel_2);
		
		textField = new JTextField();
		panel.add(textField);
		textField.setColumns(20);
		
		Component rigidArea_3_1_1_1 = Box.createRigidArea(new Dimension(400, 50));
		panel.add(rigidArea_3_1_1_1);
		
		JLabel lblNewLabel_2_1 = new JLabel("Password");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblNewLabel_2_1);
		
		textField_1 = new JTextField();
		textField_1.setHorizontalAlignment(SwingConstants.LEFT);
		textField_1.setColumns(20);
		panel.add(textField_1);
		
		Component rigidArea_3_1_1_2 = Box.createRigidArea(new Dimension(3, 20));
		panel.add(rigidArea_3_1_1_2);
		
		Component rigidArea_3_1_1 = Box.createRigidArea(new Dimension(400, 50));
		panel.add(rigidArea_3_1_1);
		
		JButton btnNewButton_5 = new JButton("Aceptar");
		panel.add(btnNewButton_5);
		
		Component rigidArea_4 = Box.createRigidArea(new Dimension(60, 20));
		panel.add(rigidArea_4);
		
		JButton btnNewButton_6 = new JButton("Cancelar");
		panel.add(btnNewButton_6);
		
		JButton btnNewButton_3 = new JButton("Explorar");
		btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Opciones.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("Mis Listas");
		btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Opciones.add(btnNewButton_4);
		
		JButton btnNewButton_4_1 = new JButton("Recientes");
		btnNewButton_4_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Opciones.add(btnNewButton_4_1);
		
		JButton btnNewButton_4_2 = new JButton("Nueva Lista");
		btnNewButton_4_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		Opciones.add(btnNewButton_4_2);
		
	}

}
