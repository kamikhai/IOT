package ru.itis.fact;

import io.grpc.stub.StreamObserver;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import ru.itis.fact.FactCalculatorGrpc.FactCalculatorImplBase;

@Slf4j
public class FactService extends FactCalculatorImplBase {

  private static List<Long> primeFactors(long n) {
    List<Long> factors = new ArrayList<>();
    for (long i = 2; i <= n / i; i++) {
      while (n % i == 0) {
        factors.add(i);
        n /= i;
      }
    }
    if (n > 1) {
      factors.add(n);
    }
    return factors;
  }

  @Override
  public void calculateFact(Number request, StreamObserver<Number> responseObserver) {
    log.debug("*** Server streaming implementation on server side ***");

    long number = request.getValue();
    List<Long> factors = primeFactors(number);
    for (Long factor : factors) {
      Number response = Number.newBuilder()
          .setValue(factor)
          .build();
      responseObserver.onNext(response);
    }
    log.debug("factors for {} - {}", number, factors);
    responseObserver.onCompleted();
  }
}
