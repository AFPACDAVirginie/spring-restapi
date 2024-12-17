package fr.afpa.restapi.web.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fr.afpa.restapi.dao.AccountDao;
import fr.afpa.restapi.model.Account;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Ajouter la/les annotations nécessaires pour faire de "AccountRestController" un contrôleur de REST API
 */

@RestController
@RequestMapping("/accounts")
public class AccountRestController {
    private final AccountDao accountDao;
    /**
     * Implémenter un constructeur
     *
     * Injecter {@link AccountDao} en dépendance par injection via constructeur
     * Plus d'informations -> https://keyboardplaying.fr/blogue/2021/01/spring-injection-constructeur/
     */
	public AccountRestController(AccountDao accountDao) {
		this.accountDao = accountDao;
	}

    /**
     * Méthode pour récupérer tous les comptes via une requête GET
     * @return Liste des comptes
     */
    @GetMapping
    public List<Account> getAllAccounts() {
        return accountDao.findAll();
    }

    /**
     * Implémenter une méthode qui traite les requêtes GET avec un identifiant "variable de chemin" et qui retourne les informations du compte associé
     *  Méthode pour récupérer un compte par son ID via une requête GET
     *       @param id Identifiant du compte
     *       @return Informations du compte
     * Plus d'informations sur les variables de chemin -> https://www.baeldung.com/spring-pathvariable
     */
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") String id) {
        Optional<Account> account = accountDao.findById(Long.parseLong(id));
        if (account.isPresent()) {
            return new ResponseEntity<>(account.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Implémenter une méthode qui traite les requêtes POST
     * Cette méthode doit recevoir les informations d'un compte en tant que "request body", elle doit sauvegarder le compte en mémoire et retourner ses informations (en json)
     * Tutoriel intéressant https://stackabuse.com/get-http-post-body-in-spring/
     * Le serveur devrait retourner un code http de succès (201 Created)
     *  Méthode pour créer un nouveau compte via une requête POST
     *       @param account Données du compte à créer
     *       @return Le compte créé avec un code HTTP 201 Created
     **/
    @PostMapping
    public ResponseEntity<Account> postAccount(@RequestBody Account account) {
        Account savedAccount = accountDao.save(account);
        return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
    }

    /**
     * Implémenter une méthode qui traite les requêtes PUT
     *  Méthode pour mettre à jour un compte via une requête PUT
     *       @param account Données du compte à mettre à jour
     *       @return Le compte mis à jour
     */
    @PutMapping("/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable("id") String id, @RequestBody Account account) {
        Optional<Account> existingAccount = accountDao.findById(Long.parseLong(id));
        if (existingAccount.isPresent()) {
            account.setId(Long.valueOf(id));
            Account updatedAccount = accountDao.save(account);
            return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    /**
     * Implémenter une méthode qui traite les requêtes  DELETE
     *  Méthode pour supprimer un compte via une requête DELETE
     *       @param id Identifiant du compte à supprimer
     *       @return Réponse HTTP 204 No Content si la suppression est réussie
     * L'identifiant du compte devra être passé en "variable de chemin" (ou "path variable")
     * Dans le cas d'une suppression effectuée avec succès, le serveur doit retourner un statut http 204 (No content)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Account> deleteAccount(@PathVariable("id") String id) {
        Optional<Account> existingAccount = accountDao.findById(Long.parseLong(id));
        if (existingAccount.isPresent()) {
            accountDao.delete(existingAccount.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); //Retourne un code 404 si le compte n'existe pas
        }
    }
}
