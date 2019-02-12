package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.swing.table.DefaultTableModel;

import Vista.Vista;

public class Modelo {
	private Vista miVista;

	private String bd;
	private String login;
	private String pwd;
	private String url;
	private String driver;
	private String sqlTabla1;
	private String sqlTabla2;
	private Connection conexion;

	private DefaultTableModel miTabla;

	public Modelo() {
		bd = "liga";
		login = "root";
		pwd = "";
		url = "jdbc:mysql://localhost/" + bd;
		driver = "com.mysql.cj.jdbc.Driver";
		sqlTabla1 = "Select * from prueba";
		sqlTabla2 = "Select * from equipos";
		try {
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, login, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
//		cargarTabla1();
		cargarTabla2();
	}

	private void cargarTabla1() {
		int numColumnas = getNumColumnas(sqlTabla1);
		int numFilas = getNumFilas(sqlTabla1);

		String[] cabecera = new String[numColumnas];

		Object[][] contenido = new Object[numFilas][numColumnas];
		PreparedStatement pstmt;
		try {
			pstmt = conexion.prepareStatement(sqlTabla1);
			ResultSet rset = pstmt.executeQuery();
			ResultSetMetaData rsmd = rset.getMetaData();
			for (int i = 0; i < numColumnas; i++) {
				cabecera[i] = rsmd.getColumnName(i+1);
			}
			int fila = 0;
			while (rset.next()) {
				for (int col = 1; col <= numColumnas; col++) {
					contenido[fila][col - 1] = rset.getString(col);
				}
				fila++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		miTabla = new DefaultTableModel(contenido, cabecera);
	}

	private void cargarTabla2() {
		miTabla = new DefaultTableModel();
		int numColumnas = getNumColumnas(sqlTabla2);
		Object[] contenido = new Object[numColumnas];
		PreparedStatement pstmt;
		try {
			pstmt = conexion.prepareStatement(sqlTabla2);
			ResultSet rset = pstmt.executeQuery();
			ResultSetMetaData rsmd = rset.getMetaData();
			for (int i = 0; i < numColumnas; i++) {
				miTabla.addColumn(rsmd.getColumnName(i+1));
			}
			while (rset.next()) {
				for (int col = 1; col <= numColumnas; col++) {
					contenido[col - 1] = rset.getString(col);
				}
				miTabla.addRow(contenido);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private int getNumColumnas(String sql) {
		int num = 0;
		try {
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			ResultSet rset = pstmt.executeQuery();
			ResultSetMetaData rsmd = rset.getMetaData();
			num = rsmd.getColumnCount();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return num;
	}

	private int getNumFilas(String sql) {
		int numFilas = 0;
		try {
			PreparedStatement pstmt = conexion.prepareStatement(sql);
			ResultSet rset = pstmt.executeQuery();
			while (rset.next())
				numFilas++;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return numFilas;
	}

	public DefaultTableModel getTabla() {
		return miTabla;
	}

	public void setVista(Vista miVista) {
		this.miVista = miVista;
	}

}
