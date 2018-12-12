package blockchain.JDBC;

import com.mysql.cj.protocol.Resultset;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class JDBCutil {

    private static Connection connection;

    public static Connection gen_connct(String dbname, String username, String password) {
        if (connection == null) {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/" + dbname + "?serverTimezone=GMT%2B8";
            try {
                Class.forName(driver);
                connection = (Connection) DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("成功连接数据库！");
        }
        return connection;
    }

    public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
