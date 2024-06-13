package pl.edu.pja.trainmate.core;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_METHOD;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static pl.edu.pja.trainmate.core.config.Profiles.INTEGRATION;
import static pl.edu.pja.trainmate.core.config.objectmapper.CustomObjectMapperConfig.createObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.edu.pja.trainmate.core.common.UserId;
import pl.edu.pja.trainmate.core.config.security.LoggedUserDataDto;
import pl.edu.pja.trainmate.core.config.security.LoggedUserDataProvider;
import pl.edu.pja.trainmate.core.config.security.RoleType;
import pl.edu.pja.trainmate.core.config.security.UserIdProvider;
import pl.edu.pja.trainmate.core.domain.user.querydsl.UserQueryService;

@ActiveProfiles(INTEGRATION)
@RunWith(SpringRunner.class)
@TestInstance(PER_METHOD)
public abstract class ControllerSpecification {

    public static final UserId EXISTING_USER_ID = UserId.valueOf("208d35f7-2c9a-447c-84f0-2566560dc78e");

    @MockBean
    UserIdProvider userIdProvider;

    @MockBean
    UserQueryService queryService;

    @SpyBean
    LoggedUserDataProvider loggedUserDataProvider;

    @Autowired
    protected MockMvc mockMvc;

    protected static final ObjectMapper objectMapper = createObjectMapper();

    @BeforeEach
    private void setup() {
        when(userIdProvider.getLoggedUserId()).thenReturn(EXISTING_USER_ID);
        doReturn(EXISTING_USER_ID).when(loggedUserDataProvider).getLoggedUserId();
    }

    public void userWithRole(RoleType roleType) {
        when(queryService.getUserByKeycloakId(anyString()))
            .thenReturn(new LoggedUserDataDto(EXISTING_USER_ID, roleType));
    }

    @SneakyThrows
    public MvcResult performGet(String url) {
        return mockMvc.perform(
                get(url)
            )
            .andReturn();
    }

    @SneakyThrows
    public MvcResult performPost(String url, Object body) {
        var json = objectMapper.writeValueAsString(body);
        return mockMvc.perform(
                post(url)
                    .contentType(APPLICATION_JSON)
                    .content(json)
                    .accept(APPLICATION_JSON)
            )
            .andReturn();
    }

    @SneakyThrows
    public MvcResult performPost(String url, String paramName, String paramvalue) {
        return mockMvc.perform(
                post(url)
                    .param(paramName, paramvalue)
                    .accept(APPLICATION_JSON)
            )
            .andReturn();
    }

    @SneakyThrows
    public MvcResult performPut(String url, Object body) {
        var json = objectMapper.writeValueAsString(body);
        return mockMvc.perform(
                put(url)
                    .contentType(APPLICATION_JSON)
                    .content(json)
                    .accept(APPLICATION_JSON)
            )
            .andReturn();
    }

    @SneakyThrows
    public MvcResult performDelete(String url, Object body) {
        var json = objectMapper.writeValueAsString(body);
        return mockMvc.perform(
                delete(url)
                    .contentType(APPLICATION_JSON)
                    .content(json)
                    .accept(APPLICATION_JSON)
            )
            .andReturn();
    }

    @SneakyThrows
    public MvcResult performDelete(String url) {
        return mockMvc.perform(
                delete(url)
            )
            .andReturn();
    }

}
