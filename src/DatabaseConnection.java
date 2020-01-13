import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    public DatabaseConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            con = DriverManager.getConnection("jdbc:mysql://remotemysql.com:3306/YFMcmGkWeX", "YFMcmGkWeX", "Aqbd2K6iT5");
            st = con.createStatement();
            System.out.println("\n Connected successfully \n");
        } catch (SQLException e) {
            e.printStackTrace();
            con = null;
        }
    }

    public ResultSet runSQL(String sqlQuery) {
        try {
            if(st.execute(sqlQuery))
                rs = st.getResultSet();
        } catch (SQLException e) {
            System.out.println(sqlQuery);
            e.printStackTrace();
        }

        return rs;
    }

    public void close() {
        try {
            if (st != null) {
                st.close();
            }

            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
