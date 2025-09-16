package br.com.pavimor.gestaoEstoqueSimples.conexaoBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class BD {

	private final String URL = "jdbc:mysql://localhost:3306/gestaoEstoquePavimor";
	private final String USER = "root";
	private final String PASSWORD = "1212";

	public Connection conectarBD() throws SQLException {
		try {
			return DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Falha na conexão com o banco:\n" + e.getMessage(), "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
			throw e;
		}

	}

}
