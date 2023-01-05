package dbAccess;


import mod.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDb {

    /**
     * validateCredentials checks to ensure the user's entered credentials are valid
     * @param username String object referencing user name entry
     * @param password String object referencing user password entry
     * @return true if valid else false
     * @throws SQLException to handle SQL related runtime errors
     */
    public static boolean validateCredentials(String username, String password) throws SQLException {
        String searchStatement = "SELECT * FROM users WHERE User_Name=? AND Password=?";

        MakeQuery.setPreparedStatement(JDBC.getConnection(), searchStatement);
        PreparedStatement preparedStatement = MakeQuery.getPreparedStatement();

        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            // Tester to print out that the DB is bing accessed & name & password were found
            return (resultSet.next());
        } catch (Exception e) {

            // Tester printing out that name and password didn't work
            System.out.println("entered catch statement! returning false");
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * getUsers gets an observable list populated with all the user objects
     * @return ObservableList populated with Users
     * @throws SQLException to handle SQL related runtime exceptions
     */
    public static ObservableList<UserModel> getUsers() throws SQLException {
        ObservableList<UserModel> users = FXCollections.observableArrayList();

        String searchStatement = "SELECT * FROM users;";

        MakeQuery.setPreparedStatement(JDBC.getConnection(), searchStatement);
        PreparedStatement preparedStatement = MakeQuery.getPreparedStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            ;

            while (resultSet.next()) {
                UserModel newUser = new UserModel(
                        resultSet.getInt("User_ID"),
                        resultSet.getString("User_Name"),
                        resultSet.getString("Password")
                );
                users.add(newUser);
            }
            return users;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
