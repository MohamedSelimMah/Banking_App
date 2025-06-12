import java.util.Scanner;

public class App{
	private BankClass bank;

	public App(BankClass bank){
		this.bank=bank;
	}

	public void displayMenu(){
		Scanner in = new Scanner(System.in);
		while (true){
			System.out.println("\n-------Welcome to the bank!------");
			System.out.println("Menu Options:");
			System.out.println("1. Create Account");
			System.out.println("2. Login");
			System.out.println("3. List Accounts(Admin only)");
			System.out.println("4. Exit");

			String choice = in.nextLine().trim();

			switch(choice){
				case "1":
					createAccount();
					break;
				case "2":
					login();
					break;
				case "3":
					listAcc();
					break;
				case "4":
					exitApp();
					break;
				default:
					System.out.println("invalid option try again!");}
		}

	}

	public void createAccount(){
		//TODO: HNAdels user input for account creation
	}

	public void login(){
		//TODO: Authenticates user and directs to account creation
	}

	public void handleLoggedInUser(AccountClass account){
		//TODO: Manages logged-in user operations
	}

	public void listAcc(){
		//TODO: Shows admin-only account list
	}

	public void exitApp(){
		System.out.println("GoodBye! Have a Good Dayy!!");
		System.exit(0);
	}
}
