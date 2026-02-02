============================================================
TP APPLICATIONS RÉPARTIES – RAuth GRPC
============================================================

Auteurs :
- Olivia RIVES - 22109912
- Mathis PILON - 22204682

Version : 2.0 +  service de journalisation fonctionnel avec un client AS test

Groupe : TP1
Date   : 02.02.2025


A noter :
Il est nécessaire de faire un mvn clean compile pour pouvoir exécuter

Le code est organisé en packages dans src/main/java/org/example :

1. org.example.as : service d'authentification
   - GrpcServer.java
   - ListeAuth.java
   - ASManagerImpl.java
   - ASCheckerImpl.java

2. org.example.log : service de journalisation
   - LogServer.java

3. org.example.clients : clients de test
   - ManagerClient.java : port 28415
   - CheckerClient.java : port 28414



