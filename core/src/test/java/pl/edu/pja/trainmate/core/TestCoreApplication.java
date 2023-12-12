package pl.edu.pja.trainmate.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestCoreApplication {

//	@Bean
//	@ServiceConnection
//	PostgreSQLContainer<?> postgresContainer() {
//		return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
//	}
//
//	@Bean
//	@ServiceConnection
//	RabbitMQContainer rabbitContainer() {
//		return new RabbitMQContainer(DockerImageName.parse("rabbitmq:latest"));
//	}

    public static void main(String[] args) {
        SpringApplication.from(CoreApplication::main).with(TestCoreApplication.class).run(args);
    }

}
