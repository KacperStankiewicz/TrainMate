package pl.edu.pja.trainmate.core

import org.junit.jupiter.api.Test

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class CoreApplicationIT extends IntegrationSpecification {

    @Test
    void contextLoads() throws Exception {
        given:
        var url = "/actuator/health";

        when:
        var response = mockMvc.perform(get(url));

        then:
        response
            .andExpect(status().isOk())
            .andExpect(content().json("{\"status\":\"UP\"}"));
    }
}
