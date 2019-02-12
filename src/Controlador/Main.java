package Controlador;

import Modelo.Modelo;
import Vista.Vista;


public class Main {

	public static void main(String[] args) {
		
		Controlador miControlador = new Controlador ();
		Modelo miModelo = new Modelo ();
		Vista miVista = new Vista();
		miControlador.setVista(miVista);
		miControlador.setModelo(miModelo);
		
		miModelo.setVista(miVista);
		
		miVista.setModelo(miModelo);
		miVista.setControlador(miControlador);
		
		miVista.setVisible(true);

	}

}
