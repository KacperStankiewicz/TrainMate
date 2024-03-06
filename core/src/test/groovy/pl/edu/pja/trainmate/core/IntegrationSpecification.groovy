package pl.edu.pja.trainmate.core

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestExecutionListeners
import org.springframework.test.web.servlet.MockMvc
import pl.edu.pja.trainmate.core.utils.DatabaseCleanupListener
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS

@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = MOCK, classes = [TestCoreApplication.class, IntegrationTestConfig.class])
@TestExecutionListeners(value = [DatabaseCleanupListener.class], mergeMode = MERGE_WITH_DEFAULTS)
@AutoConfigureMockMvc
abstract class IntegrationSpecification extends Specification {

    @Autowired
    MockMvc mockMvc;
}
