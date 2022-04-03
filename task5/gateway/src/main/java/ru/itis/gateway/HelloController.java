package ru.itis.gateway;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@PostMapping
	public void sendMessage(@RequestBody HelloRequestDto message) {
		ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8085)
				.usePlaintext()
				.build();

		HelloServiceGrpc.HelloServiceBlockingStub stub = HelloServiceGrpc.newBlockingStub(channel);

		HelloServiceOuterClass.HelloResponse helloResponse = stub.hello(HelloServiceOuterClass.HelloRequest.newBuilder().setName(message.getName()).build());

		channel.shutdown();
	}

}
