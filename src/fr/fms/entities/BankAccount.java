package fr.fms.entities;

import fr.fms.exception.InsufficientBalanceException;

/**
 * Représente un compte bancaire.
 * 
 * Un compte possède un numéro de compte, un titulaire et un solde.
 */
public class BankAccount {
    // =========================
    // ATTRIBUTS
    // =========================
	
	private String accountNumber;
	private String holder;
	private double balance;
	
    // =========================
    // CONSTRUCTEUR
    // =========================
	
	/** Crée un compte bancaire avec un numéro, un titulaire et un solde.
	 * 
	 * @param accountNumber numéro du compte bancaire
	 * @param holder titulaire du compte bancaire
	 * @param balance solde du compte bancaire
	 */
	public BankAccount(String accountNumber, String holder, double balance) {
		this.accountNumber = accountNumber;
		this.holder = holder;
		this.balance = balance;
	}
	
    // =========================
    // ACCESSEURS
    // =========================
	
	/** Récupère le numéro du compte bancaire
	 * @return the accountNumber numéro du compte bancaire
	 */
	public String getAccountNumber() {
		return accountNumber;
	}
	/** Enregistre le numéro du compte bancaire
	 * @param accountNumber numéro du compte bancaire
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	/** Récupère le titulaire du compte bancaire
	 * @return the holder titulaire du compte bancaire
	 */
	public String getHolder() {
		return holder;
	}
	/** Enregistre le titulaire du compte bancaire
	 * @param holder titulaire du compte bancaire
	 */
	public void setHolder(String holder) {
		this.holder = holder;
	}
	/** Récupère le solde du compte bancaire
	 * @return the balance solde du compte bancaire
	 */
	public double getBalance() {
		return balance;
	}
	/** Enregistre le solde du compte bancaire
	 * @param balance solde du compte bancaire
	 */
	public void setBalance(double balance) {
		this.balance = balance;
	}

	// =========================
    // MÉTHODES
    // =========================
	
	/** Retourne les informations du compte bancaire sous forme de chaine.
	 * @return représentation textuelle du compte bancaire
	 */
	@Override
	public String toString() {
		return "BankAccount [accountNumber=" + accountNumber + ", holder=" + holder + ", balance=" + balance + "]";
	}
	
	/**
	 * Dépose un montant sur le compte bancaire.
	 *
	 * @param amount montant à déposer
	 */
	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException ("Le montant doit être supérieur à 0");
		}
		
		balance += amount;
	}
	
	/**
	 * Retire un montant sur le compte bancaire.
	 *
	 * @param amount montant à retirer
	 * @throws InsufficientBalanceException si le solde est insuffisant
	 */
	public void withdraw(double amount) throws InsufficientBalanceException {
		if (amount <= 0) {
			throw new IllegalArgumentException ("Le montant doit être supérieur à 0");
		}
		if(amount > balance) {
			throw new InsufficientBalanceException("Le solde est insuffisant pour effectuer le retrait.");
		}
		
		balance -= amount;
	}
	
	/**
	 * Effectue un virement vers un autre compte bancaire.
	 *
	 * @param destination compte bancaire sur lequel transférer l'argent
	 * @param amount montant à virer
	 * @throws InsufficientBalanceException 
	 */	public void transfer(BankAccount destination, double amount) throws InsufficientBalanceException {
		 // On retire l'argent du compte
		 withdraw(amount);
		 // Puis on le dépose sur un compte destinataire
		 destination.deposit(amount);
	}
	
}
