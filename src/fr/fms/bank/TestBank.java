package fr.fms.bank;

import fr.fms.dao.BankAccountDao;

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
		System.out.println(dao.read("FR-1111-2222"));
		
	}

}
