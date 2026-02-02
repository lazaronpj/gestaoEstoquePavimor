package br.com.pavimor.gestaoEstoqueSimples.view.telaAlterar;

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

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.pavimor.gestaoEstoqueSimples.dao.DaoProduto;
import br.com.pavimor.gestaoEstoqueSimples.model.Produto;
import br.com.pavimor.gestaoEstoqueSimples.util.ValidadorCampoCusto;

/**
 * Tela responsável pela alteração de dados de produtos cadastrados.
 *
 * Permite buscar um produto pelo ID, habilitar edição dos campos
 * e persistir as alterações no banco de dados.
 *
 * Camada: View
 *
 * @author Lazaro Nogueira
 * @version 1.0
 * @since 2026-02-01
 */

public class TelaAlterarProdutos {

	private final JTextField campoId, campoNome, campoQuantidade, campoCustoUnitario;
	private final JComboBox<String> comboUnidade, comboLocalizacao;

	/**
	 * Constrói e inicializa a interface gráfica da tela de alteração.
	 *
	 * Responsabilidades:
	 * - Buscar produto pelo ID
	 * - Habilitar edição controlada
	 * - Validar dados
	 * - Persistir alterações
	 */
	
	public TelaAlterarProdutos() {
		JFrame frame = new JFrame("Gestão de Estoque Pavimor - Alterar Produtos");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(500, 400);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());

		JPanel pNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel dica = new JLabel("<html>Digite o <font color = 'red'><b>ID</b></font> do produto que você deseja <b>alterar</b>!</html>");
		dica.setFont(new Font("Arial", Font.PLAIN, 14));
		dica.setPreferredSize(new Dimension(400, 25));
		pNorte.add(dica);

