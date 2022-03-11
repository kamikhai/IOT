package ru.itis.std;

import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import ru.itis.std.StdCalculatorGrpc.StdCalculatorImplBase;

@Slf4j
public class StdService extends StdCalculatorImplBase {

  private static final StandardDeviation standardDeviation = new StandardDeviation();

  @Override
  public StreamObserver<Number> calculateStd(StreamObserver<Number> responseObserver) {
    log.debug("*** Client streaming implementation on server side ***");

    return new StreamObserver<>() {
      private static final List<Double> numbers = new ArrayList<>();

      @Override
      public void onNext(Number number) {
        double currentValue = number.getValue();
        log.debug("Number with value {} is processed", currentValue);
        numbers.add(currentValue);
      }

      @Override
      public void onError(Throwable throwable) {
        // not needed
      }

      @Override
      public void onCompleted() {
        double standardDeviationResult = standardDeviation.evaluate(
            numbers.stream().mapToDouble(Double::doubleValue).toArray());

        Number response = Number.newBuilder()
            .setValue(standardDeviationResult)
            .build();

        responseObserver.onNext(response);
        log.debug("Send std result: - {}", standardDeviationResult);
        responseObserver.onCompleted();
      }
    };
  }

}
