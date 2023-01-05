package dbAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mod.ContactModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ContactDb {
    /**
     * getContacts gets a list populated with Contact and Appointment Objects
     * which are joined via contact identification number
     *
     * @return ObservableList populated with contact objects
     * @throws SQLException to handle SQL related errors
     */
    public static ObservableList<ContactModel> getContacts() throws SQLException {
        ObservableList<ContactModel> contacts = FXCollections.observableArrayList();

        String queryStatement = "SELECT * FROM contacts;";
        //AS c INNER JOIN appointments AS a ON c.Contact_ID = a.Contact_ID;";

        MakeQuery.setPreparedStatement(JDBC.getConnection(), queryStatement);
        PreparedStatement preparedStatement = MakeQuery.getPreparedStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            // Forward scroll resultSet
            while (resultSet.next()) {
                ContactModel newContact = new ContactModel(
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Email")
                );
                contacts.add(newContact);
            }
            return contacts;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * getByName gets the identification number associated with contact object's Name
     *
     * @param cName String object referencing Contact Name
     * @return Contact object
     * @throws SQLException to handle any SQL related exceptions
     */
    public static ContactModel getByName(String cName) throws SQLException {
        String queryStatement = "SELECT * FROM contacts WHERE Contact_Name=?";

        MakeQuery.setPreparedStatement(JDBC.getConnection(), queryStatement);
        PreparedStatement preparedStatement = MakeQuery.getPreparedStatement();

        preparedStatement.setString(1, cName);


        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            ;

            // uses a while loop to step through the result sets
            while (resultSet.next()) {
                ContactModel newContact = new ContactModel(
                        resultSet.getInt("Contact_ID"),
                        resultSet.getString("Contact_Name"),
                        resultSet.getString("Email")
                );

                return newContact;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }
}
