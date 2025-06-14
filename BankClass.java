import java.util.ArrayList;

public class BankClass{
	private ArrayList<AccountClass> accounts = new ArrayList<>();
	private String secretKey;

	
	public BankClass (String secretKey){
		this.secretKey=secretKey;
	}


	public void getTranHisto(){
		//TODO:print transaction history
	}

	public void createAccount(String name, String pin){
		//TODO: Creates a new account, no return value needed
	}

	public boolean authenticate(int accountNum, String pin){
		//TODO: Returns true if login is successful, false otherwise
        return false;
	}

	public boolean deposit(int accountNum, double amount){
	       //TODO: Returns succes/failure of no such account
           return false;
	}
	
	public boolean withdraw(int accountNum, double amount){
		//TODO: returns sucess/failue if insufficient funds or accounts not found
        return false;
	}

	public double checkBal(int accountNum){
		//TODO: Returns decrypted account balance
         return 0.0;	
}

	public void ListAccounts(){
		//TODO: Admin features to display all accounts
	}

	public void addTransaction(int accountNum, String transaction){
		//TODO: Adds transactions record to account's history
	}

	private AccountClass FindAcc(int accountNum){
		//TODO: used  unternally too locate an account object
        return null;
	}
}	

