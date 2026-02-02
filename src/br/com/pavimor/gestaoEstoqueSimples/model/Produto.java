package br.com.pavimor.gestaoEstoqueSimples.model;

import java.time.LocalDateTime;

/**
 * Representa um Produto do sistema de gestão de estoque.
 * 
 * Classe do tipo DTO (Data Transfer Object), utilizada para
 * transporte de dados entre as camadas da aplicação.
 * 
 * Contém apenas atributos e métodos de acesso, sem regras de negócio.
 * 
 * Camada: Model
 * 
 * @author Lazaro Nogueira
 * @version 1.0
 * @since 2026-02-01
 */


public class Produto {
	private int idProduto;
	private String nomeProduto;
	private String unidade;
	private String localizacao;
	private int quantidade;
	private double custoUnitario;
	/*LocalDateTime - Utilizei essas variáveis pra pegar exatamente a data e hora e gravá-la no banco de dados.*/
	private LocalDateTime dataEntrada;  
	private LocalDateTime dataSaida;
	/*quantidadeMinima - Utilizei Integer pra facilitar algumas operações de if, else e mais*/
	private Integer quantidadeMinima; 

	/**
	 * Construtor completo para criação de um produto com todos os atributos.
	 */
	public Produto(int idProduto, String nomeProduto, String unidade, String localizacao, int quantidade, double custoUnitario, LocalDateTime dataEntrada, LocalDateTime dataSaida, int quantidadeMinima) {
		this.idProduto = idProduto;
		this.nomeProduto = nomeProduto;
		this.unidade = unidade;
		this.localizacao = localizacao;
		this.quantidade = quantidade;
		this.custoUnitario = custoUnitario;
		this.dataEntrada = dataEntrada;
		this.dataSaida = dataSaida;
		this.quantidadeMinima = quantidadeMinima;
	}

	/**
	 * Construtor padrão utilizado em operações de leitura e manipulação parcial.
	 */
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

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
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

	public Integer getQuantidadeMinima() {
		return quantidadeMinima;
	}

	public void setQuantidadeMinima(Integer quantidadeMinima) {
		this.quantidadeMinima = quantidadeMinima;
	}
}