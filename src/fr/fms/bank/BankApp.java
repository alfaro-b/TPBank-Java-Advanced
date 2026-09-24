package fr.fms.bank;

import java.util.Scanner;

import fr.fms.dao.BankAccountDao;

/**
 * Application console permettant à un conseiller bancaire de gérer les comptes clients.
 */
public class BankApp {

	public static void main(String[] args) {

		// Chargement du driver MariaDB
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// Création du DAO
		BankAccountDao dao = new BankAccountDao();
		
		// Try-with-ressources
		try(Scanner scanner = new Scanner(System.in)) {

			int choice;
	
			do {
				// Menu principal de l'application
				System.out.println("\n===== GESTION BANCAIRE =====");
				System.out.println("1 - Créer un compte");
				System.out.println("2 - Afficher les comptes");
				System.out.println("3 - Consulter un compte");
				System.out.println("4 - Effectuer un dépôt");
				System.out.println("5 - Effectuer un retrait");
				System.out.println("6 - Effectuer un virement");
				System.out.println("0 - Quitter");
				System.out.print("Votre choix : ");
	
				choice = scanner.nextInt();
	
				switch (choice) {
				case 1:
					// création
					break;
	
				case 2:
					dao.readAll()
						.stream()
						.forEach(account -> System.out.println(account));
					break;
	
				case 3:
					// consultation
					break;
	
				case 4:
					// dépôt
					break;
	
				case 5:
					// retrait
					break;
	
				case 6:
					// virement
					break;
	
				case 0:
					System.out.println("Au revoir.");
					break;
	
				default:
					System.out.println("Choix invalide.");
				}
	
			} while (choice != 0);
		}
	}
}
