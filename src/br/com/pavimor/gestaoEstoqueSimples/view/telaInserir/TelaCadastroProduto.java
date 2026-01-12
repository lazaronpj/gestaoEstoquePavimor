package br.com.pavimor.gestaoEstoqueSimples.view.telaInserir;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.pavimor.gestaoEstoqueSimples.dao.DaoProduto;
import br.com.pavimor.gestaoEstoqueSimples.model.Produto;
import br.com.pavimor.gestaoEstoqueSimples.util.ValidadorCampoCusto;

/**
 * Essa classe é responsável pela inserção de produtos no banco de dados. Permite inserir nome, quantidade, preço e selecionar unidade de
 * medida.
 * 
 * @author Lazaro Nogueira
 * @version 1.0
 * @since 2025-09-17
 */

public class TelaCadastroProduto {

	/**
	 * Campos para inserção de nome, quantidade, unidade, custo unitário do produto
	 * 
	 **/

	private JTextField campoNome, campoQuantidade, campoCustoUnitario;
	private JComboBox<String> comboUnidade, comboLocalizacao;

	/**
	 * Método responsável por criar uma tela em Java Swing para poder inserir produtos via Java Swing.
	 */

	public TelaCadastroProduto() {
		JFrame frame = new JFrame("Gestão de Estoque Pavimor - Cadastro de Produtos");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(500, 380);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());

		JPanel pNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel dica = new JLabel("Preencha os dados do produto para inserir:");
		pNorte.add(dica);

		JPanel pCentro = new JPanel();
		pCentro.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

		campoNome = new JTextField();
		campoNome.setToolTipText("Digite o nome do produto!");
		campoQuantidade = new JTextField();
		campoQuantidade.setToolTipText("Digite a quantidade do produto!");
		campoCustoUnitario = new JTextField();
		campoCustoUnitario.setToolTipText("Digite o custo unitário do produto em (R$)!");
		comboUnidade = new JComboBox<>(new String[]{"Selecione uma Unidade de Medida", "Unidade", "Minheiro", "Caixa", "Kg", "Litro",
				"Metro²", "Saco", "m³", "Litro"});
		comboUnidade.setToolTipText("Selecione o tipo de unidade do produto a ser inserido.");
		comboLocalizacao = new JComboBox<>(new String[]{"Selecione uma Localização", "Galpão de materiais", "Galpão de embalagens",
				"Almoxarifado", "Oficina", "Pátio de pavimentação", "Pátio de vedação", "Pátio de estruturas"});
		comboLocalizacao.setToolTipText("Selecione a localização do produto!");

		final Color corFoco = new Color(245, 255, 245);
		final Color corOriginal = new Color(255, 255, 255);

