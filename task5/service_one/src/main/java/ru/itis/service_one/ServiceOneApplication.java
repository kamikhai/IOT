package ru.itis.service_one;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;

@EnableKafka
@SpringBootApplication
public class ServiceOneApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceOneApplication.class, args);
	}

	@Bean
	public HelloServiceImpl helloService(ApplicationContext context) {
		return new HelloServiceImpl(context.getBean(KafkaTemplate.class));
	}

	@Bean
	@SneakyThrows
	public Server server(ApplicationContext context) {
		Server server = ServerBuilder
				.forPort(8085)
				.addService(helloService(context)).build();

		server.start();
		server.awaitTermination();
		return server;
	}
}
