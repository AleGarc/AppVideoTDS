package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BoxLayout;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import java.awt.Component;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;

import controlador.ControladorAppVideo;


public class PanelAltaCliente extends JPanel{
	
	private VentanaMain ventana;
	private JLabel lblNombre;
	private JLabel lblApellidos;
	private JLabel lblFechaNacimiento;
	private JLabel lblEmail;
	private JLabel lblUsuario;
	private JLabel lblPassword;
	private JLabel lblPasswordChk;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JDateChooser fechaNacimiento;
	private JTextField txtEmail;
	private JTextField txtUsuario;
	private JPasswordField txtPassword;
	private JPasswordField txtPasswordChk;
	private JButton btnRegistrar;
	private JButton btnCancelar;

	private JLabel lblNombreError;
	private JLabel lblFechaNacimientoError;
	private JLabel lblUsuarioError;
	private JLabel lblPasswordError;
	private JPanel panelCampoNombre;
	private JPanel panel;
	private JPanel panelCampoApellidos;
	private JPanel panelCamposEmail;
	private JPanel panelCamposUsuario;
	private JPanel panelCamposFechaNacimiento;
	private JPanel datosPersonales;
	private JPanel frmRegistro;
	private JPanel panelInvisible;
	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public PanelAltaCliente(VentanaMain v){
        ventana=v; 
        initialize();
    }

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		
		frmRegistro = new JPanel();
		frmRegistro.setLayout(new BoxLayout(frmRegistro, BoxLayout.Y_AXIS));
		//frmRegistro.
		//frmRegistro.setTitle("Registro AppMusic");
		//frmRegistro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frmRegistro.getContentPane().setLayout(new BorderLayout());
		//frmRegistro.setResizable(false);
		//frmRegistro.getContentPane().setLayout(new BorderLayout());
		fixedSize(frmRegistro, Constantes.ventana_x_size-35, Constantes.ventana_y_size-180);
		frmRegistro.setBorder(new LineBorder(new Color(0, 0, 0)));
		//Container contentPane = frmRegistro.getContentPane();
		panelInvisible = new JPanel();
		panelInvisible.setBackground(Color.LIGHT_GRAY);
		
		frmRegistro.add(panelInvisible);
		frmRegistro.setBackground(Color.LIGHT_GRAY);
		
		datosPersonales = new JPanel();
		frmRegistro.add(datosPersonales);
		datosPersonales.setBackground(Color.LIGHT_GRAY);
		datosPersonales.setBorder(new LineBorder(new Color(0, 0, 0)));
		fixedSize(datosPersonales,400,300);
		//datosPersonales.setBorder(new TitledBorder(null, "Datos de Registro", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		datosPersonales.setLayout(new BoxLayout(datosPersonales, BoxLayout.Y_AXIS));
		
		datosPersonales.add(creaLineaNombre());
		datosPersonales.add(crearLineaApellidos());
		datosPersonales.add(crearLineaFechaNacimiento());
		datosPersonales.add(crearLineaEmail());
		datosPersonales.add(crearLineaUsuario());
		datosPersonales.add(crearLineaPassword());
		

		datosPersonales.add(crearPanelBotones());

		ocultarErrores();

