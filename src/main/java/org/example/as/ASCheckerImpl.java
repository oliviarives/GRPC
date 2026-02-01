package org.example.as;

import com.proto.auth.*;
import io.grpc.stub.StreamObserver;

public class ASCheckerImpl extends ASCheckerGrpc.ASCheckerImplBase {

    private final ListeAuth listeAuth;

    public ASCheckerImpl(ListeAuth listeAuth) {
        this.listeAuth = listeAuth;
    }

    @Override
    public void verifier(Identite request, StreamObserver<StatutPaire> responseObserver) {
        System.out.println("[Checker] Vérification demandée pour : " + request.getLogin());

        boolean isValide = listeAuth.tester(request.getLogin(), request.getMdp());

        StatutVerificationPaire statut = isValide ? StatutVerificationPaire.GOOD : StatutVerificationPaire.BAD;

        responseObserver.onNext(StatutPaire.newBuilder()
                .setStatut(statut)
                .build());
        responseObserver.onCompleted();
    }
}