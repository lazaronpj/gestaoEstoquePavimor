package br.com.pavimor.gestaoEstoqueSimples.view.telaInserir;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.pavimor.gestaoEstoqueSimples.dao.DaoProdutos;
import br.com.pavimor.gestaoEstoqueSimples.model.Produto;
import br.com.pavimor.gestaoEstoqueSimples.view.telaPrincipal.Tela;

/**
 * Essa classe é responsável pela inserção de dados de produtos
 * 
 * @author Lazaro Nogueira
 * @version 1.0
 * @since 2025-09-16
 */

public class Inserir {
	private JTextField campoNome;
	private JComboBox<String> comboUnidade;
	private JTextField campoQuantidade;
	private JTextField campoCustoUnitario;

	/**
	 * Método responsável por criar uma tela para poder inserir produtos em Java
	 * Swing
	 * 
	 */
	public void inserirProdutos() {
		JFrame frame = new JFrame("Gestão de Estoque Pavimor - Inserir Produtos");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(600, 350);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());

		JPanel pNorte = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		JLabel dica = new JLabel("<html>Digite os dados do produto que você deseja <b>inserir</b>!</html>");
		dica.setFont(new Font("Arial", Font.PLAIN, 14));
		dica.setPreferredSize(new Dimension(350, 25));
		pNorte.add(dica);

		JPanel pCentro = new JPanel();
		pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));

		campoNome = new JTextField();
		comboUnidade = new JComboBox<>(new String[]{"Selecione uma opção", "Unidade", "Metro Quadrado (m²)", "Saco (sc)", "Metro Cúbico (m³)", "Litro (L)"});
		campoQuantidade = new JTextField();
		campoCustoUnitario = new JTextField();

		JPanel campos = new JPanel();
		campos.setLayout(new BoxLayout(campos, BoxLayout.Y_AXIS));
		campos.add(criarLinha("Nome do produto:", campoNome));
		campos.add(Box.createVerticalStrut(5));
		campos.add(criarLinha("Unidade de medida:", comboUnidade));
		campos.add(Box.createVerticalStrut(5));
		campos.add(criarLinha("Quantidade:", campoQuantidade));

		campoQuantidade.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				String txt = campoQuantidade.getText().trim();

				txt = txt.replaceAll("[^0-9]", "");
				campoQuantidade.setText(txt);
			}
		});

		campos.add(Box.createVerticalStrut(5));
		campos.add(criarLinha("<html>Custo unitário em <b>R$</b>:</html>:", campoCustoUnitario));

		campoCustoUnitario.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				String numeros = campoCustoUnitario.getText().replaceAll("\\D", "");

				if (numeros.isEmpty()) {
					campoCustoUnitario.setText("0.00");
					return;
				}

				try {
					double valor = Double.parseDouble(numeros) / 100;
					String valorFormatado = String.format(Locale.US, "%.2f", valor);
					campoCustoUnitario.setText(valorFormatado);

				} catch (NumberFormatException en) {
					JOptionPane.showMessageDialog(frame, "O campo Custo Unitário devem conter apenas números válidos!", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
		JButton enviar = new JButton("Enviar");
		enviar.setFont(new Font("Arial", Font.BOLD, 14));
		enviar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Produto p = new Produto();
					p.setNomeProduto(campoNome.getText());
					p.setUnidade((String) comboUnidade.getSelectedItem());
					p.setQuantidade(Integer.parseInt(campoQuantidade.getText()));
					p.setCustoUnitario(Double.parseDouble(campoCustoUnitario.getText()));

					p.setDataEntrada(LocalDateTime.now());
					p.setDataSaida(null);

					DaoProdutos dao = new DaoProdutos();
					if (dao.inserir(p)) {
						JOptionPane.showMessageDialog(null, "✅ Produto cadastrado com sucesso!");
					} else {
						JOptionPane.showMessageDialog(null, "⚠ Erro ao cadastrar produto.");
					}
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Quantidade ou Custo inválidos. Digite números válidos.");
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro inesperado: " + ex.getMessage());
					ex.printStackTrace();
				}
			}
		});

		JButton limparCampos = new JButton("Limpar Campos");
		limparCampos.setFont(new Font("Arial", Font.BOLD, 14));
		limparCampos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				campoNome.setText("");
				comboUnidade.setSelectedIndex(0);
				campoQuantidade.setText("");
				campoCustoUnitario.setText("");
			}
		});

		botoes.add(enviar);
		botoes.add(limparCampos);

		pCentro.add(campos);
		pCentro.add(Box.createVerticalStrut(15));
		pCentro.add(botoes);

		JPanel pSul = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		pSul.add(Tela.criarCopyright());

		frame.add(pNorte, BorderLayout.NORTH);
		frame.add(pCentro, BorderLayout.CENTER);
		frame.add(pSul, BorderLayout.PAGE_END);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private JPanel criarLinha(String texto, javax.swing.JComponent campo) {
		JPanel linha = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
		JLabel label = new JLabel(texto);
		label.setFont(new Font("Arial", Font.PLAIN, 14));
		label.setPreferredSize(new Dimension(180, 25));
		campo.setFont(new Font("Arial", Font.PLAIN, 14));
		campo.setPreferredSize(new Dimension(300, 25));
		linha.add(label);
		linha.add(campo);
		return linha;
	}
}
