package br.com.pavimor.gestaoEstoqueSimples.view.telaRegistrarSaida;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.pavimor.gestaoEstoqueSimples.dao.DaoProduto;
import br.com.pavimor.gestaoEstoqueSimples.model.Produto;

/**
 * Representa a interface gráfica responsável pelo registro
 * de saída de produtos do estoque.
 *
 * Permite:
 * - Buscar produto pelo ID
 * - Validar quantidade de saída
 * - Atualizar o estoque no banco de dados
 *
 * Camada: View
 *
 * @author Lazaro Nogueira
 * @version 1.0
 * @since 2026-02-01
 */

public class TelaRegistrarSaida {

	 /**
     * Campos de entrada e exibição de dados do produto.
     */
	
	private JTextField campoId, campoQuantidadeNova, campoQuantidadeAtual, campoNome, campoUnidadeMedida, campoLocalizacao, campoCustoUnitario;


    /**
     * Constrói e inicializa a tela de registro de saída de produtos.
     *
     * Responsabilidades:
     * - Montar a interface gráfica
     * - Configurar validações de entrada
     * - Definir ações dos botões
     */
	
	public TelaRegistrarSaida() {
		JFrame frame = new JFrame("Gestão de Estoque Pavimor - Registrar Saída");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(450, 450);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());

		JPanel pNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel dica = new JLabel("<html>Digite o <font color='red'><b>ID</b></font> do produto para registrar a saída:</html>");
		dica.setFont(new Font("Arial", Font.PLAIN, 14));
		pNorte.add(dica);

		JPanel pCentro = new JPanel();

		campoId = new JTextField();
		pCentro.add(criarLinha("ID: ", campoId));
		campoId.setToolTipText("Digite o número do ID do produto a dar baixa no estoque!");

		campoNome = new JTextField();
		campoNome.setEditable(false);
		pCentro.add(criarLinha("Nome: ", campoNome));

		campoUnidadeMedida = new JTextField();
		campoUnidadeMedida.setEditable(false);
		pCentro.add(criarLinha("Unidade de Medida: ", campoUnidadeMedida));

		campoLocalizacao = new JTextField();
		campoLocalizacao.setEditable(false);
		pCentro.add(criarLinha("Localização: ", campoLocalizacao));

		campoCustoUnitario = new JTextField();
		campoCustoUnitario.setEditable(false);
		pCentro.add(criarLinha("<html>Custo Unitário <b>(R$)</b>:</html> ", campoCustoUnitario));

		pCentro.add(Box.createRigidArea(new Dimension(150, 20)));
		campoQuantidadeAtual = new JTextField();
		campoQuantidadeAtual.setEditable(false);
		campoQuantidadeAtual.setPreferredSize(new Dimension(150, 25));
		pCentro.add(criarLinha("Quantidade Atual: ", campoQuantidadeAtual));

		campoQuantidadeNova = new JTextField();
		campoQuantidadeNova.setEditable(false);
		campoQuantidadeNova.setPreferredSize(new Dimension(150, 25));
		pCentro.add(criarLinha("Quantidade a Remover: ", campoQuantidadeNova));

