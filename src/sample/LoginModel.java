package sample;
import java.sql.*;
public class LoginModel {
    Connection conection;

    public LoginModel(){

        conection = SQLiteConnection.connector();
        if (conection == null) System.exit(1);
    }

    public boolean isDbConnected(){

        try {
            return conection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}
