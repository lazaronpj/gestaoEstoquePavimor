package br.com.pavimor.gestaoEstoqueSimples.view.telaPrincipal;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import br.com.pavimor.gestaoEstoqueSimples.view.telaAlterar.TelaAlterarProdutos;
import br.com.pavimor.gestaoEstoqueSimples.view.telaExcluir.TelaExcluirProdutos;
import br.com.pavimor.gestaoEstoqueSimples.view.telaInserir.TelaCadastroProduto;
import br.com.pavimor.gestaoEstoqueSimples.view.telaRegistrarSaida.TelaRegistrarSaida;
import br.com.pavimor.gestaoEstoqueSimples.view.telaVisualizar.TelaVisualizarProdutos;

/**
 * Representa a tela principal da aplicação de gestão de estoque.
 *
 * Esta tela funciona como ponto de entrada do sistema,
 * permitindo acesso às principais funcionalidades.
 *
 * Permite:
 * - Inserir novos produtos
 * - Visualizar produtos cadastrados
 * - Alterar dados de produtos
 * - Excluir produtos
 * - Registrar saída de produtos do estoque
 *
 * Também oferece atalhos de teclado para navegação rápida:
 * I (Inserir), V (Visualizar), A (Alterar), E (Excluir), R (Registrar Saída)
 *
 * Camada: View
 *
 * @author Lazaro Nogueira
 * @version 1.0
 * @since 2026-02-01
 */
public class Tela {

    /**
     * Constrói e inicializa a tela principal da aplicação.
     *
     * Responsabilidades:
     * - Montar a interface gráfica inicial
     * - Criar botões de navegação
     * - Associar ações e atalhos de teclado
     * - Direcionar o usuário para as demais telas do sistema
     */
    public Tela() {
        JFrame frame = new JFrame("Gestão de Estoque Pavimor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 150);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());

        JPanel pNorte = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel dica = new JLabel("<html>Bem-vindo(a) ao gerenciador de estoque <b>Pavimor</b>!</html>");
        dica.setFont(new Font("Arial", Font.PLAIN, 14));
        dica.setPreferredSize(new Dimension(400, 25));
        pNorte.add(dica);

        JPanel pCentro = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton btnInserir = new JButton("Inserir (I)");
        btnInserir.setBackground(Color.GREEN);
        btnInserir.setToolTipText("Inserir novos produtos!");
        btnInserir.setFont(new Font("Arial", Font.BOLD, 14));

        JButton btnVisualizar = new JButton("Visualizar (V)");
        btnVisualizar.setToolTipText("Visualizar produtos!");
        btnVisualizar.setBackground(Color.BLUE);
        btnVisualizar.setFont(new Font("Arial", Font.BOLD, 14));

        JButton btnAlterar = new JButton("Alterar (A)");
        btnAlterar.setToolTipText("Alterar campos de produtos!");
        btnAlterar.setBackground(Color.YELLOW);
        btnAlterar.setFont(new Font("Arial", Font.BOLD, 14));

        JButton btnExcluir = new JButton("Excluir (E)");
        btnExcluir.setToolTipText("Excluir produtos!");
        btnExcluir.setBackground(Color.RED);
        btnExcluir.setFont(new Font("Arial", Font.BOLD, 14));

        JButton btnSaida = new JButton("Registrar Saída (R)");
        btnSaida.setToolTipText("Registrar saída de produtos!");
        btnSaida.setBackground(Color.ORANGE);
        btnSaida.setFont(new Font("Arial", Font.BOLD, 14));

        btnInserir.addActionListener(e -> new TelaCadastroProduto());
        btnVisualizar.addActionListener(e -> new TelaVisualizarProdutos());
        btnAlterar.addActionListener(e -> new TelaAlterarProdutos());
        btnExcluir.addActionListener(e -> new TelaExcluirProdutos());
        btnSaida.addActionListener(e -> new TelaRegistrarSaida());

        /**
         * Atalhos de teclado para navegação rápida entre as telas.
         */
        btnInserir.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int tecla = e.getKeyCode();

                switch (tecla) {
                    case KeyEvent.VK_I:
                        btnInserir.doClick();
                        break;
                    case KeyEvent.VK_V:
                        btnVisualizar.doClick();
                        break;
                    case KeyEvent.VK_A:
                        btnAlterar.doClick();
                        break;
                    case KeyEvent.VK_E:
                        btnExcluir.doClick();
                        break;
                    case KeyEvent.VK_R:
                        btnSaida.doClick();
                        break;
                }
            }
        });

        pCentro.add(btnInserir);
        pCentro.add(btnVisualizar);
        pCentro.add(btnAlterar);
        pCentro.add(btnExcluir);
        pCentro.add(btnSaida);

        JPanel pSul = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pSul.add(criarCopyright());

        frame.add(pNorte, BorderLayout.NORTH);
        frame.add(pCentro, BorderLayout.CENTER);
        frame.add(pSul, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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

    /**
     * Método principal responsável por iniciar a aplicação.
     *
     * @param args argumentos de inicialização (não utilizados)
     */
    public static void main(String[] args) {
        new Tela();
    }
}
