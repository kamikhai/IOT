package ru.itis.max;

import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import ru.itis.max.MaxCalculatorGrpc.MaxCalculatorImplBase;

@Slf4j
public class MaxService extends MaxCalculatorImplBase {

  @Override
  public StreamObserver<Number> calculateMax(StreamObserver<Number> responseObserver) {
    log.debug("*** Bi directional streaming implementation on server side ***");
    return new StreamObserver<>() {

      private Long currentMax = null;

      @Override
      public void onNext(Number number) {

        long currentValue = number.getValue();
        if (currentMax == null) {
          currentMax = currentValue;
        } else {
          currentMax = Math.max(currentValue, currentMax);
        }
        Number response = Number.newBuilder().setValue(currentMax).build();

        log.debug("Current max - {}", currentMax);
        responseObserver.onNext(response);
      }

      @Override
      public void onError(Throwable throwable) {
        // not needed
      }

      @Override
      public void onCompleted() {
        log.debug("close bi directional streaming");
        responseObserver.onCompleted();
      }
    };
  }
}
