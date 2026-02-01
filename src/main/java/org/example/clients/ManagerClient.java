package org.example.clients;

import com.proto.auth.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Scanner;

public class ManagerClient {
    public static void main(String[] args) {
        // Connexion au port 28415 (utilisé pour le Manager)
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 28415)
                .usePlaintext()
                .build();

        // On crée un stub "Manager"
        ASManagerGrpc.ASManagerBlockingStub stub = ASManagerGrpc.newBlockingStub(channel);
        Scanner sc = new Scanner(System.in);

        System.out.println("=== CLIENT MANAGER (Port 28415) ===");

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Vérifier un utilisateur");
            System.out.println("2. Ajouter un utilisateur");
            System.out.println("3. Modifier un mot de passe");
            System.out.println("4. Supprimer un utilisateur");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");

            int choix = sc.nextInt();
            if (choix == 0) break;

            System.out.print("Login : ");
            String login = sc.next();
            System.out.print("Mot de passe : ");
            String mdp = sc.next();

            Identite id = Identite.newBuilder().setLogin(login).setMdp(mdp).build();

            switch (choix) {
                case 1: // CHECK
                    StatutPaire resCheck = stub.verifier(id);
                    System.out.println(">>> Résultat : " + resCheck.getStatut());
                    break;
                case 2: // ADD
                    StatutOpe resAdd = stub.ajouter(id);
                    System.out.println(">>> Ajout : " + resAdd.getStatut());
                    break;
                case 3: // MOD
                    StatutOpe resMod = stub.modifierMotDePasse(id);
                    System.out.println(">>> Modif : " + resMod.getStatut());
                    break;
                case 4: // DEL
                    StatutOpe resDel = stub.supprimer(id);
                    System.out.println(">>> Suppression : " + resDel.getStatut());
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
        channel.shutdown();
        sc.close();
    }
}