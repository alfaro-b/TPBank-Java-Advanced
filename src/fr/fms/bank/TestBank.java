package fr.fms.bank;

import fr.fms.dao.BankAccountDao;
import fr.fms.entities.BankAccount;
import fr.fms.exception.InsufficientBalanceException;

/**
 * Classe permettant de tester les différentes opérations réalisées sur les comptes bancaires avec BankAccountDao.
 */
public class TestBank {

	public static void main(String[] args) {
		
		// Chargement du driver MariaDB
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		// Création du DAO
		BankAccountDao dao = new BankAccountDao();

		// =========================
		// READ ALL
		// =========================
		
		System.out.println("----- LISTE DES COMPTES BANCAIRES -----");
		dao.readAll().forEach(System.out::println);

		// =========================
		// READ
		// =========================

		System.out.println("\n----- READ -----");
		// Un compte valide
		System.out.println(dao.read("FR-1111-2222"));
		// Un compte qui n'existe pas
		System.out.println(dao.read("FR-0000-2222"));
		
		// =========================
		// CREATE
		// =========================

//		System.out.println("\n----- INSERT -----");
//
//		BankAccount bankAccountCreated =
//				new BankAccount("FR-4321-8765", "Sophie Desgran", 3000.00);
//
//		dao.create(bankAccountCreated);
//		System.out.println(bankAccountCreated);
		
		// =========================
		// DEPOT D'ARGENT
		// =========================
		
//		System.out.println("\n----- DEPOSIT -----");
//		BankAccount bankAccountToDeposit = dao.read("FR-1111-2222");
//		System.out.println("Avant dépôt : " + bankAccountToDeposit);
//		bankAccountToDeposit.deposit(200);
//		dao.update(bankAccountToDeposit);
//		System.out.println("Après dépôt : " + dao.read(bankAccountToDeposit.getAccountNumber()));
		
		// =========================
		// RETRAIT D'ARGENT
		// =========================
		
		System.out.println("\n----- WITHDRAW -----");
		BankAccount bankAccountToWithdraw = dao.read("FR-1111-2222");
		System.out.println("Avant retrait : " + bankAccountToWithdraw);
		try {
			bankAccountToWithdraw.withdraw(200);
			dao.update(bankAccountToWithdraw);
		} catch (InsufficientBalanceException e) {
			System.out.println(e.getMessage());
		}
		
		System.out.println("Après retrait : " + dao.read(bankAccountToWithdraw.getAccountNumber()));

	}

}
