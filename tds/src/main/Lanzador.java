package main;

import java.awt.EventQueue;

import vista.VentanaMain;

public class Lanzador {
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMain principal = new VentanaMain();
					principal.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}