		//frmRegistro.revalidate();
		//frmRegistro.pack();
		add(frmRegistro);
}
	private JPanel creaLineaNombre() {
		JPanel lineaNombre = new JPanel();
		//lineaNombre.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		lineaNombre.setLayout(new BoxLayout(lineaNombre, BoxLayout.Y_AXIS));
		lineaNombre.setBackground(Color.LIGHT_GRAY);
		panelCampoNombre = new JPanel();
		panelCampoNombre.setBackground(Color.LIGHT_GRAY);
		lineaNombre.add(panelCampoNombre);//, BorderLayout.CENTER);
		//fixedSize(panelCampoNombre, 400,30);
		lblNombre = new JLabel("*Nombre: ", JLabel.RIGHT);
		panelCampoNombre.add(lblNombre);
		fixedSize(lblNombre, 75, 20);
		txtNombre = new JTextField();
		panelCampoNombre.add(txtNombre);
		fixedSize(txtNombre, 270, 20);
		
		lblNombreError = new JLabel("El nombre es obligatorio", SwingConstants.LEFT);
		
		lineaNombre.add(lblNombreError);//, BorderLayout.SOUTH);
		fixedSize(lblNombreError, 224, 15);
		lblNombreError.setForeground(Color.RED);
		
		return lineaNombre;
	}

	private JPanel crearLineaApellidos() {
		JPanel lineaApellidos = new JPanel();
		//lineaApellidos.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		lineaApellidos.setLayout(new BoxLayout(lineaApellidos, BoxLayout.Y_AXIS));
		lineaApellidos.setBackground(Color.LIGHT_GRAY);
		panelCampoApellidos = new JPanel();
		panelCampoApellidos.setBackground(Color.LIGHT_GRAY);
		lineaApellidos.add(panelCampoApellidos);
		
		lblApellidos = new JLabel("Apellidos: ", JLabel.RIGHT);
		panelCampoApellidos.add(lblApellidos);
		fixedSize(lblApellidos, 75, 20);
		txtApellidos = new JTextField();
		panelCampoApellidos.add(txtApellidos);
		fixedSize(txtApellidos, 270, 20);

		
		
		
		return lineaApellidos;
	}

	private JPanel crearLineaEmail() {
		JPanel lineaEmail = new JPanel();
		//lineaEmail.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		lineaEmail.setLayout(new BoxLayout(lineaEmail, BoxLayout.Y_AXIS));
		lineaEmail.setBackground(Color.LIGHT_GRAY);
		panelCamposEmail = new JPanel();
		panelCamposEmail.setBackground(Color.LIGHT_GRAY);
		lineaEmail.add(panelCamposEmail);
		
		lblEmail = new JLabel("Email: ", JLabel.RIGHT);
		panelCamposEmail.add(lblEmail);
		fixedSize(lblEmail, 75, 20);
		txtEmail = new JTextField();
		panelCamposEmail.add(txtEmail);
		fixedSize(txtEmail, 270, 20);
	
		
		return lineaEmail;
	}

	private JPanel crearLineaUsuario() {
		JPanel lineaUsuario = new JPanel();
		//lineaUsuario.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		lineaUsuario.setLayout(new BoxLayout(lineaUsuario, BoxLayout.Y_AXIS));
		lineaUsuario.setBackground(Color.LIGHT_GRAY);
		panelCamposUsuario = new JPanel();
		panelCamposUsuario.setBackground(Color.LIGHT_GRAY);
		lineaUsuario.add(panelCamposUsuario);
		
		lblUsuario = new JLabel("*Usuario: ", JLabel.RIGHT);
		panelCamposUsuario.add(lblUsuario);
		fixedSize(lblUsuario, 75, 20);
		txtUsuario = new JTextField();
		panelCamposUsuario.add(txtUsuario);
		fixedSize(txtUsuario, 270, 20);
		lblUsuarioError = new JLabel("El usuario ya existe", SwingConstants.LEFT);
		fixedSize(lblUsuarioError, 150, 15);
		lblUsuarioError.setForeground(Color.RED);
		lineaUsuario.add(lblUsuarioError);
		
		return lineaUsuario;
	}

	private JPanel crearLineaPassword() {
		JPanel lineaPassword = new JPanel();
		//lineaPassword.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		lineaPassword.setLayout(new BoxLayout(lineaPassword, BoxLayout.Y_AXIS));
		lineaPassword.setBackground(Color.LIGHT_GRAY);
		panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		lineaPassword.add(panel);
		
		lblPassword = new JLabel("*Password: ", JLabel.RIGHT);
		panel.add(lblPassword);
		fixedSize(lblPassword, 75, 20);
		txtPassword = new JPasswordField();
		panel.add(txtPassword);
		fixedSize(txtPassword, 100, 20);
		lblPasswordChk = new JLabel("*Otra vez:", JLabel.RIGHT);
		panel.add(lblPasswordChk);
		fixedSize(lblPasswordChk, 60, 20);
		txtPasswordChk = new JPasswordField();
		panel.add(txtPasswordChk);
		fixedSize(txtPasswordChk, 100, 20);

		lblPasswordError = new JLabel("Error al introducir las contraseñas", JLabel.LEFT);
		lineaPassword.add(lblPasswordError);
		lblPasswordError.setForeground(Color.RED);
		
		return lineaPassword;
	}

	private JPanel crearLineaFechaNacimiento() {
		
		JPanel lineaFechaNacimiento = new JPanel();
		//lineaFechaNacimiento.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		lineaFechaNacimiento.setLayout(new BoxLayout(lineaFechaNacimiento, BoxLayout.Y_AXIS));
		lineaFechaNacimiento.setBackground(Color.LIGHT_GRAY);
		panelCamposFechaNacimiento = new JPanel();
		panelCamposFechaNacimiento.setBackground(Color.LIGHT_GRAY);
		lineaFechaNacimiento.add(panelCamposFechaNacimiento);
		
		lblFechaNacimiento = new JLabel("*Fecha de Nacimiento: ", JLabel.RIGHT);
		panelCamposFechaNacimiento.add(lblFechaNacimiento);
		fixedSize(lblFechaNacimiento, 130, 20);
		
		
		fechaNacimiento = new JDateChooser();
		fixedSize(fechaNacimiento, 215,20);
		//txtFechaNacimiento = new JTextField();
		panelCamposFechaNacimiento.add(fechaNacimiento);
		//fixedSize(txtFechaNacimiento, 215, 20);
		lblFechaNacimientoError = new JLabel("Introduce la fecha de nacimiento", SwingConstants.LEFT);
		fixedSize(lblFechaNacimientoError, 150, 15);
		lblFechaNacimientoError.setForeground(Color.RED);
		lineaFechaNacimiento.add(lblFechaNacimientoError);
		
		return lineaFechaNacimiento;
	}

	private JPanel crearPanelBotones() {
		JPanel lineaBotones = new JPanel(); 
		//frmRegistro.getContentPane().add(lineaBotones, BorderLayout.SOUTH);
		lineaBotones.setBorder(new EmptyBorder(5, 0, 0, 0));
		lineaBotones.setLayout(new FlowLayout(FlowLayout.CENTER));
		lineaBotones.setBackground(Color.LIGHT_GRAY);
		
		btnRegistrar = new JButton("Registrar");
		lineaBotones.add(btnRegistrar);
		
		btnCancelar = new JButton("Cancelar");
		lineaBotones.add(btnCancelar);

		crearManejadorBotonRegistar();
		crearManejadorBotonCancelar();
		
		return lineaBotones;
	}

	private void crearManejadorBotonRegistar() {
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean OK = false;
				OK = checkFields();
				if (OK) {
					boolean registrado = false;
					Date fecha = fechaNacimiento.getDate();
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
					String fechaString = formato.format(fecha);
					String nombre_completo = txtNombre.getText() + " " +txtApellidos.getText();
					registrado = ControladorAppVideo.getUnicaInstancia().registrarUsuario(nombre_completo, txtEmail.getText(), txtUsuario.getText(),
							new String(txtPassword.getPassword()), 
							fechaString);
					if (registrado) {
						JOptionPane.showMessageDialog(frmRegistro, "Asistente registrado correctamente.", "Registro",
								JOptionPane.INFORMATION_MESSAGE);
						
						txtNombre.setText(""); txtApellidos.setText(""); 
						txtEmail.setText(""); txtPassword.setText(""); 
						txtUsuario.setText(""); fechaNacimiento.setCalendar(null);
						txtPasswordChk.setText("");
					} else {
						JOptionPane.showMessageDialog(frmRegistro, "No se ha podido llevar a cabo el registro.\n",
								"Registro", JOptionPane.ERROR_MESSAGE);
						//frmRegistro.setTitle("Login Gestor Eventos");
					}
				}
			}
		});
	}

	private void crearManejadorBotonCancelar() {
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtNombre.setText("");
				txtApellidos.setText("");
				txtEmail.setText("");
				txtUsuario.setText("");
				txtPassword.setText("");
				txtPasswordChk.setText("");
				txtUsuario.setText("");
				fechaNacimiento.setCalendar(null);
				ocultarErrores();
			}
		});
	}

	/**
	 * Comprueba que los campos de registro estén bien
	 */
	private boolean checkFields() {
		boolean salida = true;
		/* borrar todos los errores en pantalla */
		ocultarErrores();
		if (txtNombre.getText().trim().isEmpty()) {
			lblNombreError.setVisible(true);
			lblNombre.setForeground(Color.RED);
			txtNombre.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
			fixedSize(datosPersonales,400,400);
			fixedSize(panelInvisible,400,50);
			//rigidArea5;
		}
		if (txtUsuario.getText().trim().isEmpty()) {
			lblUsuarioError.setText("El usuario es obligatorio");
			lblUsuarioError.setVisible(true);
			lblUsuario.setForeground(Color.RED);
			txtUsuario.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
			fixedSize(datosPersonales,400,400);
			fixedSize(panelInvisible,400,50);
		}
		String password = new String(txtPassword.getPassword());
		String password2 = new String(txtPasswordChk.getPassword());
		if (password.isEmpty()) {
			lblPasswordError.setText("El password no puede estar vacio");
			lblPasswordError.setVisible(true);
			lblPassword.setForeground(Color.RED);
			txtPassword.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
			fixedSize(datosPersonales,400,400);
			fixedSize(panelInvisible,400,50);
		} 
		if (password2.isEmpty()) {
			lblPasswordError.setText("El password no puede estar vacio");
			lblPasswordError.setVisible(true);
			lblPasswordChk.setForeground(Color.RED);
			txtPasswordChk.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
			fixedSize(datosPersonales,400,400);
			fixedSize(panelInvisible,400,50);
		} 
		if (!password.equals(password2)) {
			lblPasswordError.setText("Los dos passwords no coinciden");
			lblPasswordError.setVisible(true);
			lblPassword.setForeground(Color.RED);
			lblPasswordChk.setForeground(Color.RED);
			txtPassword.setBorder(BorderFactory.createLineBorder(Color.RED));
			txtPasswordChk.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
			fixedSize(datosPersonales,400,400);
			fixedSize(panelInvisible,400,50);
		}
		/* Comprobar que no exista otro usuario con igual login */
		/*if (!lblUsuarioError.getText().isEmpty() && Controlador.getUnicaInstancia().esUsuarioRegistrado(txtUsuario.getText())) {
			lblUsuarioError.setText("Ya existe ese usuario");
			lblUsuarioError.setVisible(true);
			lblUsuario.setForeground(Color.RED);
			txtUsuario.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
		}*/
		if (fechaNacimiento.getDate() == null) {
			lblFechaNacimientoError.setVisible(true);
			lblFechaNacimiento.setForeground(Color.RED);
			fechaNacimiento.setBorder(BorderFactory.createLineBorder(Color.RED));
			salida = false;
			fixedSize(datosPersonales,400,400);
			fixedSize(panelInvisible,400,50);
		}

		frmRegistro.revalidate();
		//frmRegistro.pack();
		
		return salida;
	}

	/**
	 * Oculta todos los errores que pueda haber en la pantalla
	 */
	private void ocultarErrores() {
		lblNombreError.setVisible(false);
		lblFechaNacimientoError.setVisible(false);
		lblUsuarioError.setVisible(false);
		lblPasswordError.setVisible(false);
		lblFechaNacimientoError.setVisible(false);
		
		Border border = new JTextField().getBorder();
		txtNombre.setBorder(border);
		//txtApellidos.setBorder(border);
		//txtEmail.setBorder(border);
		txtUsuario.setBorder(border);
		txtPassword.setBorder(border);
		txtPasswordChk.setBorder(border);
		txtPassword.setBorder(border);
		txtPasswordChk.setBorder(border);
		txtUsuario.setBorder(border);
		fechaNacimiento.setBorder(border);
		
		lblNombre.setForeground(Color.BLACK);
		lblApellidos.setForeground(Color.BLACK);
		lblEmail.setForeground(Color.BLACK);
		lblUsuario.setForeground(Color.BLACK);
		lblPassword.setForeground(Color.BLACK);
		lblPasswordChk.setForeground(Color.BLACK);
		lblFechaNacimiento.setForeground(Color.BLACK);
		fixedSize(datosPersonales,400,300);
		fixedSize(panelInvisible,400,110);
	}

	/**
	 * Fija el tamaño de un componente
	 */
	private void fixedSize(JComponent o, int x, int y) {
		Dimension d = new Dimension(x, y);
		o.setMinimumSize(d);
		o.setMaximumSize(d);
		o.setPreferredSize(d);
	}
}
