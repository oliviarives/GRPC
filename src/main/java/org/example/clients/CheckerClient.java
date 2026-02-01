package org.example.clients;

import com.proto.auth.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class CheckerClient {
    public static void main(String[] args) {
        // Connexion au port 28414 (Port dédié au Checker)
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 28414)
                .usePlaintext()
                .build();

        // On crée un stub "Checker" (lecture seule)
        ASCheckerGrpc.ASCheckerBlockingStub stub = ASCheckerGrpc.newBlockingStub(channel);
        Scanner sc = new Scanner(System.in);

        System.out.println("=== CLIENT CHECKER (Port 28414) ===");

        while (true) {
            System.out.println("\nEntrez un login à vérifier (ou 'exit' pour quitter) :");
            String login = sc.next();

            if (login.equalsIgnoreCase("exit")) break;

            System.out.println("Entrez le mot de passe :");
            String mdp = sc.next();

            // Création de la requête
            Identite identite = Identite.newBuilder()
                    .setLogin(login)
                    .setMdp(mdp)
                    .build();

            // Appel gRPC
            try {
                StatutPaire reponse = stub.verifier(identite);
                System.out.println(">>> Résultat du serveur : " + reponse.getStatut());
            } catch (Exception e) {
                System.err.println("Erreur : Impossible de contacter le serveur (Vérifiez le port 28414)");
            }
        }

        channel.shutdown();
        sc.close();
    }
}