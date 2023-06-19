package compras;

import javax.swing.*;
import java.awt.*;

public class Loja {
    private final CarrinhoDeCompras carrinho;
    private JFrame frame;
    private final JList<Produto> listaProdutos;
    private final DefaultListModel<Produto> model;

    public Loja() {
        carrinho = new CarrinhoDeCompras();
        model = new DefaultListModel<>();
        listaProdutos = new JList<>(model);
    }

    private void criarInterface() {
        frame = new JFrame("Carrinho de Compras");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel painelCarrinho = new JPanel(new BorderLayout());

        listaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listaProdutos);
        painelCarrinho.add(scrollPane, BorderLayout.CENTER);

        JButton btnAdicionar = new JButton("Adicionar Produto");
        btnAdicionar.addActionListener(e -> adicionarProduto());
        painelCarrinho.add(btnAdicionar, BorderLayout.NORTH);

        JButton btnComprar = new JButton("Finalizar compra");
        btnComprar.addActionListener(e -> comprarProduto());
        painelCarrinho.add(btnComprar, BorderLayout.SOUTH);

        JPanel painelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel lblTotal = new JLabel("Total: R$ 0.00");
        painelTotal.add(lblTotal);

        frame.add(painelCarrinho, BorderLayout.CENTER);
        frame.add(painelTotal, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }

    private void adicionarProduto() {
        String[] tipos = {"Eletrônico", "Alimento", "Vestuário"};
        String tipoSelecionado = (String) JOptionPane.showInputDialog(frame, "Selecione o tipo de produto:", "Adicionar Produto", JOptionPane.PLAIN_MESSAGE, null, tipos, tipos[0]);

        if (tipoSelecionado != null) {
            String nome = JOptionPane.showInputDialog(frame, "Digite o nome do produto:");
            double preco = Double.parseDouble(JOptionPane.showInputDialog(frame, "Digite o preço do produto:"));

            Produto produto;
            switch (tipoSelecionado) {
                case "Eletrônico":
                    String marca = JOptionPane.showInputDialog(frame, "Digite a marca do produto eletrônico:");
                    produto = new Eletronico(nome, preco, marca);
                    break;
                case "Alimento":
                    String dataValidade = JOptionPane.showInputDialog(frame, "Digite a data de validade do alimento:");
                    produto = new Alimento(nome, preco, dataValidade);
                    break;
                case "Vestuário":
                    String tamanho = JOptionPane.showInputDialog(frame, "Digite o tamanho do vestuário:");
                    produto = new Vestuario(nome, preco, tamanho);
                    break;
                default:
                    return;
            }
            carrinho.adicionarProduto(produto);
            model.addElement(produto);

            double total = carrinho.calcularTotal();
            atualizarTotal(total);
        }
    }

    private void comprarProduto() {
        double total = carrinho.calcularTotal();
        JOptionPane.showMessageDialog(frame, "Compra realizada! Total: R$" + total);
        carrinho.limparCarrinho();
        model.clear();
        atualizarTotal(0.0);
    }

    private void atualizarTotal(double total) {
        frame.setTitle("Carrinho de Compras - Total: R$" + total);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
             Loja gui = new Loja();
            gui.criarInterface();
        });
    }
}
