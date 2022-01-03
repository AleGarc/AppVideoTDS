package vista;

import java.awt.Dimension;

import javax.swing.JComponent;

public class Constantes {
	public static final int ventana_x_size=1024;
	public static final int ventana_y_size=720;
	public static final int x_size =1024;
	public static final int y_size =720;
	
	public static void fixedSize(JComponent c,int x, int y) {
		c.setMinimumSize(new Dimension(x,y));
		c.setMaximumSize(new Dimension(x,y));
		c.setPreferredSize(new Dimension(x,y));
	}
}
