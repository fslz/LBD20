package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnector {


    private static String host;
    private static String sid;
    private static String port;
    private static String user;
    private static String password;


    public Connection getConnection(String host, String sid, String port, String user, String password){

        this.host = host;
        this.sid = sid;
        this.port = port;
        this.user = user;
        this.password = password;

        String url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;
        Connection connection;

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(url, user, password);
        }
        catch(SQLException e){
            // alert TODO
            return null;
        }
        catch(ClassNotFoundException e){
            // alert TODO
            return null;
        }

        return connection;

    }


    public Connection getConnection() {

        String url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;
        Connection connection;

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(url, user, password);
        }
        catch(SQLException e){
            // alert TODO
            return null;
        }
        catch(ClassNotFoundException e){
            // alert TODO
            return null;
        }

        return connection;

    }

}
