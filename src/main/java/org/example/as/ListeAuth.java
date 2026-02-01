package org.example.as;

import java.util.HashMap;
import java.util.Scanner;

/**
 * GÃ¨re une Liste d'Authentification avec persistance dans une BD H2
 * @author torguet
 *
 */
public class ListeAuth {

    /**
     * HashMap pour stocker les couples login, mot de passe
     */
    private HashMap<String, String> authEntries;

    /**
     * constructeur
     */
    public ListeAuth() {
        authEntries = new HashMap<>();
        // ajoute des entrÃ©es de test
        authEntries.put("Toto", "Toto");
        authEntries.put("Titi", "Titi");
        authEntries.put("Tata", "Tata");
        authEntries.put("Tutu", "Tutu");
    }

    /**
     * creation d'un couple (login, mot de passe)
     * @param login : le login
     * @param passwd : le mot de passe
     * @return true si Ã§a c'est bien passÃ©
     */
    public synchronized boolean creer(String login, String passwd) {
        if(authEntries.containsKey(login))
            return false; // le login est dÃ©jÃ  prÃ©sent
        authEntries.put(login, passwd); // on l'ajoute
        return true; // Ã§a c'est bien passÃ©
    }

    /**
     *  mise Ã  jour d'un couple (login, mot de passe)
     * @param login : le login
     * @param passwd : le mot de passe
     * @return true si Ã§a c'est bien passÃ©
     */
    public synchronized boolean mettreAJour(String login, String passwd) {
        if(!authEntries.containsKey(login))
            return false; // le login n'est pas prÃ©sent
        authEntries.put(login, passwd); // on remplace le mot de passe
        return true; // Ã§a c'est bien passÃ©
    }

    /**
     *  suppression d'un couple (login, mot de passe)
     * @param login : le login
     * @param passwd : le mot de passe
     * @return true si Ã§a c'est bien passÃ©
     */

    public synchronized boolean supprimer(String login, String passwd) {
        if(!tester(login, passwd))
            return false; // le login ou le mot de passe ne sont pas corrects
        authEntries.remove(login); // on supprime le couple
        return true; // Ã§a c'est bien passÃ©
    }

    /**
     *  test d'un couple (login, mot de passe)
     * @param login : le login
     * @param passwd : le mot de passe
     * @return true si Ã§a c'est bien passÃ©
     */
    public synchronized boolean tester(String login, String passwd) {
        if(!authEntries.containsKey(login))
            return false; // le login n'est pas prÃ©sent
        if (authEntries.get(login).equals(passwd))
            return true; // le mot de passe est correct
        return false; // le mot de passe n'est pas correct
    }


    /**
     * Programme de test
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        ListeAuth la = new ListeAuth();
        Scanner sc = new Scanner(System.in);

        while (true) {
            int choix;
            String login;
            String passwd;

            System.out.println("+---------------------------------+");
            System.out.println("| 1 - creer une paire             |");
            System.out.println("| 2 - tester une paire            |");
            System.out.println("| 3 - mettre Ã  jour une paire     |");
            System.out.println("| 4 - supprimer une paire         |");
            System.out.println("| 0 - arreter                     |");
            System.out.println("+---------------------------------+");

            choix = sc.nextInt();
            sc.nextLine(); // saute le retour Ã  la ligne

            switch (choix) {
                case 0:
                    sc.close();
                    System.exit(0);
                case 1:
                    System.out.println("Tapez le login");
                    login = sc.next();
                    sc.nextLine(); // saute le retour Ã  la ligne
                    System.out.println("Tapez le mot de passe");
                    passwd = sc.next();
                    sc.nextLine(); // saute le retour Ã  la ligne
                    if (!la.creer(login, passwd))
                        System.out.println("La paire existe deja!");
                    else
                        System.out.println("Creation effectuee.");
                    break;
                case 2:
                    System.out.println("Tapez le login");
                    login = sc.next();
                    sc.nextLine(); // saute le retour Ã  la ligne
                    System.out.println("Tapez le mot de passe");
                    passwd = sc.next();
                    if (la.tester(login, passwd))
                        System.out.println("ValidÃ©");
                    else
                        System.out.println("Erreur d'authentification");
                    break;
                case 3:
                    System.out.println("Tapez le login");
                    login = sc.next();
                    sc.nextLine(); // saute le retour Ã  la ligne
                    System.out.println("Tapez le mot de passe");
                    passwd = sc.next();
                    if(!la.mettreAJour(login, passwd))
                        System.out.println("La paire n'existe pas!");
                    else
                        System.out.println("MAJ effectue.");
                    break;
                case 4:
                    System.out.println("Tapez le login");
                    login = sc.next();
                    sc.nextLine(); // saute le retour Ã  la ligne
                    System.out.println("Tapez le mot de passe");
                    passwd = sc.next();
                    if (!la.supprimer(login, passwd))
                        System.out.println("La paire n'existe pas!");
                    else
                        System.out.println("Retrait effectue.");
                    break;
            }
        }
    }
}