package br.com.pavimor.gestaoEstoqueSimples.util;

public class ValidadorCampoCusto {

	public static String limparNumeros(String valor) {
		return valor.replaceAll("[^0-9]", "");
	}

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
