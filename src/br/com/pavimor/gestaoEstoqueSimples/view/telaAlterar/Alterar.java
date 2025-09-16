package br.com.pavimor.gestaoEstoqueSimples.view.telaAlterar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.pavimor.gestaoEstoqueSimples.model.Produto;
import br.com.pavimor.gestaoEstoqueSimples.view.telaPrincipal.Tela;

public class Alterar {

	private JTextField campoId;
	private JTextField campoNome;
	private JComboBox<String> comboUnidade;
	private JTextField campoQuantidade;
	private JTextField campoCustoUnitario;

	public void alterarProdutos() {

		JFrame frame = new JFrame("Gestão de Estoque Pavimor - Alterar Produtos");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(450, 220);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());

		JPanel pNorte = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		JLabel dica = new JLabel("<html>Digite o <b>id</b> do produto que você deseja <b>alterar</b>!</html>");
		dica.setFont(new Font("Arial", Font.PLAIN, 14));
		dica.setPreferredSize(new Dimension(350, 25));

		pNorte.add(dica);

		JPanel pCentro = new JPanel();
		pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));

		JPanel campos = new JPanel();
		campos.setLayout(new BoxLayout(campos, BoxLayout.Y_AXIS));
		campoId = new JTextField();
		campos.add(criarLinhaUnica("Digite o ID do produto para alterar: ", campoId));

		campoId.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				String txt = campoId.getText().trim();

				txt = txt.replaceAll("[^0-9]", "");
				campoId.setText(txt);
			}
		});

		JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
		JButton enviar = new JButton("Enviar");
		enviar.setFont(new Font("Arial", Font.BOLD, 14));
		enviar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// bd
				//
				// mostrarFormularioAlteracao();

			}
		});

		JButton limparCampos = new JButton("Limpar Campos");
		limparCampos.setFont(new Font("Arial", Font.BOLD, 14));
		limparCampos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				campoId.setText("");
			}
		});

		botoes.add(enviar);
		botoes.add(limparCampos);

		pCentro.add(campos);
		pCentro.add(Box.createVerticalStrut(15));
		pCentro.add(botoes);

		JPanel pSul = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pSul.add(Tela.criarCopyright());

		frame.add(pNorte, BorderLayout.NORTH);
		frame.add(pCentro, BorderLayout.CENTER);
		frame.add(pSul, BorderLayout.SOUTH);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	private JPanel criarLinhaUnica(String texto, javax.swing.JComponent campo) {
		JPanel linha = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
		JLabel label = new JLabel(texto);
		label.setFont(new Font("Arial", Font.PLAIN, 14));
		label.setPreferredSize(new Dimension(250, 25));
		campo.setFont(new Font("Arial", Font.PLAIN, 14));
		campo.setPreferredSize(new Dimension(80, 25));
		linha.add(label);
		linha.add(campo);
		return linha;
	}

	public void mostrarFormularioAlteracao(Produto pdt) {
		JFrame frameAlt = new JFrame("");
		frameAlt.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameAlt.setSize(600, 350);
		frameAlt.setResizable(false);
		frameAlt.setLayout(new BorderLayout());

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

		campoQuantidade.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				String txt = campoQuantidade.getText().trim();

				txt = txt.replaceAll("[^0-9]", "");
				campoQuantidade.setText(txt);
			}
		});

		campos.add(Box.createVerticalStrut(5));
		campos.add(criarLinha("Custo unitário em R$:", campoCustoUnitario));

		campoCustoUnitario.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				String txt = campoCustoUnitario.getText().trim();

				txt = txt.replaceAll("[^0-9.]", "");
				campoCustoUnitario.setText(txt);
			}
		});

		JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
		JButton enviar = new JButton("Enviar");
		enviar.setFont(new Font("Arial", Font.BOLD, 14));
		enviar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String nome = campoNome.getText().trim();
					String unidade = (String) comboUnidade.getSelectedItem();
					int qtd = Integer.parseInt(campoQuantidade.getText().trim());
					double custoUnitario = Double.parseDouble(campoCustoUnitario.getText().trim());

					int confirmacao = JOptionPane.showConfirmDialog(frameAlt, "Deseja realmente alterar esse item?", "Confirmação", JOptionPane.YES_NO_OPTION);

					if (confirmacao == JOptionPane.YES_OPTION) {
						// bd
					} else {
						frameAlt.dispose();
					}

				} catch (NumberFormatException ex) {

					JOptionPane.showMessageDialog(frameAlt, "Os campos 'Unidade, Quantidade, Custo Unitário' devem possuir um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
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

		frameAlt.add(pCentro, BorderLayout.CENTER);
		frameAlt.add(pSul, BorderLayout.SOUTH);

		frameAlt.setLocationRelativeTo(null);
		frameAlt.setVisible(true);

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
