package br.com.pavimor.gestaoEstoqueSimples.view.telaInserir;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
		JLabel dica = new JLabel("<html>Bem-vindo(a) ao gerenciador de estoque <b>Pavimor</b>!</html>");
		dica.setFont(new Font("Arial", Font.PLAIN, 14));
		dica.setPreferredSize(new Dimension(350, 25));
		pNorte.add(dica);

		JPanel pCentro = new JPanel();
		pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));

		campoNome = new JTextField();
		comboUnidade = new JComboBox<>(new String[]{"Unidade", "Metro Quadrado (m²)", "Saco (sc)", "Metro Cúbico (m³)", "Litro (L)"});
		campoQuantidade = new JTextField();
		campoCustoUnitario = new JTextField();

		JPanel campos = new JPanel();
		campos.setLayout(new BoxLayout(campos, BoxLayout.Y_AXIS));
		campos.add(criarLinha("Nome do produto:", campoNome));
		campos.add(Box.createVerticalStrut(5));
		campos.add(criarLinha("Unidade de medida:", comboUnidade));
		campos.add(Box.createVerticalStrut(5));
		campos.add(criarLinha("Quantidade:", campoQuantidade));
		campos.add(Box.createVerticalStrut(5));
		campos.add(criarLinha("Custo unitário em R$:", campoCustoUnitario));

		JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
		JButton enviar = new JButton("Enviar");
		enviar.setFont(new Font("Arial", Font.BOLD, 14));
		enviar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// bd

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
		frame.add(pSul, BorderLayout.SOUTH);

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
