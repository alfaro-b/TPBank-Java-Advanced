package fr.fms.bank;

import java.util.Scanner;

import fr.fms.dao.BankAccountDao;
import fr.fms.entities.BankAccount;
import fr.fms.exception.InsufficientBalanceException;

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
				scanner.nextLine();
	
				switch (choice) {
				case 1:
					// Créer un compte bancaire
					System.out.println("Saisissez le numéro du compte bancaire à créer: (Format FR-XXXX-XXXX)");
					String accountNumberToCreate = scanner.nextLine();
					if (!BankAccount.isValidAccountNumber(accountNumberToCreate)) {
						System.out.println(
								"Numéro invalide. Format attendu : FR-XXXX-XXXX");
						break;
					}
					
					System.out.println("Saisissez le prénom et le nom du titulaire du compte");
					String holderToCreate = scanner.nextLine();
					
					BankAccount bankAccountCreated =
							new BankAccount(accountNumberToCreate, holderToCreate, 0);

					dao.create(bankAccountCreated);
					System.out.println(bankAccountCreated);
					break;
	
				case 2:
					// Afficher tous les comptes
					dao.readAll()
						.stream()
						.forEach(account -> System.out.println(account));
					break;
	
				case 3:
					// Consulter un compte
					System.out.println("Saisissez le numéro du compte bancaire que vous voulez consulter: (Format FR-XXXX-XXXX)");
					String accountNumberToRead = scanner.nextLine();
					
					if (!BankAccount.isValidAccountNumber(accountNumberToRead)) {
						System.out.println("Numéro invalide. Format attendu : FR-XXXX-XXXX");
						break;
					}
					BankAccount bankAccountToRead = dao.read(accountNumberToRead);

					if (bankAccountToRead == null) {
						System.out.println("Aucun compte bancaire trouvé.");
						break;
					}

					System.out.println(bankAccountToRead);

					break;
	
				case 4:
					// Déposer de l'argent sur un compte
					System.out.println("Saisissez le numéro du compte bancaire sur lequel vous voulez effectuer un dépôt : (Format FR-XXXX-XXXX)");
					String accountNumberToDeposit = scanner.nextLine();
					
					if (!BankAccount.isValidAccountNumber(accountNumberToDeposit)) {
						System.out.println("Numéro invalide. Format attendu : FR-XXXX-XXXX");
						break;
					}
					BankAccount bankAccountToDeposit = dao.read(accountNumberToDeposit);

					if (bankAccountToDeposit == null) {
						System.out.println("Aucun compte bancaire trouvé.");
						break;
					}
					
					System.out.println("Saisissez le montant du dépôt : ");
					double amount = scanner.nextDouble();
					scanner.nextLine();
					
					System.out.println("Avant dépôt : " + bankAccountToDeposit);
					try {
					bankAccountToDeposit.deposit(amount);
					dao.update(bankAccountToDeposit);
					System.out.println("Après dépôt : " + dao.read(bankAccountToDeposit.getAccountNumber()));
					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
					}
					break;
	
				case 5:
					// Retirer de l'argent sur un compte
					System.out.println(
							"Saisissez le numéro du compte bancaire sur lequel vous voulez effectuer un retrait : (Format FR-XXXX-XXXX)");

					String accountNumberToWithdraw = scanner.nextLine();

					if (!BankAccount.isValidAccountNumber(accountNumberToWithdraw)) {
						System.out.println("Numéro invalide. Format attendu : FR-XXXX-XXXX");
						break;
					}

					BankAccount bankAccountToWithdraw = dao.read(accountNumberToWithdraw);

					if (bankAccountToWithdraw == null) {
						System.out.println("Aucun compte bancaire trouvé.");
						break;
					}

					System.out.println("Saisissez le montant du retrait : ");
					double amountToWithdraw = scanner.nextDouble();
					scanner.nextLine();

					System.out.println("Avant retrait : " + bankAccountToWithdraw);

					try {
						bankAccountToWithdraw.withdraw(amountToWithdraw);

						dao.update(bankAccountToWithdraw);

						System.out.println(
								"Après retrait : "
								+ dao.read(bankAccountToWithdraw.getAccountNumber()));

					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());

					} catch (InsufficientBalanceException e) {
						System.out.println(e.getMessage());
					}

					break;
	
				case 6:
					// Effectuer un virement entre deux comptes
					System.out.println(
							"Saisissez le numéro du compte source : (Format FR-XXXX-XXXX)");

					String sourceAccountNumber = scanner.nextLine();

					if (!BankAccount.isValidAccountNumber(sourceAccountNumber)) {
						System.out.println("Numéro invalide. Format attendu : FR-XXXX-XXXX");
						break;
					}

					BankAccount sourceAccount = dao.read(sourceAccountNumber);

					if (sourceAccount == null) {
						System.out.println("Compte source introuvable.");
						break;
					}

					System.out.println(
							"Saisissez le numéro du compte destinataire : (Format FR-XXXX-XXXX)");

					String destinationAccountNumber = scanner.nextLine();

					if (!BankAccount.isValidAccountNumber(destinationAccountNumber)) {
						System.out.println("Numéro invalide. Format attendu : FR-XXXX-XXXX");
						break;
					}

					BankAccount destinationAccount = dao.read(destinationAccountNumber);

					if (destinationAccount == null) {
						System.out.println("Compte destinataire introuvable.");
						break;
					}

					System.out.println("Saisissez le montant du virement : ");
					double transferAmount = scanner.nextDouble();
					scanner.nextLine();

					System.out.println("Avant virement :");
					System.out.println("Compte source : " + sourceAccount);
					System.out.println("Compte destinataire : " + destinationAccount);

					try {
						sourceAccount.transfer(destinationAccount, transferAmount);

						dao.update(sourceAccount);
						dao.update(destinationAccount);

						System.out.println("Après virement :");
						System.out.println("Compte source : "
								+ dao.read(sourceAccount.getAccountNumber()));
						System.out.println("Compte destinataire : "
								+ dao.read(destinationAccount.getAccountNumber()));

					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());

					} catch (InsufficientBalanceException e) {
						System.out.println(e.getMessage());
					}

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
