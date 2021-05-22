package holik.hotel.spring;

import holik.hotel.spring.web.controller.HomeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class HotelUsingSpringApplicationTests {

	@Autowired
	private HomeController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
