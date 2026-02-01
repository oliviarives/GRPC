package org.example.clients;

import com.proto.auth.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GrpcClient {
    public static void main(String[] args) {
        // on créée le canal de communication vers localhost:28414
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 28414)
                .usePlaintext()
                .build();

        // on créée le stub côté client
        ServiceAuthentificationGrpc.ServiceAuthentificationBlockingStub stub =
                ServiceAuthentificationGrpc.newBlockingStub(channel);

        System.out.println("Test 1: verif identite inexistant");
        Identite user = Identite.newBuilder().setLogin("Toto").setMdp("pass").build();
        StatutPaire reponse = stub.verifier(user);
        System.out.println("resultat : " + reponse.getStatut()); // BAD

        System.out.println("Test 2: creation Toto");
        StatutOpe reponseAjout = stub.ajouter(user);
        System.out.println("Ajout : " + reponseAjout.getStatut()); // DONE

        System.out.println("Test 3: verif Toto");
        reponse = stub.verifier(user);
        System.out.println("resultat : " + reponse.getStatut()); // GOOD

        channel.shutdown();
    }
}