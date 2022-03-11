package ru.itis;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.itis.fact.FactService;
import ru.itis.max.MaxService;
import ru.itis.root.RootService;
import ru.itis.std.StdService;

@Slf4j
public class Main {

  @SneakyThrows
  public static void main(String[] args) {
    log.debug("<<< Server is starting >>>");

    Server server = ServerBuilder.forPort(5051)
        .addService(new RootService())
        .addService(new StdService())
        .addService(new FactService())
        .addService(new MaxService())
        .build();
    server.start();
    log.debug("<<< Server started >>>");

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      log.debug("Received Shutdown Request");
      server.shutdown();
      log.debug("Successfully stopped the server");
    }));

    server.awaitTermination();
  }
}