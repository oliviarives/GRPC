package org.example.log;

import com.proto.auth.Empty;
import com.proto.auth.LogRequest;
import com.proto.auth.LoggingServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import java.io.IOException;

public class LogServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(3244)
                .addService(new LoggingServiceImpl())
                .build();

        System.out.println("LOG SERVER démarré sur le port 3244");
        server.start();
        server.awaitTermination();
    }

    // impl du service
    static class LoggingServiceImpl extends LoggingServiceGrpc.LoggingServiceImplBase {
        @Override
        public void log(LogRequest request, StreamObserver<Empty> responseObserver) {
            System.out.println("LOG REÇU");
            System.out.println(" Date   : " + request.getHeure());
            System.out.println(" IP     : " + request.getClient());
            System.out.println(" Action : " + request.getReq());
            System.out.println(" User   : " + request.getLogin());
            System.out.println(" Statut : " + request.getResultat());

            // réponse vide
            responseObserver.onNext(Empty.newBuilder().build());
            responseObserver.onCompleted();
        }
    }
}