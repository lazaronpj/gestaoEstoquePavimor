package br.com.pavimor.gestaoEstoqueSimples.util;

/**
 * Classe utilitária responsável pela limpeza e formatação
 * de valores monetários informados pelo usuário.
 *
 * Camada: Util
 *
 * @author Lazaro Nogueira
 * @version 1.0
 * @since 2026-02-01
 */

public class ValidadorCampoCusto {

	/**
	 * Remove caracteres que não sejam números.
	 *
	 * @param valor valor informado pelo usuário
	 * @return string contendo apenas números
	 */
	public static String limparNumeros(String valor) {
		return valor.replaceAll("[^0-9]", "");
	}

	/**
	 * Formata um valor numérico para padrão monetário simples (R$).
	 *
	 * @param valor valor bruto informado
	 * @return valor formatado com casas decimais
	 */
	public static String formatarMoeda(String valor) {
		if (valor == null || valor.isEmpty()) {
			return "0.00";
		}

		String numeros = limparNumeros(valor);

		if (numeros.length() < 3) {
			numeros = String.format("%03d", Integer.parseInt(numeros));
		}

		String reais = numeros.substring(0, numeros.length() - 2).replaceFirst("^0+", "");
		if (reais.isEmpty())
			reais = "0";

		String centavos = numeros.substring(numeros.length() - 2).replaceAll("[^0-9.,]", "").replace(",", ".");

		return reais + "." + centavos;
	}
}
