package org.example.as;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class GrpcServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        // Instance unique de la base de données partagée entre les deux services
        ListeAuth database = new ListeAuth();

        // 1. Démarrage du Serveur MANAGER (Port 28415)
        // Il utilise la classe ASManagerImpl
        Server serverManager = ServerBuilder.forPort(28415)
                .addService(new ASManagerImpl(database))
                .build();

        // 2. Démarrage du Serveur CHECKER (Port 28414)
        // Il utilise la classe ASCheckerImpl
        Server serverChecker = ServerBuilder.forPort(28414)
                .addService(new ASCheckerImpl(database))
                .build();

        serverManager.start();
        serverChecker.start();

        System.out.println("Serveur MANAGER démarré sur le port 28415");
        System.out.println("Serveur CHECKER démarré sur le port 28414");

        // Attente de l'arrêt
        serverManager.awaitTermination();
        serverChecker.awaitTermination();
    }
}