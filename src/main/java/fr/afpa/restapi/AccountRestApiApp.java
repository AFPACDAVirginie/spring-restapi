package fr.afpa.restapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale du projet.
 * Ajouter l'annotation @SpringBootApplication à la classe
 * 
 * Cette annotation est FONDAMENTALE pour activer la recherche automatique des beans dans les différents packages de l'
 * 
 * Documentation -> https://medium.com/@boris.alexandre.rose/springbootapplication-ab08032a7049
 * 
 */
@SpringBootApplication
public class AccountRestApiApp {
    public static void main(String[] args) {
        SpringApplication.run(AccountRestApiApp.class, args);
    }
}
