package br.com.pavimor.gestaoEstoqueSimples.conexaoBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * Classe responsável por gerenciar a conexão com o banco de dados MySQL.
 * 
 * Centraliza as configurações de acesso e fornece conexões
 * para as classes DAO da aplicação.
 * 
 * Camada: Infraestrutura / Persistência
 * 
 * @author Lazaro Nogueira
 * @version 1.0
 * @since 2026-02-01
 */


public class BD {
	private final String URL = "jdbc:mysql://localhost:3306/gestaoEstoquePavimor";
	private final String USER = "root";
	private final String PASSWORD = "1212";
	
	/**
	 * Estabelece uma conexão com o banco de dados.
	 * 
	 * @return Conexão ativa com o banco
	 * @throws SQLException caso a conexão não possa ser realizada
	 */
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
