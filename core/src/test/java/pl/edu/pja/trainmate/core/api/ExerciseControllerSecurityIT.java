package pl.edu.pja.trainmate.core.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class ExerciseControllerSecurityIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void searchExercisesByCriteria() throws Exception {
        this.mockMvc.perform(post("/exercise/search"))
            .andDo(print())
            .andExpect(status().isForbidden());
    }
}