		campoNome.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				campoNome.setBackground(corFoco);
			}

			@Override
			public void focusLost(FocusEvent e) {
				campoNome.setBackground(corOriginal);
			}
		});

		campoQuantidade.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				campoQuantidade.setBackground(corFoco);
			}

			@Override
			public void focusLost(FocusEvent e) {
				campoQuantidade.setBackground(corOriginal);
			}
		});

		campoCustoUnitario.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				campoCustoUnitario.setBackground(corFoco);
			}

			@Override
			public void focusLost(FocusEvent e) {
				campoCustoUnitario.setBackground(corOriginal);
			}
		});

		comboUnidade.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				comboUnidade.setBackground(corFoco);
			}

			@Override
			public void focusLost(FocusEvent e) {
				comboUnidade.setBackground(corOriginal);
			}
		});

		comboLocalizacao.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				comboLocalizacao.setBackground(corFoco);
			}

			@Override
			public void focusLost(FocusEvent e) {
				comboLocalizacao.setBackground(corOriginal);
			}
		});

		campoNome.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				String txt = campoNome.getText();
				if (txt.length() > 30) {
					JOptionPane.showMessageDialog(pCentro, "O campo 'nome' não pode exceder 30 caracteres!", "Aviso",
							JOptionPane.WARNING_MESSAGE);
					campoNome.setText("");
				} else {
					campoNome.setText(txt);
				}
			}
		});

		campoQuantidade.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				String txt = campoQuantidade.getText().trim().replaceAll("[^0-9]", "");

				if (txt.length() > 20) {
					JOptionPane.showMessageDialog(pCentro, "O campo 'quantidade' não pode exceder 20 caracteres!", "Aviso",
							JOptionPane.WARNING_MESSAGE);
					campoQuantidade.setText("");
				} else {
					campoQuantidade.setText(txt);
				}
			}
		});

		campoCustoUnitario.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				try {
					String txt = campoCustoUnitario.getText().trim().replaceAll("[^0-9]", "");
					if (txt.length() > 20) {
						JOptionPane.showMessageDialog(pCentro, "O campo 'custo unitário' não pode exceder 20 caracteres!", "Aviso",
								JOptionPane.WARNING_MESSAGE);
						campoCustoUnitario.setText("0.00");
					} else {
						campoCustoUnitario.setText(ValidadorCampoCusto.formatarMoeda(campoCustoUnitario.getText()));
					}
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Custo Unitário inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
					campoCustoUnitario.setText("0.00");
				}
			}
		});

		pCentro.add(criarLinha("Nome do produto:", campoNome));
		pCentro.add(criarLinha("Unidade de medida:", comboUnidade));
		pCentro.add(criarLinha("Localização", comboLocalizacao));
		pCentro.add(criarLinha("Quantidade:", campoQuantidade));
		pCentro.add(criarLinha("<html>Custo unitário <b>(R$)</b>:</html>", campoCustoUnitario));

		JPanel pSul = new JPanel();
		pSul.setLayout(new BorderLayout());

		JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		JButton btnInserir = new JButton("Inserir");
		btnInserir.setBackground(Color.GREEN);
		btnInserir.setToolTipText("Clique aqui para inserir produto no banco de dados!");
		btnInserir.setFont(new Font("Arial", Font.BOLD, 14));
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setBackground(Color.LIGHT_GRAY);
		btnLimpar.setToolTipText("Clique aqui para limpar campos!");
		btnLimpar.setFont(new Font("Arial", Font.BOLD, 14));
		btnInserir.addActionListener(e -> salvarProduto());
		btnLimpar.addActionListener(e -> limparCampos());
		botoesPanel.add(btnInserir);
		botoesPanel.add(btnLimpar);

		JPanel copyrightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		copyrightPanel.add(criarCopyright());

		pSul.add(botoesPanel, BorderLayout.NORTH);
		pSul.add(copyrightPanel, BorderLayout.SOUTH);

		frame.add(pNorte, BorderLayout.NORTH);
		frame.add(pCentro, BorderLayout.CENTER);
		frame.add(pSul, BorderLayout.SOUTH);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * Esse método cria um painel com rótulo e campo de texto já padronizado
	 * 
	 * @param texto
	 *            Cria um rótulo do tipo JLabel
	 * @param campo
	 *            Cria um componente visual, nesse caso um JTextField
	 * @return
	 */
	private JPanel criarLinha(String texto, JComponent campo) {
		JPanel linha = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		JLabel label = new JLabel(texto);
		label.setFont(new Font("Arial", Font.PLAIN, 14));
		label.setPreferredSize(new Dimension(150, 25));
		campo.setPreferredSize(new Dimension(250, 25));
		linha.add(label);
		linha.add(campo);
		return linha;
	}

	/**
	 * Esse método valida campos
	 * 
	 * @return True se o campo não estiver vazio e selecionado a unidade corretamente
	 * @return False se o campo estiver vazio e não selecionada a unidade de medida, ou se o custo for menor ou igual a 0.0
	 */
	private boolean validarCampos() {
		Produto p = new Produto();
		if (campoNome.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe o nome do produto!");
			return false;
		}

		if (comboUnidade.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(null, "Selecione uma unidade de medida!");
			return false;
		}

		if (comboLocalizacao.getSelectedIndex() == 0) {
			JOptionPane.showMessageDialog(null, "Selecione uma localização no estoque!");
			return false;
		}

		if (campoQuantidade.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Informe a quantidade!");
			return false;
		}

		String custoTexto = campoCustoUnitario.getText().replace(",", ".");
		try {
			double custo = Double.parseDouble(custoTexto);
			if (custo <= 0.0) {
				JOptionPane.showMessageDialog(null, "O custo unitário deve ser maior que zero!");
				return false;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Custo unitário inválido!");
			return false;
		}

		if (campoCustoUnitario.getText().trim().isEmpty() && p.getCustoUnitario() == 0.00) {
			JOptionPane.showMessageDialog(null, "Informe o custo unitário!");
			return false;
		}

		return true;
	}

	/**
	 * Método responsável por salvar produto no banco de dados
	 */
	private void salvarProduto() {
		try {
			if (!validarCampos()) {
				return;
			}

			Produto p = new Produto();
			p.setNomeProduto(campoNome.getText());
			p.setUnidade(comboUnidade.getSelectedItem().toString());
			p.setLocalizacao(comboLocalizacao.getSelectedItem().toString());
			p.setQuantidade(Integer.parseInt(campoQuantidade.getText()));
			p.setCustoUnitario(Double.parseDouble(campoCustoUnitario.getText()));
			p.setDataEntrada(LocalDateTime.now());
			p.setDataSaida(null);

			DaoProduto dao = new DaoProduto();
			if (dao.inserir(p)) {
				JOptionPane.showMessageDialog(null, "Produto inserido com sucesso!");
				limparCampos();
			} else {
				JOptionPane.showMessageDialog(null, "Falha ao inserir produto.");
			}

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "Preencha corretamente os campos numéricos." + ex.getMessage());
		}

	}

	/**
	 * Esse método serve para limpar campos de texto
	 * 
	 */

	private void limparCampos() {
		campoNome.setText("");
		comboUnidade.setSelectedIndex(0);
		campoQuantidade.setText("");
		campoCustoUnitario.setText("");
	}

	private void alerta(String msg) {
		JOptionPane.showMessageDialog(null, "<html>⚠️ <b><font color='orange'>Aviso:</font></b> " + msg + "</html>", "Aviso",
				JOptionPane.WARNING_MESSAGE);
	}

	private void alertaSucesso(String msg) {
		JOptionPane.showMessageDialog(null, "<html>✅ <b><font color='green'>Sucesso:</font></b> " + msg + "</html>", "Sucesso",
				JOptionPane.INFORMATION_MESSAGE);
	}

	private void alertaErro(String msg) {
		JOptionPane.showMessageDialog(null, "<html>❌ <b><font color='red'>Erro:</font></b> " + msg + "</html>", "Erro",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Esse método cria um JLabel de copyright
	 */

	public static final JLabel criarCopyright() {
		String texto = "<html><div style='text-align:center; color:#808080; font-family: Arial, sans-serif;'>© 2025 Lazaro Coder <span style='color:#C0C0C0;'>v1.0</span></div></html>";
		return new JLabel(texto);
	}
}
