package br.com.pavimor.gestaoEstoqueSimples.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import br.com.pavimor.gestaoEstoqueSimples.conexaoBD.BD;
import br.com.pavimor.gestaoEstoqueSimples.model.Produto;

/**
 * Classe responsável pelas operações de persistência de Produto no banco de dados.
 * 
 * Implementa operações CRUD (Create, Read, Update e Delete),
 * isolando a lógica de acesso a dados da aplicação.
 * 
 * Camada: DAO (Data Access Object)
 * 
 * @author Lazaro Nogueira
 * @version 1.0
 * @since 2026-02-01
 */


public class DaoProduto {

	/**
	 * Insere um novo produto no banco de dados.
	 * 
	 * @param pdt Produto a ser persistido
	 * @return true se a inserção ocorrer com sucesso
	 * @return false caso ocorra erro na operação
	 */

	public boolean inserir(Produto pdt) {
		String sql = "INSERT INTO produtos (nomeProduto, unidade, localizacao, quantidade, custoUnitario, dataEntrada) VALUES (?, ?, ?, ?, ?, NOW())";
		try (Connection conexao = new BD().conectarBD(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setString(1, pdt.getNomeProduto());
			stmt.setString(2, pdt.getUnidade());
			stmt.setString(3, pdt.getLocalizacao());
			stmt.setInt(4, pdt.getQuantidade());
			stmt.setDouble(5, pdt.getCustoUnitario());

			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			alertaErro("Erro ao inserir produtos no banco de dados!");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Lista todos os produtos cadastrados no banco de dados.
	 * 
	 * @return Lista de produtos encontrados
	 */
	public List<Produto> listarTodos() {
		List<Produto> produtos = new ArrayList<>();
		String sql = "SELECT * FROM produtos";

		try (Connection conexao = new BD().conectarBD(); PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Produto pd = new Produto();
				pd.setIdProduto(rs.getInt("idProduto"));
				pd.setNomeProduto(rs.getString("nomeProduto"));
				pd.setUnidade(rs.getString("unidade"));
				pd.setLocalizacao(rs.getString("localizacao"));
				pd.setQuantidade(rs.getInt("quantidade"));
				pd.setCustoUnitario(rs.getDouble("custoUnitario"));

				Timestamp entrada = rs.getTimestamp("dataEntrada");
				Timestamp saida = rs.getTimestamp("dataSaida");
				pd.setDataEntrada(entrada != null ? entrada.toLocalDateTime() : null);
				pd.setDataSaida(saida != null ? saida.toLocalDateTime() : null);

				produtos.add(pd);
			}
		} catch (SQLException e) {
			alertaErro("Erro ao listar produtos no banco de dados!");
			e.printStackTrace();
		}
		return produtos;
	}

	/**
	 * Atualiza os dados de um produto existente no banco.
	 * 
	 * @param pdt Produto contendo os novos dados
	 * @return true se a atualização for bem-sucedida
	 */
	public boolean atualizar(Produto pdt) {
		String sql = "UPDATE produtos SET nomeProduto = ?, unidade = ?, localizacao = ?, quantidade = ?, custoUnitario = ? WHERE idProduto = ?";
		try (Connection conexao = new BD().conectarBD(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setString(1, pdt.getNomeProduto());
			stmt.setString(2, pdt.getUnidade());
			stmt.setString(3, pdt.getLocalizacao());
			stmt.setInt(4, pdt.getQuantidade());
			stmt.setDouble(5, pdt.getCustoUnitario());
			stmt.setInt(6, pdt.getIdProduto());

			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			alertaErro("Erro ao atualizar o produto no banco de dados: ");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Busca um produto pelo seu identificador único.
	 * 
	 * @param idProduto ID do produto
	 * @return Produto encontrado ou null se não existir
	 */
	public Produto buscarPorId(int idProduto) {
		String sql = "SELECT * FROM produtos WHERE idProduto = ?";
		Produto produto = null;

		try (Connection conexao = new BD().conectarBD(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1, idProduto);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				produto = new Produto();
				produto.setIdProduto(rs.getInt("idProduto"));
				produto.setNomeProduto(rs.getString("nomeProduto"));
				produto.setLocalizacao(rs.getString("localizacao"));
				produto.setUnidade(rs.getString("unidade"));
				produto.setQuantidade(rs.getInt("quantidade"));
				produto.setCustoUnitario(rs.getDouble("custoUnitario"));

				Timestamp entrada = rs.getTimestamp("dataEntrada");
				Timestamp saida = rs.getTimestamp("dataSaida");
				produto.setDataEntrada(entrada != null ? entrada.toLocalDateTime() : null);
				produto.setDataSaida(saida != null ? saida.toLocalDateTime() : null);
			}
		} catch (SQLException e) {
			alertaErro("Erro ao buscar ID no banco de dados: ");
			e.printStackTrace();
		}
		return produto;
	}

	/**
	 * Registra a saída de uma quantidade específica de um produto.
	 * Atualiza o estoque e registra a data de saída.
	 * 
	 * @param idProduto ID do produto
	 * @param quantidade Quantidade a ser removida
	 * @return true se a operação ocorrer com sucesso
	 */
	public boolean registrarSaida(int idProduto, int quantidade) {
		String sqlUpdate = "UPDATE produtos SET quantidade = quantidade - ?, dataSaida = NOW() WHERE idProduto = ?";

		try (Connection conexao = new BD().conectarBD(); PreparedStatement stmtUpdate = conexao.prepareStatement(sqlUpdate)) {

			stmtUpdate.setInt(1, quantidade);
			stmtUpdate.setInt(2, idProduto);
			int rows = stmtUpdate.executeUpdate();

			return rows > 0;

		} catch (SQLException e) {
			alertaErro("Erro ao registrar saída no banco de dados!");
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Remove definitivamente um produto do banco de dados.
	 * 
	 * @param idProduto ID do produto a ser removido
	 * @return true se o produto for excluído com sucesso
	 */
	public boolean remover(int idProduto) {
		String sql = "DELETE FROM produtos WHERE idProduto = ?";
		try (Connection conexao = new BD().conectarBD(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1, idProduto);
			return stmt.executeUpdate() > 0;
		} catch (SQLException e) {
			alertaErro("Erro ao remover produto do banco de dados!");
			e.printStackTrace();
			return false;
		}
	}

	private void alertaErro(String msg) {
		JOptionPane.showMessageDialog(null, "<html><b><font color='red'>Erro:</font></b> " + msg + "</html>", "Erro", JOptionPane.ERROR_MESSAGE);
	}

}
