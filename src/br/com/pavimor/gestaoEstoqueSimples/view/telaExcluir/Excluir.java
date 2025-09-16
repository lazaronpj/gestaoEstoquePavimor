package br.com.pavimor.gestaoEstoqueSimples.view.telaExcluir;

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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.com.pavimor.gestaoEstoqueSimples.dao.DaoProdutos;
import br.com.pavimor.gestaoEstoqueSimples.view.telaPrincipal.Tela;

public class Excluir {

	private JTextField campoId;

	public void excluirProdutos() {
		JFrame frame = new JFrame("Gestão de Estoque Pavimor - Excluir Produtos");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(450, 220);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());

		JPanel pNorte = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		JLabel dica = new JLabel("<html>Digite o <b>id</b> do produto que você deseja <b>excluir</b> do estoque!</html>");
		dica.setFont(new Font("Arial", Font.PLAIN, 14));
		dica.setPreferredSize(new Dimension(400, 25));

		pNorte.add(dica);

		JPanel pCentro = new JPanel();
		pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));

		JPanel campos = new JPanel();
		campos.setLayout(new BoxLayout(campos, BoxLayout.Y_AXIS));
		campoId = new JTextField();
		campos.add(criarLinha("Digite o ID do produto para excluir: ", campoId));

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
					int confirmacao = JOptionPane.showConfirmDialog(frame, "Deseja realmente excluir esse item?", "Confirmação", JOptionPane.YES_NO_OPTION);

					if (confirmacao == JOptionPane.YES_OPTION) {
						try {
							int id = Integer.parseInt(campoId.getText());

							DaoProdutos dao = new DaoProdutos();
							if (dao.remover(id)) {
								JOptionPane.showMessageDialog(null, "✅ Produto removido!");
							} else {
								JOptionPane.showMessageDialog(null, "⚠ Produto não encontrado para remoção.");
							}
						} catch (NumberFormatException ex) {
							JOptionPane.showMessageDialog(null, "ID inválido, digite um número inteiro.");
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null, "Erro inesperado: " + ex.getMessage());
							ex.printStackTrace();
						}
					} else {
						frame.dispose();
					}

				} catch (NumberFormatException ex) {

					JOptionPane.showMessageDialog(frame, "O campo ID deve ser um número válido!", "Erro", JOptionPane.ERROR_MESSAGE);
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
		frame.add(pSul, BorderLayout.PAGE_END);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	private JPanel criarLinha(String texto, javax.swing.JComponent campo) {
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

}
