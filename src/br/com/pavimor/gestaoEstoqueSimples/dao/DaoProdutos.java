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

public class DaoProdutos {

	public boolean inserir(Produto pdt) {
		String sql = "INSERT INTO produtos (nomeProduto, unidade, quantidade, custoUnitario, dataEntrada, dataSaida) VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection conexao = new BD().conectarBD(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setString(1, pdt.getNomeProduto());
			stmt.setString(2, pdt.getUnidade());
			stmt.setInt(3, pdt.getQuantidade());
			stmt.setDouble(4, pdt.getCustoUnitario());

			stmt.setTimestamp(5, pdt.getDataEntrada() != null ? Timestamp.valueOf(pdt.getDataEntrada()) : null);
			stmt.setTimestamp(6, pdt.getDataSaida() != null ? Timestamp.valueOf(pdt.getDataSaida()) : null);

			return stmt.executeUpdate() > 0;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public List<Produto> listarTodos() {
		List<Produto> produtos = new ArrayList<>();
		String sql = "SELECT * FROM produtos";

		try (Connection conexao = new BD().conectarBD(); PreparedStatement stmt = conexao.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				Produto pd = new Produto();
				pd.setIdProduto(rs.getInt("idProduto"));
				pd.setNomeProduto(rs.getString("nomeProduto"));
				pd.setUnidade(rs.getString("unidade"));
				pd.setQuantidade(rs.getInt("quantidade"));
				pd.setCustoUnitario(rs.getDouble("custoUnitario"));

				Timestamp entrada = rs.getTimestamp("dataEntrada");
				Timestamp saida = rs.getTimestamp("dataSaida");
				pd.setDataEntrada(entrada != null ? entrada.toLocalDateTime() : null);
				pd.setDataSaida(saida != null ? saida.toLocalDateTime() : null);

				produtos.add(pd);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return produtos;
	}

	public boolean atualizar(Produto pdt) {
		String sql = "UPDATE produtos SET nomeProduto = ?, unidade = ?, quantidade = ?, custoUnitario = ?, dataEntrada = ?, dataSaida = ? WHERE idProduto = ?";

		try (Connection conexao = new BD().conectarBD(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setString(1, pdt.getNomeProduto());
			stmt.setString(2, pdt.getUnidade());
			stmt.setInt(3, pdt.getQuantidade());
			stmt.setDouble(4, pdt.getCustoUnitario());
			stmt.setTimestamp(5, pdt.getDataEntrada() != null ? Timestamp.valueOf(pdt.getDataEntrada()) : null);
			stmt.setTimestamp(6, pdt.getDataSaida() != null ? Timestamp.valueOf(pdt.getDataSaida()) : null);
			stmt.setInt(7, pdt.getIdProduto());

			return stmt.executeUpdate() > 0;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}
	public Produto buscarPorId(int idProduto) {
		String sql = "SELECT * FROM produtos WHERE idProduto = ?";
		Produto produto = null;

		try (Connection conexao = new BD().conectarBD(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1, idProduto);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				produto = new Produto();
				produto.setIdProduto(rs.getInt("idProduto"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return produto;
	}

	public void registrarSaida(int idProduto) {
		String sql = "UPDATE produtos SET dataSaida = NOW() WHERE idProduto = ?";
		try (Connection conexao = new BD().conectarBD(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1, idProduto);
			int rows = stmt.executeUpdate();

			if (rows > 0) {
				JOptionPane.showMessageDialog(null, "Data de saída registrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Produto não encontrado", "Erro", JOptionPane.ERROR_MESSAGE);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean remover(int idProduto) {
		String sql = "DELETE FROM produtos WHERE idProduto = ?";

		try (Connection conexao = new BD().conectarBD(); PreparedStatement stmt = conexao.prepareStatement(sql)) {

			stmt.setInt(1, idProduto);
			return stmt.executeUpdate() > 0;
		} catch (SQLException ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
