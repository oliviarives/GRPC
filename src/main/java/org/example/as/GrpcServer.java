package org.example.as;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        ListeAuth database = new ListeAuth();

        Server serverManager = ServerBuilder.forPort(28415)
                .addService(new ASManagerImpl(database))
                .build();

        Server serverChecker = ServerBuilder.forPort(28414)
                .addService(new ASCheckerImpl(database))
                .build();

        serverManager.start();
        serverChecker.start();

        System.out.println("Serveur MANAGER démarré sur le port 28415");
        System.out.println("Serveur CHECKER démarré sur le port 28414");

        //Attente de l'arrêt
        serverManager.awaitTermination();
        serverChecker.awaitTermination();
    }
}