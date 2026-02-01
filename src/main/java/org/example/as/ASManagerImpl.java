package org.example.as;

import com.proto.auth.*;
import io.grpc.stub.StreamObserver;

public class ASManagerImpl extends ASManagerGrpc.ASManagerImplBase {

    private final ListeAuth listeAuth;

    public ASManagerImpl(ListeAuth listeAuth) {
        this.listeAuth = listeAuth;
    }

    @Override
    public void verifier(Identite request, StreamObserver<StatutPaire> responseObserver) {
        // Le manager peut aussi vérifier
        boolean isValide = listeAuth.tester(request.getLogin(), request.getMdp());

        responseObserver.onNext(StatutPaire.newBuilder()
                .setStatut(isValide ? StatutVerificationPaire.GOOD : StatutVerificationPaire.BAD)
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void ajouter(Identite request, StreamObserver<StatutOpe> responseObserver) {
        System.out.println("[Manager] Ajout demandé : " + request.getLogin());
        boolean succes = listeAuth.creer(request.getLogin(), request.getMdp());
        renvoyerReponse(succes, responseObserver);
    }

    @Override
    public void modifierMotDePasse(Identite request, StreamObserver<StatutOpe> responseObserver) {
        System.out.println("[Manager] Modif demandée : " + request.getLogin());
        boolean succes = listeAuth.mettreAJour(request.getLogin(), request.getMdp());
        renvoyerReponse(succes, responseObserver);
    }

    @Override
    public void supprimer(Identite request, StreamObserver<StatutOpe> responseObserver) {
        System.out.println("[Manager] Suppression demandée : " + request.getLogin());
        boolean succes = listeAuth.supprimer(request.getLogin(), request.getMdp());
        renvoyerReponse(succes, responseObserver);
    }

    // Méthode privée pour éviter la répétition de code
    private void renvoyerReponse(boolean succes, StreamObserver<StatutOpe> responseObserver) {
        StatutVerificationOpe statut = succes ? StatutVerificationOpe.DONE : StatutVerificationOpe.ERROR;

        responseObserver.onNext(StatutOpe.newBuilder()
                .setStatut(statut)
                .build());
        responseObserver.onCompleted();
    }
}