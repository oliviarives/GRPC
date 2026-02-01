package org.example;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        // on lance le serveur sur le port 50051
        Server server = ServerBuilder.forPort(50051)
                .addService(new AuthServiceImpl()) // on attache notre service au port
                .build();

        System.out.println("Démarrage du serveur gRPC sur le port 50051...");
        server.start();

        server.awaitTermination();
    }
}