		JPanel pCentro = new JPanel();
		pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));

		campoId = new JTextField();
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBackground(Color.BLUE);
		btnBuscar.setFont(new Font("Arial", Font.BOLD, 14));
		btnBuscar.setToolTipText("Clique aqui para buscar o ID correspondente a um produto!");
		JPanel linhaId = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
		linhaId.add(new JLabel("ID:"));
		campoId.setPreferredSize(new Dimension(200, 25));
		campoId.setToolTipText("Digite o número do ID do produto a ser alterado!");
		linhaId.add(campoId);
		linhaId.add(btnBuscar);
		pCentro.add(linhaId);

		campoId.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				String txt = campoId.getText().trim();

				txt = txt.replaceAll("[^0-9]", "");
				campoId.setText(txt);

				if (campoId.getText().trim().isEmpty()) {
					campoId.setBackground(new Color(255, 204, 203));
				} else {
					campoId.setBackground(Color.WHITE);
				}
			}
		});

		campoNome = new JTextField();
		campoNome.setToolTipText("Digite o novo nome a ser alterado!");
		campoQuantidade = new JTextField();
		campoQuantidade.setToolTipText("Digite a nova quantidade do produto a ser alterado!");
		campoCustoUnitario = new JTextField();
		campoCustoUnitario.setToolTipText("Digite o novo custo unitário do produto a ser alterado em (R$)!");
		comboUnidade = new JComboBox<>(new String[]{"Selecione uma Unidade de Medida", "Unidade", "Minheiro", "Caixa", "Kg", "Litro",
				"Metro²", "Saco", "m³", "Litro"});
		comboUnidade.setToolTipText("Selecione o tipo de unidade do produto a ser alterado!");
		comboLocalizacao = new JComboBox<>(new String[]{"Selecione uma Localização", "Galpão de materiais", "Galpão de embalagens",
				"Almoxarifado", "Oficina", "Pátio de pavimentação", "Pátio de vedação", "Pátio de estruturas"});
		comboLocalizacao.setToolTipText("Selecione o novo valor de localização a ser alterado!");

		campoNome.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				String txt = campoNome.getText();
				if (txt.length() > 30) {
					campoNome.setText(txt.substring(0, 30));
					alerta("O campo 'nome' não pode exceder 30 caracteres!");
					campoNome.setText(txt);
				} else {
					campoNome.setText(txt);
				}
			}
		});

		campoQuantidade.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				String txt = campoQuantidade.getText().trim().replaceAll("[^0-9]", "");

				if (txt.length() > 20) {
					alerta("O campo 'quantidade' não pode exceder 20 caracteres!");
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

		JPanel campos = new JPanel();
		campos.setLayout(new BoxLayout(campos, BoxLayout.Y_AXIS));
		campos.add(criarLinha("Nome:", campoNome));
		campos.add(criarLinha("Unidade:", comboUnidade));
		campos.add(criarLinha("Localização:", comboLocalizacao));
		campos.add(criarLinha("Quantidade:", campoQuantidade));
		campos.add(criarLinha("<html>Custo unitário <b>(R$)</b>:</html>", campoCustoUnitario));
		pCentro.add(campos);

		JPanel pSul = new JPanel(new BorderLayout());
		JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		JButton btnAlterar = new JButton("Alterar");
		btnAlterar.setToolTipText("Clique aqui para alterar campos!");
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setToolTipText("Clique aqui para limpar campos!");

		botoesPanel.add(btnAlterar);
		botoesPanel.add(btnLimpar);

		JPanel copyrightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		copyrightPanel.add(criarCopyright());

		pSul.add(botoesPanel, BorderLayout.NORTH);
		pSul.add(copyrightPanel, BorderLayout.SOUTH);

		frame.add(pNorte, BorderLayout.NORTH);
		frame.add(pCentro, BorderLayout.CENTER);
		frame.add(pSul, BorderLayout.SOUTH);

		btnAlterar.addActionListener(e -> alterarProduto());
		btnLimpar.addActionListener(e -> limparCampos());

		btnAlterar.setFont(new Font("Arial", Font.BOLD, 14));
		btnLimpar.setFont(new Font("Arial", Font.BOLD, 14));
		btnAlterar.setBackground(Color.YELLOW);
		btnLimpar.setBackground(Color.LIGHT_GRAY);

		campoNome.setEditable(false);
		campoQuantidade.setEditable(false);
		comboUnidade.setEnabled(false);
		comboLocalizacao.setEnabled(false);
		campoCustoUnitario.setEditable(false);
		btnAlterar.setEnabled(false);

		btnBuscar.addActionListener(e -> {
			buscarProduto();
			if (!campoNome.getText().isEmpty()) {
				campoNome.setEditable(true);
				campoQuantidade.setEditable(true);
				comboUnidade.setEnabled(true);
				comboLocalizacao.setEnabled(true);
				campoCustoUnitario.setEditable(true);
				btnAlterar.setEnabled(true);

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
			} else {
				campoNome.setEditable(false);
				campoQuantidade.setEditable(false);
				comboUnidade.setEnabled(false);
				comboLocalizacao.setEnabled(false);
				campoCustoUnitario.setEditable(false);
				btnAlterar.setEnabled(false);
			}
		});

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * Cria uma linha de formulário com rótulo e campo.
	 *
	 * @param texto texto do rótulo
	 * @param campo componente de entrada
	 * @return painel configurado
	 */
	
	private JPanel criarLinha(String texto, javax.swing.JComponent campo) {
		JPanel linha = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

		JLabel label = new JLabel(texto);
		label.setFont(new Font("Arial", Font.PLAIN, 14));
		label.setPreferredSize(new Dimension(150, 25));

		campo.setFont(new Font("Arial", Font.PLAIN, 14));
		campo.setPreferredSize(new Dimension(250, 25));

		linha.add(label);
		linha.add(campo);
		return linha;
	}

	/**
	 * Valida os campos obrigatórios antes da alteração.
	 *
	 * @return true se os campos estiverem válidos
	 */
	private boolean validarCampos() {
		if (campoNome.getText().trim().isEmpty()) {
			alerta("Informe o nome do produto!");
			return false;
		}

		if (comboUnidade.getSelectedIndex() == 0) {
			alerta("Selecione uma unidade de medida!");
			return false;
		}

		if (campoQuantidade.getText().trim().isEmpty()) {
			alerta("Informe a quantidade!");
			return false;
		}

		String custoTexto = campoCustoUnitario.getText().replace(",", ".");
		try {
			double custo = Double.parseDouble(custoTexto);
			if (custo <= 0.0) {
				alerta("O custo unitário deve ser maior que zero!");
				return false;
			}
		} catch (NumberFormatException e) {
			alertaErro("Custo unitário inválido!");
			return false;
		}

		return true;
	}


	/**
	 * Busca um produto no banco de dados pelo ID informado
	 * e preenche os campos da interface.
	 */
	
	private void buscarProduto() {
		String txt = campoId.getText().trim();

		if (txt.isEmpty()) {
			campoId.setBackground(new Color(255, 204, 203));

			if (!campoId.isFocusOwner()) {
				alerta("Digite um ID válido para continuar!");
			}
			return;
		}

		campoId.setBackground(Color.WHITE);

		try {

			int id = Integer.parseInt(campoId.getText());
			DaoProduto dao = new DaoProduto();
			Produto p = dao.buscarPorId(id);

			if (p != null) {
				campoNome.setText(p.getNomeProduto());
				comboUnidade.setSelectedItem(p.getUnidade());
				comboLocalizacao.setSelectedItem(p.getLocalizacao());
				campoQuantidade.setText(String.valueOf(p.getQuantidade()));
				campoCustoUnitario.setText(String.valueOf(p.getCustoUnitario()));

			} else {
				limparCampos();
				alerta("O ID não encontrado.");
			}
		} catch (NumberFormatException ex) {
			alertaErro("ID inválido (Digite somente números).");

		}
	}

	/**
	 * Aplica as alterações do produto após validação.
	 *
	 * Verifica se houve mudanças reais antes de persistir.
	 */
	private void alterarProduto() {
		try {

			if (!validarCampos()) {
				return;
			}

			Produto p = new Produto();
			DaoProduto dao = new DaoProduto();

			int id = Integer.parseInt(campoId.getText());
			Produto dadosOriginais = dao.buscarPorId(id);

			if (dadosOriginais.getNomeProduto().equals(campoNome.getText())
					&& dadosOriginais.getUnidade().equals(comboUnidade.getSelectedItem().toString())
					&& dadosOriginais.getLocalizacao().equals(comboLocalizacao.getSelectedItem().toString())
					&& dadosOriginais.getQuantidade() == Integer.parseInt(campoQuantidade.getText())
					&& dadosOriginais.getCustoUnitario() == Double.parseDouble(campoCustoUnitario.getText())) {

				alerta("Nenhum dado foi alterado. Modifique os campos antes de salvar.");
				return;
			}

			p.setIdProduto(Integer.parseInt(campoId.getText()));
			p.setNomeProduto(campoNome.getText());
			p.setUnidade(comboUnidade.getSelectedItem().toString());
			p.setLocalizacao(comboLocalizacao.getSelectedItem().toString());
			p.setQuantidade(Integer.parseInt(campoQuantidade.getText()));
			p.setCustoUnitario(Double.parseDouble(campoCustoUnitario.getText()));
			p.setDataEntrada(LocalDateTime.now());

			if (dao.atualizar(p)) {
				alertaSucesso("Produto alterado com sucesso!");
			} else {
				alerta("Falha ao alterar produto, busque o ID antes de tentar alterar os dados.");
			}
		} catch (NumberFormatException ex) {
			alertaErro("Preencha corretamente os campos numéricos.");
		}
	}

	/**
	 * Limpa e desabilita os campos da interface.
	 */
	private void limparCampos() {
		campoNome.setEditable(false);
		campoQuantidade.setEditable(false);
		comboUnidade.setEnabled(false);
		comboLocalizacao.setEnabled(false);
		campoCustoUnitario.setEditable(false);
		campoId.setText("");
		campoNome.setText("");
		comboUnidade.setSelectedIndex(0);
		comboLocalizacao.setSelectedIndex(0);
		campoQuantidade.setText("");
		campoCustoUnitario.setText("");
	}


	/** Exibe alerta informativo. */
	private void alerta(String msg) {
		JOptionPane.showMessageDialog(null, "<html>⚠️ <b><font color='orange'>Aviso:</font></b> " + msg + "</html>", "Aviso",
				JOptionPane.WARNING_MESSAGE);
	}

	/** Exibe mensagem de sucesso. */
	private void alertaSucesso(String msg) {
		JOptionPane.showMessageDialog(null, "<html>✅ <b><font color='green'>Sucesso:</font></b> " + msg + "</html>", "Sucesso",
				JOptionPane.INFORMATION_MESSAGE);
	}

	/** Exibe mensagem de erro. */
	private void alertaErro(String msg) {
		JOptionPane.showMessageDialog(null, "<html>❌ <b><font color='red'>Erro:</font></b> " + msg + "</html>", "Erro",
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Cria o rodapé padrão da aplicação.
	 *
	 * @return JLabel de copyright
	 */
	public static final JLabel criarCopyright() {
		String texto = "<html><div style='text-align:center; color:#808080; font-family: Arial, sans-serif;'>© 2025 Lazaro Coder <span style='color:#C0C0C0;'>v1.0</span></div></html>";
		return new JLabel(texto);
	}
}
