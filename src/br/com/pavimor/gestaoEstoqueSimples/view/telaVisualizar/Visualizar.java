package br.com.pavimor.gestaoEstoqueSimples.view.telaVisualizar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import br.com.pavimor.gestaoEstoqueSimples.dao.DaoProdutos;
import br.com.pavimor.gestaoEstoqueSimples.model.Produto;
import br.com.pavimor.gestaoEstoqueSimples.view.telaPrincipal.Tela;

public class Visualizar {

	public void visualizarProdutos() {
		JFrame frame = new JFrame("Gestão de Estoque Pavimor - Visualizar Produtos");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(900, 400);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());

		JPanel pNorte = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		JLabel dica = new JLabel("<html>Visualize abaixo os dados contidos no <b>banco de dados</b>!</html>");
		dica.setFont(new Font("Arial", Font.PLAIN, 14));
		dica.setPreferredSize(new Dimension(600, 25));
		pNorte.add(dica);

		String[] colunas = {"ID", "Nome do Produto", "Unidade de Medida", "Quantidade", "Custo Unitário", "Data de Entrada", "Data de Saída"};

		DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0);
		JTable tabelaProdutos = new JTable(modeloTabela);
		JScrollPane scrollProdutos = new JScrollPane(tabelaProdutos);

		TableRowSorter<DefaultTableModel> produtosSorter = new TableRowSorter<>(modeloTabela);
		tabelaProdutos.setRowSorter(produtosSorter);

		carregarProdutos(modeloTabela);

		JPanel pSul = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pSul.add(Tela.criarCopyright());

		frame.add(pNorte, BorderLayout.NORTH);
		frame.add(scrollProdutos, BorderLayout.CENTER);
		frame.add(pSul, BorderLayout.SOUTH);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	public void carregarProdutos(DefaultTableModel modeloTabela) {
		DaoProdutos dao = new DaoProdutos();
		List<Produto> produtos = dao.listarTodos();

		modeloTabela.setRowCount(0);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

		for (Produto p : produtos) {
			Object[] linha = {p.getIdProduto(), p.getNomeProduto(), p.getUnidade(), p.getQuantidade(), String.format("R$ %.2f", p.getCustoUnitario()),
					p.getDataEntrada() != null ? p.getDataEntrada().format(formatter) : "", p.getDataSaida() != null ? p.getDataSaida().format(formatter) : ""};
			modeloTabela.addRow(linha);
		}
	}
}
