package br.com.pavimor.gestaoEstoqueSimples.view.telaExcluir;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.pavimor.gestaoEstoqueSimples.dao.DaoProduto;
import br.com.pavimor.gestaoEstoqueSimples.model.Produto;

/**
 * Tela responsável pela exclusão de produtos do estoque.
 *
 * Permite buscar um produto pelo ID, validar sua existência
 * e confirmar a remoção no banco de dados.
 *
 * Camada: View
 *
 * @author Lazaro Nogueira
 * @version 1.0
 * @since 2026-02-01
 */

public class TelaExcluirProdutos {

	private JTextField campoId;

	/**
	 * Constrói e inicializa a interface gráfica da tela de exclusão.
	 *
	 * Responsabilidades:
	 * - Montar a interface
	 * - Validar entrada do ID
	 * - Acionar exclusão do produto
	 */
	
	public TelaExcluirProdutos() {
		JFrame frame = new JFrame("Gestão de Estoque Pavimor - Excluir Produtos");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(450, 190);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());

		JPanel pNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel dica = new JLabel(
				"<html>Digite o <b><font color = 'red'>ID</font></b> do produto que você deseja <b>excluir</b> do estoque!</html>");
		dica.setFont(new Font("Arial", Font.PLAIN, 14));
		dica.setPreferredSize(new Dimension(400, 25));
		pNorte.add(dica);

		JPanel pCentro = new JPanel();

		campoId = new JTextField();
		campoId.setToolTipText("Digite o número do ID do produto para excluir no estoque!");
		campoId.setPreferredSize(new Dimension(200, 25));
		pCentro.add(criarLinha("Digite o ID do produto para excluir: ", campoId));

		campoId.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent e) {
				String txt = campoId.getText().trim();

				if (txt.length() > 10) {
					alerta("O campo ID não pode ser maior do que 10 caracteres!");
					campoId.setText("");
				} else {
					txt = txt.replaceAll("[^0-9]", "");
					campoId.setText(txt);
				}
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
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setToolTipText("Clique aqui para excluir o produto do banco de dados!");
		btnExcluir.setFont(new Font("Arial", Font.BOLD, 14));
		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.setFont(new Font("Arial", Font.BOLD, 14));
		btnLimpar.setToolTipText("Clique aqui para limpar campos!");
		btnExcluir.addActionListener(e -> excluirProduto());
		btnExcluir.setBackground(Color.RED);
		btnLimpar.setBackground(Color.LIGHT_GRAY);
		btnLimpar.addActionListener(e -> campoId.setText(""));
		botoesPanel.add(btnExcluir);
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
	 * Realiza a exclusão de um produto a partir do ID informado.
	 *
	 * Fluxo:
	 * - Valida o ID
	 * - Busca o produto no banco
	 * - Solicita confirmação do usuário
	 * - Remove o produto se confirmado
	 */
	
	private void excluirProduto() {
		try {
			int id = Integer.parseInt(campoId.getText());
			DaoProduto dao = new DaoProduto();

			Produto produtoParaExcluir = dao.buscarPorId(id);

			if (produtoParaExcluir == null) {
				alerta("Produto não encontrado no banco de dados!");
				campoId.setText("");
				return;
			}

			int confirmacao = JOptionPane.showConfirmDialog(null,
					"Deseja realmente excluir o produto: " + produtoParaExcluir.getNomeProduto() + " (ID: " + id + ")?",
					"Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);

			if (confirmacao == JOptionPane.YES_OPTION) {
				boolean removidoComSucesso = dao.remover(id);

				if (removidoComSucesso) {
					alertaSucesso("Produto excluído com sucesso!");
					campoId.setText("");
				} else {
					alertaErro("Falha ao excluir o produto.");
				}
			} else {
				campoId.setText("");
				return;
			}

		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(null, "O campo ID deve conter um número válido!", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
			campoId.setText("");
		}
	}

	/**
	 * Cria uma linha de formulário com rótulo e campo.
	 *
	 * @param texto texto exibido no rótulo
	 * @param campo componente de entrada
	 * @return painel configurado
	 */
	
	private JPanel criarLinha(String texto, javax.swing.JComponent campo) {
		JPanel linha = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
		JLabel label = new JLabel(texto);
		label.setFont(new Font("Arial", Font.PLAIN, 14));
		label.setPreferredSize(new Dimension(250, 25));
		campo.setFont(new Font("Arial", Font.PLAIN, 14));
		campo.setPreferredSize(new Dimension(90, 25));
		linha.add(label);
		linha.add(campo);
		return linha;
	}

	/** Exibe alerta informativo ao usuário. */
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
