
import java.sql.*;

public class Conn {
    
    Connection c;
    Statement s;

    public Conn () {
        try {
        	Class.forName("oracle.jdbc.driver.OracleDriver");
        	
        	String url = "jdbc:oracle:thin:@192.168.1.33:1521/XEPDB1";
            String username = "system";
            String password = "@oracle#1234";
            
            c = DriverManager.getConnection(url, username, password);
            s = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Connection getConnection() {
        return c;
    }

}