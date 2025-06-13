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
		Scanner scanner = new Scanner(System.in);

		System.out.println("enter username");
		String username= scanner.nextLine();
	
		System.out.println("enter PIN");
		String pin = scanner.nextLine();

		if(pin.length() != 4){
			System.out.println("Invalid input: the pin length = 4");
			return;
		}
		else if(username.isEmpty()){
			System.out.println("Invalid input: Username can't be empty");
			return;
		}
		bank.createAccount(username, pin);
		
		System.out.println("Account Created Successfully!");
		}

	public void login(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Account Number");
		int accountNum = scanner.nextInt();
		scanner.nextLine();

		System.out.println("Enter PIN");
		String pin= scanner.nextLine();

		if(bank.authenticate(accountNum, pin)){
			System.out.println("Logged In Successfully!");
			handleLoggedInUser(accountNum);
		}
		else{
			System.out.println("Log in failed retry! verify your inputs");
		}


	}

	public void handleLoggedInUser(AccountClass account){
		//TODO: Manage logged in operations
	}

	public void listAcc(){
		//TODO: Shows admin-only account list
	}

	public void exitApp(){
		System.out.println("GoodBye! Have a Good Dayy!!");
		System.exit(0);
	}
}
