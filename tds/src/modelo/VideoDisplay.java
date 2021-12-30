package modelo;


import java.awt.Dimension;
import java.awt.Image;
import java.awt.Color;
import javax.swing.*;


public class VideoDisplay extends JPanel {
	private String titulo;
	private String url;
	private ImageIcon icono;
	
    public VideoDisplay(String titulo, String url, ImageIcon icono){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); 
    	this.titulo = titulo;
    	this.url = url;
    	this.icono = icono;
    	
        this.setMaximumSize(new Dimension(170,100));
        this.setPreferredSize(new Dimension(170,100));
        this.setMinimumSize(new Dimension(170,100));
        
        this.setBackground(Color.WHITE);
        
        JLabel lblimagen = new JLabel();
        ImageIcon imageIcon = icono;
		Image image = imageIcon.getImage(); 
		Image newimg = image.getScaledInstance(80, 60,  java.awt.Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newimg); 
		lblimagen.setIcon(imageIcon);
		lblimagen.setHorizontalTextPosition(JLabel.CENTER);
		lblimagen.setVerticalTextPosition(JLabel.BOTTOM);
		lblimagen.setAlignmentX(CENTER_ALIGNMENT);
        this.add(lblimagen);
        
        JLabel lblTexto = new JLabel(titulo);
        lblTexto.setAlignmentX(CENTER_ALIGNMENT);
        this.add(lblTexto);
        
    }

	public String getTitulo() {
		return titulo;
	}

	public String getUrl() {
		return url;
	}

	public Icon getIcono() {
		return icono;
	}    

}
