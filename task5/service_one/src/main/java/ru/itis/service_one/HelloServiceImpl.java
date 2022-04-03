package ru.itis.service_one;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import ru.itis.gateway.HelloServiceGrpc;
import ru.itis.gateway.HelloServiceOuterClass;

@RequiredArgsConstructor
public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

	private final KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String msg) {
		kafkaTemplate.send("msg", msg);
	}

	@Override
	public void hello(HelloServiceOuterClass.HelloRequest request, StreamObserver<HelloServiceOuterClass.HelloResponse> responseObserver) {

		String greeting = "Hello, " + request.getName();
		System.out.println(greeting);
		sendMessage(greeting);

		var response = HelloServiceOuterClass.HelloResponse.newBuilder()
				.setMessage(greeting)
				.build();

		responseObserver.onNext(response);
		responseObserver.onCompleted();
	}

}
