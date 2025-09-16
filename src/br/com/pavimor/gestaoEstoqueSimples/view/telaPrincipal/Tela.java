package br.com.pavimor.gestaoEstoqueSimples.view.telaPrincipal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tela {

	public static JLabel criarCopyright() {
		String texto = "<html><div style='text-align:center; color:#808080; font-family: Arial, sans-serif;'>" + "© 2025 Lazaro Coder <span style='color:#C0C0C0;'>v1.0</span>" + "</div></html>";
		return new JLabel(texto);

	}

	public static void abrirTela() {
		JFrame frame = new JFrame("Gestão de Estoque Pavimor");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 170);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());

		JPanel pNorte = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
		JLabel dica = new JLabel("<html>Bem-vindo(a) ao gerenciador de estoque <b>Pavimor</b>!</html>");
		dica.setFont(new Font("Arial", Font.PLAIN, 14));
		dica.setPreferredSize(new Dimension(350, 25));

		pNorte.add(dica);

		JPanel pCentro = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JPanel botoes = new JPanel();
		JButton inserir = new JButton("Inserir");
		inserir.setFont(new Font("Arial", Font.BOLD, 14));

		JButton visualizar = new JButton("Visualizar");
		visualizar.setFont(new Font("Arial", Font.BOLD, 14));

		JButton alterar = new JButton("Alterar");
		alterar.setFont(new Font("Arial", Font.BOLD, 14));

		JButton excluir = new JButton("Excluir");
		excluir.setFont(new Font("Arial", Font.BOLD, 14));

		botoes.add(inserir);
		botoes.add(visualizar);
		botoes.add(alterar);
		botoes.add(excluir);
		pCentro.add(botoes);

		JPanel pSul = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pSul.add(criarCopyright());

		frame.add(pNorte, BorderLayout.NORTH);
		frame.add(pCentro, BorderLayout.CENTER);
		frame.add(pSul, BorderLayout.SOUTH);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	public static void main(String[] args) {
		abrirTela();
	}

}
