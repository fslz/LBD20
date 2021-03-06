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


    /*

    // Create a OracleDataSource instance explicitly
    OracleDataSource ods = new OracleDataSource();

    // Set the user name, password, driver type and network protocol
    ods.setUser("scott");
    ods.setPassword("tiger");
    ods.setDriverType("oci8");
    ods.setNetworkProtocol("ipc");

    // Retrieve a connection
    Connection conn = ods.getConnection();

    */


    public Connection getConnection(String host, String sid, String port, String user, String password){

        DbConnector.host = host;
        DbConnector.sid = sid;
        DbConnector.port = port;
        DbConnector.user = user;
        DbConnector.password = password;

        String url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;
        Connection connection;

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection(url, user, password);
        }
        catch(SQLException | ClassNotFoundException e){
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
        catch(SQLException | ClassNotFoundException e){
            // alert TODO
            return null;
        }

        return connection;

    }

}
