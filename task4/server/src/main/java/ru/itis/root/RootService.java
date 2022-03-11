package ru.itis.root;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import ru.itis.root.RootCalculatorGrpc.RootCalculatorImplBase;

@Slf4j
public class RootService extends RootCalculatorImplBase {

  @Override
  public void calculateRoot(Number request, StreamObserver<Number> responseObserver) {
    log.debug("*** Unary implementation on server side ***");

    var value = request.getValue();
    log.debug("Request has been received on server side: number's value - {}", value);
    var result = Math.sqrt(value);
    var response = Number.newBuilder()
        .setValue(result)
        .build();
    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
