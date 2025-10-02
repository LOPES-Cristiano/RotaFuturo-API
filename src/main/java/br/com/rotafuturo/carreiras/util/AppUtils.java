package br.com.rotafuturo.carreiras.util;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.regex.Pattern;
public final class AppUtils {
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$");
	private AppUtils() {
	}
	public static LocalDate getCurrentDate() {
		return LocalDate.now();
	}
	public static LocalTime getCurrentTime() {
		return LocalTime.now();
	}
	public static boolean isNotNullOrEmpty(String str) {
		return str != null && !str.trim().isEmpty();
	}
	public static boolean isValidEmail(String email) {
		return isNotNullOrEmpty(email) && EMAIL_PATTERN.matcher(email).matches();
	}
	public static String normalizeString(String str) {
		if (!isNotNullOrEmpty(str)) {
			return "";
		}
		String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
		return normalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "").toLowerCase();
	}
	public static <T> T orElse(Optional<T> optional, T defaultValue) {
		return optional.orElse(defaultValue);
	}
}
