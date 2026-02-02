package br.com.pavimor.gestaoEstoqueSimples.view.telaVisualizar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import br.com.pavimor.gestaoEstoqueSimples.dao.DaoProduto;
import br.com.pavimor.gestaoEstoqueSimples.model.Produto;

/**
 * Representa a interface gráfica responsável pela visualização
 * dos produtos cadastrados no sistema de gestão de estoque.
 *
 * Permite:
 * - Visualizar todos os produtos
 * - Filtrar produtos com estoque zerado ou baixo
 * - Ordenar os dados exibidos na tabela
 *
 * Camada: View
 *
 * @author Lazaro Nogueira
 * @version 1.0
 * @since 2026-02-01
 */

public class TelaVisualizarProdutos {

	 /**
     * Constrói e inicializa a tela de visualização de produtos.
     *
     * Responsabilidades:
     * - Montar a interface gráfica
     * - Configurar tabela e renderização
     * - Definir filtros e ações dos botões
     */
	
	public TelaVisualizarProdutos() {
		JFrame frame = new JFrame("Gestão de Estoque Pavimor - Visualizar Produtos");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(1250, 600);
		frame.setResizable(true);
		frame.setLayout(new BorderLayout());

		JPanel pNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel dica = new JLabel("Lista de Produtos Cadastrados");
		dica.setFont(new Font("Arial", Font.BOLD, 14));
		pNorte.add(dica);

		String[] colunas = {"<html><b><font color='red'>ID</font></b></html>", "Nome", "Unidade", "Localização", "Quantidade", "<html>Custo Unitário <b>(R$)</b></html>", "Data Entrada", "Data Saída"};

		DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JTable tabela = new JTable(modeloTabela);
		JScrollPane scroll = new JScrollPane(tabela);
		preencherTabela(modeloTabela, null);

		DefaultTableCellRenderer centralizar = new DefaultTableCellRenderer();
		centralizar.setHorizontalAlignment(JLabel.CENTER);
		int[] colunasCentralizadas = {0, 3, 4, 5};
		for (int i : colunasCentralizadas) {
			tabela.getColumnModel().getColumn(i).setCellRenderer(centralizar);
		}

		TableRowSorter<DefaultTableModel> produtosOrdenados = new TableRowSorter<>(modeloTabela);
		tabela.setRowSorter(produtosOrdenados);

		JPanel pSul = new JPanel(new BorderLayout());
		JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

		JButton btnFiltroZerado = new JButton("Estoque Zerado");
		btnFiltroZerado.setFont(new Font("Arial", Font.BOLD, 14));
		btnFiltroZerado.setBackground(Color.RED);
		btnFiltroZerado.setToolTipText("Clique aqui para visualizar itens com estoque zerado!");

		JButton btnFiltroBaixo = new JButton("Estoque Baixo");
		btnFiltroBaixo.setFont(new Font("Arial", Font.BOLD, 14));
		btnFiltroBaixo.setBackground(Color.ORANGE);
		btnFiltroBaixo.setToolTipText("Clique aqui para visualizar itens com estoque baixo!");

		JButton btnFiltroTodos = new JButton("Todos");
		btnFiltroTodos.setFont(new Font("Arial", Font.BOLD, 14));
		btnFiltroTodos.setBackground(Color.GREEN);
		btnFiltroTodos.setToolTipText("Clique aqui para visualizar todos os itens!");

		botoesPanel.add(btnFiltroZerado);
		botoesPanel.add(btnFiltroBaixo);
		botoesPanel.add(btnFiltroTodos);

		JPanel copyrightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		copyrightPanel.add(criarCopyright());

		pSul.add(botoesPanel, BorderLayout.NORTH);
		pSul.add(copyrightPanel, BorderLayout.SOUTH);

		btnFiltroZerado.addActionListener(e -> preencherTabela(modeloTabela, "zerado"));
		btnFiltroBaixo.addActionListener(e -> preencherTabela(modeloTabela, "baixo"));
		btnFiltroTodos.addActionListener(e -> preencherTabela(modeloTabela, null));

		frame.add(pNorte, BorderLayout.NORTH);
		frame.add(scroll, BorderLayout.CENTER);
		frame.add(pSul, BorderLayout.SOUTH);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
     * Preenche a tabela de produtos conforme o filtro informado.
     *
     * O método:
     * - Consulta os produtos via DAO
     * - Aplica filtros de estoque (zerado ou baixo)
     * - Formata dados exibidos na tabela
     * - Atualiza dinamicamente o modelo da JTable
     *
     * @param model modelo da tabela a ser preenchido
     * @param tipoFiltro tipo de filtro aplicado:
     *                   "zerado", "baixo" ou null (todos)
     */
	
	private void preencherTabela(DefaultTableModel model, String tipoFiltro) {
		DaoProduto dao = new DaoProduto();
		DateTimeFormatter dataFormatada = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		model.setRowCount(0);

		try {
			List<Produto> produtos = dao.listarTodos();
			if (produtos.isEmpty()) {
				alerta("Nenhum produto cadastrado!");
				return;
			}

			for (Produto p1 : produtos) {
				int quantidade = p1.getQuantidade();

				int limiteEstoqueBaixo;
				if (p1.getQuantidadeMinima() != null) {
					limiteEstoqueBaixo = p1.getQuantidadeMinima();
				} else {
					limiteEstoqueBaixo = 10;
				}

				if (tipoFiltro != null) {
					if (tipoFiltro.equals("zerado") && quantidade != 0) {
						continue;
					}
					if (tipoFiltro.equals("baixo") && (quantidade == 0 || quantidade > limiteEstoqueBaixo)) {
						continue;
					}
				}

				model.addRow(new Object[]{p1.getIdProduto(), p1.getNomeProduto(), p1.getUnidade(), p1.getLocalizacao(), p1.getQuantidade(), String.format("%.2f", p1.getCustoUnitario()),
						p1.getDataEntrada().format(dataFormatada), getStatusEstoque(p1, dataFormatada)});
			}

			if (model.getRowCount() == 0 && tipoFiltro != null) {
				alerta("Nenhum produto encontrado para o filtro selecionado!");
			}
		} catch (Exception e) {
			alertaErro("Erro ao carregar produtos: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
     * Retorna a data de saída do produto formatada e colorida
     * conforme a situação do estoque.
     *
     * Regras:
     * - Estoque zerado: vermelho
     * - Estoque baixo: laranja
     * - Estoque normal: verde
     *
     * Caso não exista data de saída, retorna mensagem padrão.
     *
     * @param produto produto avaliado
     * @param formatter formatador de data utilizado
     * @return String HTML com data e cor correspondente ao status do estoque
     */
	
	private String getStatusEstoque(Produto produto, DateTimeFormatter formatter) {
		int quantidade = produto.getQuantidade();
		int estoqueBaixo = produto.getQuantidadeMinima() != null ? produto.getQuantidadeMinima() : 10;

		String cor = null;
		if (quantidade == 0) {
			cor = "red";
		} else if (quantidade <= estoqueBaixo) {
			cor = "orange";
		} else {
			cor = "green";
		}

		String txtData = null;
		if (produto.getDataSaida() != null) {
			txtData = produto.getDataSaida().format(formatter);
		} else {
			txtData = "SEM SAÍDA DO ESTOQUE";
		}
		return String.format("<html><font color = '%s'>%s</font></html>", cor, txtData);

	}

	 /**
     * Exibe uma mensagem de aviso ao usuário.
     *
     * @param msg mensagem a ser exibida
     */
	
	private void alerta(String msg) {
		JOptionPane.showMessageDialog(null, "<html>⚠️ <b><font color='orange'>Aviso:</font></b> " + msg + "</html>", "Aviso", JOptionPane.WARNING_MESSAGE);
	}

	 /**
     * Exibe uma mensagem de erro ao usuário.
     *
     * @param msg mensagem de erro
     */
	private void alertaErro(String msg) {
		JOptionPane.showMessageDialog(null, "<html>❌ <b><font color='red'>Erro:</font></b> " + msg + "</html>", "Erro", JOptionPane.ERROR_MESSAGE);
	}

	/**
     * Cria e retorna o rodapé padrão da aplicação.
     *
     * @return JLabel contendo informações de copyright
     */
	public static final JLabel criarCopyright() {
		String texto = "<html><div style='text-align:center; color:#808080; font-family: Arial, sans-serif;'>© 2025 Lazaro Coder <span style='color:#C0C0C0;'>v1.0</span></div></html>";
		return new JLabel(texto);
	}
}
