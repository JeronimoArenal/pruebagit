package es.proyecto.bookstore.config;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class DateFormatter implements Formatter<Date>  {

  @Autowired
  private MessageSource messageSource;
  
  public DateFormatter() {
    super();
  }

  public Date parse(final String text, final Locale locale) throws ParseException {
    final SimpleDateFormat dateFormat = createDateFormat(locale);
    return dateFormat.parse(text);
  }

  public String print(final Date date, final Locale locale) {
    final SimpleDateFormat dateFormat = createDateFormat(locale);
    return dateFormat.format(date);
  }

  private SimpleDateFormat createDateFormat(final Locale locale) {
    final String format = this.messageSource.getMessage("date.format", null, locale);
    final SimpleDateFormat dateFormat = new SimpleDateFormat(format);
    dateFormat.setLenient(false);
    return dateFormat;
  }

}
