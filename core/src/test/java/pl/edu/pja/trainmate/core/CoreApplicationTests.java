package pl.edu.pja.trainmate.core;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CoreApplicationTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void contextLoads() throws Exception {
//        given:
        var url = "/actuator/health";

//        when:
        var response = mockMvc.perform(get(url));

//        then:
        response
            .andExpect(status().isOk())
            .andExpect(content().json("{\"status\":\"UP\"}"));

    }

}
