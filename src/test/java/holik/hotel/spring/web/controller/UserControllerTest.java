package holik.hotel.spring.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void registerForm() throws Exception {
        mockMvc.perform(get("/signup").accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "text/html;charset=UTF-8"));
    }

    @Test
    public void viewSignInPage() throws Exception {
        mockMvc.perform(get("/signin").accept(MediaType.ALL))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "text/html;charset=UTF-8"));
    }
}