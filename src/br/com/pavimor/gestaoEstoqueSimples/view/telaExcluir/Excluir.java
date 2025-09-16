package br.com.pavimor.gestaoEstoqueSimples.view.telaExcluir;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.pavimor.gestaoEstoqueSimples.view.telaPrincipal.Tela;

public class Excluir {

	public static void excluirProdutos() {
		JFrame frame = new JFrame("Gestão de Estoque Pavimor - Excluir Produtos");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(800, 180);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());

		JPanel pNorte = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		JLabel dica = new JLabel("Bem-vindo(a) ao gerenciador de estoque Pavimor!");
		dica.setFont(new Font("Arial", Font.PLAIN, 14));
		dica.setPreferredSize(new Dimension(350, 25));

		pNorte.add(dica);

		JPanel pCentro = new JPanel();
		pCentro.setLayout(new BoxLayout(pCentro, BoxLayout.Y_AXIS));

		JPanel pSul = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pSul.add(Tela.criarCopyright());

		frame.add(pNorte, BorderLayout.NORTH);
		frame.add(pCentro, BorderLayout.CENTER);
		frame.add(pSul, BorderLayout.SOUTH);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

}
