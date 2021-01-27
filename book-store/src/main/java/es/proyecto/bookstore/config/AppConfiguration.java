package es.proyecto.bookstore.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfiguration implements WebMvcConfigurer {


  @Autowired
  DateFormatter dateFormatter;

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addFormatter(dateFormatter);
  }

}
