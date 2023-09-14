import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
	public static String getNow() {
		LocalDateTime now = LocalDateTime.now();
		String date = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
		return date;
	}
}
