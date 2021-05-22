package holik.hotel.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import java.util.Locale;

@SpringBootApplication
public class HotelUsingSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelUsingSpringApplication.class, args);
	}
}
