package Controlador;

import Modelo.Modelo;
import Vista.Vista;

public class Controlador {
	private Modelo miModelo;
	private Vista miVista;
	
	public void setModelo(Modelo miModelo) {
		this.miModelo = miModelo;
	}
	public void setVista(Vista miVista) {
		this.miVista = miVista;
	}
}
