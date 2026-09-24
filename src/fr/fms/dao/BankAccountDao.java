package fr.fms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.fms.entities.BankAccount;

public class BankAccountDao {
	// =========================
	// PARAMETRES DE CONNEXION
	// =========================

	private String url = "jdbc:mariadb://localhost:3306/bank";
	private String login = "root";
	private String password = "";


	// =========================
	// CONNEXION
	// =========================

	/**
	 * Ouvre une connexion vers la base de données Bank.
	 *
	 * @return connexion à la base de données
	 * @throws SQLException si la connexion échoue
	 */
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, login, password);
	}
	
	// =========================
	// READ ALL
	// =========================

	/**
	 * Récupère tous les comptes présents dans la base de données.
	 *
	 * @return liste contenant tous les comptes bancaires
	 */
	public ArrayList<BankAccount> readAll() {

		ArrayList<BankAccount> bankAccounts = new ArrayList<>();

		String sql = "SELECT * FROM bank_account";

		try (
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery()
		) {

			while (resultSet.next()) {

				String accountNumber = resultSet.getString("AccountNumber");
				String holder = resultSet.getString("Holder");
				double balance = resultSet.getDouble("Balance");

				BankAccount bankAccount =
						new BankAccount(accountNumber, holder, balance);

				bankAccounts.add(bankAccount);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bankAccounts;
	}
}
