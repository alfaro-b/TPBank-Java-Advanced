package fr.fms.bank;

import fr.fms.dao.BankAccountDao;

public class TestBank {

	public static void main(String[] args) {
		
		// Chargement du driver MariaDB
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		BankAccountDao dao = new BankAccountDao();

		dao.readAll().forEach(System.out::println);

	}

}
