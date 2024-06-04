package pl.edu.pja.trainmate.core.api.user;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static pl.edu.pja.trainmate.core.api.user.UserEndpoints.GET_USER_INFO;
import static pl.edu.pja.trainmate.core.config.security.RoleType.PERSONAL_TRAINER;
import static pl.edu.pja.trainmate.core.config.security.RoleType.TRAINED_PERSON;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import pl.edu.pja.trainmate.core.ControllerSpecification;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerSecurityIT extends ControllerSpecification {

    @Test
    void shouldAllowAccessForTrainedPersonWhenGettingUserInfo() {
        userWithRole(TRAINED_PERSON);
        var response = performGet(GET_USER_INFO);
        assertNotEquals(FORBIDDEN, HttpStatus.valueOf(response.getResponse().getStatus()));
    }

    @Test
    void shouldAllowAccessForPersonalTrainerWhenGettingUserInfo() {
        userWithRole(PERSONAL_TRAINER);
        var response = performGet(GET_USER_INFO);
        assertNotEquals(FORBIDDEN, HttpStatus.valueOf(response.getResponse().getStatus()));
    }
}