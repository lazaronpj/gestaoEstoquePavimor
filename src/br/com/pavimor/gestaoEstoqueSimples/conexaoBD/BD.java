package br.com.pavimor.gestaoEstoqueSimples.conexaoBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class BD {
	private static final String URL = "jdbc:mysql://localhost:3306/gestaoEstoquePavimor";
	private static final String USER = "root";
	private static final String PASSWORD = "1212";

	public Connection conectarBD() throws SQLException {
		try {
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "<html><font color = 'RED'>Falha na conexão com o banco de dados!</font></html>" + e.getMessage(), "Erro de Conexão", JOptionPane.ERROR_MESSAGE);

			System.out.println("Erro ao conectar no banco: " + e.getMessage());
			throw e;
		}
	}
}
