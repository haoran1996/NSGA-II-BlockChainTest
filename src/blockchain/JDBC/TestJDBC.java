package blockchain.JDBC;

import java.sql.*;

public class TestJDBC {
    //mysql驱动包名
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    //数据库连接地址
    private static final String URL = "jdbc:mysql://localhost:3306/test?serverTimezone=GMT%2B8";
    //用户名
    private static final String USER_NAME = "root";
    //密码
    private static final String PASSWORD = "12345";
    public static void main(String[] args){
        Connection connection = null;
        try {
            //加载mysql的驱动类
            Class.forName(DRIVER_NAME);
            //获取数据库连接
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            //mysql查询语句
            String sql = "SELECT name FROM demo";
            PreparedStatement prst = connection.prepareStatement(sql);
            //结果集
            ResultSet rs = prst.executeQuery();
            while (rs.next()) {
                System.out.println("用户名:" + rs.getString("name"));
            }
            rs.close();
            prst.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
