package br.com.pavimor.gestaoEstoqueSimples.view.telaAlterar;

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
				try {
					int id = Integer.parseInt(campoId.getText().trim());

					DaoProdutos dao = new DaoProdutos();
					Produto produto = dao.buscarPorId(id);
					if (produto != null) {
						mostrarFormularioAlteracao(produto);
						frame.dispose();
					} else {
						JOptionPane.showMessageDialog(frame, "⚠ Produto não encontrado!", "Aviso", JOptionPane.WARNING_MESSAGE);
					}

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(frame, "Digite um ID válido (somente números).", "Erro", JOptionPane.ERROR_MESSAGE);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(frame, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
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
		JFrame frameAlt = new JFrame("Alterando Dados de Produtos");
		frameAlt.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frameAlt.setSize(600, 350);
		frameAlt.setResizable(false);
		frameAlt.setLayout(new BorderLayout());

		JPanel pNorte = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		JLabel dica1 = new JLabel("<html>Digite corretamente os <b>dados</b> do produto que você deseja <b>alterar</b>!</html>");
		dica1.setFont(new Font("Arial", Font.PLAIN, 14));
		dica1.setPreferredSize(new Dimension(450, 25));
		pNorte.add(dica1);

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
		campos.add(criarLinha("<html>Custo unitário em <b>R$</b>:</html>", campoCustoUnitario));

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
					JOptionPane.showMessageDialog(null, "O campo Custo Unitário devem conter apenas números válidos!", "Erro", JOptionPane.ERROR_MESSAGE);
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
					p.setIdProduto(Integer.parseInt(campoId.getText()));
					p.setNomeProduto(campoNome.getText().trim());
					p.setUnidade((String) comboUnidade.getSelectedItem());
					p.setQuantidade(Integer.parseInt(campoQuantidade.getText().trim()));
					p.setCustoUnitario(Double.parseDouble(campoCustoUnitario.getText().trim()));

					p.setDataEntrada(LocalDateTime.now());
					p.setDataSaida(null);

					int confirmacao = JOptionPane.showConfirmDialog(null, "Deseja realmente alterar esse produto?", "Confirmação", JOptionPane.YES_NO_OPTION);

					if (confirmacao == JOptionPane.YES_OPTION) {
						DaoProdutos dao = new DaoProdutos();
						if (dao.atualizar(p)) {
							JOptionPane.showMessageDialog(null, "✅ Produto atualizado com sucesso!");
						} else {
							JOptionPane.showMessageDialog(null, "⚠ Nenhum produto encontrado para atualizar.");
						}
					}

				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Os campos Quantidade e Custo Unitário devem ser numéricos válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Erro inesperado: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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

		frameAlt.add(pNorte, BorderLayout.PAGE_START);
		frameAlt.add(pCentro, BorderLayout.CENTER);
		frameAlt.add(pSul, BorderLayout.PAGE_END);

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
