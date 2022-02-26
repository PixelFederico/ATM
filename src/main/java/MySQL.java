import java.math.BigDecimal;
import java.sql.*;

public class MySQL {

    private static Connection conn = null;
    private static Statement stmt;

    public static void init(){
        //Statement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            conn =
                    DriverManager.getConnection("jdbc:mysql://localhost:3306/atm","root",null);

            stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users ("
                    + "pin TEXT(255) NOT NULL,"
                    + "name TEXT(255) NOT NULL,"
                    + "bank DOUBLE,"
                    + "wallet DOUBLE)"
            );

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    public static void addUser(User user){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("INSERT INTO `users`(`pin`, `name`, `bank`, `wallet`) VALUES ('"+user.pin+"','"+user.name+"','"+user.bank+"','"+user.wallet+"')");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    public static boolean exist(String pin){
        try {
            ResultSet result;
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            String getid = "SELECT name FROM users WHERE pin = '"+pin+"'";
            result = stmt.executeQuery(getid);
            result.last();
            double count = result.getRow();

            if(count==1) return true;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return false;
    }
    public static User getUser(String pin){

        try {
            ResultSet nameresult = null;
            ResultSet bankresult = null;
            ResultSet walletresult = null;

            stmt = conn.createStatement();

            String namequery = "SELECT name FROM users WHERE pin = '"+pin+"'";
            nameresult = stmt.executeQuery(namequery);
            nameresult.next();
            String name = nameresult.getString("name");

            String bankquery = "SELECT bank FROM users WHERE pin = '"+pin+"'";
            bankresult = stmt.executeQuery(bankquery);
            bankresult.next();
            double bank = bankresult.getDouble("bank");

            String walletquery = "SELECT wallet FROM users WHERE pin = '"+pin+"'";
            walletresult = stmt.executeQuery(walletquery);
            walletresult.next();
            double wallet = walletresult.getDouble("wallet");

            return new User(name, wallet, bank, pin);

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return null;
    }
    public static void withdraw(User user, double withdraw){
        try {
            stmt = conn.createStatement();
            BigDecimal newwallet = BigDecimal.valueOf(user.wallet).add(BigDecimal.valueOf(withdraw));
            BigDecimal newbank = BigDecimal.valueOf(user.bank).subtract(BigDecimal.valueOf(withdraw));

            if(newbank.abs().equals(newbank)){
                stmt.executeUpdate("UPDATE users SET bank='"+newbank+"' WHERE pin = '"+user.pin+"'");
                stmt.executeUpdate("UPDATE users SET wallet='"+newwallet+"' WHERE pin = '"+user.pin+"'");
            }else{
                System.out.println("Impossibile ritirare, non hai abbastanza soldi in banca");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    public static void deposit(User user, double withdraw){
        try {
            stmt = conn.createStatement();
            BigDecimal newbank = BigDecimal.valueOf(user.bank).add(BigDecimal.valueOf(withdraw));
            BigDecimal newwallet = BigDecimal.valueOf(user.wallet).subtract(BigDecimal.valueOf(withdraw));

            if(newwallet.abs().equals(newwallet)) {
                stmt.executeUpdate("UPDATE users SET bank='" + newbank + "' WHERE pin = '" + user.pin + "'");
                stmt.executeUpdate("UPDATE users SET wallet='" + newwallet + "' WHERE pin = '" + user.pin + "'");
            }else{
                System.out.println("Impossibile depositare, non hai abbastanza soldi in tasca");
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    public static void deleteAccount(User user){
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM `users` WHERE pin='"+user.pin+"'");
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
    public static boolean pinExist(String pin){
        try {
            ResultSet result;
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            String getpin = "SELECT pin FROM users WHERE pin = '"+pin+"'";
            result = stmt.executeQuery(getpin);
            result.last();
            double count = result.getRow();

            if(count>0) return true;
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return false;
    }
}
