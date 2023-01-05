package dbAccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mod.CustomerModel;
import mod.DivModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CustomerDb{

    /**
     * getCustomers is the getter method for lists to be populated with customer objects.
     * @return ObservableList populated with customer objects
     * @throws SQLException to handle any SQL related errors
     */
    public static ObservableList<CustomerModel> getCustomers() throws SQLException {
        ObservableList<CustomerModel> customers = FXCollections.observableArrayList();

        String searchStatement = "SELECT * FROM customers AS c INNER JOIN first_level_divisions AS d ON c.Division_ID = d.Division_ID INNER JOIN countries AS co ON co.Country_ID=d.COUNTRY_ID;";

        MakeQuery.setPreparedStatement(JDBC.getConnection(), searchStatement);
        PreparedStatement preparedStatement = MakeQuery.getPreparedStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            // Forward scroll resultSet
            while (resultSet.next()) {

                CustomerModel newCustomer = new CustomerModel(
                        resultSet.getInt("Customer_ID"),
                        resultSet.getString("Customer_Name"),
                        resultSet.getString("Address"),
                        resultSet.getString("Postal_Code"),
                        resultSet.getString("Phone"),
                        resultSet.getString("Division"),
                        resultSet.getString("Country"),
                        resultSet.getInt("Division_ID")
                );
                customers.add(newCustomer);
            }
            return customers;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * addCustomer accepts all necessary values that populate a customer object, in its parameters
     * It uses there values to create a new customer object and adds the new objects information
     * to the customers table of the dataBase
     * @param name String object referencing the customer's name
     * @param address String object referencing the customer's physical address
     * @param postalCode String object referencing postal code of address
     * @param phone String object referencing customer's phone number
     * @param division String object referencing the division
     * @return returns true if the customer was successfully created else false.
     * @throws SQLException to handle any SQL related errors
     */
    public static boolean addCustomer(String name, String address, String postalCode, String phone, String division) throws SQLException {

        DivModel newDivision = DivDb.getId(division);

        String insertStatement = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";

        MakeQuery.setPreparedStatement(JDBC.getConnection(), insertStatement);
        PreparedStatement preparedStatement = MakeQuery.getPreparedStatement();

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, address);
        preparedStatement.setString(3, postalCode);
        preparedStatement.setString(4, phone);
        preparedStatement.setInt(5, newDivision.getDivId());

        try {
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Number of rows affected: " + preparedStatement.getUpdateCount());
            } else {
                System.out.println("No changes made.");
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * updateInfo takes a selected customer and repopulates the object with updated information
     * @param Id Int variable holding customer's identification number
     * @param name String object referencing customer's name.
     * @param address String object referencing customer Address
     * @param postalCode String object referencing customer Postal Code
     * @param phone String object referencing customer Phone Number
     * @param division String object referencing division
     * @return true if updated successfully else false
     * @throws SQLException to handle SQL related errors
     */
    public static boolean updateInfo(int Id, String name, String address, String postalCode, String phone, String division) throws SQLException {
        DivModel newDivision = DivDb.getId(division);

        String insertStatement = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=? WHERE Customer_ID=?";

        MakeQuery.setPreparedStatement(JDBC.getConnection(), insertStatement);
        PreparedStatement preparedStatement = MakeQuery.getPreparedStatement();

        preparedStatement.setString(1, name);
        preparedStatement.setString(2, address);
        preparedStatement.setString(3, postalCode);
        preparedStatement.setString(4, phone);
        preparedStatement.setInt(5, newDivision.getDivId());
        preparedStatement.setInt(6, Id);

        try {
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Number of rows affected: " + preparedStatement.getUpdateCount());
            } else {
                System.out.println("No rows changed.");
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * deleteCustomer removes a customer object from the customers database table.
     * @param id Int variable holding customer's identification number
     * @return true if successfully deleted else false
     * @throws SQLException to handle SQL related errors
     */
    public static boolean delete(int id) throws SQLException {
        String insertStatement = "DELETE from customers WHERE Customer_Id=?";

        MakeQuery.setPreparedStatement(JDBC.getConnection(), insertStatement);
        PreparedStatement preparedStatement = MakeQuery.getPreparedStatement();

        preparedStatement.setInt(1, id);

        try {
            preparedStatement.execute();
            if (preparedStatement.getUpdateCount() > 0) {
                System.out.println("Rows affected: " + preparedStatement.getUpdateCount());
            } else {
                System.out.println("No change");
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}

