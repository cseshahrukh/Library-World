package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class OracleConnect {
    private Connection connection;
    private static final String host = "127.0.0.1";
    private static final String dbname = "orcl";
    private static final String username = "library";
    private static final String password = "library";
    private static final String port = "1521";

    public OracleConnect() throws Exception {
        //System.out.println("Line 17");
        String url = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + dbname;
        //System.out.println("Line 19");
        this.connection = DriverManager.getConnection(url, username, password);
        //System.out.println("Line 21");
        System.out.println("Connected");
    }

    public void updateDB(String query) throws Exception {
        Statement statement = this.connection.createStatement();
        statement.executeUpdate(query);
    }

    public ResultSet searchDB(String query) throws Exception {
        Statement statement = this.connection.createStatement();
        return statement.executeQuery(query);
    }

    public void close() throws Exception {
        this.connection.close();
    }
}
