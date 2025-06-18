// Required Libraries: Add MySQL connector jar to your project.

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.sql.*;
import java.util.Base64;
import java.util.Scanner;

public class BankingATM {

    // AES Utility Class
    static class AESUtil {
        private final SecretKey secretKey;

        public AESUtil() throws Exception {
            this.secretKey = generateKey();
        }

        private SecretKey generateKey() throws Exception {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            return keyGen.generateKey();
        }

        public String encrypt(String data) throws Exception {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        }

        public String decrypt(String encryptedData) throws Exception {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
            return new String(decryptedData);
        }
    }

    // Database Utility Class
    static class DBUtil {
        private static final String URL = "jdbc:mysql://127.0.0.1:3306/?user=root";
        private static final String USER = "root";
        private static final String PASSWORD = "password";

        static Connection getConnection() throws SQLException {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }

        static void initializeDB() {
            try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
                String createTable = "CREATE TABLE IF NOT EXISTS accounts (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "username VARCHAR(50) NOT NULL," +
                        "password VARCHAR(255) NOT NULL," +
                        "balance VARCHAR(255) NOT NULL" +
                        ")";
                stmt.execute(createTable);
            } catch (SQLException e) {
                System.out.println("Database initialization error: " + e.getMessage());
            }
        }
    }

    // Main Application Class
    public static void main(String[] args) {
        DBUtil.initializeDB();
        try (Scanner scanner = new Scanner(System.in)) {
            AESUtil aesUtil = new AESUtil();

            while (true) {
                System.out.println("\nWelcome to ATM Banking System");
                System.out.println("1. Create Account");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                if (choice == 1) {
                    createAccount(scanner, aesUtil);
                } else if (choice == 2) {
                    login(scanner, aesUtil);
                } else if (choice == 3) {
                    System.out.println("Thank you for using our service!");
                    break;
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void createAccount(Scanner scanner, AESUtil aesUtil) throws Exception {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        String encryptedPassword = aesUtil.encrypt(password);
        String encryptedBalance = aesUtil.encrypt("0"); // Default balance is 0

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO accounts (username, password, balance) VALUES (?, ?, ?)")) {
            pstmt.setString(1, username);
            pstmt.setString(2, encryptedPassword);
            pstmt.setString(3, encryptedBalance);
            pstmt.executeUpdate();
            System.out.println("Account created successfully!");
        } catch (SQLException e) {
            System.out.println("Error creating account: " + e.getMessage());
        }
    }

    private static void login(Scanner scanner, AESUtil aesUtil) throws Exception {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM accounts WHERE username = ?")) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String encryptedPassword = rs.getString("password");
                if (aesUtil.decrypt(encryptedPassword).equals(password)) {
                    String encryptedBalance = rs.getString("balance");
                    System.out.println("Login successful!");
                    manageAccount(scanner, aesUtil, conn, rs.getInt("id"), encryptedBalance);
                } else {
                    System.out.println("Incorrect password.");
                }
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }

    private static void manageAccount(Scanner scanner, AESUtil aesUtil, Connection conn, int accountId, String encryptedBalance) throws Exception {
        while (true) {
            System.out.println("\nAccount Menu");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("Current Balance: " + aesUtil.decrypt(encryptedBalance));
            } else if (choice == 2) {
                System.out.print("Enter amount to deposit: ");
                int amount = scanner.nextInt();
                scanner.nextLine();
                encryptedBalance = aesUtil.encrypt(String.valueOf(Integer.parseInt(aesUtil.decrypt(encryptedBalance)) + amount));
                updateBalance(conn, accountId, encryptedBalance);
            } else if (choice == 3) {
                System.out.print("Enter amount to withdraw: ");
                int amount = scanner.nextInt();
                scanner.nextLine();
                int currentBalance = Integer.parseInt(aesUtil.decrypt(encryptedBalance));
                if (currentBalance >= amount) {
                    encryptedBalance = aesUtil.encrypt(String.valueOf(currentBalance - amount));
                    updateBalance(conn, accountId, encryptedBalance);
                } else {
                    System.out.println("Insufficient balance.");
                }
            } else if (choice == 4) {
                System.out.println("Logged out.");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void updateBalance(Connection conn, int accountId, String encryptedBalance) {
        try (PreparedStatement pstmt = conn.prepareStatement("UPDATE accounts SET balance = ? WHERE id = ?")) {
            pstmt.setString(1, encryptedBalance);
            pstmt.setInt(2, accountId);
            pstmt.executeUpdate();
            System.out.println("Balance updated successfully.");
        } catch (SQLException e) {
            System.out.println("Error updating balance: " + e.getMessage());
        }
    }
}

