package org.example.clients;

import com.proto.auth.LogRequest;
import com.proto.auth.LoggingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.time.LocalDateTime;

public class TestLogClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 3244)
                .usePlaintext()
                .build();

        LoggingServiceGrpc.LoggingServiceBlockingStub stub = LoggingServiceGrpc.newBlockingStub(channel);

        System.out.println("Envoi d'un log de test...");

        // on créé un faux log
        LogRequest log = LogRequest.newBuilder()
                .setReq("TEST_ADD")
                .setLogin("toto")
                .setResultat("DONE")
                .setClient("192.168.1.55")
                .setHeure(LocalDateTime.now().toString())
                .build();

        // appel rpc
        try {
            stub.log(log);
            System.out.println("Log envoyé avec succès !");
        } catch (Exception e) {
            System.err.println("Erreur : Le serveur de log est-il allumé ?");
        }

        channel.shutdown();
    }
}