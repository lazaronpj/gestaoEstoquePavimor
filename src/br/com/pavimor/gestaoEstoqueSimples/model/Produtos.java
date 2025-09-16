package br.com.pavimor.gestaoEstoqueSimples.model;

public class Produtos {

	private String nomeProduto;
	private String unidade;
	private int quantidade;
	private double custoUnitario;

	public Produtos(String nomeProduto, String unidade, int quantidade, double custoUnitario) {
		this.nomeProduto = nomeProduto;
		this.unidade = unidade;
		this.quantidade = quantidade;
		this.custoUnitario = custoUnitario;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public double getCustoUnitario() {
		return custoUnitario;
	}

	public void setCustoUnitario(double custoUnitario) {
		this.custoUnitario = custoUnitario;
	}

}
