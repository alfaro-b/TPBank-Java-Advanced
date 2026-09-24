package fr.fms.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import fr.fms.entities.BankAccount;

/**
 * DAO permettant d'effectuer les opérations CRUD sur les comptes bancaires de la base de données Bank.
 */
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
	
	// =========================
	// READ
	// =========================

	/**
	 * Recherche un compte bancaire grâce à son numéro de compte.
	 *
	 * @param accountNumber numéro du compte recherché
	 * @return bankAccount correspondant au numéro de compte,
	 *         ou null si aucun compte n'est trouvé
	 */
	public BankAccount read(String accountNumber) {

		String sql =
				"SELECT * FROM bank_account WHERE AccountNumber = ?";

		try (
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(sql)
		) {

			ps.setString(1, accountNumber);

			try (ResultSet resultSet = ps.executeQuery()) {

				if (resultSet.next()) {

					return new BankAccount(
						resultSet.getString("AccountNumber"),
						resultSet.getString("Holder"),
						resultSet.getDouble("Balance")
					);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	// =========================
	// CREATE
	// =========================

	/**
	 * Ajoute un nouveau compte bancaire dans la base de données.
	 *
	 * @param bankAccount compte bancaire à ajouter 
	 */
	public void create(BankAccount bankAccount) {

		String sql =
				"INSERT INTO bank_account "
				+ "(AccountNumber, Holder, Balance) "
				+ "VALUES (?, ?, ?)";

		try (
			Connection connection = getConnection();
			PreparedStatement ps = connection.prepareStatement(sql)
		) {

			ps.setString(1, bankAccount.getAccountNumber());
			ps.setString(2, bankAccount.getHolder());
			ps.setDouble(3, bankAccount.getBalance());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
