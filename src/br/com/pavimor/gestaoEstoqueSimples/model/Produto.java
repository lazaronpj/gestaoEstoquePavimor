package br.com.pavimor.gestaoEstoqueSimples.model;

import java.time.LocalDateTime;

public class Produto {

	private int idProduto;
	private String nomeProduto;
	private String unidade;
	private int quantidade;
	private double custoUnitario;
	private LocalDateTime dataEntrada;
	private LocalDateTime dataSaida;

	public Produto(int idProduto, String nomeProduto, String unidade, int quantidade, double custoUnitario, LocalDateTime dataEntrada, LocalDateTime dataSaida) {
		this.idProduto = idProduto;
		this.nomeProduto = nomeProduto;
		this.unidade = unidade;
		this.quantidade = quantidade;
		this.custoUnitario = custoUnitario;
		this.dataEntrada = dataEntrada;
		this.dataSaida = dataSaida;
	}

	public Produto() {
	}

	public int getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
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

	public LocalDateTime getDataEntrada() {
		return dataEntrada;
	}

	public void setDataEntrada(LocalDateTime dataEntrada) {
		this.dataEntrada = dataEntrada;
	}

	public LocalDateTime getDataSaida() {
		return dataSaida;
	}

	public void setDataSaida(LocalDateTime dataSaida) {
		this.dataSaida = dataSaida;
	}
}
