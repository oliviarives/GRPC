package org.example;

import com.proto.auth.*;
import io.grpc.stub.StreamObserver;

public class AuthServiceImpl extends ServiceAuthentificationGrpc.ServiceAuthentificationImplBase {

    private final ListeAuth listeAuth = new ListeAuth();

    @Override
    public void verifier(Identite request, StreamObserver<StatutPaire> responseObserver) {
        boolean isValide = listeAuth.tester(request.getLogin(), request.getMdp());

        StatutVerificationPaire statut = isValide ? StatutVerificationPaire.GOOD : StatutVerificationPaire.BAD;

        StatutPaire reponse = StatutPaire.newBuilder()
                .setStatut(statut)
                .build();

        responseObserver.onNext(reponse);
        responseObserver.onCompleted();
    }

    @Override
    public void ajouter(Identite request, StreamObserver<StatutOpe> responseObserver) {
        boolean succes = listeAuth.creer(request.getLogin(), request.getMdp());
        renvoyerReponseOpe(succes, responseObserver);
    }

    @Override
    public void supprimer(Identite request, StreamObserver<StatutOpe> responseObserver) {
        boolean succes = listeAuth.supprimer(request.getLogin(), request.getMdp());
        renvoyerReponseOpe(succes, responseObserver);
    }

    @Override
    public void modifierMotDePasse(Identite request, StreamObserver<StatutOpe> responseObserver) {
        boolean succes = listeAuth.mettreAJour(request.getLogin(), request.getMdp());
        renvoyerReponseOpe(succes, responseObserver);
    }

    // Méthode utilitaire pour éviter la duplication de code
    private void renvoyerReponseOpe(boolean succes, StreamObserver<StatutOpe> responseObserver) {
        StatutVerificationOpe statut = succes ? StatutVerificationOpe.DONE : StatutVerificationOpe.ERROR;

        StatutOpe reponse = StatutOpe.newBuilder()
                .setStatut(statut)
                .build();

        responseObserver.onNext(reponse);
        responseObserver.onCompleted();
    }
}