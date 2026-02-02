package org.example.clients;

import com.proto.auth.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class CheckerClient {
    public static void main(String[] args) {
        // connexion au port 28414
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 28414)
                .usePlaintext()
                .build();

        // on crée un stub "Checker"
        ASCheckerGrpc.ASCheckerBlockingStub stub = ASCheckerGrpc.newBlockingStub(channel);
        Scanner sc = new Scanner(System.in);

        System.out.println("CLIENT CHECKER");

        while (true) {
            System.out.println("\nEntrez un login à vérifier :");
            String login = sc.next();

            if (login.equalsIgnoreCase("exit")) break;

            System.out.println("Entrez le mot de passe :");
            String mdp = sc.next();

            // on créé la requête
            Identite identite = Identite.newBuilder()
                    .setLogin(login)
                    .setMdp(mdp)
                    .build();

            try {
                StatutPaire reponse = stub.verifier(identite);
                System.out.println("Résultat du serveur : " + reponse.getStatut());
            } catch (Exception e) {
                System.err.println("Erreur : Impossible de contacter le serveur \nVérifiez le port 28414");
            }
        }

        channel.shutdown();
        sc.close();
    }
}