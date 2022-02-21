package startup;

import java.sql.*;

public class DbConn {
    private Connection connection;
    private static final String host = "127.0.0.1";
    private static final String dbname = "orcl";
    private static final String username = "library";
    private static final String password = "library";
    private static final String port = "1521";

    public DbConn() throws Exception {
        String url = "jdbc:oracle:thin:@//" + host + ":" + port + "/" + dbname;
        this.connection = DriverManager.getConnection(url, username, password);
        System.out.println("Connected");
    }

    public int updateDB(String query) throws Exception {
        Statement statment = this.connection.createStatement();
        return statment.executeUpdate(query);
    }

    public ResultSet searchDB(String query) throws Exception {
        Statement statement = this.connection.createStatement();
        return statement.executeQuery(query);
    }

    public void close() throws Exception {
        this.connection.close();
    }
}

