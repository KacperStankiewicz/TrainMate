package pl.edu.pja.trainmate.core.utils

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.test.context.TestContext
import org.springframework.test.context.TestExecutionListener

@Component
class DatabaseCleanupListener implements TestExecutionListener {

    @Autowired
    DatabaseCleanupService cleanupService;

    void beforeTestClass(TestContext testContext) {
        testContext.getApplicationContext()
            .getAutowireCapableBeanFactory()
            .autowireBean(this)
    }

    void afterTestMethod(TestContext testContext) throws Exception {
        cleanupDatabase()
    }

    private void cleanupDatabase() {
        cleanupService.cleanupDatabase()
    }
}
