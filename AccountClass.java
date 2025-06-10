public class AccountClass{
	private int accountNum;
	private String EncryptedAccHolderName;
	private String EncryptedBal;
	private String EncryptedTranHistory;
	private String HashPin;

	public AccountClass (int accountNum, String EncryptedAccHolderName, String EncryptedBal, String EncryptedTranHistory, String HashPin){
		this.accountNum=accountNum;
		this.EncryptedAccHolderName=EncryptedAccHolderName;
		this.EncryptedBal=EncryptedBal;
        this.EncryptedTranHistory=EncryptedTranHistory;
		this.HashPin=HashPin;
	}

	private void encryptAccHolName(String name){
		 // TODO: implement encryption logic
	}

	private void encryptBal(double balance){
		 // TODO: implement encryption logic
	}

	private void encryptTranHisto(String transaction){
		 // TODO: implement encryption logic
	}
	
	public String decryptAccHolName(){
		 // TODO: implement decryption logic
        return null;  // placeholder
	}
	
	public void addTransaction(String transaction){
        // TODO: add encrypted transaction logic
	}

	public double decryptBal(){
		 // TODO: implement decryption logic
        return 0.0;  // placeholder
	}

	public String decryptTranHisto(){
		 // TODO: implement decryption logic
        return null;  // placeholder
	}
}	
