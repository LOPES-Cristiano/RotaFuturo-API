package br.com.rotafuturo.carreiras.util;


import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Classe utilitaria para funcoes comuns de aplicacao.
 * como obter a data atual,validar emails, normalizar strings, etc.
 */
public final class AppUtils {

    /**
     * Regex basica para validacao de email.
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$"
    );

    /**
     * Construtor privado para evitar a instanciacao da classe utilitaria.
     */
    private AppUtils() {
        // Nada a fazer
    }

    /**
     * Retorna a data atual.
     * @return A data atual como um objeto LocalDate.
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }

    /**
     * Retorna a hora atual.
     * @return A hora atual como um objeto LocalTime.
     */
    public static LocalTime getCurrentTime() {
        return LocalTime.now();
    }

    /**
     * Verifica se uma string nao e nula e nao esta vazia.
     * @param str A string a ser verificada.
     * @return true se a string for valida, false caso contrario.
     */
    public static boolean isNotNullOrEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    /**
     * Valida um endereco de email usando uma expressao regular.
     * @param email O email a ser validado.
     * @return true se o email for valido, false caso contrario.
     */
    public static boolean isValidEmail(String email) {
        return isNotNullOrEmpty(email) && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Normaliza uma string, removendo acentos e convertendo para minusculas.
     * @param str A string a ser normalizada.
     * @return A string normalizada.
     */
    public static String normalizeString(String str) {
        if (!isNotNullOrEmpty(str)) {
            return "";
        }
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        return normalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").toLowerCase();
    }
    
    /**
     * Retorna um valor de um Optional, ou um valor padrao se o Optional estiver vazio.
     * @param optional O Optional a ser verificado.
     * @param defaultValue O valor padrao.
     * @param <T> O tipo do objeto.
     * @return O valor do Optional ou o valor padrao.
     */
    public static <T> T orElse(Optional<T> optional, T defaultValue) {
        return optional.orElse(defaultValue);
    }
}
 