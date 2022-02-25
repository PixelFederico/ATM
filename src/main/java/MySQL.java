import java.sql.*;

public class MySQL {

    public static Connection conn = null;
    private static Statement stmt;

    public static void init(){
        Statement stmt = null;
        ResultSet rs = null;
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

            if(count>0) return true;
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
}
