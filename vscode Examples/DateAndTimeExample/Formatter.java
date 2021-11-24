package DateAndTimeExample;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class Formatter {
    public static void main(String[] args) {
        var formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);
        System.out.println(formatter.withLocale(Locale.CHINA).format(ZonedDateTime.now()));
    }
    
}