		campoId.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				campoId.setText(campoId.getText().replaceAll("[^0-9]", ""));
			}
		});
		campoQuantidadeNova.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				campoQuantidadeNova.setText(campoQuantidadeNova.getText().replaceAll("[^0-9]", ""));
			}
		});

		final Color corFoco = new Color(245, 255, 245);
		final Color corOriginal = new Color(255, 255, 255);

		campoId.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				campoId.setBackground(corFoco);
			}

			@Override
			public void focusLost(FocusEvent e) {
				campoId.setBackground(corOriginal);
			}
		});

		JPanel pSul = new JPanel(new BorderLayout());
		JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		JButton btnBuscar = new JButton("Buscar Produto");
		btnBuscar.setToolTipText("Clique aqui para buscar o ID correspondente a um produto!");
		JButton btnRegistrar = new JButton("Registrar Saída");
		btnRegistrar.setToolTipText("Clique aqui para registrar saída do produto!");
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setToolTipText("Clique aqui para limpar campos!");

		botoesPanel.add(btnBuscar);
		botoesPanel.add(btnRegistrar);
		botoesPanel.add(btnLimpar);

		JPanel copyrightPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		copyrightPanel.add(criarCopyright());

		pSul.add(botoesPanel, BorderLayout.NORTH);
		pSul.add(copyrightPanel, BorderLayout.SOUTH);

		btnBuscar.addActionListener(e -> buscarProduto());
		btnBuscar.setBackground(Color.GREEN);
		btnRegistrar.addActionListener(e -> registrarSaida());
		btnRegistrar.setBackground(Color.ORANGE);
		btnLimpar.addActionListener(e -> limparCampos());
		btnLimpar.setBackground(Color.LIGHT_GRAY);

		frame.add(pNorte, BorderLayout.NORTH);
		frame.add(pCentro, BorderLayout.CENTER);
		frame.add(pSul, BorderLayout.SOUTH);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	 /**
     * Busca um produto pelo ID informado e atualiza os campos da tela.
     *
     * Regras:
     * - ID deve ser numérico
     * - Produto deve existir
     * - Campos são liberados apenas após busca válida
     */
	
	private void buscarProduto() {
		String idTexto = campoId.getText().trim();
		if (idTexto.isEmpty()) {
			alerta("Digite o ID do produto!");
			campoQuantidadeNova.setEditable(false);
			campoQuantidadeNova.setText("");
			campoQuantidadeAtual.setText("");
			return;
		}
		try {
			int id = Integer.parseInt(idTexto);
			DaoProduto dao = new DaoProduto();
			Produto pdt = dao.buscarPorId(id);

			if (pdt == null) {
				alerta("Produto não encontrado!");
				campoNome.setEditable(false);
				campoNome.setText("");
				campoUnidadeMedida.setEditable(false);
				campoUnidadeMedida.setText("");
				campoLocalizacao.setEditable(false);
				campoLocalizacao.setText("");
				campoCustoUnitario.setEditable(false);
				campoCustoUnitario.setText("");
				campoQuantidadeAtual.setText("");
				campoQuantidadeNova.setEditable(false);
				campoQuantidadeNova.setText("");
			} else {
				campoNome.setText(String.valueOf(pdt.getNomeProduto()));
				campoUnidadeMedida.setText(String.valueOf(pdt.getUnidade()));
				campoLocalizacao.setText(String.valueOf(pdt.getLocalizacao()));
				campoCustoUnitario.setText(String.valueOf(pdt.getCustoUnitario()));
				campoQuantidadeAtual.setText(String.valueOf(pdt.getQuantidade()));
				campoQuantidadeNova.setEditable(true);
				campoQuantidadeNova.setText("");

				final Color corFoco = new Color(245, 255, 245);
				final Color corOriginal = new Color(255, 255, 255);

				campoQuantidadeNova.addFocusListener(new FocusAdapter() {

					@Override
					public void focusGained(FocusEvent e) {
						campoQuantidadeNova.setBackground(corFoco);
					}

					@Override
					public void focusLost(FocusEvent e) {
						campoQuantidadeNova.setBackground(corOriginal);
					}
				});

			}
		} catch (NumberFormatException ex) {
			alertaErro("ID inválido!");
			campoNome.setText("");
			campoQuantidadeNova.setEditable(false);
			campoQuantidadeNova.setText("");
			campoQuantidadeAtual.setText("");
		}
	}
	
	 /**
     * Registra a saída de um produto do estoque.
     *
     * Validações aplicadas:
     * - ID e quantidade obrigatórios
     * - Quantidade maior que zero
     * - Estoque suficiente
     *
     * Após confirmação do usuário, atualiza o estoque no banco
     * e reflete o novo valor na interface.
     */
	
	private void registrarSaida() {
		String idTexto = campoId.getText().trim();
		String qtdTexto = campoQuantidadeNova.getText().trim();

		if (idTexto.isEmpty() || qtdTexto.isEmpty()) {
			alerta("Preencha o ID e a quantidade a remover!");
			return;
		}

		try {
			int id = Integer.parseInt(idTexto);
			int quantidadeSaida = Integer.parseInt(qtdTexto);

			DaoProduto dao = new DaoProduto();
			Produto pdt = dao.buscarPorId(id);

			if (pdt == null) {
				alerta("Produto não encontrado!");
				campoQuantidadeNova.setEditable(false);
				return;
			}

			if (quantidadeSaida <= 0) {
				alertaErro("Quantidade inválida!");
				return;
			}

			if (pdt.getQuantidade() < quantidadeSaida) {
				alertaErro("Estoque insuficiente! Disponível: " + pdt.getQuantidade());
				return;
			}

			int confirmacao = JOptionPane.showConfirmDialog(null, "Deseja realmente registrar saída de " + quantidadeSaida + " unidade(s) do produto " + pdt.getNomeProduto() + "?", "Confirmação",
					JOptionPane.YES_NO_OPTION);

			if (confirmacao == JOptionPane.YES_OPTION) {
				boolean sucessoRegistrarSaida = dao.registrarSaida(id, quantidadeSaida);

				if (sucessoRegistrarSaida) {
					int novaQuantidade = pdt.getQuantidade() - quantidadeSaida;

					alertaSucesso("Saída registrada com sucesso!\n" + "Quantidade removida: " + quantidadeSaida + "\n" + "Quantidade atual: " + novaQuantidade);

					campoId.setText("");
					campoQuantidadeAtual.setText(String.valueOf(novaQuantidade));
					campoQuantidadeNova.setText("");
					campoQuantidadeNova.setEditable(false);
				} else {
					alertaErro("Erro ao registrar saída no banco de dados!");
				}
			}

		} catch (NumberFormatException ex) {
			alertaErro("Digite valores numéricos válidos!");
		}
	}

	   /**
     * Limpa os campos da tela e redefine o estado inicial.
     */
	
	private void limparCampos() {
		campoId.setText("");
		campoQuantidadeNova.setText("");
		campoQuantidadeAtual.setText("");
		campoQuantidadeNova.setEditable(false);
	}

	 /**
     * Cria uma linha padrão de formulário contendo um rótulo e um campo.
     *
     * @param texto texto do rótulo
     * @param campo componente de entrada ou exibição
     * @return JPanel configurado com label e campo
     */
	
	private JPanel criarLinha(String texto, javax.swing.JComponent campo) {
		JPanel linha = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
		JLabel label = new JLabel(texto);
		label.setFont(new Font("Arial", Font.PLAIN, 14));
		label.setPreferredSize(new Dimension(160, 25));
		campo.setFont(new Font("Arial", Font.PLAIN, 14));
		campo.setPreferredSize(new Dimension(165, 25));
		linha.add(label);
		linha.add(campo);
		return linha;
	}

    /**
     * Exibe uma mensagem de aviso ao usuário.
     *
     * @param msg mensagem exibida
     */
	
	private void alerta(String msg) {
		JOptionPane.showMessageDialog(null, "<html>⚠️ <b><font color='orange'>Aviso:</font></b> " + msg + "</html>", "Aviso", JOptionPane.WARNING_MESSAGE);
	}

	/**
     * Exibe uma mensagem de sucesso ao usuário.
     *
     * @param msg mensagem exibida
     */
	private void alertaSucesso(String msg) {
		JOptionPane.showMessageDialog(null, "<html>✅ <b><font color='green'>Sucesso:</font></b> " + msg + "</html>", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
     * Exibe uma mensagem de erro ao usuário.
     *
     * @param msg mensagem exibida
     */
	private void alertaErro(String msg) {
		JOptionPane.showMessageDialog(null, "<html>❌ <b><font color='red'>Erro:</font></b> " + msg + "</html>", "Erro", JOptionPane.ERROR_MESSAGE);
	}

	/**
     * Cria e retorna o rodapé padrão da aplicação.
     *
     * @return JLabel com informações de copyright
     */
	public static final JLabel criarCopyright() {
		String texto = "<html><div style='text-align:center; color:#808080; font-family: Arial, sans-serif;'>© 2025 Lazaro Coder <span style='color:#C0C0C0;'>v1.0</span></div></html>";
		return new JLabel(texto);
	}
}